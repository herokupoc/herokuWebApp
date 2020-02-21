/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import com.herokuPOC.entity.User;
import com.herokuPOC.services.ContainerManager;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evangelistap
 */
@Named
@ViewScoped
public class UserController implements Serializable{
   
    @EJB
    private ContainerManager EJBUser;
    private User user;

    
    @PostConstruct
    public void init(){
        user = new User();
        
    }
    
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    public String Login(){
        User us = null; 
        String redirect = null;
        
        try {
            us = EJBUser.Login(user);
            if (us != null){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", us);
                redirect = "/index";
            }else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error Message", "Bad Credentials!")); 
            }
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in login!")); 
                
        }
        
        return redirect;
    
    }
    
    
}
