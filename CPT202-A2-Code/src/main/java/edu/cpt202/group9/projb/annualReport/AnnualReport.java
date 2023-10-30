package edu.cpt202.group9.projb.annualReport;

import javax.persistence.*;

@Entity(name = "annual_report")
public class AnnualReport {

    @Id
    private int year;

    private double salesOfMonth1;
    private double salesOfMonth2;
    private double salesOfMonth3;
    private double salesOfMonth4;
    private double salesOfMonth5;
    private double salesOfMonth6;
    private double salesOfMonth7;
    private double salesOfMonth8;
    private double salesOfMonth9;
    private double salesOfMonth10;
    private double salesOfMonth11;
    private double salesOfMonth12;

    public AnnualReport() {
    }

    public AnnualReport(int year, double salesOfMonth1, double salesOfMonth2, double salesOfMonth3, double salesOfMonth4, double salesOfMonth5, double salesOfMonth6, double salesOfMonth7, double salesOfMonth8, double salesOfMonth9, double salesOfMonth10, double salesOfMonth11, double salesOfMonth12) {
        this.year = year;
        this.salesOfMonth1 = salesOfMonth1;
        this.salesOfMonth2 = salesOfMonth2;
        this.salesOfMonth3 = salesOfMonth3;
        this.salesOfMonth4 = salesOfMonth4;
        this.salesOfMonth5 = salesOfMonth5;
        this.salesOfMonth6 = salesOfMonth6;
        this.salesOfMonth7 = salesOfMonth7;
        this.salesOfMonth8 = salesOfMonth8;
        this.salesOfMonth9 = salesOfMonth9;
        this.salesOfMonth10 = salesOfMonth10;
        this.salesOfMonth11 = salesOfMonth11;
        this.salesOfMonth12 = salesOfMonth12;
    }

    public double getSalesOfMonth1() {
        return salesOfMonth1;
    }

    public void setSalesOfMonth1(double salesOfMonth1) {
        this.salesOfMonth1 = salesOfMonth1;
    }

    public double getSalesOfMonth2() {
        return salesOfMonth2;
    }

    public void setSalesOfMonth2(double salesOfMonth2) {
        this.salesOfMonth2 = salesOfMonth2;
    }

    public double getSalesOfMonth3() {
        return salesOfMonth3;
    }

    public void setSalesOfMonth3(double salesOfMonth3) {
        this.salesOfMonth3 = salesOfMonth3;
    }

    public double getSalesOfMonth4() {
        return salesOfMonth4;
    }

    public void setSalesOfMonth4(double salesOfMonth4) {
        this.salesOfMonth4 = salesOfMonth4;
    }

    public double getSalesOfMonth5() {
        return salesOfMonth5;
    }

    public void setSalesOfMonth5(double salesOfMonth5) {
        this.salesOfMonth5 = salesOfMonth5;
    }

    public double getSalesOfMonth6() {
        return salesOfMonth6;
    }

    public void setSalesOfMonth6(double salesOfMonth6) {
        this.salesOfMonth6 = salesOfMonth6;
    }

    public double getSalesOfMonth7() {
        return salesOfMonth7;
    }

    public void setSalesOfMonth7(double salesOfMonth7) {
        this.salesOfMonth7 = salesOfMonth7;
    }

    public double getSalesOfMonth8() {
        return salesOfMonth8;
    }

    public void setSalesOfMonth8(double salesOfMonth8) {
        this.salesOfMonth8 = salesOfMonth8;
    }

    public double getSalesOfMonth9() {
        return salesOfMonth9;
    }

    public void setSalesOfMonth9(double salesOfMonth9) {
        this.salesOfMonth9 = salesOfMonth9;
    }

    public double getSalesOfMonth10() {
        return salesOfMonth10;
    }

    public void setSalesOfMonth10(double salesOfMonth10) {
        this.salesOfMonth10 = salesOfMonth10;
    }

    public double getSalesOfMonth11() {
        return salesOfMonth11;
    }

    public void setSalesOfMonth11(double salesOfMonth11) {
        this.salesOfMonth11 = salesOfMonth11;
    }

    public double getSalesOfMonth12() {
        return salesOfMonth12;
    }

    public void setSalesOfMonth12(double salesOfMonth12) {
        this.salesOfMonth12 = salesOfMonth12;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "AnnualReport{" +
                "year=" + year +
                ", salesOfMonth1=" + salesOfMonth1 +
                ", salesOfMonth2=" + salesOfMonth2 +
                ", salesOfMonth3=" + salesOfMonth3 +
                ", salesOfMonth4=" + salesOfMonth4 +
                ", salesOfMonth5=" + salesOfMonth5 +
                ", salesOfMonth6=" + salesOfMonth6 +
                ", salesOfMonth7=" + salesOfMonth7 +
                ", salesOfMonth8=" + salesOfMonth8 +
                ", salesOfMonth9=" + salesOfMonth9 +
                ", salesOfMonth10=" + salesOfMonth10 +
                ", salesOfMonth11=" + salesOfMonth11 +
                ", salesOfMonth12=" + salesOfMonth12 +
                '}';
    }
}
