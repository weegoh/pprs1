package com.gangoffours.pprs.pprs.services;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;

public interface IDrugVectorService {
    Map<String, Map<String, Double>> GetVectors();
    Double GetSimilarity(String drugBankId1, String drugBankId2);
    Stream<Pair<String,Double>> GetSimilarDrugs(String drugBankId);
    Future<Map<String, Map<String, Double>>> GetVectorsAsync();
}