package com.services;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cache.CrudeMemoryCache;
import com.security.SecurityFunctions;

import helper.HelperFunctions;
import messages.GetFileInfoFromDSRequest;
import messages.GetFileInfoFromDSResponse;
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

		//Check if file is in memory/cache
		Long ts_then = CrudeMemoryCache.cacheStoreTime.get(filename);
		Long ts_now = System.currentTimeMillis();
		Long ts_max = TimeUnit.MINUTES.toMillis(Long.parseLong(PropertyStore.cachePersistanceTime));
		if(ts_then != null && ts_now-ts_then<ts_max) {//Serve from cache if entry time is within persistence limit
			String filecontent = CrudeMemoryCache.cacheStore.get(filename);
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("filecontent", filecontent);
			request.getSession().setAttribute("filename", filename);
			request.getRequestDispatcher("readfile.jsp").forward(request, response);
		}else{
			if(ts_then != null && ts_now-ts_then>ts_max){//Remove from cache if older than max persistence time allowed
				CrudeMemoryCache.cacheStore.remove(filename);
				CrudeMemoryCache.cacheStoreTime.remove(filename);
			}
			//Get file info from DS
			GetFileInfoFromDSRequest infoRequest = new GetFileInfoFromDSRequest();
			infoRequest.setFilename(SecurityFunctions.encrypt(filename, key1));
			infoRequest.setToken(token);
			infoRequest.setEncryptedUsername(usernameEnc);
			infoRequest.setOperation("r");
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
				if(CrudeMemoryCache.cacheStore.size()<20) {
					System.out.println("Size of cache: "+ CrudeMemoryCache.cacheStore.size());
					CrudeMemoryCache.cacheStore.put(filename, filecontent);
					CrudeMemoryCache.cacheStoreTime.put(filename, System.currentTimeMillis());
				}
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
