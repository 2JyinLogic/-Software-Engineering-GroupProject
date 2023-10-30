package edu.cpt202.group9.projb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.cpt202.group9.projb.support.SupportArticle;
import edu.cpt202.group9.projb.support.SupportArticleRepository;

@Controller
public class HelpPageController {
    
    @Autowired
    SupportArticleRepository artRepo;

    @GetMapping("/help")
    public String helpMainPage(Model model) {
        model.addAttribute("articleList", artRepo.findAll());
        
        return "main_and_help_page/helppage";
    }
    
    @GetMapping("/help/article")
    public String articlePage(@RequestParam(name="name", required=true) String name, Model model) {
        var art = artRepo.findById(name);
        SupportArticle realart = null;

        if(art.isPresent()) {
            realart = art.get();
        }
        else {
            realart = new SupportArticle("NOT FOUND", "The article cannot be found", "anyname");
        }

        model.addAttribute("article", realart);

        // templates/article.html
        return "supportarticle";
    }
}
