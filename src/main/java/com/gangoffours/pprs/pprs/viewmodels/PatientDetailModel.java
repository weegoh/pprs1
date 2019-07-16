package com.gangoffours.pprs.pprs.viewmodels;

import java.util.ArrayList;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import com.gangoffours.pprs.pprs.common.validator.Phone;
import com.gangoffours.pprs.pprs.models.Drug;

@Data
@NoArgsConstructor
public class PatientDetailModel{
    public Integer Id;

    @NotBlank(message="First name must not be blank")
    @Size(max=50, message="First name must be less than 50 characters")
    public String FirstName;

    @NotBlank(message="Last name must not be blank")
    @Size(max=50, message="Last name must be less than 50 charcters")
    public String LastName;

    @Pattern(regexp="^[0-3]?[0-9]/[0-3]?[0-9]/[0-9]{4}$",message="Date must be formatted as dd/mm/yyyy")
    @NotNull
    public String DateOfBirth;

    @NotNull
    @Pattern(regexp="^(Male|Female|Other)$",message="Please choose a gender")
    public String Gender;

    @NotNull
    @Phone(message="Please enter a valid 11 digit phone number")
    public String Contact;

    @NotNull(message="Street number required")
    @Min(value=1, message="Street Number required")
    @Max(value=999, message="Number must be less than 999")
    public Integer StreetNum;

    @Max(value=999, message="Number must be less than 999")
    public Integer UnitNum;

    @NotBlank(message="Street name must not be blank")
    @Size(max=100, message="Street name must be less than 100 charcters")
    public String Street;

    @NotBlank(message="Suburb must not be blank")
    @Size(max=100, message="Suburb must be must be less than 100 charcters")
    public String Suburb;

    @NotBlank(message="State must not be blank")
    @Size(max=100, message="State must be must be less than 100 charcters")
    public String State;

    @Pattern(regexp="[0-9]{3,5}", message="3-5 digit zip code required")
    public String Postcode;

    @NotBlank(message="Country must not be blank")
    @Size(max=100, message="Country must be less than 100 charcters")
    public String Country;
    
    public ArrayList<Drug> AllergicDrugs;
    public ArrayList<Drug> CurrentDrugs;

    public String getFullName() {
        return FirstName + " " + LastName;
    }

    public String getUrl() {
        return "/patient/" + String.valueOf(Id);
    }
}