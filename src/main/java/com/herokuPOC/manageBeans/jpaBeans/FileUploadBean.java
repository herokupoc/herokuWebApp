/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
/**
 *
 * @author evangelistap
 */
@ManagedBean(name = "FileUploadBean")
@SessionScoped
public class FileUploadBean {
    //class variables
    private UploadedFile file;
    private String fileObjKeyName = null;
    private String bucketName = null;
    private Regions clientRegion = null;
    private AmazonS3 s3Client = null;
    
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
            upload( e);
            // Add message
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Uploaded Successfully"));
	    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Uploaded Successfully"));
	    
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Failed to Upload!"));
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("File Failed to Upload!"));
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                       
	
    } 
    
    
 public boolean upload(FileUploadEvent fUEvent) throws IOException {
       try {
        //
        String pattern = "yyyyMMdd"; 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); 
        String date = simpleDateFormat. format(new Date()); 
        System. out. println(date);
           
        String fileNameIn = fUEvent.getFile().getFileName();
        InputStream in = fUEvent.getFile().getInputstream();
        File tempFile = File.createTempFile("temp", "txt");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        copyStream (in, out);
        out.close();        
        
        // Upload a file as a new object with ContentType and title specified.
        bucketName = System.getenv("S3_BUCKET_NAME");
        System.out.println("bucketName: " + bucketName);
        clientRegion = Regions.EU_WEST_1;
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
		.withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
            System.out.println(this.fileName);
            //fileObjKeyName = fileNameIn.replace("\\","_");
            //fileObjKeyName = fileNameIn.replace('/', '_');
            //fileObjKeyName = fileNameIn.replace(' ', '_');
            PutObjectRequest request = new PutObjectRequest(bucketName, date+"/"+fileNameIn, tempFile);
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
   public static void copyStream(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
    }
}
}