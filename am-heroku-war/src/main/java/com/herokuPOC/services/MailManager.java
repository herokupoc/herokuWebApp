/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;


import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;


/**
 *
 * @author ferreirai
 */
@Stateless
public class MailManager {
    
     @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;
     
    private ContainerManager fileUploadFacade;
     
        
        
    public void sendMail2User(String from,  String subject,String body){
        /*
        Configuration configuration = new Configuration()
        .domain(System.getenv("MAILGUN_DOMAIN"))
        .apiKey(System.getenv("MAILGUN_API_KEY"))
        .from("Heroko WebApp", from);

            Mail.using(configuration)
        .to("nader.rouis@amadeus.com")
        .subject(subject)
        .text(body)
        .build()
        .send();       
       */
    }
            
    public void sendMail2CentralTeam(String from, String subject,String body){
        //String centralteamEmail = getCentralTeamEmail();
       /*
        String centralteamEmail = "nader.rouis@amadeus.com";
        Configuration configuration = new Configuration()
        .domain(System.getenv("MAILGUN_DOMAIN"))
        .apiKey(System.getenv("MAILGUN_API_KEY"))
        .from("Heroko WebApp", from);

            Mail.using(configuration)
        .to(centralteamEmail)
        .subject(subject)
        .text(body)
        .build()
        .send();  
           */
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
    	   iae.printStackTrace();
          // send email to central teamsendEmail("inacio.ferreira@cgi.com","inacio.ferreira@cgi.com","subject","body");
       }
        return out;
    }
    
}
