package com.herokuPOC.manageBeans.jpaBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.herokuPOC.entity.Airline;
import com.herokuPOC.services.AirlineFacade;

@ManagedBean(name = "airline")
@SessionScoped
public class AirlineBean implements Serializable{
	
	private  List<Airline> airlines;
	
	private  Airline selectedAirline;
	
	private Integer airlineId;
	private String mdmId;
	private String name;
	

	@EJB
	private AirlineFacade airlineFacade;
	

	
	public List<Airline> getAirlines ()
	{
		return airlineFacade.findAll();
	}


	public void setAirlines(List<Airline> airlines) {
		this.airlines = airlines;
	}


	public Airline getSelectedAirline() {
		return selectedAirline;
	}


	public void setSelectedAirline(Airline selectedAirline) {
		System.out.println("Aerolínea seleccionada: " + selectedAirline.getAirlineName());
		this.selectedAirline = selectedAirline;
	}
	
	
	
	public Integer getAirlineId() {
		return airlineId;
	}


	public void setAirlineId(Integer airlineId) {
		this.airlineId = airlineId;
	}


	public String getMdmId() {
		return mdmId;
	}


	public void setMdmId(String mdmId) {
		this.mdmId = mdmId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String displayAirline(){
		System.out.println("Display airline ");
		return "editAirline";
	}
	
	public String saveAirline(){
		System.out.println("Save airline ");
		
		selectedAirline = airlineFacade.edit(selectedAirline);
		return "editAirline";
	}
	
	public String newAirline(){
		
		System.out.println("new airline ");
		name = "";
		mdmId = "";
		selectedAirline = null;
		return "createAirline";
	}
	
	public String createAirline(){
		
		System.out.println("Create airline ");
		
		Airline airline = new Airline();
		airline.setAirlineId(airlineId);
	    airline.setAirlineName(name);
	    airline.setAirlineMdmId(mdmId);

	    airlineFacade.create(airline);
	    
	    selectedAirline = airline;
		
		return "crudOperations";
	}
	
	public String deleteAirline(){
		
		System.out.println("Delete airline ");
		airlineFacade.remove(selectedAirline);
		
		return "crudOperations";
	}
	
	
	
	
}
