/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jigonzalez
 */
@Entity
@Table(name = "fileContainer")
@NamedQuery(
    name = "fileContainer.findFileByName", 
    query = "SELECT f.name, f.header FROM FileContainer f where f.name = :name "
)

public class FileContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	private Integer id;
	private String name;
	private String header;
	
	private Integer record_qty;
	private Integer record_err_qty;
	
	private String load_status;
	private String upload_by;

	private Timestamp upload_date;
	
	private Integer sf_qty_record_sync;         
	         


	public FileContainer() {
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
            if (name.contains(".")){
                name = name.substring(0, name.indexOf('.'));
            }   
        
            this.name = name;
	}



	public String getHeader() {
		return header;
	}



	public void setHeader(String header) {
		this.header = header;
	}



	public Integer getRecord_qty() {
		return record_qty;
	}



	public void setRecord_qty(Integer record_qty) {
		this.record_qty = record_qty;
	}



	public Integer getRecord_err_qty() {
		return record_err_qty;
	}



	public void setRecord_err_qty(Integer record_err_qty) {
		this.record_err_qty = record_err_qty;
	}



	public String getLoad_status() {
		return load_status;
	}



	public void setLoad_status(String load_status) {
		this.load_status = load_status;
	}



	public String getUpload_by() {
		return upload_by;
	}



	public void setUpload_by(String upload_by) {
		this.upload_by = upload_by;
	}



	public Timestamp getUpload_date() {
		return upload_date;
	}



	public void setUpload_date(Timestamp upload_date) {
		this.upload_date = upload_date;
	}



	public Integer getSf_qty_record_sync() {
		return sf_qty_record_sync;
	}



	public void setSf_qty_record_sync(Integer sf_qty_record_sync) {
		this.sf_qty_record_sync = sf_qty_record_sync;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	

}
