/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
/**
 *
 * @author evangelistap
 */
@ManagedBean(name = "FileUploadBean")
@SessionScoped
public class FileUploadBean {
    //class variables
    private UploadedFile file;
    private String bucketName = null;
    // https://docs.aws.amazon.com/zh_tw/sdk-for-java/v1/developer-guide/java-dg-region-selection.html
    private Regions clientRegion = null;
    private String fileObjKeyName = null;
    private AmazonS3 s3Client = null;
    
    public FileUploadBean(){
        bucketName = System.getenv("S3_BUCKET_NAME");
        System.out.println("bucketName: " + bucketName);
        clientRegion = Regions.EU_WEST_1;
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
		.withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
    }
    
    @EJB
    private FileUploadFacade fileuploadFacade;
    private FileContainer fileContainer;
    private String fileName;
    private String fileOrg;
    private String fileEncoding;
    private Timestamp timeStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
    
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

     
    /*public List<FileContainer> getFilesFromDb(){
      return fileuploadFacade.findAll();
    } 
    */
    
    
    public void CheckFileIntegrity(){
    //implement validation rules file 
    }
    
    public void insertFiletoDb(UploadedFile file){
        //implement insert on the DB 
        //example
        FileContainer fileContainertoDb = new FileContainer();
        
        fileContainertoDb.setId(1);
        fileContainertoDb.setLoad_status("COMPLETED");
        fileContainertoDb.setName(file.getFileName());
        fileContainertoDb.setUpload_date(timeStamp);
        fileContainertoDb.setRecord_err_qty(0);
        fileContainertoDb.setSf_qty_record_sync(0);
        fileContainertoDb.setRecord_qty(0);
        fileContainertoDb.setHeader("HEADER");
        
        fileuploadFacade.create(fileContainertoDb);
    }
    
    
    public void fileUploadListener(FileUploadEvent e){
    // Get uploaded file from the FileUploadEvent
	
    try {
                
            this.file = e.getFile();
            // Print out the information of the file
            System.out.println("Uploaded File Name: "+file.getFileName());
            System.out.println("Uploaded File Size: "+file.getSize());
            
            //if passes all validations insert on DB    
            //insertFiletoDb(file);
            
            upload(file.getFileName());
            
            //move file to Amazon storage
            // Add message
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Uploaded Successfully"));
	    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Uploaded Successfully"));
	    
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Failed to Upload!"));
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Failed to Upload!"));
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                       
	
    }

    public boolean upload(String fileName) throws IOException {
       try {
        // Upload a file as a new object with ContentType and title specified.
            
            System.out.println(this.fileName);
            
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
       
       return true;
   }

}