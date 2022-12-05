package com.parkin.parkinglot.common;

public class CarDto {
    Long id;
    String licencePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(Long id, String licencePlate, String parkingSpot, String ownerName) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.parkingSpot = parkingSpot;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
