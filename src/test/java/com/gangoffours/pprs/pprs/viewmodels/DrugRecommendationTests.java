package com.gangoffours.pprs.pprs.viewmodels;

import java.util.ArrayList;

import com.gangoffours.pprs.pprs.common.Triplet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

import org.springframework.data.util.Pair;

@RunWith(SpringRunner.class)
public class DrugRecommendationTests {

    @Test
    public void WhenNotAllergic_ShouldBeFirst() {
        // Arrange
        ArrayList<DrugRecommendation> data = new ArrayList<DrugRecommendation>() {
            private static final long serialVersionUID = 1L;
            {
            add(new DrugRecommendation() {{
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>() {
                    private static final long serialVersionUID = 1L;
                    {
                    add(Pair.of(new DrugModel("DB00001","Test","Class"), 0.5));
                }});
                setSimilarity(1.0);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel,DrugInteractionType,Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Minor, 1.0
                        ));
                    }}
                );
            }});
            add(new DrugRecommendation() {{
                setDrugName("first");
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>());
                setSimilarity(0.5);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel,DrugInteractionType,Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Major, 0.5
                        ));
                    }}
                );
            }});
        }};

        // Act
        DrugRecommendation actual = data.stream().sorted().findFirst().get();

        // Assert
        Assert.assertEquals(
            "first",
            actual.getDrugName()
        );
    }
    
    @Test
    public void WhenBetterWorstInteraction_ShouldBeFirst() {
        // Arrange
        ArrayList<DrugRecommendation> data = new ArrayList<DrugRecommendation>() {
            private static final long serialVersionUID = 1L;
            {
            add(new DrugRecommendation() {{
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>());
                setSimilarity(1.0);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel, DrugInteractionType, Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Major, 1.0
                        ));
                    }}
                );
            }});
            add(new DrugRecommendation() {{
                setDrugName("first");
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>());
                setSimilarity(0.5);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel, DrugInteractionType, Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Minor, 0.5
                        ));
                    }}
                );
            }});
        }};

        // Act
        DrugRecommendation actual = data.stream().sorted().findFirst().get();

        // Assert
        Assert.assertEquals(
            "first",
            actual.getDrugName()
        );
    }

    @Test
    public void WhenMoreSimilar_ShouldBeFirst() {
        // Arrange
        ArrayList<DrugRecommendation> data = new ArrayList<DrugRecommendation>() {
            private static final long serialVersionUID = 1L;
            {
            add(new DrugRecommendation() {{
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>());
                setSimilarity(0.5);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel, DrugInteractionType, Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Minor, 1.0
                        ));
                    }}
                );
            }});
            add(new DrugRecommendation() {{
                setDrugName("first");
                setAllergicDrugs(new ArrayList<Pair<DrugModel, Double>>());
                setSimilarity(1.0);
                setInteractiveDrugs(
                    new ArrayList<Triplet<DrugModel, DrugInteractionType, Double>>() {
                        private static final long serialVersionUID = 1L;
                        {
                        add(new Triplet<DrugModel, DrugInteractionType, Double>(
                            new DrugModel("DB00001","Test","Class"), DrugInteractionType.Minor, 0.5
                        ));
                    }}
                );
            }});
        }};

        // Act
        DrugRecommendation actual = data.stream().sorted().findFirst().get();

        // Assert
        Assert.assertEquals(
            "first",
            actual.getDrugName()
        );
    }

}