package com.gangoffours.pprs.pprs.viewmodels;

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
@XmlRootElement (name="patient")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlPatient{
    @XmlElement
    public Integer patientId;
    @XmlElement
    public String patientName;
    @XmlElement
    public String dateOfBirth;
    @XmlElement
    public XmlAddress address;
}