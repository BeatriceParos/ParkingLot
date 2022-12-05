package com.parkin.parkinglot.entities;

import jakarta.persistence.*;

@Entity
public class Car {
    private User owner;

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private Long id;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String licencePlate;

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    private String parkingSpot;

    @Basic
    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getLicencePlate() {
        return licencePlate;
    }
}
