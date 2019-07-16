package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.gangoffours.pprs.pprs.common.Lazy;
import com.gangoffours.pprs.pprs.common.Triplet;
import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.repositories.IDrugInteractionRepository;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;
import com.gangoffours.pprs.pprs.viewmodels.DrugInteractionType;
import com.gangoffours.pprs.pprs.viewmodels.DrugModel;

import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "singleton")
public class DrugInteractivityService implements IDrugInteractivityService {

    private final IDrugRepository _drugRepository;
    private final Lazy<Collection<Triplet<String,String,Integer>>> _interactions;
    private final IDrugVectorService _drugVectorService;

    public DrugInteractivityService(
        IDrugRepository drugRepository,
        IDrugInteractionRepository drugInteractionRepository,
        IDrugVectorService drugVectorService
    ) {
        _drugRepository = drugRepository;
        _interactions = new Lazy<Collection<Triplet<String,String,Integer>>>(
            () -> InitInteractions(drugInteractionRepository)
        );
        _drugVectorService = drugVectorService;
    }

    public Collection<Triplet<DrugModel, DrugInteractionType, Double>> GetInteractiveDrugs(
        String drugBankId,
        Set<String> currentDrugBankIds
    ) {
        return _interactions
            .Value()
            .stream()
            .filter(di -> drugBankId.equals(di.getFirst()) || drugBankId.equals(di.getSecond()))
            .map(di -> GetPair(di, drugBankId))
            .collect(Collectors.toMap(a -> a.getFirst(), a -> a.getSecond(), Math::max))
            .entrySet()
            .stream()
            .filter(e -> currentDrugBankIds.contains(e.getKey()))
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .map(e -> 
                new Triplet<DrugModel, DrugInteractionType, Double>(
                    convertDrugModel(_drugRepository.findByDrugbankid(e.getKey())),
                    DrugInteractionType.valueOf(e.getValue()),
                    _drugVectorService.GetSimilarity(drugBankId, e.getKey())
            ))
            .collect(Collectors.toList());
    }

    public Future<Collection<Triplet<String,String,Integer>>> GetInteractionsAsync() {
        CompletableFuture<Collection<Triplet<String,String,Integer>>> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            completableFuture.complete(_interactions.Value());
        });

        return completableFuture;
    }

    private Collection<Triplet<String,String,Integer>> InitInteractions(
        IDrugInteractionRepository drugInteractionRepository
    ) {
        return drugInteractionRepository
            .findAll()
            .stream()
            .map(di -> new Triplet<String,String,Integer>(
                di.getDrugbankida(),
                di.getDrugbankidb(),
                di.getLevel()
            ))
            .collect(Collectors.toList());
    }

    private Pair<String, Integer> GetPair(
        Triplet<String,String,Integer> di,
        String drugBankId
    ) {
        return Pair.of(
            drugBankId.equals(di.getFirst())
                    ? di.getSecond()
                    : drugBankId,
            di.getThird()
        );
    }

    private DrugModel convertDrugModel(Drug drug) {
        return new DrugModel(
            drug.getDrugbankid(),
            drug.getName(),
            drug.getDrugclass()
        );
    }
}