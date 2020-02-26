/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 * @author jigonzalez
 */
@Entity
@Table(name = "record")
public class Record implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Integer recordId;
	private Integer file_line;
	
	private String err_type;
	private String err_msg;
	private String contact_flag;
	private String account_flag;
	private String owner_flag;
	private String sfcontact_id;
	private String contact_org;
	private String salutation;
	private String firstname;
	private String midname;
	private String lastname;
        private String mail;
        private String cellular;
        private String account_segmentation;
        private String validated;
        private String flagto_sf;
        private String sfaccount_id;
        private String sfowner_id;
        
	
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "fileContainerId")
	private FileContainer fileContainer;
	

	public Record() {
	}


	

	public Integer getRecordId() {
		return recordId;
	}




	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}




	public Integer getFile_line() {
		return file_line;
	}


	public void setFile_line(Integer file_line) {
		this.file_line = file_line;
	}


	public String getErr_type() {
		return err_type;
	}


	public void setErr_type(String err_type) {
		this.err_type = err_type;
	}


	public String getErr_msg() {
		return err_msg;
	}


	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}


	public String getContact_flag() {
		return contact_flag;
	}


	public void setContact_flag(String contact_flag) {
		this.contact_flag = contact_flag;
	}


	public String getAccount_flag() {
		return account_flag;
	}


	public void setAccount_flag(String account_flag) {
		this.account_flag = account_flag;
	}


	public String getOwner_flag() {
		return owner_flag;
	}


	public void setOwner_flag(String owner_flag) {
		this.owner_flag = owner_flag;
	}


	public String getSfcontact_id() {
		return sfcontact_id;
	}


	public void setSfcontact_id(String sfcontact_id) {
		this.sfcontact_id = sfcontact_id;
	}


	public String getContact_org() {
		return contact_org;
	}


	public void setContact_org(String contact_org) {
		this.contact_org = contact_org;
	}


	public String getSalutation() {
		return salutation;
	}


	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getMidname() {
		return midname;
	}


	public void setMidname(String midname) {
		this.midname = midname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public FileContainer getFileContainer() {
		return fileContainer;
	}


	public void setFileContainer(FileContainer fileContainer) {
		this.fileContainer = fileContainer;
	}

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the cellular
     */
    public String getCellular() {
        return cellular;
    }

    /**
     * @param cellular the cellular to set
     */
    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    /**
     * @return the account_segmentation
     */
    public String getAccount_segmentation() {
        return account_segmentation;
    }

    /**
     * @param account_segmentation the account_segmentation to set
     */
    public void setAccount_segmentation(String account_segmentation) {
        this.account_segmentation = account_segmentation;
    }

    /**
     * @return the validated
     */
    public String getValidated() {
        return validated;
    }

    /**
     * @param validated the validated to set
     */
    public void setValidated(String validated) {
        this.validated = validated;
    }

    /**
     * @return the flagto_sf
     */
    public String getFlagto_sf() {
        return flagto_sf;
    }

    /**
     * @param flagto_sf the flagto_sf to set
     */
    public void setFlagto_sf(String flagto_sf) {
        this.flagto_sf = flagto_sf;
    }
	
	
        //private String sfowner_id;

    /**
     * @return the sfowner_id
     */
    public String getSfowner_id() {
        return sfowner_id;
    }

    /**
     * @param sfowner_id the sfowner_id to set
     */
    public void setSfowner_id(String sfowner_id) {
        this.sfowner_id = sfowner_id;
    }
    
    
    
    
    /**
     * @return the sfaccount_id
     */
    public String getSfaccount_id() {
        return sfaccount_id;
    }

    /**
     * @param sfaccount_id the sfaccount_id to set
     */
    public void setSfaccount_id(String sfaccount_id) {
        this.sfaccount_id = sfaccount_id;
    }
    
}
