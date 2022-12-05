package com.parkin.parkinglot.ejb;

import com.parkin.parkinglot.common.CarDto;
import com.parkin.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.*;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("findAllCars");
        try{
            TypedQuery<Car> typedQuery=entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars= typedQuery.getResultList();
        return copyCarsToDto(cars);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carsdt = new ArrayList<CarDto>();
        for (Car car : cars) {
            carsdt.add(new CarDto(car.getId(), car.getLicencePlate(), car.getParkingSpot(), car.getOwner().getUsername()));
        }
        return carsdt;
    }
}
