package com.heroku;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;

import com.herokuPOC.entity.Airline;
import com.herokuPOC.services.AirlineFacade;

public class JPATest {
	
	@PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
	private EntityManager em;
	


	@Test
	public void test() {

	}

}
