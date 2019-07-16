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
@XmlRootElement (name="doseQuantity")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlMedicationDoseQuantity{
    @XmlElement
    public String lowValue;
    @XmlElement
    public String lowUnit;
}