package com.gangoffours.pprs.pprs.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class DrugScalarKey implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "drug_bank_id")
    private String drugBankId;
    @Column(name = "drug_name")
    private String drugName;
}