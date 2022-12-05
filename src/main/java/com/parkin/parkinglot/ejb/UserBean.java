package com.parkin.parkinglot.ejb;

import com.parkin.parkinglot.common.CarDto;
import com.parkin.parkinglot.common.UserDto;
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
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<UserDto> findAllUser(){
        LOG.info("findAllUser");
        try{
            TypedQuery<User> typedQuery=entityManager.createQuery("SELECT c FROM User c", User.class);
            List<User> user= typedQuery.getResultList();
            return copyUserToDto(user);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private List<UserDto> copyUserToDto(List<User> users) {
        List<UserDto> usersdt = new ArrayList<UserDto>();
        for (User user : users) {
            usersdt.add(new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword()));
        }
        return usersdt;
    }
}
