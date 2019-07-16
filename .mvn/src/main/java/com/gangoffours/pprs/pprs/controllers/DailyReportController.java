package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.services.IReportService;
import com.gangoffours.pprs.pprs.viewmodels.XmlDailyReport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DailyReportController {
    private IReportService _reportService;

    // IoC
    public DailyReportController(IReportService reportService) {
        _reportService = reportService;
    }
    @RequestMapping(value = "/report/daily")
    public XmlDailyReport getDailyReport(@RequestParam("reportDate") String reportDate) {
        XmlDailyReport dReport = new XmlDailyReport();
        String reportDateTimeStart = reportDate + " 00:00";
        String reportDateTimeEnd = reportDate + " 23:59";
        dReport.setPrescriptions(_reportService.FindByDateBetween(reportDateTimeStart, reportDateTimeEnd));
        if(dReport.getPrescriptions()==null){
            dReport = null;
        }
        return dReport;
    }
}