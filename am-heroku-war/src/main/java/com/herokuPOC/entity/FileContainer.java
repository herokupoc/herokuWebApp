package com.herokuPOC.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "fileContainer")
@NamedQueries({
    @NamedQuery(
         name = "fileContainer.findFileByNameHeader", 
        query = "SELECT f.name, f.header FROM FileContainer f where f.name = :name and f.header =:header "
    ),
    @NamedQuery(
         name = "fileContainer.findAllUploadedToDb", 
        query = "SELECT f.name FROM FileContainer f where f.load_status = 'PENDING' "
    )
        ,
    @NamedQuery(
         name = "fileContainer.findFileById", 
        query = "SELECT f FROM FileContainer f where f.id = :id "
    )
}) 


@XmlRootElement
public class FileContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Integer id;
	private String name;
	private String header;
	
	private Integer record_qty;
	private Integer record_err_qty;
	
	private String load_status;
	private String upload_by;

	private Date upload_date;
	
	private Integer sf_qty_record_sync; 
	
	
	@OneToMany(mappedBy = "fileContainer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Record> records = new ArrayList<Record>();
	         

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
            //if (name.contains(".")){
            //    name = name.substring(0, name.indexOf('.'));
            //}   
        
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



	public Date getUpload_date() {
		return upload_date;
	}



	public void setUpload_date(Date upload_date) {
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


        /*
	public List<Record> getRecords() {
		return records;
	}



	public void setRecords(List<Record> records) {
		this.records = records;
	}
	*/
	

	

}
