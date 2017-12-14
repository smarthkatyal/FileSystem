package com.services;

import java.io.IOException;

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
import messages.LockResponse;
import messages.ReadRequest;
import messages.ReadResponse;

/**
 * Servlet implementation class WriteRemoteFile
 */
public class WriteRemoteFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HelperFunctions hf; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteRemoteFile() {
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
		infoRequest.setOperation("w");
		String infoRequestJson = infoRequest.getJsonString();
		String replyInfoRequest = hf.getDirectoryInfo(infoRequestJson);
		GetFileInfoFromDSResponse fileInfoResponse = new GetFileInfoFromDSResponse();
		fileInfoResponse=fileInfoResponse.getClassFromJsonString(replyInfoRequest);
		if(fileInfoResponse.getAuthstatus().equals("Y")){
			fileInfoResponse.setServerurl(SecurityFunctions.decrypt(fileInfoResponse.getServerurl(),key1));
			//Read from the server returned
			ReadRequest readRequest = new ReadRequest();
			readRequest.setFilename(SecurityFunctions.encrypt(filename, key1));
			readRequest.setToken(token);
			readRequest.setEncryptedUsername(usernameEnc);
			readRequest.setDirectory(fileInfoResponse.getDirectory());//This is also encrypted with key1
			String jsonReadRequest = readRequest.getJsonString();
			String readResponsereply = hf.sendReadRequest(jsonReadRequest,fileInfoResponse.getServerurl());
			ReadResponse readResponse = new ReadResponse();
			readResponse = readResponse.getClassFromJsonString(readResponsereply);
			String filecontent = SecurityFunctions.decrypt(readResponse.getFilecontent(),key1);
			//Send Request to lock file
			LockRequest lockRequest = new LockRequest();
			lockRequest.setEmail(SecurityFunctions.encrypt("hello@gmail.com", key1));
			lockRequest.setFilename(SecurityFunctions.encrypt(filename, key1));
			lockRequest.setToken(token);
			lockRequest.setUsername(usernameEnc);
			String lockRequestStr = lockRequest.getJsonString();
			String lockResponseStr = hf.sendLockRequest(lockRequestStr);
			LockResponse lockResponse = new LockResponse(); 
			lockResponse = lockResponse.getClassFromJsonString(lockResponseStr);
			//Success case 
			if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("Y"))	{
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				request.getSession().setAttribute("directory", SecurityFunctions.decrypt(fileInfoResponse.getDirectory(),key1));
				request.getSession().setAttribute("serverurl", fileInfoResponse.getServerurl());
				System.out.println(filecontent);
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}//Failure case with unable to get lock on file
			else if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("N")) {
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthstatus()==null)
					fileInfoResponse.setAuthstatus("");
				request.getSession().setAttribute("message", "Unable to get lock on file, file is already locked");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			} //Failure case when token validation or any other problem
			else{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthstatus()==null)
					fileInfoResponse.setAuthstatus("");
				request.getSession().setAttribute("message", "Token Validation Failed");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}			
		}else {
			System.out.println("Validation Failed");
			request.getSession().setAttribute("status", "0");
			if(fileInfoResponse.getAuthstatus()==null)
				fileInfoResponse.setAuthstatus("");
			request.getSession().setAttribute("message", fileInfoResponse.getAuthstatus());
			request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			//Set error pattern here and return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
