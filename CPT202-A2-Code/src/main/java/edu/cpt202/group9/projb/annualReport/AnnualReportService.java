package edu.cpt202.group9.projb.annualReport;

import java.util.List;
import java.util.Optional;

public interface AnnualReportService {

    boolean generateAnnualReport(int year);

    boolean hasThisYear(int year);

    boolean deleteAnnualReport(int year);

    Optional<AnnualReport> findByYear(int year);
}
