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
@XmlRootElement (name="medicationItem")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlMedicationItem{
    @XmlElement
    public String itemId;

    @XmlElement
    public String frequencyQualifier; //PatientPerscription.medqualifier

    @XmlElement
    public String dosageInstruction;

    @XmlElement
    public XmlMedicationDoseQuantity doseQuantity;

    @XmlElement(name="medicationItemDispensedDate")
    public String dispenseDate;

    @XmlElement
    public String medicationStartDateTime;

    @XmlElement
    public String medicationEndDateTime;

    @XmlElement
    public String reasonForMedication;

    @XmlElement
    public String quantityDispensed;

    @XmlElement
    public String quantityDispensedUnits;

    @XmlElement
    public String duration;

    @XmlElement
    public String durationUnits;

    @XmlElement
    public String medicationName;

    @XmlElement
    public String frequency;
}