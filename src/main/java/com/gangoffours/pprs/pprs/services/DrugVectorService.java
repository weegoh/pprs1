package com.gangoffours.pprs.pprs.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gangoffours.pprs.pprs.common.Lazy;
import com.gangoffours.pprs.pprs.models.DrugScalar;
import com.gangoffours.pprs.pprs.repositories.IDrugScalarRepository;
import com.google.common.collect.Streams;

import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class DrugVectorService implements IDrugVectorService {
    private final Lazy<Map<String, Map<String, Double>>> _vectors; // caches all drug scalars as vectors
    private final HashMap<Pair<String, String>, Double> _similarity; // cache all drug similarities

    public DrugVectorService(IDrugScalarRepository drugScalarRepository) {
        // lazy init vectors
        _vectors = new Lazy<Map<String, Map<String, Double>>>(() -> InitVectors(drugScalarRepository));

        // init simlarity cache
        _similarity = new HashMap<Pair<String, String>, Double>();
    }

    private Map<String, Map<String, Double>> InitVectors(IDrugScalarRepository drugScalarRepository) {
        // buld vector cache from database scalars
        return drugScalarRepository
            .findAll()
            .stream()
            .collect(
                Collectors.groupingBy(d -> d.getId().getDrugBankId(),
                Collectors.toMap(d -> d.getId().getDrugName(), DrugScalar::getScalar))
            );
    }

    public Double GetSimilarity(
        String drugBankId1,
        String drugBankId2
    ) {
        Pair<String, String> pair = Pair.of(drugBankId1, drugBankId2);
        Pair<String, String> reversePair = Pair.of(drugBankId2, drugBankId1);

        // if no cache create cached value
        // if one pair does not exist, the other shouldn't either,
        // but seems like a harmless check since it doesn't really affect computer or
        // space complexity
        if (!_similarity.containsKey(pair) || !_similarity.containsKey(reversePair)) {
            if(_vectors.Value().containsKey(drugBankId1) && _vectors.Value().containsKey(drugBankId2))
            {
                Double similarity = computeSimRatio(
                    _vectors.Value().get(drugBankId1),
                    _vectors.Value().get(drugBankId2)
                );

                // store similarity for the pair and the rerverse pair
                _similarity.put(pair, similarity);
                _similarity.put(reversePair, similarity);
            }
            else {
                _similarity.put(pair, 0.0);
                _similarity.put(reversePair, 0.0);
            }
        }

        // respond with stored similarity
        return _similarity.get(pair);
    }

    private Double computeSimRatio(
        Map<String, Double> uVector,
        Map<String, Double> vVector
    ) {
        // find smallest map to iterate over, to reduce compute time
        Map<String, Double> uMap = uVector.size() < vVector.size() ? uVector : vVector;
        Map<String, Double> vMap = uVector.size() >= vVector.size() ? uVector : vVector;

        // get u and v paired scalars as vector for similarity
        Pair<ArrayList<Double>, ArrayList<Double>> uv = uMap.entrySet().stream()
                // excluded
                .filter(kv -> vMap.containsKey(kv.getKey()) && vMap.get(kv.getKey()) > 0)
                // map matching vector scalars
                .map(kv -> Pair.of(kv.getValue(), vMap.get(kv.getKey())))
                // convert list of pairs to pair of lists to be used with dot product
                .reduce(Pair.of(new ArrayList<Double>(), new ArrayList<Double>()), (tl, t) -> {
                    tl.getFirst().add(t.getFirst());
                    tl.getSecond().add(t.getSecond());

                    return tl;
                }, (tl1, tl2) -> tl1);

        // caculate similatity
        return computeSimRatio(
            uv.getFirst(),
            uv.getSecond()
        );
    }

    private Double computeSimRatio(
        Collection<Double> u,
        Collection<Double> v
    ) {
        return
            dotProduct(u, v) /
            (Math.sqrt(dotProduct(u, u)) * Math.sqrt(dotProduct(v, v)));
    }

    private Double dotProduct(
        Collection<Double> u,
        Collection<Double> v
    ) {
        Optional<Double> r = Streams
            .zip(u.stream(), v.stream(), (a, b) -> a * b)
            .reduce((a, b) -> a + b);
        
        return r.isPresent() ? r.get() : 0.0;
    }

    public Map<String, Map<String, Double>> GetVectors() {
        return _vectors.Value();
    }

    public Future<Map<String, Map<String, Double>>> GetVectorsAsync() {
        CompletableFuture<Map<String, Map<String, Double>>> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            completableFuture.complete(_vectors.Value());
        });

        return completableFuture;
    }

    @Override
    public Stream<Pair<String, Double>> GetSimilarDrugs(String drugBankId) {
        return _vectors
            .Value()
            .keySet()
            .stream()
            .map(s -> Pair.of(s, GetSimilarity(drugBankId, s)));
    }
}