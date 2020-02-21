/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.manageBeans.jpaBeans;

import com.herokuPOC.entity.User;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author evangelistap
 */
@Named
@ViewScoped

public class SessionController implements Serializable{
    
    public void checkSession(){
        try {
            User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            
            if(us == null){
                FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
            
            }            
        } catch (Exception e) {
        }
        
        
        }
    
    public void closeSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();    
    }
    
    
}
    

