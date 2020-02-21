/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *
 * @author ferreirai
 */
@Entity
@Table(name = "MailStore")
@NamedQueries({ @NamedQuery(name = "mail.findAll", query = "SELECT a FROM MailStore a") })
public class MailStore implements Serializable {

        public MailStore() {
	}
    
	private static final long serialVersionUID = 1L;
	 
	@Id
        @Basic(optional = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "jobid")
	private Integer jobId;
        
        @Basic(optional = false)
        @Column(name = "sendTo")
	private String sendTo;
        
        @Basic(optional = false)
        @Column(name = "sentFrom")
	private String sentFrom;
        
        @Basic(optional = false)
        @Column(name = "subject")
	private String subject;	
        
        @Basic(optional = true)
        @Column(name = "body")
        private String body;

	

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
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the sendTo
     */
    public String getSendTo() {
        return sendTo;
    }

    /**
     * @param sendTo the sendTo to set
     */
    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    /**
     * @return the sentFrom
     */
    public String getSentFrom() {
        return sentFrom;
    }

    /**
     * @param sentFrom the sentFrom to set
     */
    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }
	
		@Override
	public int hashCode() {
		int hash = 0;
		hash += (jobId != null ? jobId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof MailStore)) {
			return false;
		}
		MailStore other = (MailStore) object;
		return !((this.jobId == null && other.jobId != null)
                        || (this.jobId != null && !this.jobId.equals(other.jobId)));
	}

	@Override
	public String toString() {
		return "com.herokuPOC.entity.MailStore[ jobId=" + jobId ;
	}


}
