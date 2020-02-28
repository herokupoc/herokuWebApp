/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.FileContainer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
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
    @EJB
    private MailManager mailManager; 

 
    public void executeJob1(){ 
        
        
        //String body1 = "The file " + "Test" +" was validated and can be checked for errors in the records.\n";
         //                   mailManager.sendMail2User("general@amadeus.com", "Amadeus POC - File validated: " + "Test", body1);
        
        listFromDb = new ArrayList<>();
        
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
                    if (success) {
                        // calls the Stored procedure to validate the data integrity
                        boolean integrityDone = checkDataIntegrity(fileContainer.getId());
                        // update the Container File Status to LOADED 
                        boolean update = fileUploadFacade.update(fileContainer);
                        if(integrityDone && update) {
                            // send email to the user saying that the file is vailable for searching in the webapp
                            String body = "The file " + fileName +" was validated and can be checked for errors in the records.\n";
                            //mailManager.sendMail2User("general@amadeus.com", "Amadeus POC - File validated: " + fileName, body);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
                    // send email to central team
                    String body = "Error on process:" + "JOB3\n";
                    body = body + ex.getLocalizedMessage();
                    mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Error on heroku POC WebApp",body);
                }
                if (success) {
                    // call the PostGreSQL function to validate
                    boolean update = fileUploadFacade.update(fileContainer);                    
                }
            });
        }         
        System.out.println("ENDED JOB 2");
    }
    
    public boolean checkDataIntegrity(int fileContainerId){
        boolean bReturn = true;
        try{
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.checkdataintegrity");
         // set parameters
           storedProcedure.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
           storedProcedure.registerStoredProcedureParameter(2, Boolean.class, ParameterMode.OUT);
           storedProcedure.setParameter(1, fileContainerId);
           Boolean out = (Boolean)storedProcedure.getOutputParameterValue(2);
           
           System.out.println("out : " + out.toString());                     
 
       } catch (IllegalArgumentException | IllegalStateException | java.lang.ClassCastException iae){
           // send email to central team
           String body = "Error on process: checkdataintegrity " + "JOB3" + "\n";
           body = body + iae.getLocalizedMessage();
           mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Error on heroku POC WebApp",body);
           bReturn = false;           
       }
        return bReturn;
    }
    
    public void executeJob2(){ 
       try{                    
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.integratedata_sf");
         // set parameters
           storedProcedure.registerStoredProcedureParameter(1, Boolean.class, ParameterMode.OUT);
 
           Boolean out = (Boolean)storedProcedure.getOutputParameterValue(1);

           System.out.println("integratedata_sf output : " + out.toString());                     
          
            String body = "The Container files have now been processed! You can go and check the Status of the records on the Web App!";
            mailManager.sendMail2User("herokuwebapp@amadeus.com","WebApp - Containers validated",body);
           
       } catch (IllegalArgumentException | IllegalStateException iae){
           // send email to central team
           String body = "Error on process: integratedata_sf " + "JOB3" + "\n";
           body = body + iae.getLocalizedMessage();
           mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Error on heroku POC WebApp",body);
          
       }       
    }
    /*
    public void sendEmail(String from, String to, String subject,String body){
        //em.getTransaction().begin();
        MailStore mailStore = new MailStore();
        mailStore.setSendTo(to);
        mailStore.setSentFrom(from);
        mailStore.setSubject(subject);
        mailStore.setBody(body);
        em.persist(mailStore);
        //em.getTransaction().commit();
    }
    
    public String getCentralTeamEmail(){
        String out = null;
        try{
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("util.get_t_util_header_lookup");
         // set parameters
           storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		   storedProcedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
           storedProcedure.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
           storedProcedure.setParameter( 1,"CENTRAL_TEAM_EMAIL");
		   storedProcedure.setParameter( 2,"online");

           out = (String)storedProcedure.getOutputParameterValue(3);

           System.out.println("out : " + out);
           
           
           //User us = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
           
       } catch (IllegalArgumentException | IllegalStateException iae){
          // send email to central teamsendEmail("inacio.ferreira@cgi.com","inacio.ferreira@cgi.com","subject","body");
       }
        return out;
    }
*/
}