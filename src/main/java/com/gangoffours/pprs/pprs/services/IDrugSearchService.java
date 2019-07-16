package com.gangoffours.pprs.pprs.services;

import java.util.List;
import com.gangoffours.pprs.pprs.viewmodels.DrugModel;

public interface IDrugSearchService
{
    List<DrugModel> searchResults(DrugModel drugmodel);
}