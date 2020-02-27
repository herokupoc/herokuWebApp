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

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;


/**
 *
 * @author ferreirai
 */
@Stateless
public class MailManager {
    
     @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;
     
    @EJB
    private ContainerManager fileUploadFacade;
    
        
    public void sendMail2User(String from,  String subject,String body){
       
        // Create a trust manager that does not validate certificate chains
    	/*
        TrustManager[] trustAllCerts = new TrustManager[] { 
            new X509TrustManager() {     
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
                    return new X509Certificate[0];
                } 
                @Override
                public void checkClientTrusted( 
                    java.security.cert.X509Certificate[] certs, String authType) {
                    } 
                @Override
                public void checkServerTrusted( 
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            } 
        }; 
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL"); 
            sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
        }
        //User user = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Email fromNew = new Email(from);
        String subjectNew = subject;
        //Email toNew = new Email(user.getEmail());
        Email toNew = new Email("inacio.ferreira@cgi.com");
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromNew, subjectNew, toNew, content);
        String apiKey = System.getenv("SENDGRID_API_KEY");
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
          request.method = Method.POST;
          request.endpoint = "mail/send";
          request.body = mail.build();
          String host = sg.getHost();
          
          System.out.println("\nHost: " + host);
          Response response = sg.api(request);
          System.out.println(response.statusCode);
          System.out.println(response.body);
          System.out.println(response.headers);
        } catch (IOException ex) {
          System.out.println("ERRRO: \n" + ex.getLocalizedMessage() );
        }
        
        */
       
    }
            
    public void sendMail2CentralTeam(String from, String subject,String body){
       
        String centralteamEmail = getCentralTeamEmail();
        Email fromNew = new Email(from);
        String subjectNew = subject;
        Email toNew = new Email(centralteamEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromNew, subjectNew, toNew, content);
        
        System.out.println("from: " + from);
        System.out.println("subject: " + subject);
        System.out.println("centralteamEmail: " + centralteamEmail);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
          request.method = Method.POST;
          request.endpoint = "mail/send";
          request.body = mail.build();
          Response response = sg.api(request);
          System.out.println(response.statusCode);
          System.out.println(response.body);
          System.out.println(response.headers);
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
       
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
    
}
