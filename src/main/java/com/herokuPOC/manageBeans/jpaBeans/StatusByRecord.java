/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ferreirai
 */
@ManagedBean(name = "statusByRecord")
@SessionScoped
public class StatusByRecord implements Serializable {
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

    private FileUploadBean fub;
    
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


    
    
}
