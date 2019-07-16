package com.gangoffours.pprs.pprs.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@Entity
public class Drug{
    @Id
    @Column(length = 10, nullable=false)
    @NonNull
    public String drugbankid;

    @NonNull
    @Column(nullable=false)
    public String name;
    
    public String cas;
   
    public String drugtype;
   
    public String keggc_id;
    
    public String keggd_id;
    
    public String pccid;
 
    public String pcsid;
  
    public String chemid;

    public String pharid;
 
    public String hetid;
       
    public String uniprotid;
   
    public String uniprottitle;
    
    public String genbankid;
    
    public String dpdid;
 
    public String rxlistlink;
  
    public String pdrhealthlink;

    public String wikiid;
 
    public String drugslink;

    public String ndcid;
   
    public String chemspider;
    
    public String binding;
    
    public String ttd;
 
    public String sideeffect;
  
    public String interaction;

    public String professional;
 
    public String interactioncontent;

    public String professionalcontent;
    
    public String overview;
 
    public String sideeffectcontent;
  
    public String drugclass;

    public String drugid;
    
    @ManyToMany(mappedBy="currentdrugs")
    public Collection<Patient> currentpatients;
    
    @ManyToMany(mappedBy="allergicdrugs")
    public Collection<Patient> allergicpatients;
}