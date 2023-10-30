package edu.cpt202.group9.projb.monthlyReport;


import java.io.Serializable;

public class MonthReportTemplatePK implements Serializable {

    private int year;
    private int month;

    public MonthReportTemplatePK() {
    }

    public MonthReportTemplatePK(int year, int month) {
        this.year = year;
        this.month = month;
    }

}

