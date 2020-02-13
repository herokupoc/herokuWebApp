package com.herokuPOC.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.primefaces.event.FileUploadEvent;


public class AWSStorageFacadeTemp {
    
   public AWSStorageFacadeTemp() {
    
  }
    
    private final String fileObjKeyName = null;
    private String bucketName = null;
    private Regions clientRegion = null;
    private AmazonS3 s3Client = null;
    
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