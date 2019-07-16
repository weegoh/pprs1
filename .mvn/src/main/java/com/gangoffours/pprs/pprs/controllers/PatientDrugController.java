package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.services.*;
import com.gangoffours.pprs.pprs.viewmodels.PatientDetailModel;

import java.util.List;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class PatientDrugController extends AuthControllerBase {

    private IPatientService _patientService;
    private IPatientDrugService _patientDrugService;

    public PatientDrugController(
        IUserRepository userRepository,
        IPatientService patientService,
        IPatientDrugService patientDrugService
    ) {
        super(userRepository);
        _patientService = patientService;
        _patientDrugService = patientDrugService;
    }

    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Patient Drug Profile";
    }

    @GetMapping("/patient/{patientid}/drug")   
    public String getIndex(Model model, @PathVariable("patientid") int patientid) {

        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(patientid));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(patientid));

        return "patientDrug/index";
    }

    @GetMapping("/patient/{patientid}/addCurrentDrug/{drugid}")   
    public String getIndexWithAddCurrent(Model model, @PathVariable("patientid") int patientid, @PathVariable("drugid") String drugid) {
        
        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        _patientDrugService.addCurrent(patientid, drugid);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(patientid));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(patientid));

        return "redirect:/patient/" + String.valueOf(patientid) + "/drug";
    }

    @PostMapping("/patient/{patientid}/removeCurrentDrug")   
    public String getIndexWithRemoveCurrent(Model model, @PathVariable("patientid") int patientid, @RequestParam(value = "drugid", required = false) List<String> allDrugs) {

        if (allDrugs != null) {
            for (String entry : allDrugs) {
                _patientDrugService.removeCurrent(patientid, entry);
            }
        }

        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(patientid));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(patientid));

        return "redirect:/patient/" + String.valueOf(patientid) + "/drug";
    }

    @GetMapping("/patient/{patientid}/addAllergicDrug/{drugid}")   
    public String getIndexWithAddAllergic(
        Model model,
        @PathVariable("patientid") int patientid,
        @PathVariable("drugid") String drugid
    ) {
        _patientDrugService.addAllergic(patientid, drugid);
        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(patientid));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(patientid));

        return "redirect:/patient/" + String.valueOf(patientid) + "/drug";
    }

    @PostMapping("/patient/{patientid}/removeAllergicDrug")   
    public String getIndexWithRemoveAllergic(Model model, @PathVariable("patientid") int patientid, @RequestParam(value = "drugid", required = false) List<String> allDrugs) {
  
        if (allDrugs != null) {
            for (String entry : allDrugs) {
                _patientDrugService.removeAllergic(patientid, entry);
            }
        }

        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(patientid));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(patientid));

        return "redirect:/patient/" + String.valueOf(patientid) + "/drug";
    }
}