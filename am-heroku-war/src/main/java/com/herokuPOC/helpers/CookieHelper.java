package com.herokuPOC.helpers;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean(name = "cookieHelper")
@SessionScoped
public class CookieHelper implements Serializable{

	private String cookieUserName = "userName";
	
	
	public String getCookieUserName() {
		System.out.println("getCookie");
		setCookie("userName", "ignacio.ramos", 24*60*60);
		return cookieUserName;
	}
	public void setCookieUserName(String cookieUserName) {
		System.out.println("setCookie");
		this.cookieUserName = cookieUserName;
	}
	
	public void setCookieUserName() {
		System.out.println("setCookie");
		setCookie ("userName", "ignacio.ramos", 60*60*24 );
	}
	private void setCookie(String name, String value, int expiry) {

	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    Cookie cookie = null;

	    Cookie[] userCookies = request.getCookies();
	    if (userCookies != null && userCookies.length > 0 ) {
	        for (int i = 0; i < userCookies.length; i++) {
	            if (userCookies[i].getName().equals(name)) {
	                cookie = userCookies[i];
	                break;
	            }
	        }
	    }

	    if (cookie != null) {
	        cookie.setValue(value);
	    } else {
	        cookie = new Cookie(name, value);
	        cookie.setPath(request.getContextPath());
	    }

	    cookie.setMaxAge(expiry);

	    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
	    response.addCookie(cookie);
	  }

	  private Cookie getCookie(String name) {

	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
	    Cookie cookie = null;

	    Cookie[] userCookies = request.getCookies();
	    if (userCookies != null && userCookies.length > 0 ) {
	        for (int i = 0; i < userCookies.length; i++) {
	            if (userCookies[i].getName().equals(name)) {
	                cookie = userCookies[i];
	                return cookie;
	            }
	        }
	    }
	    return null;
	  }
	}
