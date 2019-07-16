package com.gangoffours.pprs.pprs.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gangoffours.pprs.pprs.viewmodels.*;
import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;


@Service
public class DrugSearchService implements IDrugSearchService{

    private IDrugRepository _drugRepo;

    @Autowired
    public DrugSearchService (IDrugRepository drugRepo) {
        _drugRepo = drugRepo;
    } 

    // Takes an instance of DrugModel which has the user's input in each field then returns an array of Drugs from the drug repository that contain all the fields
    public List<DrugModel> searchResults(DrugModel drugmodel) {

        List<DrugModel> foundDrugs = new ArrayList<DrugModel>();

        for (Drug drug : _drugRepo.findByDrugDetails(drugmodel.id, drugmodel.name, drugmodel.drugclass)) {
            DrugModel newDrugModel = new DrugModel();
            newDrugModel.id = drug.drugbankid;
            newDrugModel.name = drug.name;
            newDrugModel.drugclass = drug.drugclass;

            foundDrugs.add(newDrugModel);
        }

        return foundDrugs;
    }

}