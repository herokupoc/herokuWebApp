/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.entity.Airline;
import com.herokuPOC.entity.FileContainer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class FileContainerFacade extends AbstractFacade<FileContainer> {

  @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public FileContainerFacade() {
    super(FileContainer.class);
  }
  
}
