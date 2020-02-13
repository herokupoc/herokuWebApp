/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import java.io.IOException;

/**
 *
 * @author jigonzalez
 * @param <T>
 */
public abstract class AbstractAWSFacade<T> {

  private Class<T> entityClass;

  public AbstractAWSFacade(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  
  public boolean upload(T entity) throws IOException {
    
      return true;
  }


  public Object find(Object id) {
    return "";
  }

  
}
