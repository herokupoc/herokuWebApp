package com.herokuPOC.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.Record;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.event.FileUploadEvent;

@Stateless
public class StorageManager {
    
    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;
    
    @EJB
    private ContainerManager recordFacade; 
   
    @EJB
    private MailManager mailManager;
    
    public StorageManager() {

   }
    
    
    
    public boolean upload(FileUploadEvent fUEvent) throws IOException {
        String fileObjKeyName = null;
        String bucketName = null;
        Regions clientRegion = null;
        AmazonS3 s3Client = null;
        
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
           try (FileOutputStream out = new FileOutputStream(tempFile)) {
               copyStream (in, out);
           }        
        
        // Upload a file as a new object with ContentType and title specified.
        bucketName = System.getenv("S3_BUCKET_NAME");
        System.out.println("bucketName: " + bucketName);
        clientRegion = Regions.EU_WEST_1;
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
		.withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
            System.out.println(fileNameIn);
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
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Amadeus heroku Web App Storage Error","Hi, we have an error on Storage: \n The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response. \n" + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Amadeus heroku Web App Storage Error","Hi, we have an error on Storage: \n Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3. \n" + e.getLocalizedMessage());
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
   
   public boolean getRecordsFromFile(String date,String fileNameId,FileContainer fileContainer) throws IOException{
       List<Record> listToDb = new ArrayList<Record>();
       
       S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        String fileObjKeyName = null;
        String bucketName = null;
        Regions clientRegion = null;
        AmazonS3 s3Client = null; 
        
        bucketName = System.getenv("S3_BUCKET_NAME");
        System.out.println("bucketName: " + bucketName);
        clientRegion = Regions.EU_WEST_1;
        try {
            s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
		.withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
				
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, date+"/"+fileNameId));
            
            listToDb = getRecordList(fileNameId,fullObject.getObjectContent(),fileContainer);
            if (listToDb.isEmpty()) return false;
            for (Record record : listToDb) {
                if(record.getFileContainer() != null){
                    recordFacade.createRecord(record);
                }
            }
            
         } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Amadeus heroku Web App Storage Error","Hi, we have an error on Storage: \n The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response. \n" + e.getLocalizedMessage());
            return false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com","Amadeus heroku Web App Storage Error","Hi, we have an error on Storage: \n Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3. \n" + e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }                      
        }
        return true;
   }
   
   // TO DO -> this function should return an Array of File Records
    private List<Record> getRecordList(String fileName, InputStream input,FileContainer fileContainer) throws IOException {
        List<Record> listToDb = new ArrayList<Record>();
        Record recordToDb = new Record();
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        // while there is a new line and this new line is not the header record
        int i = 1;
        while ((line = reader.readLine()) != null) {
            if (i==1){
                i++;
                continue;
            }
            System.out.println(line);
            
            String[] arrOfStr = line.split("\\|"); 
            StringTokenizer st1 = 
             new StringTokenizer(line, "|"); 
            try {
 
                recordToDb.setFile_line(Integer.parseInt(arrOfStr[0]));
                recordToDb.setErr_type(arrOfStr[1]);
                recordToDb.setErr_msg(arrOfStr[2]);
                recordToDb.setContact_flag(arrOfStr[3]);
                recordToDb.setAccount_flag(arrOfStr[4]);
                recordToDb.setOwner_flag(arrOfStr[5]);
                recordToDb.setSfcontact_id(arrOfStr[6]);
                recordToDb.setContact_org(arrOfStr[7]);
                recordToDb.setSalutation(arrOfStr[8]);
                recordToDb.setFirstname(arrOfStr[9]);
                recordToDb.setMidname(arrOfStr[10]);
                recordToDb.setLastname(arrOfStr[11]);
                recordToDb.setMail(arrOfStr[17]);
                recordToDb.setCellular(arrOfStr[13]);
                recordToDb.setAccount_segmentation(arrOfStr[22]);
                recordToDb.setSfaccount_id(arrOfStr[23]);
                recordToDb.setSfowner_id(arrOfStr[24]);
                recordToDb.setFileContainer(fileContainer);
                
            } catch(NoSuchElementException nsee) {
                nsee.printStackTrace();
            }
            listToDb.add(recordToDb);
            recordToDb = new Record();
            //
            /*
            0  CO-D-01-04Feb2020-F-I-06-001-CP1252|	-> file_line
            1  ERROR_TYPE|				-> err_type
            2  ERROR_MSG|				-> err_msg
            3  Contact Flag|  				-> contact_flag
            4  Account Flag|				-> account_flag
            5  Sales Team Flag|				-> owner_flag
            6  Salesforce Contact Id|			-> sfcontact_id
            7  Contact Organization|			-> contact_org
            8  Salutation|				-> salutation
            9  First Name|				-> firstname
            10 Mid Name|				-> midname
            11 Last Name|				-> lastname
           
            recordToDb.setFile_line(i);
            recordToDb.setErr_type(arrOfStr[1]);
            recordToDb.setErr_msg(arrOfStr[2]);
            recordToDb.setContact_flag(arrOfStr[3]);
            recordToDb.setAccount_flag(arrOfStr[4]);
            recordToDb.setOwner_flag(arrOfStr[5]);
            recordToDb.setSfcontact_id(arrOfStr[6]);
            recordToDb.setContact_org(arrOfStr[7]);
            recordToDb.setSalutation(arrOfStr[8]);
            recordToDb.setFirstname(arrOfStr[9]);
            recordToDb.setMidname(arrOfStr[10]);
            recordToDb.setLastname(arrOfStr[11]);
             */
            
            
            i++;
        }
        
        return listToDb;
    }
}