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
import messages.GetFileInfoFromDSResponse;
import messages.LockRequest;
import messages.PropertyStore;
import messages.ReadRequest;
import messages.ReadResponse;

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
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("filecontent", filecontent);
			request.getSession().setAttribute("filename", filename);
			request.getRequestDispatcher("readfile.jsp").forward(request, response);
		}else {
			System.out.println("Validation Failed");
			request.getSession().setAttribute("status", "0");
			if(fileInfoResponse.getAuthstatus()==null)
				fileInfoResponse.setAuthstatus("");
			request.getSession().setAttribute("message", fileInfoResponse.getAuthstatus());
			request.getRequestDispatcher("readfile.jsp").forward(request, response);
			//Set error pattern here and return;
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
