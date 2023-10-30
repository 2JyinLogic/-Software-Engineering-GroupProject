package edu.cpt202.group9.projb.userMasterFileItem;


import edu.cpt202.group9.projb.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserMasterFileItemService {

    boolean newUserMasterFileItem(UserMasterFileItem userMasterFileItem);

    Optional<UserMasterFileItem> findByUserName(String userName);

    /**
     *
     * @param usr
     * @returns true iff the item, the user, and all related info are deleted
     */
    boolean deleteUserMasterFileItem(User usr);

    boolean updateUserMasterFileItem(String firstName, String lastName, long phoneNum, String email, String oldUserName);

    List<UserMasterFileItem> findAllUser();

    boolean hasUserMasterFileItemId(int id);


}
