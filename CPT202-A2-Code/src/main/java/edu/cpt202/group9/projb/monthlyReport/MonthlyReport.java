package edu.cpt202.group9.projb.monthlyReport;

import javax.persistence.*;

@Entity(name = "monthly_report")
@IdClass(MonthReportTemplatePK.class)
public class MonthlyReport {
    @Id
    private int year;
    @Id
    private int month;
    private double totalSales;
    private double meanOfSales;
    private double maxOfSales;
    private double minOfSales;
    private double stdOfSales;

    public MonthlyReport() {
    }

    public MonthlyReport(int year, int month, double totalSales, double meanOfSales, double maxOfSales, double minOfSales, double stdOfSales) {
        this.year = year;
        this.month = month;
        this.totalSales = totalSales;
        this.meanOfSales = meanOfSales;
        this.maxOfSales = maxOfSales;
        this.minOfSales = minOfSales;
        this.stdOfSales = stdOfSales;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getMeanOfSales() {
        return meanOfSales;
    }

    public void setMeanOfSales(double meanOfSales) {
        this.meanOfSales = meanOfSales;
    }

    public double getMaxOfSales() {
        return maxOfSales;
    }

    public void setMaxOfSales(double maxOfSales) {
        this.maxOfSales = maxOfSales;
    }

    public double getMinOfSales() {
        return minOfSales;
    }

    public void setMinOfSales(double minOfSales) {
        this.minOfSales = minOfSales;
    }

    public double getStdOfSales() {
        return stdOfSales;
    }

    public void setStdOfSales(double stdOfSales) {
        this.stdOfSales = stdOfSales;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "year=" + year +
                ", month=" + month +
                ", totalSales=" + totalSales +
                ", meanOfSales=" + meanOfSales +
                ", maxOfSales=" + maxOfSales +
                ", minOfSales=" + minOfSales +
                ", stdOfSales=" + stdOfSales +
                '}';
    }
}
