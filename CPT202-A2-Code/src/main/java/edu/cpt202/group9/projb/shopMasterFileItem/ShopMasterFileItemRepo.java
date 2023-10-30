package edu.cpt202.group9.projb.shopMasterFileItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopMasterFileItemRepo extends JpaRepository<ShopMasterFileItem, Integer> {

    //find
    @Transactional
    @Query(value="select * from shop_master_file_item where item_name=?1",nativeQuery = true)
    Optional<ShopMasterFileItem> findByItemName(String itemName);

    //delete
    @Transactional
    @Modifying
    @Query(value="delete from shop_master_file_item where item_name=?1",nativeQuery = true)
    void deleteByItemName(String item_name);

    //update
    @Transactional
    @Modifying
    @Query(value="update shop_master_file_item " +
            "set number=?1 " +
            "where item_name=?2",nativeQuery = true)
    void updateShopMasterFileItem(double number,String itemName);

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void cancelForeignKeyConstraint();
    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyConstraint();

}
