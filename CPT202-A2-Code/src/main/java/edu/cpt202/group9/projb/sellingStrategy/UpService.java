package edu.cpt202.group9.projb.sellingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UpService {

    @Autowired
    private UpRepo upRepo;
    
    public UpSellingStrategy newUpSellingStrategy(UpSellingStrategy upSellingStrategy){
       return upRepo.save(upSellingStrategy);
    }

    public List<UpSellingStrategy> getUpList(){
        return upRepo.findAll();
    }

    public boolean deleteUpSellingStrategyById(int id) {
        Optional<UpSellingStrategy> targetUpSellingStrategy = findById(id);
        
        if (!targetUpSellingStrategy.isPresent()) {
            return false;
        } else {
            System.out.println("11111");
            upRepo.delete(targetUpSellingStrategy.get());
            return true;
        }
    }

    public Optional<UpSellingStrategy> findById(int id) {
        return upRepo.findById(id);
    }

    /**
     * Finds all up-selling strategies of lowname
     * @param lowName
     * @return
     */
    public List<UpSellingStrategy> findByLowName(String lowName) {
        var ret = new ArrayList<UpSellingStrategy>();

        var all = upRepo.findAll();
        for(var up : all) {
            if(up.getLowServiceName().equals(lowName)) {
                ret.add(up);
            }
        }

        return ret;
    }
}
