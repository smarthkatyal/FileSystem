package com.filesystem;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.security.SecurityFunctions;

import helper.HelperFunctions;
import messages.GetFileInfoFromDSRequest;
import messages.PropertyStore;
import messages.ReadRequest;

/**
 * Servlet implementation class ReadRemoteFile
 */
public class ReadRemoteFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HelperFunctions hf; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadRemoteFile() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		hf = new HelperFunctions();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		//Get file info from DS
		GetFileInfoFromDSRequest infoRequest = new GetFileInfoFromDSRequest();
		infoRequest.setFilename(SecurityFunctions.encrypt(filename, key1));
		infoRequest.setToken(token);
		infoRequest.setEncryptedUsername(usernameEnc);
		String infoRequestJson = infoRequest.getJsonString();
		String replyInfoRequest = hf.getDirectoryInfo(infoRequestJson);
		SecurityFunctions.decrypt(replyInfoRequest, key1);
		
		//Read from the server returned
		ReadRequest readRequest = new ReadRequest();
		readRequest.setFilename(filename);
		readRequest.setToken(token);
		String jsonRequest = readRequest.getJsonString();
		System.out.println("*******\nSending DirectoryInfo Reqeust(Before Encryption):\n"+jsonRequest+"\n*********");
		String reply = hf.sendReadRequest(SecurityFunctions.encrypt(jsonRequest, key1));
		SecurityFunctions.decrypt(reply, key1);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
