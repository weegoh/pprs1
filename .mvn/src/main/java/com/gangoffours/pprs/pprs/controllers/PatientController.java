package com.gangoffours.pprs.pprs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.common.ResourceNotFoundException;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import com.gangoffours.pprs.pprs.services.IPatientService;
import com.gangoffours.pprs.pprs.services.IPatientDrugService;
import com.gangoffours.pprs.pprs.viewmodels.PatientDetailModel;

@Controller
@RequestMapping("/patient")
public class PatientController extends AuthControllerBase {

    private IPatientService _patientService;
    private IPatientDrugService _patientDrugService;

    // IoC
    public PatientController(IPatientService patientService, IUserRepository userRepository, IPatientDrugService patientDrugService) {
        super(userRepository);
        _patientService = patientService;
        _patientDrugService = patientDrugService;;
    }

    // Set the PageTitle for the controller
    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Patient";
    }

    // Mapping for patient index view
    @GetMapping
    public String getIndex(Model model, String patientName) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Patient Search");

        if (patientName != null && patientName.length() > 0) {
            model.addAttribute("patientName", patientName);
            model.addAttribute("patients", _patientService.FindByName(patientName));
        }

        return "patient/index";
    }

    // Mapping for patient detail view
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
    public String getIndex(Model model, @PathVariable Integer id) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Patient Detail");

        PatientDetailModel result = _patientService.GetPatientDetail(id);
        if (result == null)
            throw new ResourceNotFoundException();

        model.addAttribute("patient", result);
        model.addAttribute("CurrentDrugs", _patientDrugService.getCurrent(result.getId()));
        model.addAttribute("AllergicDrugs", _patientDrugService.getAllergic(result.getId()));

        return "patient/detail";
    }

    // Mapping for new patient detail view
    @GetMapping("/new")
    public String getNew(Model model) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Patient Detail");

        // Fill the "New" Badge
        model.addAttribute("IsNew", "New");
        model.addAttribute("patient", new PatientDetailModel());

        return "patient/detail";
    }

    // Create a new patient from Post data
    @PostMapping("/new")
    public String postPatientCreate(
        @ModelAttribute("patient") @Valid PatientDetailModel patientDetail,
        BindingResult bindingResult,
        Model model
    ) {
        model.addAttribute("IsNew", "New");
        if (bindingResult.hasErrors()) {
            return "patient/detail";
        }
        
        int id = _patientService.CreatePatient(patientDetail);
        return "redirect:/patient/" + id;
    }

    // Update existing patient from post data
    @PostMapping("/update")
    public String postPatientUpdate(
        @ModelAttribute("patient") @Valid PatientDetailModel patientDetail,
        BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "patient/detail";
        }
        // Get the existing entity record
        _patientService.UpdatePatient(patientDetail);

        return "redirect:/patient/" + patientDetail.Id;
    }
}