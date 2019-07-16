package com.gangoffours.pprs.pprs.models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Table(name="patient")
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class Patient{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    @NonNull
    public String first_name;
    @NonNull
    public String last_name;
    @NonNull
    public String date_of_birth;
    @NonNull
    public String gender;
    @NonNull
    public String contact;
    @NonNull
    public Integer street_num;
    public Integer unit_num;
    @NonNull
    public String street;
    @NonNull
    public String suburb;
    @NonNull
    public String state;
    @NonNull
    public String postcode;
    @NonNull
    public String country;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "currentdrug",
        inverseJoinColumns = @JoinColumn(
            name = "drugbankid",
            referencedColumnName = "drugbankid"
        ),
        joinColumns = @JoinColumn(
            name = "patientid",
            referencedColumnName = "id"
        )
    )
    public Collection<Drug> currentdrugs;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "allergicdrug",
        inverseJoinColumns = @JoinColumn(
            name = "drugbankid",
            referencedColumnName = "drugbankid"
        ),
        joinColumns = @JoinColumn(
            name = "patientid",
            referencedColumnName = "id"
        )
    )
    public Collection<Drug> allergicdrugs;
}