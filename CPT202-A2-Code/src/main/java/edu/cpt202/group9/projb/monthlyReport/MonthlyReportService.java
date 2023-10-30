package edu.cpt202.group9.projb.monthlyReport;

import edu.cpt202.group9.projb.annualReport.AnnualReport;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface MonthlyReportService {
    boolean generateMonthlyReport(int year, int month);

    boolean deleteMonthlyReport(int year,int month);

    boolean hasThisYearAndMonth(int year,int month);

    Optional<MonthlyReport> findByYearAndMonth(int year, int month);

}
