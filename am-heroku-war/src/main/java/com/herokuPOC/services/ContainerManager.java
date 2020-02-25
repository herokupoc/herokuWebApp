/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.Record;
import com.herokuPOC.entity.RecordH;
import com.herokuPOC.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.context.RequestContext;


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
      
      //em.getTransaction().begin();
      fileContainerTemp.setLoad_status("LOADED");
      em.persist(fileContainerTemp);       
      //em.getTransaction().commit();
      
      return true;
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
    public void createFileContainer(FileContainer fileContainer){
        //em.getTransaction().begin();  
        em.persist(fileContainer);       
        //em.getTransaction().commit();  
    }

    public List<FileContainer> findAllFileContainer(){        
        String fileContainerFromDb = "SELECT u from FileContainer u ";
        Query query = em.createQuery(fileContainerFromDb);
        List<FileContainer> fileContainerList = query.getResultList();        
        return fileContainerList;
    }
     public void createRecord(Record record){
        //em.getTransaction().begin();  
        em.persist(record);       
        //em.getTransaction().commit();  
    }       
    public List<RecordH> recordsFromFileId(String fileId){
        String recordListQuery;
        List<RecordH> recordsList = new ArrayList<>();
        try {
            
            recordListQuery = "SELECT h from RecordH h where file_id = :fileid ";
            Query query = em.createQuery(recordListQuery);
            query.setParameter("fileid", Integer.valueOf(fileId));
            
            recordsList = query.getResultList();
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in getting records from file!")); 
        }
    
        return recordsList;
    }
}
