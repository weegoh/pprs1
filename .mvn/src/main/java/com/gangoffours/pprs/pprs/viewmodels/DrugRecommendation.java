package com.gangoffours.pprs.pprs.viewmodels;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalDouble;

import com.gangoffours.pprs.pprs.common.Triplet;

import org.springframework.data.util.Pair;

import lombok.Data;

@Data
public class DrugRecommendation implements Comparable<DrugRecommendation> {
    
    private String drugBankId;
    private String drugName;
    private Double similarity;
    private Collection<Pair<DrugModel, Double>> allergicDrugs;
    private Collection<Triplet<DrugModel, DrugInteractionType, Double>> interactiveDrugs;
    private Collection<Pair<DrugModel,Double>> currentDrugSimilarity;

    public Boolean getIsAllergic() {
        return !(allergicDrugs == null || allergicDrugs.isEmpty());
    }

    public DrugInteractionType getWorstInteraction() {

        if (interactiveDrugs == null || interactiveDrugs.isEmpty()) return DrugInteractionType.None;

        Optional<DrugInteractionType> maxInteraction = interactiveDrugs
            .stream()
            .map(a -> a.getSecond())
            .max((a,b) -> a.getValue() - b.getValue());

        return maxInteraction.isPresent()
            ? maxInteraction.get()
            : DrugInteractionType.None;
    }
    public Double getAverageCurrentSimilarity() {

        if (currentDrugSimilarity == null || currentDrugSimilarity.isEmpty()) return 0.0;

        OptionalDouble average = currentDrugSimilarity
            .stream()
            .mapToDouble(Pair::getSecond)
            .average();

        return average.isPresent()
            ? average.getAsDouble()
            : 0;
    }
    
    @Override
    public int compareTo(DrugRecommendation di) {
        Integer lhsGtRhs =
            (this.getIsAllergic() && !di.getIsAllergic() ? 1 << 3 : 0) |
            (this.getWorstInteraction().getValue() > di.getWorstInteraction().getValue() ? 1 << 2 : 0) |
            (this.getSimilarity() < di.getSimilarity() ? 1 << 1 : 0) |
            (this.getAverageCurrentSimilarity() < di.getAverageCurrentSimilarity() ? 1 << 0 : 0);
        Integer rhsGtLhs =
            (di.getIsAllergic() && !this.getIsAllergic() ? 1 << 3 : 0) |
            (di.getWorstInteraction().getValue() > this.getWorstInteraction().getValue() ? 1 << 2 : 0) |
            (di.getSimilarity() < this.getSimilarity() ? 1 << 1 : 0) |
            (di.getAverageCurrentSimilarity() < this.getAverageCurrentSimilarity() ? 1 << 0 : 0);

        return lhsGtRhs - rhsGtLhs;
    }
}