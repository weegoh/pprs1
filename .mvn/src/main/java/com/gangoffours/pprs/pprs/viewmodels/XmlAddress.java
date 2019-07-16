package com.gangoffours.pprs.pprs.viewmodels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement (name="patientAddress")
@XmlAccessorType (XmlAccessType.FIELD)
public class XmlAddress{
    @XmlElement
    public Integer street_num;
    @XmlElement
    public Integer unit_num;
    @XmlElement
    public String street;
    @XmlElement
    public String suburb;
    @XmlElement
    public String state;
    @XmlElement
    public String postcode;
    @XmlElement
    public String country;
}