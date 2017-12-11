package com.filesystem;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.security.SecurityFunctions;

import helper.HelperFunctions;
import messages.GetFileInfoFromDSRequest;
import messages.GetFileInfoFromDSResponse;
import messages.LockRequest;
import messages.ReadRequest;
import messages.ReadResponse;
import messages.WriteRequest;

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
        // TODO Auto-generated constructor stub
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
		String filename = request.getParameter("fn");
		
		WriteRequest writeRequest = new WriteRequest();
		//writeRequest.setDirectory(directory);
		//After sending changes open the same page again with success msg
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("writefileopen.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
