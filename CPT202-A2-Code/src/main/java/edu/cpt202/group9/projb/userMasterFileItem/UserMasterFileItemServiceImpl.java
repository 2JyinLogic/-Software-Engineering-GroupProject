package edu.cpt202.group9.projb.userMasterFileItem;

import edu.cpt202.group9.projb.appointment.Appointment;
import edu.cpt202.group9.projb.appointment.AppointmentRepo;
import edu.cpt202.group9.projb.security.AccountRepository;
import edu.cpt202.group9.projb.security.AccountService;
import edu.cpt202.group9.projb.user.User;
import edu.cpt202.group9.projb.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserMasterFileItemServiceImpl implements UserMasterFileItemService {
    @Autowired
    private UserMasterFileItemRepo userMasterFileItemRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepository accountRepository;

    //Add
    @Override
    public boolean newUserMasterFileItem(UserMasterFileItem userMasterFileItem) {
        if (hasUserMasterFileItemId(userMasterFileItem.getId())) {
            return false;
        } else {
            userMasterFileItemRepo.save(userMasterFileItem);
            return true;
        }

    }

    //find
    @Override
    public Optional<UserMasterFileItem> findByUserName(String userName) {
        return userMasterFileItemRepo.findByUsername(userName);
    }

    //delete
    @Override
    public boolean deleteUserMasterFileItem(User usr) {
        var acc = usr.getAccount();

        // first delete the item
        var targetUserMasterFileItem = userMasterFileItemRepo.findByUsername(acc.getUsername());
        if(!targetUserMasterFileItem.isPresent()) {
            return false;
        }
        var user = targetUserMasterFileItem.get().getUser();
        userMasterFileItemRepo.deleteById(targetUserMasterFileItem.get().getId());

        // then the appointments
        // getAppointments may read the database. SO copy the list.
        var tarAppList = appointmentRepo.findByUser(user);
        for(var app: user.getAppointments()){
            tarAppList.add(app);
        }
        for(var app: tarAppList){
            appointmentRepo.delete(app);
        }

        // then delete the user
        userRepo.delete(user);
        // then delete the account.
        accountRepository.delete(acc);

        return true;
    }

    //update
    @Override
    public boolean updateUserMasterFileItem(String firstName, String lastName, long phoneNum, String email, String oldUserName) {
        var targetUserMasterFileItem = userMasterFileItemRepo.findByUsername(oldUserName);

        if (targetUserMasterFileItem.isEmpty()) {
            return false;
        } else {
            userMasterFileItemRepo.cancelForeignKeyConstraint();
            userMasterFileItemRepo.updateUserMasterFileItem(firstName, lastName, phoneNum, email, oldUserName);
            userMasterFileItemRepo.enableForeignKeyConstraint();
            return true;
        }


    }


    @Override
    public List<UserMasterFileItem> findAllUser() {
        return userMasterFileItemRepo.findAll();
    }

    @Override
    public boolean hasUserMasterFileItemId(int id) {
        return userMasterFileItemRepo.findById(id).isPresent();
    }
}
