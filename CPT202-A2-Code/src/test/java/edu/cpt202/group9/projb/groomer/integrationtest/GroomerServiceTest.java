package edu.cpt202.group9.projb.groomer.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.groomer.GroomerRepository;
import edu.cpt202.group9.projb.groomer.GroomerService;

/**
 * Contains unit tests for groomer accesser's interaction with the database.
 * 
 * @version 2023.4.17
 * @since 2023.4.10
 * @author Hanyu Zhang
 */
@SpringBootTest(properties = "spring.profiles.active=test")
//@Sql(scripts = "test-clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
public class GroomerServiceTest {

    @Autowired
    GroomerRepository repo;

    @Autowired
    GroomerService groomerService;

    /**
     * Tests findAllGroomers when there is no groomer.
     */
    @Test
    public void testFindAllGroomersNoGroomer() {
        groomerService.findAllGroomers();

        assertTrue(groomerService.findAllGroomers().isEmpty());
    }

    /**
     * Tests findAllGroomers when there does have groomers.
     */
    @Test
    public void testFindAllGroomersTwoGroomer() {
        Groomer g1 = new Groomer(3l, "groomerA", 3);
        Groomer g2 = new Groomer(6l, "groomerB", 0);
        repo.save(g1);
        repo.save(g2);

        List<Groomer> groomers = new ArrayList<Groomer>();
        groomers.add(g1);
        groomers.add(g2);

        var res = groomerService.findAllGroomers();
        assertEquals(groomers, res);
    }

    /**
     * Tests findGroomerByEmployeeId when the groomer is not present.
     */
    @Test
    public void testFindGroomerByEmployeeIdAbsent() {
        /* when the database is empty */
        var res = groomerService.findGroomerByEmployeeId(1l);
        assertFalse(res.isPresent());

        repo.save(new Groomer(1l, "groomerA", 3));

        /* when the database is not empty */
        res = groomerService.findGroomerByEmployeeId(2l);
        assertFalse(res.isPresent());
    }

    /**
     * Tests findGroomerByEmployeeId when the groomer is present.
     */
    @Test
    public void testFindGroomerByEmployeeIdPresent() {
        Long employeeId = 1l;
        Groomer g = new Groomer(employeeId, "groomerA", 2);
        repo.save(g);
        var res = groomerService.findGroomerByEmployeeId(employeeId);
        assertTrue(g.equals(res.get()));
        assertTrue(res.isPresent());
    }

    /**
     * Tests findGroomersByRank when the rank is invalid, which is not an intrger
     * between 0-5.
     */
    @Test
    public void testFindGroomersByRankInvalidRank() {
        /* when the database is empty */
        assertThrows(IllegalArgumentException.class, () -> groomerService.findGroomersByRank(6));

        Groomer g = new Groomer(4l, "groomerA", 2);
        repo.save(g);

        /* when the database is not empty */
        assertThrows(IllegalArgumentException.class, () -> groomerService.findGroomersByRank(6));
    }

    /**
     * Tests FindGroomersByRank when there is no groomer.
     */
    @Test
    public void testFindGroomersByRankNoGroomer() {
        /* when the database is empty */
        assertTrue(groomerService.findGroomersByRank(1).isEmpty());

        Groomer g = new Groomer(3l, "groomerA", 3);
        repo.save(g);

        /* when the database is not empty */
        assertTrue(groomerService.findGroomersByRank(1).isEmpty());
    }

    /**
     * Tests findGroomersByRank when there does have groomers with the specific
     * rank.
     */
    @Test
    public void testFindGroomersByRankTwoGroomer() {
        Groomer g1 = new Groomer(3l, "groomerA", 3);
        Groomer g2 = new Groomer(6l, "groomerB", 0);
        repo.save(g1);
        repo.save(g2);

        List<Groomer> grommerOfRankThree = new ArrayList<Groomer>();
        grommerOfRankThree.add(g1);

        assertEquals(grommerOfRankThree, groomerService.findGroomersByRank(3));
    }

    /**
     * Tests addNewGroomer when there is a groomer with the employee id so it's
     * expected to fail.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testAddNewGroomerFailure() {
        Long employeeId = 1l;

        groomerService.addNewGroomer(employeeId, "groomerA", 1);

        assertFalse(groomerService.addNewGroomer(employeeId, "groomerB", 0));

    }

    /**
     * Tests when there are no groomer with the employee id so it's expected to
     * succeed.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testAddNewGroomerSuccess() {
        Long employeeId1 = 4l;
        Long employeeId2 = 6l;

        /* when the database is empty */
        assertTrue(groomerService.addNewGroomer(employeeId1, "groomerA", 1));
        assertTrue(groomerService.hasEmployeeId(employeeId1));

