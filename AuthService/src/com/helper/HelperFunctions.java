package com.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.messages.PropertyStore;

public class HelperFunctions {

	public  Connection sqlconnect() {
		Connection con= null;
		if(!PropertyStore.loadstatus.equals("1"))
			PropertyStore.loadProperties();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(  
					"jdbc:mysql://"+PropertyStore.dbip+":"+
							PropertyStore.dbport+"/"+PropertyStore.dbname+
							"?useSSL=false",
							PropertyStore.dbusername,
							PropertyStore.dbpassword);
		} 

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
