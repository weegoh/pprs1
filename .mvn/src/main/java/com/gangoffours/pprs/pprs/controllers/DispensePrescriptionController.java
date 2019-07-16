package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.services.*;
import com.gangoffours.pprs.pprs.viewmodels.PatientDetailModel;
import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import com.gangoffours.pprs.pprs.repositories.IPrescriptionDefaultRepository;
import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.models.PrescriptionDefault;
import com.gangoffours.pprs.pprs.viewmodels.PrescriptionModel;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.Date;

@Controller
public class DispensePrescriptionController extends AuthControllerBase {

    private IPatientService _patientService;
    private IDrugRepository _drugRepo;
    private IPrescriptionDefaultRepository _prescriptionDefaultRepo;
    private IDispensePrescriptionService _dispensePrescriptionService;
    private IPatientDrugService _patientDrugService;

    public DispensePrescriptionController(IUserRepository userRepository, 
        IPatientService patientService, 
        IDrugRepository drugRepository,
        IPrescriptionDefaultRepository prescriptionDefaultRepo,
        IPatientDrugService patientDrugService,
        IDispensePrescriptionService dispensePrescriptionService) {
        super(userRepository);
        _patientService = patientService;
        _patientDrugService = patientDrugService;
        _drugRepo = drugRepository;
        _prescriptionDefaultRepo =  prescriptionDefaultRepo;
        _dispensePrescriptionService = dispensePrescriptionService;
    }

    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Dispense Prescription";
    }

    @GetMapping("/patient/{patientid}/DispenseDrug/{drugid}")   
    public String getRecommendation(
        Model model, 
        @PathVariable("patientid") Integer patientId, 
        @PathVariable("drugid") String drugId) {

        PatientDetailModel result = _patientService.GetPatientDetail(patientId);
        Drug drug = _drugRepo.findByDrugbankid(drugId);
        PrescriptionDefault def = _prescriptionDefaultRepo.findBydrugbankid(drugId);
        PrescriptionModel prescription = new PrescriptionModel();
        prescription = _dispensePrescriptionService.ToModel(def);


        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientId);
        model.addAttribute("DrugID", drugId);
        model.addAttribute("DrugName", drug.name);
        model.addAttribute("Complete", "false");
        model.addAttribute("Prescription", prescription);

        return "dispense/index";
    }

    @PostMapping("/patient/{patientid}/DispenseComplete/{drugid}")   
    public String getRecommendationNewThresholds(
        PrescriptionModel prescription, 
        Model model, 
        @PathVariable("patientid") Integer patientId, 
        @PathVariable("drugid") String drugId,
        Authentication authentication
    ) {
        _patientDrugService.addCurrent(patientId, drugId);
        PatientDetailModel result = _patientService.GetPatientDetail(patientId);
        Drug drug = _drugRepo.findByDrugbankid(drugId);
        
        prescription.patientId = patientId;
        prescription.drugBankId = drugId;
        prescription.user = getUser(authentication).getFullname();
        prescription.dateTime = _dispensePrescriptionService.DateToString(new Date());
        int id = _dispensePrescriptionService.SavePrescription(prescription);
        prescription.Id = id;

        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientId);
        model.addAttribute("DrugID", drugId);
        model.addAttribute("DrugName", drug.name);
        model.addAttribute("Complete", "true");
        model.addAttribute("Prescription", prescription);
        
        return "dispense/index";
    }
}