        /* when the database is not empty */
        assertTrue(groomerService.addNewGroomer(employeeId2, "groomerB", 2));
        assertTrue(groomerService.hasEmployeeId(employeeId2));
    }

    /**
     * Tests deleteGroomerByEmployeeId when there is no groomer with the employee id
     * so it's expected to fail.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testDeleteGroomerByEmployeeIdFailure() {
        Long employeeId = 4l;
        Long employeeId2 = 5l;
        String name = "groomerA";
        int rank = 2;

        /* when the database is empty */
        assertFalse(groomerService.deleteGroomerByEmployeeId(employeeId));

        repo.save(new Groomer(employeeId, name, rank));

        /* when the database is not empty */
        assertFalse(groomerService.deleteGroomerByEmployeeId(employeeId2));
        assertTrue(groomerService.hasEmployeeId(employeeId));
    }

    /**
     * Tests deleteGroomerByEmployeeId when there are already a groomer with the
     * employee id so it's expected to succeed.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testDeleteGroomerByEmployeeIdSuccess() {
        Long employeeId = 4l;
        repo.save(new Groomer(employeeId, "groomerA", 3));

        assertTrue(groomerService.deleteGroomerByEmployeeId(employeeId));
        assertFalse(groomerService.hasEmployeeId(employeeId));
    }

    /**
     * Tests editGroomerEmployeeId when there is no groomer with the employee id so
     * it's expected to fail.
     */
    @Test////////////////////////////////
    public void testEditGroomerEmployeeIdFailure() {
        Long employeeId = 1l;
        Long newEmployeeId = 2l;
        Long employeeId2 = 3l;
        String name = "groomerA";
        int rank = 3;

        /* when the database is empty */
        assertFalse(groomerService.editGroomerName(employeeId, name));

        repo.save(new Groomer(employeeId, name, rank));

        /* when the database is not empty */
        assertFalse(groomerService.editGroomerEmployeeId(employeeId2, newEmployeeId));
    }

    /**
     * Tests editGroomerEmployeeId when there are already a groomer with employee id
     * so it's expected to succeed.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testEditGroomerEmployeeIdSuccess() {
        Long employeeId = 1l;
        Long newEmployeeId = 2l;
        String name = "groomerA";
        int rank = 3;
        repo.save(new Groomer(employeeId, name, rank));

        assertTrue(groomerService.editGroomerEmployeeId(employeeId, newEmployeeId));
        var res = groomerService.findGroomerByEmployeeId(newEmployeeId);
        assertTrue(res.isPresent());
        assertEquals(name, res.get().getName());
        assertEquals(rank, res.get().getRank());
        assertFalse(groomerService.hasEmployeeId(employeeId));
    }

    /**
     * Tests editGroomerName when there is no groomer with the employee id so it's
     * expected to fail.
     */
    @Test
    public void testEditGroomerNameFailure() {
        Long employeeId1 = 8l;
        Long employeeId2 = 9l;
        String name = "groomerA";
        String newName = "groomerA2";
        int rank = 1;

        /* when the database is empty */
        assertFalse(groomerService.editGroomerName(employeeId1, name));

        repo.save(new Groomer(employeeId1, name, rank));

        /* when the database is not empty */
        assertFalse(groomerService.editGroomerName(employeeId2, newName));
    }

    /**
     * Tests editGroomerName when there are already a groomer with the employee id
     * so it's expected to succeed.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testEditGroomerNameSuccess() {
        Long employeeId = 8l;
        String name = "groomerA";
        String newName = "groomerA2";
        int rank = 3;
        repo.save(new Groomer(employeeId, name, rank));

        assertTrue(groomerService.editGroomerName(employeeId, newName));
        var res = groomerService.findGroomerByEmployeeId(employeeId);
        assertTrue(res.isPresent());
        assertEquals(newName, res.get().getName());
        assertEquals(rank, res.get().getRank());
        assertTrue(groomerService.hasEmployeeId(employeeId));
    }

    /**
     * Tests editGroomerRank when there is no groomer with the employee id so it's
     * expected to fail.
     */
    @Test
    public void testEditGroomerRankFailure() {
        Long employeeId1 = 9l;
        Long employeeId2 = 10l;
        String name = "groomerA";
        int rank = 1;
        int newRank = 2;

        /* when the database is empty */
        assertFalse(groomerService.editGroomerRank(employeeId1, newRank));

        repo.save(new Groomer(employeeId1, name, rank));

        /* when the database is not empty */
        assertFalse(groomerService.editGroomerRank(employeeId2, newRank));
    }

    /**
     * Tests editGroomerRank when there are already a groomer with the employee id
     * so it's expected to succeed.
     * Requires that hasEmployeeId(), i.e. findGroomerByEmployeeId() works
     * correctly.
     */
    @Test
    public void testEditGroomerRankSuccess() {
        Long employeeId = 9l;
        String name = "groomerA";
        int rank = 3;
        int newRank = 4;
        repo.save(new Groomer(employeeId, name, rank));

        assertTrue(groomerService.editGroomerRank(employeeId, newRank));
        var res = groomerService.findGroomerByEmployeeId(employeeId);
        assertTrue(res.isPresent());
        assertEquals(name, res.get().getName());
        assertEquals(newRank, res.get().getRank());
        assertTrue(groomerService.hasEmployeeId(employeeId));
    }

}
