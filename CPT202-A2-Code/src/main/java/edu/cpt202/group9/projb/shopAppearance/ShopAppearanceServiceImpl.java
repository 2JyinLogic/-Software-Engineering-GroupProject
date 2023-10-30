package edu.cpt202.group9.projb.shopAppearance;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ShopAppearanceServiceImpl implements ShopAppearanceService {

    @Autowired
    private ShopAppearanceRepository shopAppearanceRepo;

    @Override
    public List<ShopAppearance> findAllShopAppearances() {
        return shopAppearanceRepo.findAll();
    }

    @Override
    public Optional<ShopAppearance> findByFileName(String fileName){
        return shopAppearanceRepo.findByFileName(fileName);
    }

    @Override
    public boolean addShopAppearance(MultipartFile file, String description)
            throws IOException {

        if (file.isEmpty()) {
            return false;
        }

        long maxFileSize = 2 * 1024 * 1024;
        if (file.getSize() > maxFileSize) {
            return false;
        }

        String fileType = file.getContentType();
        if (fileType != null && !fileType.startsWith("image/")) {
            return false;
        }

        String fileName = file.getOriginalFilename();
        byte[] data = file.getBytes();

        ShopAppearance shopAppearance = new ShopAppearance(fileName, fileType, data, null, description);
        ShopAppearance savedShopAppearance = shopAppearanceRepo.save(shopAppearance);

        return savedShopAppearance != null;
    }

    @Override
    public boolean deleteShopAppearanceByFileName(String fileName) {
        var targetImage = shopAppearanceRepo.findByFileName(fileName);

        if (!targetImage.isPresent()) {
            return false;
        } else {
            shopAppearanceRepo.delete(targetImage.get());
            return true;
        }
    }

}
