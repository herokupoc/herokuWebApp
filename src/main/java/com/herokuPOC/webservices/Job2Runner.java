/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.webservices;

import com.herokuPOC.entity.*;
import com.herokuPOC.services.AWSStorageFacadeTemp;
import com.herokuPOC.services.FileUploadFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author ferreirai
 */
public class Job2Runner {
    // declare
    private AWSStorageFacadeTemp aWSStorageFacade;
    private boolean success;
    private List<FileContainer> listFromDb ;
    
    @EJB
    private FileUploadFacade fuf;       
    
    private List<FileContainer> findAllUploadedToDb(){
        listFromDb = new ArrayList<>();        
        listFromDb = fuf.findAllUploadedToDb();
        aWSStorageFacade = new AWSStorageFacadeTemp();
        
        if (listFromDb.size() >= 0){
            listFromDb.forEach((FileContainer fileContainer) -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                String date = format.format( fileContainer.getUpload_date()   );
                String fileName = fileContainer.getName();                
                try {
                    // call the class that actually returns all the records from the Amazon S3 stored file
                    success = aWSStorageFacade.getRecordsFromFile(date,fileName);
                } catch (IOException ex) {
                    Logger.getLogger(Job2Runner.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (success) {
                    boolean update = fuf.update(fileContainer);
                }
            });
        } 
        
        return listFromDb;
    }
    
    public boolean run() {
        System.out.println("Job2Worker is running...");
        return true;
    }
    
}
