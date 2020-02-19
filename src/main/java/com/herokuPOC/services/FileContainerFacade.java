/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author evangelistap
 */
@Stateless
public class FileContainerFacade extends AbstractFacade<FileContainer> implements Serializable{

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FileContainerFacade() {
        super(FileContainer.class);
    }
    
    
    public List<FileContainer> SearchByInputForm(){
        List<FileContainer> filesFromDb = null;
            
        try {
            String querySearch = "SELECT f FROM Filecontainer f ";
            Query query = em.createQuery(querySearch);
            filesFromDb = query.getResultList();
            
        } catch (Exception e) {
        
        }
        
        return filesFromDb;
    }

}
