package edu.cpt202.group9.projb.masterfile.integrationtest;

import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItem;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemRepo;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(properties = "spring.profiles.active=test")
@Sql(scripts = "test-clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserMasterFileServiceTest {

    @Autowired
    private UserMasterFileItemRepo userMasterFileItemRepo;
    @Autowired
    private UserMasterFileItemService userMasterFileItemService;

    @Test
    public void testFindAllUserMasterFileNoFile() {

        assertFalse(userMasterFileItemService.findAllUser().isEmpty());

    }

}

