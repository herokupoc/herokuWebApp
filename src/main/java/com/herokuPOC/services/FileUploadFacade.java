/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.Airline;
import com.herokuPOC.entity.FileContainer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author evangelistap
 */
@Stateless
public class FileUploadFacade extends AbstractFacade<FileContainer> {

  @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public FileUploadFacade() {
    super(FileContainer.class);
  }
  
  
  /*public List<FileContainer> getFileByNameAndDate(FileContainer fileUploaded){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FileContainer> q = cb.createQuery(FileContainer.class);
        return em.createNamedQuery("fileContainer.findByNameAndDate", FileContainer.class).setParameter(0, fileUploaded.getName()).getResultList();
  }*/
  
}
