package edu.cpt202.group9.projb.groomer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cpt202.group9.projb.schedule.Schedule;

@Service("Groomer")
public class GroomerServiceImpl implements GroomerService {

    @Autowired
    private GroomerRepository groomerRepo;

    @Override
    public List<Groomer> findAllGroomers() {
        return groomerRepo.findAll();
    }

    @Override
    public boolean hasEmployeeId(Long employeeId) {
        return findGroomerByEmployeeId(employeeId).isPresent();
    }

    @Override
    public Optional<Groomer> findGroomerByEmployeeId(Long EmployeeId) {
        var targetGroomer = groomerRepo.findByEmployeeId(EmployeeId);

        if (targetGroomer.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(targetGroomer.get());
        }
    }

    @Override
    public List<Groomer> findGroomersByRank(int rank) {
        if (Arrays.binarySearch(new int[] { 0, 1, 2, 3, 4, 5 }, rank) < 0) {
            throw new IllegalArgumentException("Invalid argument: rank can only be an integer from 0 to 5.");
        }
        return groomerRepo.findByRank(rank);
    }

    @Override
    public boolean addNewGroomer(Long employeeId, String name, int rank) {
        if (hasEmployeeId(employeeId)) {
            return false;
        } else {
            Groomer groomer = new Groomer(employeeId, name, rank);
            groomerRepo.save(groomer);
            return true;
        }
    }

    @Override
    public boolean deleteGroomerByEmployeeId(Long employeeId) {
        var targetGroomer = groomerRepo.findByEmployeeId(employeeId);

        if (targetGroomer.isEmpty()) {
            return false;
        } else {
            groomerRepo.delete(targetGroomer.get());
            return true;
        }
    }

    @Override
    public boolean editGroomerEmployeeId(Long employeeId, Long newEmployeeId) {
        var targetGroomer = findGroomerByEmployeeId(employeeId);

        if (!targetGroomer.isPresent()) {
            return false;
        } else {
            var g = targetGroomer.get();
            g.setEmployeeId(newEmployeeId);
            groomerRepo.save(g);

            return true;
        }
    }

    @Override
    public boolean editGroomerName(Long employeeId, String newName) {
        var targetGroomer = findGroomerByEmployeeId(employeeId);

        if (!targetGroomer.isPresent()) {
            return false;
        } else {
            var g = targetGroomer.get();
            g.setName(newName);
            groomerRepo.save(g);

            return true;
        }
    }

    @Override
    public boolean editGroomerRank(Long employeeId, int newRank) {
        var targetGroomer = findGroomerByEmployeeId(employeeId);

        if (!targetGroomer.isPresent()) {
            return false;
        } else {
            var g = targetGroomer.get();
            g.setRank(newRank);
            groomerRepo.save(g);

            return true;
        }
    }
}
