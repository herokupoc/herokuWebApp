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

import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.entity.Record;
import com.herokuPOC.services.ContainerManager;


@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet requestw
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occursDDw
   * @throws IOException if an I/O error occursd
   */

  @EJB
  ContainerManager fileContainerFacade;


  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
	  
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
	
	String command = request.getParameter("command");
	
	if ("1".equals(command))
	{
		FileContainer fileContainer = fileContainerFacade.findAllFileContainer().get(0);
		System.out.println("records fileContainer: " + fileContainer.getName());
		//System.out.println("records size fileContainer: " + fileContainer.getRecords().size());
		
		
		
		
	}
	else
	{


	}

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
