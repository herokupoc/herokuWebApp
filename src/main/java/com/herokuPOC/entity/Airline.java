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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "airline__c")
@NamedQueries({ @NamedQuery(name = "airline__c.findAll", query = "SELECT a FROM Airline a") })
public class Airline implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	@Basic(optional = false)
	@Column(name = "airlineid__c")
	private Integer airlineId;

	@Basic(optional = true)
	@Column(name = "airlinemdmid__c")
	private String airlineMdmId;

	@Size(max = 2147483647)
	@Column(name = "airlinename__c")
	private String airlineName;

	public Airline() {
	}

	public Airline(Integer id, String mdmId, String name) {
		airlineId = id;
		airlineMdmId = mdmId;
		airlineName = name;
	}

	public Airline(Integer airlineId) {
		this.airlineId = airlineId;
	}

	public Integer getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Integer airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineMdmId() {
		return airlineMdmId;
	}

	public void setAirlineMdmId(String airlineMdmId) {
		this.airlineMdmId = airlineMdmId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (airlineId != null ? airlineId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Airline)) {
			return false;
		}
		Airline other = (Airline) object;
		if ((this.airlineId == null && other.airlineId != null)
				|| (this.airlineId != null && !this.airlineId.equals(other.airlineId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.herokuPOC.entity.Airline[ airlineId=" + airlineId + ", airlineName=" + airlineName + " ]";
	}

}
