package com.gangoffours.pprs.pprs.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gangoffours.pprs.pprs.models.DrugScalar;
import com.gangoffours.pprs.pprs.models.DrugScalarKey;
import com.gangoffours.pprs.pprs.repositories.IDrugScalarRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DrugVectorServiceTests {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private IDrugScalarRepository drugScalarRepository;

    @Test
    public void WhenGetSimilarity_ShouldBeExpected() throws Exception {
        // Arrange
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("1", "jump"), 0.1));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("1", "over"), 0.4));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("1", "moon"), 0.2));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("1", "red"), 0.8));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("2", "red"), 0.3));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("2", "dog"), 0.1));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("2", "walk"), 0.4));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("2", "over"), 0.2));
        testEntityManager.persist(new DrugScalar(new DrugScalarKey("2", "river"), 0.8));

        DrugVectorService drugVectorService = new DrugVectorService(drugScalarRepository);
        ProtoType p = new ProtoType();

        Map<String, Double> v1 = drugVectorService.GetVectors().get("1");
        Map<String, Double> v2 = drugVectorService.GetVectors().get("2");

        // Act
        Double actual = drugVectorService.GetSimilarity("1", "2");
        Double expected = p.computeSimRatio(v1, v2);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    private class ProtoType {

        /** The arr 1. */
        ArrayList<Double> arr1 = new ArrayList<Double>();

        /** The arr 2. */
        ArrayList<Double> arr2 = new ArrayList<Double>();

        /** The a 1. */
        ArrayList<String> a1 = new ArrayList<String>();

        /**
         * Compute similarity ratio.
         *
         * @param tf1 is the hashmap containing keywords and values of drug 1
         * @param tf2 is the hashmap containing keywords and values of drug 2
         * @return the similarity ratio between drug 1 and drug 2
         */
        public double computeSimRatio(Map<String, Double> tf1, Map<String, Double> tf2) {

            /*
             * tfidf1.put("cow", 0.3); these are for my own testing to convince myself that
             * the algorithm is working
             *
             *
             *
             * tfidf1.put("jump", 0.1); tfidf1.put("over", 0.4); tfidf1.put("moon", 0.2);
             * tfidf1.put("red", 0.8);
             * 
             * tfidf2.put("red", 0.3); tfidf2.put("dog", 0.1); tfidf2.put("walk", 0.4);
             * tfidf2.put("over", 0.2); tfidf2.put("river", 0.8);
             */

            double v = 0;
            String k;
            Integer i = 0;
            arr1.clear();
            arr2.clear();
            Set<Entry<String, Double>> set1 = tf1.entrySet();

            Iterator<Entry<String, Double>> iterator1 = set1.iterator();

            while (iterator1.hasNext()) {

                @SuppressWarnings("rawtypes")
                Map.Entry mentry1 = (Map.Entry) iterator1.next();

                if (tf2.containsKey(mentry1.getKey())) {
                    k = (String) mentry1.getKey();
                    v = tf2.get(k);
                    if (v <= 0)
                        continue;
                    // System.out.printf("f2-%s:%.3f\n",k,v);

                    arr1.add((Double) mentry1.getValue());
                    arr2.add(v);
                    i++;
                    a1.add((String) mentry1.getKey());

                }

            } // end while

            double top = dotProduct(arr1, arr2);
            double bottom = Math.sqrt(dotProduct(arr1, arr1)) * Math.sqrt(dotProduct(arr2, arr2));
            
            v = top / bottom;

            return v;
        }

        /**
         * Dot product.
         *
         * @param a the list of numbers belonging to the first drug in the drug-pair
         * @param b the list of numbers belonging to the second drug in the drug-pair
         * @return the dot product of list a and list b
         */
        public double dotProduct(List<Double> a, List<Double> b) {
            double result = 0;

            int i;
            for (i = 0; i < a.size(); i++) {
                result += (a.get(i) * b.get(i));
            }
            return result;
        }

    }
}