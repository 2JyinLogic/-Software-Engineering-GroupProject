package edu.cpt202.group9.projb.sellingStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UpSellingStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String lowServiceName;
    private String highServiceName;

    public UpSellingStrategy() {
    }

    public UpSellingStrategy(String lowServiceName, String highServiceName) {
        this.lowServiceName = lowServiceName;
        this.highServiceName = highServiceName;
    }

    public UpSellingStrategy(int id, String lowServiceName, String highServiceName) {
        this.id = id;
        this.lowServiceName = lowServiceName;
        this.highServiceName = highServiceName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getLowServiceName() {
        return lowServiceName;
    }

    public String getHighServiceName() {
        return highServiceName;
    }

    public void setLowServiceName(String lowServiceName) {
        this.lowServiceName = lowServiceName;
    }

    public void setHighServiceName(String highServiceName) {
        this.highServiceName = highServiceName;
    }

    @Override
    public String toString() {
        return "UpSellingStrategy [id=" + id + ", lowServiceName=" + lowServiceName + ", highServiceName="
                + highServiceName + "]";
    }

    
    
}

