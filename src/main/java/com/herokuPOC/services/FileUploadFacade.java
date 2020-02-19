/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.User;
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
  
  
  public List<FileContainer> findFileByNameHeader(FileContainer fileUploaded, String header){
    List<FileContainer> filesFromDb = em.createNamedQuery("fileContainer.findFileByNameHeader").setParameter("name", fileUploaded.getName()).setParameter("header", header).getResultList();
    return filesFromDb;
  }
  public List<FileContainer> findFileByNameHeader(String fileName, String header){
    List<FileContainer> filesFromDb = em.createNamedQuery("fileContainer.findFileByNameHeader").setParameter("name", fileName).setParameter("header", header).getResultList();
    return filesFromDb;
  }
  
  public List<FileContainer> findAllUploadedToDb(){
    //List<FileContainer> filesFromDb = em.createNamedQuery("fileContainer.findAllUploadedToDb").setParameter("load_status", "PENDING").getResultList();
    //return filesFromDb;
    
            String userFromDb = "SELECT u from FileContainer u where load_status = :name  ";
            Query query = em.createQuery(userFromDb);
            query.setParameter("name", "PENDING");
            List<FileContainer> fileContainerList = query.getResultList();
            return fileContainerList;
  }
  
  
  
  public boolean update(FileContainer fileContainer){
      
      FileContainer fileContainerTemp = em.find(FileContainer.class, fileContainer.getId());
      
      em.getTransaction().begin();
      fileContainerTemp.setLoad_status("LOADED");
      em.getTransaction().commit();
      
      return true;
  }
}
