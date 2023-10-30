package edu.cpt202.group9.projb.service;

import javax.persistence.*;

@Entity(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String serviceName;
    private String description;
    private double servicePrice;
    private String serviceType;

    public Service() {
    }

    public Service(String serviceName, double servicePrice, String serviceType) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceType = serviceType;
    }

    public Service(String serviceName, String description, double servicePrice, String serviceType) {
        this.serviceName = serviceName;
        this.description = description;
        this.servicePrice = servicePrice;
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Service))
            return false;
        Service other = (Service) obj;
        return servicePrice == other.servicePrice &&
                serviceName.equals(other.serviceName) && description.equals(other.description) &&
                serviceType.equals(other.serviceType);
    }
}