package edu.cpt202.group9.projb.shopAppearance;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ShopAppearanceService {

    public List<ShopAppearance> findAllShopAppearances();

    public boolean addShopAppearance(MultipartFile file, String description) throws IOException;

    public boolean deleteShopAppearanceByFileName(String fileName);

    public Optional<ShopAppearance> findByFileName(String fileName);
    
}
