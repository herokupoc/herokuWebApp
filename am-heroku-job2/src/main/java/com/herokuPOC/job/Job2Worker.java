/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author ferreirai
 */

public class Job2Worker {  
    
    static HttpClient client ;
    static HttpGet request ;
    static HttpResponse response ;
    

    public static void main(String[] args) throws ClientProtocolException,IOException{
    	
    	System.out.print("Ejecutando Job2");       	
	    client = new DefaultHttpClient();
	    request = new HttpGet(System.getenv("JOB_API_URI")+"/job/2");
	    response = client.execute(request);
	    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	    String line = "";
	    while ((line = rd.readLine()) != null) {
	      System.out.println(line);

	    }
            
    	      
    }   

}

