package com.herokuPOC.manageBeans.editorBeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.herokuPOC.services.CalculatorBean;

@ManagedBean(name = "editor")
public class EditorBean {

  private String value = "This editor is provided by PrimeFaces";
  private int sumValue = 0;

  @EJB
  private CalculatorBean calculatorBean;
  
  @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
  private EntityManager em;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getSumValue() {
	System.out.println("Voy asumar: ");
    return calculatorBean.add(5, 6);
  }

  public void setSumValue(int sumValue) {
    this.sumValue = sumValue;
  }

}
