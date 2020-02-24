/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jigonzalez
 */
@Entity
@Table(name = "mail")
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	private Integer jobId;
	private String toRecipient;
	private String fromRecipient;
	private String subject;	

	public Mail() {
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

    /**
     * @return the toRecipient
     */
    public String getToRecipient() {
        return toRecipient;
    }

    /**
     * @param toRecipient the toRecipient to set
     */
    public void setToRecipient(String toRecipient) {
        this.toRecipient = toRecipient;
    }

    /**
     * @return the fromRecipient
     */
    public String getFromRecipient() {
        return fromRecipient;
    }

    /**
     * @param fromRecipient the fromRecipient to set
     */
    public void setFromRecipient(String fromRecipient) {
        this.fromRecipient = fromRecipient;
    }
	
	


}
