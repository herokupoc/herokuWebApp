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
@Table(name = "orgEncoding")
public class OrgEncoding implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	private Integer id;
	private String organization;
	private String enconding;
	

	public OrgEncoding() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getEnconding() {
		return enconding;
	}


	public void setEnconding(String enconding) {
		this.enconding = enconding;
	}

	

	
}
