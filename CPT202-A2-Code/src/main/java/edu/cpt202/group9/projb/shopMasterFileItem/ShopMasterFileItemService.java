package edu.cpt202.group9.projb.shopMasterFileItem;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShopMasterFileItemService {
    boolean newShopMasterFileItem(ShopMasterFileItem shopMasterFileItem);
    boolean updateShopMasterFileItem(String itemName, double number);
    boolean deleteShopMasterFileItem(String itemName);
    Optional<ShopMasterFileItem> findByItemName(String itemName);
    List<ShopMasterFileItem> findAllItem();

    boolean hasItemName(String itemName);

}
