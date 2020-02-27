/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.webservices;

import com.herokuPOC.services.JobManager;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author ferreirai
 */
@Path("/job")
@Produces({MediaType.APPLICATION_JSON})
@Stateless
public class Job {
    @EJB
    private JobManager jobManager;
        
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Job
     */
    public Job() {
  
    }
    /**
     * Retrieves representation of an instance of com.herokuPOC.webservices.Job
     * @param jobId
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String runJob(@PathParam("jobId") String jobId) {
        
        if (jobId.equals("2")) {
            jobManager.executeJob1();
            System.out.println("CHAMEI O JOB 2");
            
            return "OK";  
        } if (jobId.equals("3")) {
            jobManager.executeJob2();
            System.out.println("CHAMEI O JOB 3");
            return "OK";  
        } else {            
            System.out.println("ERRO !!!!!!!!!!!!");
            return "KO";  
        }                
    }
}
