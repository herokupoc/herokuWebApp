/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class ContainerManager {

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    
    public void insertContainer(){ 
    	

    }   
    
    
    public User Login(User us){
        User user = null;
        String userFromDb;
        
        try {
            
            userFromDb = "SELECT u from User u where username = :name and organization= :org ";
            Query query = em.createQuery(userFromDb);
            query.setParameter("name", us.getUserName());
            query.setParameter("org", us.getOrganization());
            
            List<User> userList = query.getResultList();
            
            if(!userList.isEmpty()){
                user = userList.get(0);
            }
            
        } catch (Exception e) {
            throw e;
        
        }
    
        return user;
    }
    
}
