package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.services.IReportService;
import com.gangoffours.pprs.pprs.viewmodels.XmlPatientReport;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientReportController {
    private IReportService _reportService;

    // IoC
    public PatientReportController(IReportService reportService) {
        _reportService = reportService;
    }
    // Mapping for patient detail view
    @RequestMapping(value = { "/report/patient/{id}" }, method = RequestMethod.GET)
    public XmlPatientReport getPatientReport(Model model, @PathVariable Integer id) {
        XmlPatientReport pReport = new XmlPatientReport();
        pReport.Patient = _reportService.GetXmlPatientById(id);
        pReport.setPrescriptions(_reportService.FindByPatientId(id));
        if(pReport.getPrescriptions()==null){
            pReport = null;
        }
        return pReport;
    }
}