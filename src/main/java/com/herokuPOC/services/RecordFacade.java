/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;


import com.herokuPOC.entity.Record;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class RecordFacade extends AbstractFacade<Record> {

  @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public RecordFacade() {
    super(Record.class);
  }
  
}
