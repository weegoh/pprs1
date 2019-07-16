package com.gangoffours.pprs.pprs.viewmodels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//A class for marshalling prescriptions as xml
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement (name="medicationDispense")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlMedicationDispense{
    @XmlElement
    public Integer dispenseID; // PatientPrescription.id

    @XmlElement
    public String dispensingInstitution; //Hardcode for now

    @XmlElement
    public String dispenseDateTime; // PatientPrescription.datetime

    @XmlElement
    public String dispensedBy; // PatientPrescription.user

    @XmlElement
    public XmlPatient patient; 

    @XmlElement
    public XmlMedicationItem medicationItem;
}