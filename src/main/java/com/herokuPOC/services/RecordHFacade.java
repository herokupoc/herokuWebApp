/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.RecordH;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.context.RequestContext;

/**
 *
 * @author evangelistap
 */
@Stateless
public class RecordHFacade extends AbstractFacade<RecordH> {

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecordHFacade() {
        super(RecordH.class);
    }
    
    
    
    public List<RecordH> recordsFromFileId(String fileId){
        String recordListQuery;
        List<RecordH> recordsList = new ArrayList<>();
        try {
            
            recordListQuery = "SELECT h from RecordH h where file_id = :fileid ";
            Query query = em.createQuery(recordListQuery);
            query.setParameter("fileid", Integer.valueOf(fileId));
            
            recordsList = query.getResultList();
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", "Error in getting records from file!")); 
        }
    
        return recordsList;
    }
}
