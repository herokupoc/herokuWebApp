/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.OrgEncoding;
import com.herokuPOC.entity.RecordH;
import com.herokuPOC.services.ContainerManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ferreirai
 */
@ManagedBean(name = "recordsController")
@SessionScoped
public class RecordsController implements Serializable {

    @EJB
    private ContainerManager recordEJB;

    private List<RecordH> recordsList = new ArrayList<>();
    private static long serialVersionUID = 1L;

    private String fileName;
    private String uploadDate;
    private String nbRecordsError;
    private String name;
    private String status;
    private String nbRecordsSyncSF;
    private String accountName;
    private String organization;
    private String nbRecords;
    private String errorType;
    private String fileIdSelection;
    private FileContainer fileContainer;
    private List<OrgEncoding> orgList = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        setRecordsList(new ArrayList<>());
        
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the nbRecordsSyncSF
     */
    public String getNbRecordsSyncSF() {
        return nbRecordsSyncSF;
    }

    /**
     * @param nbRecordsSyncSF the nbRecordsSyncSF to set
     */
    public void setNbRecordsSyncSF(String nbRecordsSyncSF) {
        this.nbRecordsSyncSF = nbRecordsSyncSF;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the nbRecords
     */
    public String getNbRecords() {
        return nbRecords;
    }

    /**
     * @param nbRecords the nbRecords to set
     */
    public void setNbRecords(String nbRecords) {
        this.nbRecords = nbRecords;
    }

    /**
     * @return the errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the uploadDate
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the nbRecordsError
     */
    public String getNbRecordsError() {
        return nbRecordsError;
    }

    /**
     * @param nbRecordsError the nbRecordsError to set
     */
    public void setNbRecordsError(String nbRecordsError) {
        this.nbRecordsError = nbRecordsError;
    }

    /**
     * @return the recordsList
     */
    public List<RecordH> getRecordsList() {
        return recordsList;
    }

    /**
     * @param recordsList the recordsList to set
     */
    public void setRecordsList(List<RecordH> recordsList) {
        this.recordsList = recordsList;
    }

    /**
     * @return the fileIdSelection
     */
    public String getFileIdSelection() {
        return fileIdSelection;
    }

    /**
     * @param fileIdSelection the fileIdSelection to set
     */
    public void setFileIdSelection(String fileIdSelection) {
        this.fileIdSelection = fileIdSelection;
    }

    
    public String recordByFileId() {
        FacesContext fc = FacesContext.getCurrentInstance();
        fileIdSelection = getFileIdParam(fc);
        
        fileContainer = recordEJB.findFileByName(Integer.parseInt(fileIdSelection));
        fileName = fileContainer.getName();
        uploadDate = fileContainer.getUpload_date().toString();
        nbRecordsError = fileContainer.getRecord_err_qty().toString();
        name = fileContainer.getName();
        status = fileContainer.getLoad_status();
        nbRecordsSyncSF = fileContainer.getSf_qty_record_sync().toString();
        nbRecords = fileContainer.getRecord_qty().toString();
        organization = fileContainer.getName().substring(0, 2);

                
        try {
                
            setRecordsList(recordEJB.recordsFromFileId(fileIdSelection));
            
            if(this.recordsList.isEmpty()){
                 RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "There is no records for this file!!"));
                 return "searchFileResults";
            }
            this.fileName = "INACIO";
        
        } catch (Exception e) {
          RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in getting records from file!")); 
        }
        
        return "fileContainerRecords";
    }

    //get value from "f:param"
    public String getFileIdParam(FacesContext fc) {
        
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        setFileName("test");
        setNbRecordsError("1");
        //formFile:state_input
        
        return params.get("formFile:grid_selection");

    }

}
