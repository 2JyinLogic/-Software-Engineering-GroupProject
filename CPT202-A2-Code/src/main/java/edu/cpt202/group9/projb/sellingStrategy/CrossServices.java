package edu.cpt202.group9.projb.sellingStrategy;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CrossServices {
    @Autowired
    private CrossRepo crossRepo;
    public CrossSellingStrategy newCrossSellingStrategy(CrossSellingStrategy crossSellingStrategy){
       return crossRepo.save(crossSellingStrategy);
    }

    public List<CrossSellingStrategy> getCrossList(){
        return crossRepo.findAll();
    }
    
    // public void deleteCrossSellingStrategyById(int id){
    //     crossRepo.deleteById(id);
    // }

    // public boolean deleteCrossSellingStrategyById(int id){
    //     crossRepo.deleteById(id);
    //     if (!crossRepo.findById(id).isPresent()) {
    //         return true;
    //     }
    //     return false;
    // }

   
    public boolean deleteCrossSellingStrategyById(int id) {
        // var targetCrossSellingStrategy = crossRepo.findById(id);
        Optional<CrossSellingStrategy> targetCrossSellingStrategy = findById(id);


        if (!targetCrossSellingStrategy.isPresent()) {
            return false;
        } else {
            crossRepo.delete(targetCrossSellingStrategy.get());
            return true;
        }
    }

    public Optional<CrossSellingStrategy> findById(int id) {
        return crossRepo.findById(id);
    }

    public List<CrossSellingStrategy> findByName(String name) {
        return crossRepo.findByName(name);
    }
    


}