package edu.cpt202.group9.projb.monthlyReport;

import edu.cpt202.group9.projb.annualReport.AnnualReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MonthlyReportRepo extends JpaRepository<MonthlyReport,MonthReportTemplatePK> {

    @Transactional
    @Query(value="select * from monthly_report where year = ?1 and month = ?2",nativeQuery = true)
    Optional<MonthlyReport> findByYearAndMonth(int year,int month);

    @Transactional
    @Modifying
    @Query(value = "delete from monthly_report where year=?1 and month = ?2",nativeQuery = true)
    void deleteByYearAndMonth(int year,int month);
}
