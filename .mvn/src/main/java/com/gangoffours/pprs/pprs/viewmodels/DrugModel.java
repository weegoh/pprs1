package com.gangoffours.pprs.pprs.viewmodels;
import com.gangoffours.pprs.pprs.common.validator.StringHelper;

public class DrugModel{
    public String id;
    public String name;
    public String drugclass;

    public String getUrl() {
        return "/drug/" + id;
    }

    public String getName() {
        return StringHelper.Capitalise(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }

    public String getDrugclass() {
        return StringHelper.Capitalise(drugclass);
    }

    public void setDrugclass(String value) {
        drugclass = value; 
    }  

    public String getDrugClass() {
        return StringHelper.Capitalise(drugclass);
    }

    public void setName(String value) {
        name = value;
    }

    public DrugModel() {}

    public DrugModel(String id, String name, String drugclass) {
        this.id = id;
        this.name = name;
        this.drugclass = drugclass;
    }
}
