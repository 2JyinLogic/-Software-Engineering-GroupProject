package edu.cpt202.group9.projb.annualReport;

import edu.cpt202.group9.projb.appointment.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnualReportServiceImpl implements AnnualReportService {
    @Autowired
    private AnnualReportRepo annualReportRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public boolean generateAnnualReport(int year) {
        int dayNum=0;
        List<Date> monthStartTimeList = new ArrayList<>();
        List<Date> monthEndTimeList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {

            switch (i) {
                case 1, 3, 5, 7, 8, 10, 12 -> dayNum = 31;
                case 2 -> dayNum = 28;
                case 4, 6, 9, 11 -> dayNum = 30;
            }
            if(year%4==0&&year%100!=0){
                dayNum=29;
            }
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime startDate = LocalDateTime.of(year, i, 1, 0, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(year, i, dayNum, 23, 59, 59);
            ZonedDateTime zdt1 = startDate.atZone(zoneId);
            Date startDate1 = Date.from(zdt1.toInstant());
            ZonedDateTime zdt2 = endDate.atZone(zoneId);
            Date endDate1 = Date.from(zdt2.toInstant());

            monthStartTimeList.add(startDate1);
            monthEndTimeList.add(endDate1);
        }
        for(Date item:monthStartTimeList){
            System.out.println(item);
        }
        for(Date item:monthEndTimeList){
            System.out.println(item);
        }


        Optional<Double> total1 = appointmentRepo.findTotalSales(monthStartTimeList.get(0), monthEndTimeList.get(0));
        if (!total1.isPresent()) {
            total1 = Optional.of(0.0);
        }
        Optional<Double> total2 = appointmentRepo.findTotalSales(monthStartTimeList.get(1), monthEndTimeList.get(1));
        if (!total2.isPresent()) {
            total2 = Optional.of(0.0);
        }
        Optional<Double> total3 = appointmentRepo.findTotalSales(monthStartTimeList.get(2), monthEndTimeList.get(2));
        if (!total3.isPresent()) {
            total3 = Optional.of(0.0);
        }
        Optional<Double> total4 = appointmentRepo.findTotalSales(monthStartTimeList.get(3), monthEndTimeList.get(3));
        if (!total4.isPresent()) {
            total4 = Optional.of(0.0);
        }
        Optional<Double> total5 = appointmentRepo.findTotalSales(monthStartTimeList.get(4), monthEndTimeList.get(4));
        if (!total5.isPresent()) {
            total5 = Optional.of(0.0);
        }
        Optional<Double> total6 = appointmentRepo.findTotalSales(monthStartTimeList.get(5), monthEndTimeList.get(5));
        if (!total6.isPresent()) {
            total6 = Optional.of(0.0);
        }
        Optional<Double> total7 = appointmentRepo.findTotalSales(monthStartTimeList.get(6), monthEndTimeList.get(6));
        if (!total7.isPresent()) {
            total7 = Optional.of(0.0);
        }
        Optional<Double> total8 = appointmentRepo.findTotalSales(monthStartTimeList.get(7), monthEndTimeList.get(7));
        if (!total8.isPresent()) {
            total8 = Optional.of(0.0);
        }
        Optional<Double> total9 = appointmentRepo.findTotalSales(monthStartTimeList.get(8), monthEndTimeList.get(8));
        if (!total9.isPresent()) {
            total9 = Optional.of(0.0);
        }
        Optional<Double> total10 = appointmentRepo.findTotalSales(monthStartTimeList.get(9), monthEndTimeList.get(9));
        if (!total10.isPresent()) {
            total10 = Optional.of(0.0);
        }
        Optional<Double> total11 = appointmentRepo.findTotalSales(monthStartTimeList.get(10), monthEndTimeList.get(10));
        if (!total11.isPresent()) {
            total11 = Optional.of(0.0);
        }
        Optional<Double> total12 = appointmentRepo.findTotalSales(monthStartTimeList.get(11), monthEndTimeList.get(11));
        if (!total12.isPresent()) {
            total12 = Optional.of(0.0);
        }
        AnnualReport annualReport = new AnnualReport(year, total1.get(),total2.get(),total3.get(),total4.get(),total5.get(),total6.get(),total7.get(),total8.get(),total9.get(),total10.get(),total11.get(),total12.get());

        annualReportRepo.save(annualReport);
        return true;
    }

    @Override
    public boolean deleteAnnualReport(int year) {
        var annualReport = annualReportRepo.findByYear(year);
        if (annualReport.isEmpty()) {
            return false;
        } else {
            annualReportRepo.deleteByYear(year);
            return true;
        }

    }

    @Override
    public boolean hasThisYear(int year) {
        return annualReportRepo.findByYear(year).isPresent();
    }

    @Override
    public Optional<AnnualReport> findByYear(int year) {
        return annualReportRepo.findByYear(year);
    }

}
