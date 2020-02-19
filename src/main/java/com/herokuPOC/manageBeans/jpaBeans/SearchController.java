/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import com.herokuPOC.services.FileContainerFacade;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author evangelistap
 */
//@Named
@ManagedBean (name = "searchController")
@ViewScoped
public class SearchController implements Serializable{

    
    @EJB 
    private FileContainerFacade fileContainerEJB;
    
    User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
       
    private List<FileContainer> filesFromDb = new ArrayList<>() ; 
    private List<String> statusList = new ArrayList<>();
    private String organization = null;    
    private String userName = us.getUserName();    
    private String container = "Contact";
    private String inputUploadBy = null;
    private String inputStatus = null;
    private String name = null;
    private Date uploadStartDate = null;
    private Date uploadEndDate = null;  
    private FileContainer selectedFile;
    private List<FileContainer> selectedFiles;
    
    @PostConstruct
    public void init(){
        filesFromDb = new ArrayList<>();
    }
    
    /**
     * @return the statusList
     */
    public List<String> getStatusList() {
        statusList.clear();
        statusList.add("PENDING");
        statusList.add("COMPLETED");
        statusList.add("ERROR");
        
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the inputUploadBy
     */
    public String getInputUploadBy() {
        return inputUploadBy;
    }

    /**
     * @param inputUploadBy the inputUploadBy to set
     */
    public void setInputUploadBy(String inputUploadBy) {
        this.inputUploadBy = inputUploadBy;
    }

    /**
     * @return the inputStatus
     */
    public String getInputStatus() {
        return inputStatus;
    }

    /**
     * @param inputStatus the inputStatus to set
     */
    public void setInputStatus(String inputStatus) {
        this.inputStatus = inputStatus;
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
     * @return the uploadEndDate
     */
    public Date getUploadEndDate() {
        return uploadEndDate;
    }

    /**
     * @param uploadDate the uploadEndDate to set
     */
    public void setUploadEndDate(Date uploadEndDate) {
        this.uploadEndDate = uploadEndDate;
    }
    
  /**
     * @return the Organization
     */
    public String getOrganization() {
        return organization;
    }

    
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the Container
     */
    public String getContainer() {
        return container;
    }

    /**
     * @param Container the Container to set
     */
    public void setContainer(String container) {
        this.container = container;
    }

     /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        userName = us.getUserName();
        this.userName = userName;
    }

    
     /**
     * @return the filesFromDb
     */
    public List<FileContainer> getFilesFromDb() {
         return filesFromDb;
    }

    /**
     * @param filesFromDb the filesFromDb to set
     */
    public void setFilesFromDb(List<FileContainer> filesFromDb) {
        this.filesFromDb = filesFromDb;
    }
    
    
    /**
     * @return the uploadStartDate
     */
    public Date getUploadStartDate() {
        return uploadStartDate;
    }

    /**
     * @param uploadStarttDate the uploadStartDate to set
     */
    public void setUploadStartDate(Date uploadStartDate) {
        this.uploadStartDate = uploadStartDate;
    }
    
    /**
     * @return the selectedFile
     */
    public FileContainer getSelectedFile() {
        return selectedFile;
    }

    /**
     * @param selectedFile the selectedFile to set
     */
    public void setSelectedFile(FileContainer selectedFile) {
        this.selectedFile = selectedFile;
    }

    /**
     * @return the selectedFiles
     */
    public List<FileContainer> getSelectedFiles() {
        return selectedFiles;
    }

    /**
     * @param selectedFiles the selectedFiles to set
     */
    public void setSelectedFiles(List<FileContainer> selectedFiles) {
        this.selectedFiles = selectedFiles;
    }

    
    public void SearchFiles() {
        try {
            filesFromDb = fileContainerEJB.findAll();
            //filesFromDb = fileContainerEJB.SearchByInputForm(userName, uploadEndDate);
        
        } catch (Exception e) {
            System.out.println("com.herokuPOC.manageBeans.jpaBeans.SearchController.SearchFiles()");
        
        }
        return;
        
    }
    
    public void SearchTest(){
        System.out.println("com.herokuPOC.manageBeans.jpaBeans.SearchController.SearchFiles()");
    
    }



    
}
