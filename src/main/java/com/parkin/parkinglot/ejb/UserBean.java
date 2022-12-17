package com.parkin.parkinglot.ejb;

import com.parkin.parkinglot.common.CarDto;
import com.parkin.parkinglot.common.UserDto;
import com.parkin.parkinglot.entities.Car;
import com.parkin.parkinglot.entities.User;
import com.parkin.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.*;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    @Inject
    PasswordBean passwordBean;
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

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }
    public void deleteUsersByIds(Collection<Long> userIds) {
        LOG.info("deleteUsersByIds");
        for(Long userId : userIds){
            User user = entityManager.find(User.class, userId);
            entityManager.remove(user);
        }
    }
}
