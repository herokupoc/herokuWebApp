/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.OrgEncoding;
import com.herokuPOC.entity.User;
import com.herokuPOC.services.ContainerManager;
import com.herokuPOC.services.MailManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author evangelistap
 */
//@Named
@ManagedBean (name = "searchController")
@ViewScoped
public class SearchController implements Serializable{

    @EJB 
    private ContainerManager fileContainerEJB;
    @EJB
    private MailManager mailManager;
        
    User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
       
    private List<FileContainer> filesFromDb = new ArrayList<>() ; 
    private List<String> statusList = new ArrayList<>();
    private List<OrgEncoding> orgList = new ArrayList<>();
    private String organization = null;    
    private String userName = us.getUserName();    
    private String container = "Contact";
    private String inputUploadBy = null;
    private String inputStatus = null;
    private String name = null;
    private Date uploadStartDate = null;
    private Date uploadEndDate = null;  
    private String url = null;
    private FileContainer selectedFile;
    private List<FileContainer> selectedFiles;
    
    @PostConstruct
    public void init(){
        filesFromDb = new ArrayList<>();
        this.url = getApplicationUri();
    }
    
    /**
     * @return the statusList
     */
    public List<String> getStatusList() {
        statusList.clear();
        statusList.add("PENDING");
        statusList.add("LOADED");
        statusList.add("VALIDATED");
        
        return statusList;
    }
    
     public List<OrgEncoding> getOrgList() {
        orgList = fileContainerEJB.findAllOrg();
        
        return orgList;
    }

     /**
     * @param orgList the orgList to set
     */
    public void setOrgList(List<OrgEncoding> orgList) {
        this.orgList = orgList;
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
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        
        try {
            //filesFromDb = fileContainerEJB.findAllFileContainer(); 
            filesFromDb = fileContainerEJB.findFileContainerByCriteria(params.get("formFile:state_input"), params.get("formFile:orgs_input"), params.get("formFile:calStart_input"), params.get("formFile:calEnd_input"));
            
        } catch (Exception e) {
            System.out.println("com.herokuPOC.manageBeans.jpaBeans.SearchController.SearchFiles()");
        
        }
        return;
        
    }
    
    public String getApplicationUri() {
        try {
          FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                null, ext.getRequestServerName(), ext.getRequestServerPort(),
                ext.getRequestContextPath(), null, null);
          return uri.toASCIIString();
        } catch (URISyntaxException e) {
          throw new FacesException(e);
        }
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    public void RunJob(int job){
        try {
            HttpClient client ;
            HttpGet request ;
            HttpResponse response ;
            
            System.out.println("Running Job " + job);
            
            client = new DefaultHttpClient();
            request = new HttpGet(getUrl()+"/webresources/job/"+job);
            response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);    
            }
            
            if(job == 3) {
                String body = "The Container files have now been processed! You can go and check the Status of the records on the Web App!";
                mailManager.sendMail2User("herokuwebapp@amadeus.com","WebApp - Containers validated",body);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     //get value from "f:param"
    public String getSearchParams(FacesContext fc) {
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        //setFileName("test");
        //setNbRecordsError("1");
        //formFile:state_input
        
        return params.get("formFile:grid_selection");

    }
}
