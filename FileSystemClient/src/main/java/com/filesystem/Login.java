package com.filesystem;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.security.SecurityFunctions;

import helper.HelperFunctions;
import messages.LoginRequest;
import messages.LoginResponse;
import messages.PropertyStore;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HelperFunctions hf;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		hf = new HelperFunctions();
		PropertyStore.loadProperties();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		LoginRequest loginRequest= new LoginRequest();
		//loginRequest.setUsername((String) request.getParameter("u"));
		loginRequest.setUsername(SecurityFunctions.encrypt((String) request.getParameter("u"), (String) request.getParameter("p")));
		String enc = SecurityFunctions.encrypt((String) request.getParameter("p"), (String) request.getParameter("p"));
		String dec = SecurityFunctions.decrypt(enc, (String) request.getParameter("p"));
		
		
		loginRequest.setPassword(SecurityFunctions.encrypt((String) request.getParameter("p"), (String) request.getParameter("p")));
		String reply = hf.sendLoginRequest(loginRequest.getJsonString());
		LoginResponse loginResponse = new LoginResponse();
		loginResponse = loginResponse.getClassFromJsonString(reply);
		if(loginResponse.getAuthstatus().equals("Y")&&loginResponse.getUsertype().equals("N")) {
			request.getSession().setAttribute("fname", loginResponse.getName());
			request.getSession().setAttribute("token", loginResponse.getToken());
			request.getSession().setAttribute("key1", loginResponse.getKey1());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
