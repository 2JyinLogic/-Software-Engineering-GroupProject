package edu.cpt202.group9.projb.shopMasterFileItem;



import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "shop_master_file_item")
public class ShopMasterFileItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String itemName;
    private double number;

    public ShopMasterFileItem() {
    }

    public ShopMasterFileItem(int id, String itemName, double number) {
        this.id = id;
        this.itemName = itemName;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ShopMasterFileItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", number=" + number +
                '}';
    }
}
