package com.gangoffours.pprs.pprs.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//A class for holding prescription records for XML marshalling
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement (name="putDispensedMedications")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlDailyReport{
    @XmlElement
    private List<XmlMedicationDispense> prescriptions = null;

    public List<XmlMedicationDispense> getPrescriptions(){
        return prescriptions;
    }

    public void setPrescriptions(List<XmlMedicationDispense> prescriptions) {
        this.prescriptions = prescriptions;
    }
}