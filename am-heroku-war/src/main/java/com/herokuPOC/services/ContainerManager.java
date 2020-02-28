/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.OrgEncoding;
import com.herokuPOC.entity.Record;
import com.herokuPOC.entity.RecordH;
import com.herokuPOC.entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.primefaces.context.RequestContext;


@Stateless
public class ContainerManager {

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;
    
    @EJB
    private MailManager mailManager; 

    
    public void insertContainer(){ 
    	

    }   
    
    
    public User Login(User us){
        User user = null;
        String userFromDb;
        
       try{
  
            
            userFromDb = "SELECT u from User u where username = :name and organization= :org ";
            Query query = em.createQuery(userFromDb);
            query.setParameter("name", us.getUserName());
            query.setParameter("org", us.getOrganization());
            
            List<User> userList = query.getResultList();
            
            if(!userList.isEmpty()){
                user = userList.get(0);
            }
            
        } catch (Exception e) {
            
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Amadeus heroku Web App Error on Login","Hi, we have an error on Login: \n" + e.getLocalizedMessage());
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
      fileContainerTemp.setLoad_status("VALIDATED");
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
        //em.flush();
        //em.getTransaction().commit();  
    }

    public List<FileContainer> findAllFileContainer(){        
        String fileContainerFromDb = "SELECT u from FileContainer u ";
        Query query = em.createQuery(fileContainerFromDb);
        List<FileContainer> fileContainerList = query.getResultList();        
        return fileContainerList;
    }
    
    public List<OrgEncoding> findAllOrg(){        
        String fileContainerFromDb = "SELECT u from OrgEncoding u ";
        Query query = em.createQuery(fileContainerFromDb);
        List<OrgEncoding> orgList = query.getResultList();        
        return orgList;
    }
    
     public List<FileContainer> findFileContainerByCriteria(String status, String organization, String startdate, String enddate) throws ParseException{
        String recordListQuery;
        boolean extra_param1 = false;
        boolean extra_param2 = false;
        List<FileContainer> fileList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        Calendar cal =Calendar.getInstance();
        
        String dateInString = startdate;
        String dateInString2 = enddate;
        Date date1 = formatter.parse(dateInString);
        Date date2 = formatter.parse(dateInString2);
        
        cal.setTime(date2);
        cal.add(Calendar.HOUR_OF_DAY,23);
        cal.add(Calendar.MINUTE,59);
        date2 = cal.getTime();
        
        try {
            
            recordListQuery = "SELECT f from FileContainer f where upload_date BETWEEN  ?1 and ?2 ";
          
            if (!organization.isEmpty()){
                recordListQuery = recordListQuery.concat(" and substring(name,0,3) = :org");
                extra_param1 = true;
            }
            if (!status.isEmpty()){
                recordListQuery = recordListQuery.concat(" and load_status = :status");
                extra_param2 = true;
            }
            
            Query query = em.createQuery(recordListQuery);
            
            query.setParameter(1, date1, TemporalType.DATE);
            query.setParameter(2, date2, TemporalType.DATE);
            
            if(extra_param1){
                query.setParameter("org", organization);
            }
            
            if(extra_param2){
                query.setParameter("status", status);
            }
               
            fileList = query.getResultList();
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in getting records from file!")); 
        }
    
        return fileList;
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
            
            recordListQuery = "SELECT h from RecordH h where filecontainerid = :fileid ";
            Query query = em.createQuery(recordListQuery);
            query.setParameter("fileid", Integer.valueOf(fileId));
            
            recordsList = query.getResultList();
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in getting records from file!")); 
        }
    
        return recordsList;
    }
    public FileContainer findFileByName(int id){
        FileContainer filesFromDb;
        filesFromDb = (FileContainer)em.createNamedQuery("fileContainer.findFileById").setParameter("id", id).getSingleResult();
    return filesFromDb;
  }
    
}
