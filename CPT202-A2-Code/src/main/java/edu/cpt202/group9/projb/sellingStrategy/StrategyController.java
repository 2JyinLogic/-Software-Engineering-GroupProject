package edu.cpt202.group9.projb.sellingStrategy;

import java.util.ArrayList;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.engine.AttributeName;

import edu.cpt202.group9.projb.service.ServiceServices;





@Controller
public class StrategyController {

@Autowired
private UpService upService;
@Autowired
private CrossServices crossService;
@Autowired
private ServiceServices serviceServices;

    @GetMapping("/SellingStrategy")
     public String getAllStrategy(Model model,
    @RequestParam(name = "deleteError", required = false) String deleteError,
    @RequestParam(name = "deleteSuccess", required = false) String deleteSuccess
     ){
        if (deleteError != null) {
            model.addAttribute("deleteError", deleteError);
        }

        if (deleteSuccess != null) {
            model.addAttribute("deleteSuccess", deleteSuccess);
        }
     
        model.addAttribute("upSellingStrategy", new UpSellingStrategy());
        model.addAttribute("upSellingStrategyList", upService.getUpList());
        model.addAttribute("upSellingStrategyAdd", new UpSellingStrategy());
        model.addAttribute("upSellingStrategyDelete", new UpSellingStrategy());
        model.addAttribute("crossSellingStrategy", new CrossSellingStrategy());
        model.addAttribute("crossSellingStrategyList", crossService.getCrossList());
        model.addAttribute("crossSellingStrategyAdd", new CrossSellingStrategy());
        model.addAttribute("crossSellingStrategyDelete", new CrossSellingStrategy());
        model.addAttribute("serviceList", serviceServices.findAllServices());
      
        return "selling";
    }
    // @GetMapping("/SellingStrategy")
    // public String getCrossStrategy(Model model){
    //     model.addAttribute("crossSellingStrategy", new CrossSellingStrategy());
    //     model.addAttribute("crossSellingStrategyList",crossService.getCrossList());
    //     model.addAttribute("crossSellingStrategyAdd", new CrossSellingStrategy());
    //     model.addAttribute("crossSellingStrategyDelete",new CrossSellingStrategy());
    //     return "selling";
    // }

    @PostMapping("/SellingStrategy/AddUp")
    public String confirmNewUpStrategy(Model model,@ModelAttribute("upSellingStrategyAdd") UpSellingStrategy upSellingStrategy,@ModelAttribute("model") UpSellingStrategy upSellingStrategy2){
        upService.newUpSellingStrategy(upSellingStrategy);
        return getAllStrategy(model,null,null);
    }   

    @DeleteMapping("/SellingStrategy/DeleteUp")
    public String deleteUp(Model model, @ModelAttribute("upSellingStrategyDelete") UpSellingStrategy upSellingStrategyDelete){
        boolean isDeleted = upService.deleteUpSellingStrategyById(upSellingStrategyDelete.getId());
        if (isDeleted == false) {
            model.addAttribute("deleteError", "Error: ID does not exist!");
            return "redirect:/SellingStrategy?deleteError=Error:ID does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: Strategy delete successfully!");
        return "redirect:/SellingStrategy?deleteSuccess=Succeed: Up strategy delete successfully!";
    }


    @PostMapping("/SellingStrategy/AddCross")
    public String confirmNewCrossStrategy(Model model,@ModelAttribute("crossSellingStrategyAdd") CrossSellingStrategy crossSellingStrategy){
        crossService.newCrossSellingStrategy(crossSellingStrategy);
        // return getAllStrategy(model);
        return "redirect:/SellingStrategy";

    }   

    // @DeleteMapping("/SellingStrategy/DeleteCross")
    // public String deleteCross(Model model, @ModelAttribute("crossSellingStrategyDelete") CrossSellingStrategy crossSellingStrategyDelete){
    //     crossService.deleteCrossSellingStrategyById(crossSellingStrategyDelete.getId());
    //     return getAllStrategy(model);
    // }

    @DeleteMapping("/SellingStrategy/DeleteCross")
    public String deleteCross(Model model,
            @ModelAttribute("crossSellingStrategyDelete") CrossSellingStrategy crossSellingStrategyDelete) {
        boolean isDeleted = crossService.deleteCrossSellingStrategyById(crossSellingStrategyDelete.getId());
        // System.out.println(crossSellingStrategyDelete.getId());
        // System.out.println(isDeleted);
        if (isDeleted == false) {
            model.addAttribute("deleteError", "Error: ID does not exist!");
            return "redirect:/SellingStrategy?deleteError=Error:ID does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: Strategy delete successfully!");
        return "redirect:/SellingStrategy?deleteSuccess=Succeed: Cross strategy delete successfully!";
    }

    
//     @RequestMapping("/ListCross")
//     public String ListCrossStrategy(Model model){
    
//     model.addAttribute("crossSellingStrategyList", crossService.getCrossList());
    
//         return "AllCrossStrategy";
//     }


//     @RequestMapping("/AddCross")
// public String AddCrossStrategy(Model model){
    
//     model.addAttribute("crossSellingStrategy", new CrossSellingStrategy());
    
//         return "AddCrossStrategy";
//     }

//     @PostMapping("/AddCross")
//     public String confirmNewCrossStrategy(@ModelAttribute("crossSellingStrategy") CrossSellingStrategy crossSellingStrategy){
//         crossService.newCrossSellingStrategy(crossSellingStrategy);
//         return "home";
//     }
  
//     @DeleteMapping("/DeleteCross")
// public String deleteCross(@ModelAttribute("crossSellingStrategy") CrossSellingStrategy crossSellingStrategy){
//     crossService.deleteCrossSellingStrategyById(crossSellingStrategy.getId());
//     return "home";
// }

 
   

    





   
}

