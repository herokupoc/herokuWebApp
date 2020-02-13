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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "recordH")
public class RecordH implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Integer file_id;
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
	
	private Date lastUpdated;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fileContainerId")
	private FileContainer fileContainer;
	

	public RecordH() {
	}


	public Date getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	public Integer getFile_id() {
		return file_id;
	}


	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
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

}
