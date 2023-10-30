package edu.cpt202.group9.projb.groomer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groomers")
public class GroomerController {

    @Autowired
    private GroomerService groomerService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Groomer>> finAllGroomers() {
        List<Groomer> groomers = groomerService.findAllGroomers();
        return ResponseEntity.ok(groomers);
    }

    @GetMapping("/findByEmployeeId")
    public ResponseEntity<Groomer> findGroomersByEmployeeId(@RequestParam("employeeId") Long employeeId) {
        Optional<Groomer> groomers = groomerService.findGroomerByEmployeeId(employeeId);
        return ResponseEntity.ok(groomers.get());
    }

    @GetMapping("/findByRank")
    public ResponseEntity<List<Groomer>> findGroomersByRank(@RequestParam("rank") int rank) {
        List<Groomer> groomers = groomerService.findGroomersByRank(rank);
        return ResponseEntity.ok(groomers);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewGroomer(@RequestParam("employeeId") Long employeeId,
            @RequestParam("name") String name,
            @RequestParam("rank") int rank) {
        groomerService.addNewGroomer(employeeId, name, rank);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGroomer(@PathVariable("id") Long id) {
        groomerService.deleteGroomerByEmployeeId(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editEmployeeId")
    public ResponseEntity<Void> editGroomerEmployeeId(@RequestParam("employeeId") Long employeeId,
            @RequestParam("newEmployeeId") Long newEmployeeId) {
        groomerService.editGroomerEmployeeId(newEmployeeId, newEmployeeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editName")
    public ResponseEntity<Void> editGroomerName(@RequestParam("employeeId") Long employeeId,
            @RequestParam("newName") String newName) {
        groomerService.editGroomerName(employeeId, newName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editRank")
    public ResponseEntity<Void> editGroomerRank(@RequestParam("employeeId") Long employeeId,
            @RequestParam("rank") int newRank) {
        groomerService.editGroomerRank(employeeId, newRank);
        return ResponseEntity.ok().build();
    }

}
