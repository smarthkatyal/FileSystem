package com.services;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.security.SecurityFunctions;

import helper.HelperFunctions;
import messages.WriteRequest;
import messages.WriteResponse;

/**
 * Servlet implementation class WriteRemoteFile
 */
public class SendChangesForWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HelperFunctions hf; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendChangesForWrite() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		hf = new HelperFunctions();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = (String)request.getSession().getAttribute("filename");
		String directory = (String)request.getSession().getAttribute("directory");
		String filecontent = (String)request.getParameter("writebox");
		String serverurl = (String)request.getSession().getAttribute("serverurl");
		WriteRequest writeRequest = new WriteRequest();
		writeRequest.setDirectory(SecurityFunctions.encrypt(directory, key1));
		writeRequest.setFilename(SecurityFunctions.encrypt(filename, key1));
		writeRequest.setEncryptedUsername(usernameEnc);
		writeRequest.setFilecontent(SecurityFunctions.encrypt(filecontent,key1));
		writeRequest.setToken(token);
		String jsonWriteRequest = writeRequest.getJsonString();
		String writeResponsereply = hf.sendWriteRequest(jsonWriteRequest,serverurl);
		WriteResponse writeResponse = new WriteResponse();
		writeResponse = writeResponse.getClassFromJsonString(writeResponsereply);
		if(null!=writeResponse.getAuthstatus() && writeResponse.getAuthstatus().equals("Y")) {
		//Success case	
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "File Changes saved");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else if(null!=writeResponse.getAuthstatus() && writeResponse.getAuthstatus().equals("N")) {
		//Token Validation Failed
			request.getSession().setAttribute("status", "0");
			request.getSession().setAttribute("message", "Token Validation Failed, please re-login and try again");
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "ERROR");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
