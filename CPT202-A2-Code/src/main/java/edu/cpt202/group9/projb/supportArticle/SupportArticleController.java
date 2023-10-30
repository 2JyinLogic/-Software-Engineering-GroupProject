package edu.cpt202.group9.projb.supportArticle;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class SupportArticleController {
    
    @Autowired
    SupportArticleService supportArticleService;

    @PostMapping("/articles/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        
        try {
            supportArticleService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "redirect:/articles";
    }

    @GetMapping("/articles")
    public String getListFiles(Model model) {
        List<SupportArticle> fileInfos = supportArticleService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(SupportArticleController.class, "getFile", path.getFileName().toString()).build()
                    .toString();

            return new SupportArticle(filename, url);
        }).collect(Collectors.toList());

        model.addAttribute("articles", fileInfos);

        return "storeenvironment";
    }

    @GetMapping("/articles/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = supportArticleService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


}
