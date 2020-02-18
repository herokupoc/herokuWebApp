/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.webservices;

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
//@Stateless
public class Job {

    private Job2Runner job2Runner;
    private Job3Runner job3Runner;
    
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
        //
        if (jobId.equals("2")) {
            job2Runner = new Job2Runner();
            System.out.println("CHAMEI O JOB 2");
            
            return "OK";  
        } if (jobId.equals("3")) {
            job3Runner = new Job3Runner();
            System.out.println("CHAMEI O JOB 3");
            return "OK";  
        } else {            
            System.out.println("ERRO !!!!!!!!!!!!");
            return "KO";  
        }
                
    }

    
}
