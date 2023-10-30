package edu.cpt202.group9.projb.annualReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AnnualReportRepo extends JpaRepository<AnnualReport,Integer> {

    @Transactional
    @Query(value="select * from annual_report where year = ?1",nativeQuery = true)
    Optional<AnnualReport> findByYear(int year);

    @Transactional
    @Modifying
    @Query(value = "delete from annual_report where year=?1",nativeQuery = true)
    void deleteByYear(int year);
}
