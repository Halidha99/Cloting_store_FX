package service.custom.impl;

import model.Report;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl {

    public List<Report> getDailyReport(LocalDate date) {

        List<Report> reportData = new ArrayList<>();
        reportData.add(new Report(date, 100, 5, 950.0));
        return reportData;
    }

    public List<Report> getMonthlyReport(String month, int year) {

        List<Report> reportData = new ArrayList<>();
        reportData.add(new Report(LocalDate.of(year, 1, 1), 300, 15, 2800.0));
        return reportData;
    }

    public List<Report> getYearlyReport(int year) {

        List<Report> reportData = new ArrayList<>();
        reportData.add(new Report(LocalDate.of(year, 1, 1), 1500, 100, 15000.0));
        return reportData;
    }
}
