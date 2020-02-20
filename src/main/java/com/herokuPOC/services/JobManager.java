/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.MailStore;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;


@Stateless
public class JobManager {
    private List<FileContainer> listFromDb ;
    private boolean success;    

    
    
    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;
    
    @EJB
    private StorageManager storageManager; 
    @EJB
    private ContainerManager fileUploadFacade;
    
    
    public void executeJob1(){ 
        
        listFromDb = new ArrayList<>();
        // verify all the FileContainers that where validated and need sending the email.
        //listFromDb = fileUploadFacade.findAllUploadedToDb();
        // for each send an email
        //boolean update = fileUploadFacade.update(fileContainer);
        
        // get all the FileContainer that need to be brought from AWS Storage and inserted into the database
        listFromDb = fileUploadFacade.findAllUploadedToDb();
        if (listFromDb.size() >= 0){
            // for each file do
            listFromDb.forEach((FileContainer fileContainer) -> {
                // Get the folder with the date when it was uploaded
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                String date = format.format( fileContainer.getUpload_date()   );
                // get the filename from the database
                String fileName = fileContainer.getName();                
                try {
                    // call the class that reads all the records from the Amazon S3 stored file and stores them onto the database
                    success = storageManager.getRecordsFromFile(date,fileName,fileContainer);
                } catch (IOException ex) {
                    Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (success) {
                    // call the PostGreSQL function to validate
                    //boolean update = fileUploadFacade.update(fileContainer);
                    
                }
            });
        } 
        
 
        System.out.println("CHAMEI O JOB 2");
    }
    
    public void executeJob2(){ 
       
         StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.test");
    // set parameters
      storedProcedure.registerStoredProcedureParameter("var", String.class, ParameterMode.IN);
      storedProcedure.registerStoredProcedureParameter("var", String.class, ParameterMode.OUT);
      storedProcedure.setParameter( "var","FODASSE");
      storedProcedure.executeUpdate();

      String objectList = (String)storedProcedure.getSingleResult();
      System.out.println("objectList : " + objectList);
      //for (int i = 0; i< objectList.size(); i++) {
          //Account currAccount = new Account ((Object[]) objectList.get(i));
          //tmpList.add(currAccount);
         // System.out.println("currAccount : " + currAccount.toString()); 
    
      //}
        
    }
    
    public void sendEmail(String from, String to, String subject,String body){
        em.getTransaction().begin();
        MailStore mailStore = new MailStore();
        mailStore.setSendTo(to);
        mailStore.setSentFrom(from);
        mailStore.setSubject(subject);
        mailStore.setBody(body);
        em.persist(mailStore);
        em.getTransaction().commit();
    }
    
    
}