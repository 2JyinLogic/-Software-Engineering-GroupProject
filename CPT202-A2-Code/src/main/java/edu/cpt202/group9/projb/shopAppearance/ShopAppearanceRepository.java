package edu.cpt202.group9.projb.shopAppearance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopAppearanceRepository extends JpaRepository<ShopAppearance, Long> {

    // @Query(value="select * from shop_appearance where file_name=?1" ,nativeQuery=true)
    Optional<ShopAppearance> findByFileName(String fileName);
}
