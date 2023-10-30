package edu.cpt202.group9.projb.shopMasterFileItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShopMasterFileItemServiceImpl implements ShopMasterFileItemService {
    @Autowired
    private ShopMasterFileItemRepo shopMasterFileItemRepo;

    //add
    @Override
    public boolean newShopMasterFileItem(ShopMasterFileItem shopMasterFileItem) {
        if (hasItemName(shopMasterFileItem.getItemName())) {
            return false;
        } else {
            shopMasterFileItemRepo.save(shopMasterFileItem);
            return true;
        }

    }

    //delete
    @Override
    public boolean deleteShopMasterFileItem(String itemName) {
        var targetShopMasterFileItem = shopMasterFileItemRepo.findByItemName(itemName);
        if (!targetShopMasterFileItem.isPresent()) {
            return false;
        } else {
            shopMasterFileItemRepo.cancelForeignKeyConstraint();
            shopMasterFileItemRepo.deleteByItemName(itemName);
            shopMasterFileItemRepo.enableForeignKeyConstraint();
            return true;
        }

    }

    //update
    @Override
    public boolean updateShopMasterFileItem(String itemName, double number) {
        var targetShopMasterFileItem = shopMasterFileItemRepo.findByItemName(itemName);

        if (targetShopMasterFileItem.isEmpty()) {
            return false;
        } else {
            shopMasterFileItemRepo.cancelForeignKeyConstraint();
            shopMasterFileItemRepo.updateShopMasterFileItem(number, itemName);
            shopMasterFileItemRepo.enableForeignKeyConstraint();
            return true;
        }

    }

    //find
    @Override
    public Optional<ShopMasterFileItem> findByItemName(String itemName) {
        return shopMasterFileItemRepo.findByItemName(itemName);
    }

    @Override
    public List<ShopMasterFileItem> findAllItem() {
        return shopMasterFileItemRepo.findAll();
    }

    @Override
    public boolean hasItemName(String itemName) {
        return shopMasterFileItemRepo.findByItemName(itemName).isPresent();
    }
}


