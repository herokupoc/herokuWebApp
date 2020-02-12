/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.services.FileUploadFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evangelistap
 */
@ManagedBean(name = "FileUploadBean")
@SessionScoped
public class FileUploadBean {
    //class variables
    UploadedFile file;
    
    @EJB
    private FileUploadFacade fileuploadFacade;
    private final Timestamp timeStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
    private List<FileContainer> ListFromDb;
    FileContainer fileContainertoDb = new FileContainer();
    FileContainer fileInDb = new FileContainer();
       
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

      
    public void CheckFileIntegrity(){
    //implement validation rules file 
    }
    
    public void fileValidations(UploadedFile file) throws IOException{
   
        InputStream filecontent = file.getInputstream();
        String fileLines = getFileContent(filecontent);
        
        String[] fileDelimited = fileLines.split("\\|");
        String header = fileDelimited[0];
        
        int fileRecordQty = fileLines.split("\r\n|\r|\n").length -1;
        
        fileContainertoDb.setId(1);
        fileContainertoDb.setLoad_status("PENDING");
        fileContainertoDb.setName(file.getFileName());
        fileContainertoDb.setUpload_by("UserName");
        fileContainertoDb.setUpload_date(timeStamp);
        fileContainertoDb.setRecord_err_qty(0);
        fileContainertoDb.setSf_qty_record_sync(0);
        fileContainertoDb.setRecord_qty(fileRecordQty);
        fileContainertoDb.setHeader(header);
        
        ListFromDb = fileuploadFacade.getFileByName(fileContainertoDb);

        //WORKING ON VALIDATIONS
        /*       
        if (ListFromDb.size() > 0){
            for (int i = 0; i < ListFromDb.size(); i++) {
                if (equals(fileContainertoDb.getName())){
                    if (fileInDb.getHeader().equals(fileContainertoDb.getHeader())){
                        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Testing diferent error messages", "File Already been uploaded!" + file.getFileName())); 
                        return;
                    }
                }
            }
        }  
  */      
    }
        
    
    
    
    public void fileUploadListener(FileUploadEvent e){
    // Get uploaded file from the FileUploadEvent
    try {
                
            this.file = e.getFile();
            
            System.out.println("Uploaded File Name: "+file.getFileName());
            
            if (file.getFileName().substring(0, file.getFileName().indexOf('.')).length() != 8){
               //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Testing diferent error messages", "Filename is not correct: " + file.getFileName())); 
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Filename is not correct"));
               return;
            }
            
            
            //if passes all validations inserts on DB    
            fileValidations(file);
            insertFileUploadToDb();            
            
            //move file to Amazon storage
            // Add message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "File Uploaded!"));
	    
            
        } catch (Exception ex) {
             RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Testing Validations", "File Already been uploaded! ->" + file.getFileName())); 
             Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        primefaces messages
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Watch out for PrimeFaces."));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
         */
    }

    private void insertFileUploadToDb() {
       fileuploadFacade.create(fileContainertoDb);
    }

    
   public String getFileContent( InputStream fis ) throws UnsupportedEncodingException, IOException {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    
    while ((length = fis.read(buffer)) != -1) {
        result.write(buffer, 0, length);
    
    }
    // StandardCharsets.UTF_8.name() > JDK 7
    
    return result.toString("UTF-8");
    
   }


}
   
