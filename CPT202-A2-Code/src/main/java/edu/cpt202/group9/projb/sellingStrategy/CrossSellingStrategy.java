package edu.cpt202.group9.projb.sellingStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CrossSellingStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    // name of the service that the user selected
    private String name;
    // @GenericGenerator(name = "uuid", strategy = "uuid2")
    // private String CrossName;
    // @OneToOne
    // private Service service1;
    // @OneToOne
    // private Service service2;
    // @OneToOne
    // private Service service3;
    // @OneToOne
    // private Service service4;
    // @OneToOne
    // private Service service5;
    
    private String serviceA;
    private String serviceB;
    private String serviceC;
    private String serviceD;
    private String serviceE;

    public CrossSellingStrategy() {}
    
    public CrossSellingStrategy(int id, String name, String serviceA, String serviceB, String serviceC, String serviceD,
            String serviceE) {
        this.id = id;
        this.name = name;
        this.serviceA = serviceA;
        this.serviceB = serviceB;
        this.serviceC = serviceC;
        this.serviceD = serviceD;
        this.serviceE = serviceE;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
   
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setServiceA(String serviceA) {
        this.serviceA = serviceA;
    }
    public String getServiceA() {
        return serviceA;
    }
   
    public String getServiceB() {
        return serviceB;
    }
    public void setServiceB(String serviceB) {
        this.serviceB = serviceB;
    }
    public String getServiceC() {
        return serviceC;
    }
    public void setServiceC(String serviceC) {
        this.serviceC = serviceC;
    }
    public String getServiceD() {
        return serviceD;
    }
    public void setServiceD(String serviceD) {
        this.serviceD = serviceD;
    }
    public String getServiceE() {
        return serviceE;
    }
    public void setServiceE(String serviceE) {
        this.serviceE = serviceE;
    }

}
