package com.parkin.parkinglot.ejb;

import com.parkin.parkinglot.common.CarDto;
import com.parkin.parkinglot.entities.Car;
import com.parkin.parkinglot.entities.User;
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

    public void createCar(String licencePlate, String parkingSpot, Long userId){
        LOG.info("createCar");

        Car car= new Car();
        car.setLicencePlate(licencePlate);
        car.setParkingSpot(parkingSpot);

        User user= entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public void updateCar(Long carId, String licencePlate, String parkingSpot, Long userId) {
        LOG.info("updating a car");

        Car car = entityManager.find(Car.class, carId);
        car.setLicencePlate(licencePlate);
        car.setParkingSpot(parkingSpot);

        User oldUser = car.getOwner();
        oldUser.getCars().remove(car);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
    }

    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("deleting car");

        for(Long carId : carIds){
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

    public CarDto findById(Long carId) {
        Car car = entityManager.find(Car.class, carId);
        CarDto car_obj = new CarDto(car.getId(), car.getLicencePlate(), car.getParkingSpot(), car.getOwner().getUsername());
        return car_obj;
    }
}
