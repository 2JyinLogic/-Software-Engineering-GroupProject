package edu.cpt202.group9.projb.monthlyReport;

import edu.cpt202.group9.projb.appointment.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;


@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
    @Autowired
    private MonthlyReportRepo monthlyReportRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public boolean generateMonthlyReport(int year, int month) {
        int dayNum = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 0;
        };

        if (year % 4 == 0 && year % 100 != 0) {
            dayNum = 29;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, dayNum, 23, 59, 59);
        ZonedDateTime zdt1 = startDate.atZone(zoneId);
        Date startDate1 = Date.from(zdt1.toInstant());
        ZonedDateTime zdt2 = endDate.atZone(zoneId);
        Date endDate1 = Date.from(zdt2.toInstant());

        Optional<Double> total = appointmentRepo.findTotalSales(startDate1, endDate1);
        Optional<Double> mean = appointmentRepo.findMeanOfSales(startDate1, endDate1);
        Optional<Double> max = appointmentRepo.findMaxOfSales(startDate1, endDate1);
        Optional<Double> min = appointmentRepo.findMinOfSales(startDate1, endDate1);
        Optional<Double> std = appointmentRepo.findStdOfSales(startDate1, endDate1);
        if (!total.isPresent()) {
            total = Optional.of(0.0);
        }
        if (!mean.isPresent()) {
            mean = Optional.of(0.0);
        }
        if (!max.isPresent()) {
            max = Optional.of(0.0);
        }
        if (!min.isPresent()) {
            min = Optional.of(0.0);
        }
        if (!std.isPresent()) {
            std = Optional.of(0.0);
        }

        MonthlyReport monthlyReport = new MonthlyReport(year, month, total.get(), mean.get(), max.get(), min.get(), std.get());
        monthlyReportRepo.save(monthlyReport);
        return true;
    }

    @Override
    public boolean deleteMonthlyReport(int year, int month) {
        var monthlyReport = monthlyReportRepo.findByYearAndMonth(year, month);
        if (monthlyReport.isEmpty()) {
            return false;
        } else {
            monthlyReportRepo.deleteByYearAndMonth(year, month);
            return true;
        }

    }

    @Override
    public boolean hasThisYearAndMonth(int year, int month) {
        return monthlyReportRepo.findByYearAndMonth(year, month).isPresent();
    }

    @Override
    public Optional<MonthlyReport> findByYearAndMonth(int year, int month) {
        return monthlyReportRepo.findByYearAndMonth(year, month);
    }

}
