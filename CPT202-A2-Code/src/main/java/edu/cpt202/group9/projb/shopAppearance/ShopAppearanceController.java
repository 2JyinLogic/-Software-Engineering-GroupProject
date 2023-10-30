package edu.cpt202.group9.projb.shopAppearance;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/shop-appearance")
public class ShopAppearanceController {

    @Autowired
    private ShopAppearanceService shopAppearanceService;

    @Autowired
    private ShopAppearanceRepository shopAppearanceRepo;

    @GetMapping("/find-all")
    public String findAllShopAppearances(Model model,
            @RequestParam(name = "fileNameError", required = false) String fileNameError,
            @RequestParam(name = "fileDuplicated", required = false) String fileDuplicated,
            @RequestParam(name = "deleteError", required = false) String deleteError,
            @RequestParam(name = "deleteSuccess", required = false) String deleteSuccess,
            @RequestParam(name = "descriptionCharacterError", required = false) String descriptionCharacterError,
            @RequestParam(name = "descriptionLengthError", required = false) String descriptionLengthError) {

        if (fileDuplicated != null) {
            model.addAttribute("fileDuplicated", fileDuplicated);
        }

        if (fileNameError != null) {
            model.addAttribute("fileNameError", fileNameError);
        }

        if (deleteError != null) {
            model.addAttribute("deleteError", deleteError);
        }

        if (deleteSuccess != null) {
            model.addAttribute("deleteSuccess", deleteSuccess);
        }

        if (descriptionCharacterError != null) {
            model.addAttribute("descriptionCharacterError", descriptionCharacterError);
        }

        if (descriptionLengthError != null) {
            model.addAttribute("descriptionCharacterError", descriptionLengthError);
        }

        List<ShopAppearance> images = shopAppearanceService.findAllShopAppearances();
        for (ShopAppearance image : images) {
            byte[] imageData = image.getImageData();
            String base64Encoded = Base64.getEncoder().encodeToString(imageData);
            image.setBase64Encoded(base64Encoded);
        }

        model.addAttribute("images", images);
        model.addAttribute("shopAppearanceDelete", new ShopAppearance());
        return "shopappearance";
    }

    @PostMapping("/add-shop-appearance")
    public String addShopAppearance(Model model,
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) throws IOException {

        String fileName = file.getOriginalFilename();

        if ((fileName != null) && (!fileName.matches("^[a-zA-Z0-9_.\\-]+$"))) {
            model.addAttribute("fileNameError",
                    "File name can only contain English letters, numbers, underscores, hyphens.");
            return "redirect:/shop-appearance/find-all"
                    + "?fileNameError=File name can only contain English letters, numbers, underscores, hyphens.";
        }

        Optional<ShopAppearance> appearance = shopAppearanceRepo.findByFileName(fileName);
        if (appearance.isPresent()) {
            model.addAttribute("fileDuplicated", "The file with name '" + fileName + "' already exists.");
            return "redirect:/shop-appearance/find-all"
                    + "?fileDuplicated=The file with name '" + fileName + "' already exists.";
        }

        if (file.getSize() > 0.5 * 1024 * 1024) {
            System.out.println("2222222");
            model.addAttribute("message", "File size exceeds 0.5MB.");
            return "redirect:/shop-appearance/find-all";
        }

        if (!description.matches("^['\"\\sA-Za-z0-9\n.,;:?!()\\-\\$&*#%]+$")) {
            model.addAttribute("descriptionCharacterError",
                    "Description cannot contain Chinese characters!");
            return "redirect:/shop-appearance/find-all"
                    + "?descriptionCharacterError=Description cannot contain Chinese characters!";
        }

        int maxDescriptionLength = 100;
        if (description.length() > maxDescriptionLength) {
            model.addAttribute("descriptionLengthError",
                    "Description exceeds maximum length of " + maxDescriptionLength + " characters.");
            return "redirect:/shop-appearance/find-all"
                    + "?descriptionLengthError=Description cannot exceed 100 words!";
        }

        try {
            shopAppearanceService.addShopAppearance(file, description);
            model.addAttribute("message", "File uploaded successfully");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "redirect:/shop-appearance/find-all";
    }

    @DeleteMapping("/delete-shop-appearance")
    public String deleteShopAppearance(Model model,
            @ModelAttribute("shopAppearanceDelete") ShopAppearance shopAppearanceDelete) {
        boolean isDeleted = shopAppearanceService.deleteShopAppearanceByFileName(shopAppearanceDelete.getFileName());

        if (isDeleted == false) {
            model.addAttribute("deleteError", "Error: File does not exist!");
            return "redirect:/shop-appearance/find-all?deleteError=Error: File does not exist!";
        }
        model.addAttribute("deleteSuccess", "Succeed: File delete successfully!");
        return "redirect:/shop-appearance/find-all?deleteSuccess=Succeed: File delete successfully!";
    }

}
