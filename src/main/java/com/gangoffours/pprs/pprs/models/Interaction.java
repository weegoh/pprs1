package com.gangoffours.pprs.pprs.models;

import javax.persistence.*;

import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AccessLevel;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
public class Interaction{
    @Id
    @Column(nullable=false)
    @NonNull
    public Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "drugbankid")
    @NonNull
    public Drug drug;
    
    @NonNull
    @Column(nullable=false)
    public String drugname;

    @NonNull
    @Column(nullable=false)
    public String level;
   
}