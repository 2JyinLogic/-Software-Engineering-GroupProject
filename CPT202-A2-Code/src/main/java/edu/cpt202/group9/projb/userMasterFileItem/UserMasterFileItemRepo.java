package edu.cpt202.group9.projb.userMasterFileItem;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserMasterFileItemRepo extends JpaRepository<UserMasterFileItem,Integer> {

    //find
    @Query(value="SELECT " +
            "    u.id,u.user_id " +
            "FROM " +
            "    user_master_file_item u, user uu, account a " +
            "WHERE " +
            "    a.username=?1 and " +
            "    a.username=uu.user_name and " +
            "    u.user_id=uu.id ",nativeQuery = true)
    Optional<UserMasterFileItem> findByUsername(String userName);

    //update
    @Transactional
    @Modifying
    @Query(value=
            "UPDATE account a, " +
            "user uu, " +
            "user_master_file_item u  " +
            "SET  " +
            "    uu.first_name = ?1, " +
            "    uu.last_name = ?2, " +
            "    uu.phone_num = ?3, " +
            "    uu.user_email = ?4 " +
            "WHERE " +
            "    a.username = ?5 " +
            "        AND uu.user_name = a.username " +
            "        AND u.user_id = uu.id; ",nativeQuery = true)
    void updateUserMasterFileItem(String firstName,String lastName,long phoneNum,String email,String oldUserName);

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void cancelForeignKeyConstraint();
    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyConstraint();


}