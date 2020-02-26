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
import com.herokuPOC.entity.User;
import com.herokuPOC.services.ContainerManager;
import com.herokuPOC.services.StorageManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private UploadedFile file;

    @EJB
    private ContainerManager fileuploadFacade;

    private final Timestamp timeStamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
    private List<FileContainer> ListFromDb = new ArrayList<FileContainer>();
    FileContainer fileContainertoDb = new FileContainer();
    private StorageManager aWSStorageFacade;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean fileValidations(UploadedFile file) throws IOException {

        InputStream filecontent = file.getInputstream();
        String fileLines = getFileContent(filecontent);

        String[] fileDelimited = fileLines.split("\\|");
        String header = fileDelimited[0];
        User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

        int fileRecordQty = fileLines.split("\r\n|\r|\n").length - 1;
        
        fileContainertoDb = new FileContainer();
        
        fileContainertoDb.setLoad_status("PENDING");
        fileContainertoDb.setName(file.getFileName());
        fileContainertoDb.setUpload_by(us.getUserName());
        fileContainertoDb.setUpload_date(timeStamp);
        fileContainertoDb.setRecord_err_qty(0);
        fileContainertoDb.setSf_qty_record_sync(0);
        fileContainertoDb.setRecord_qty(fileRecordQty);
        fileContainertoDb.setHeader(header);

        ListFromDb = fileuploadFacade.findFileByNameHeader(fileContainertoDb, header);

        //Validation FileName&Header
        if (ListFromDb.size() > 0) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning Message", "File Already been uploaded! " + file.getFileName()));
            return true;
        } else {
            insertFileUploadToDb();
        }

        return false;
    }

    public void fileUploadListener(FileUploadEvent e) {
        // Get uploaded file from the FileUploadEvent
        boolean flagFileInDb = false;

        try {

            this.file = e.getFile();

            if (file.getFileName().substring(0, file.getFileName().indexOf('.')).length() != 8) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Filename is not correct"));
                return;
            }

            //if passes all validations inserts on DB    
            flagFileInDb = fileValidations(file);

            if (!flagFileInDb) {
                aWSStorageFacade = new StorageManager();
                aWSStorageFacade.upload(e);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "File Uploaded successfuly!! You will receive an email with feedback!"));
            }
            
        } catch (Exception ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error Uploading File!"));
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return;
    }

    private void insertFileUploadToDb() {
        fileuploadFacade.createFileContainer(fileContainertoDb);
    }

    public String getFileContent(InputStream fis) throws UnsupportedEncodingException, IOException {
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
