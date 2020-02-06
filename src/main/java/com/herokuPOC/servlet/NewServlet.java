/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuPOC.entity.Airline;
import com.herokuPOC.services.AirlineFacade;


@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet requestw
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occursD
   * @throws IOException if an I/O error occursd
   */
  @EJB
  AirlineFacade airlineFacade;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
	  
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);

    Airline airline = new Airline();
    airline.setAirlineName("Air France1");
    airline.setAirlineId(new Integer(1));
    
    Airline airline2 = new Airline();
    airline2.setAirlineName("Air France2");
    airline2.setAirlineId(new Integer(2));
    
    Airline airline3 = new Airline();
    airline3.setAirlineName("Air France3");
    airline3.setAirlineId(new Integer(3));
    
    List<Airline> listaAirlines = new ArrayList<Airline>();
    listaAirlines.add(airline);
    listaAirlines.add(airline2);
    listaAirlines.add(airline3);
    
    request.setAttribute("airlineList", listaAirlines);
    
    
    
    
    
    
    ServletContext context = this.getServletContext();
    RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/airlines.jsp");
    dispatcher.forward(request, response);

  }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
