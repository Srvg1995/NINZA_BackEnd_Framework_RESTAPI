package com.ninza.hrm.api.genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import com.mysql.cj.jdbc.Driver;

public class DataBaseUtility {

	static Connection con = null;
	static ResultSet result = null;
/*In below line we are creating a Utility Object(ie,FileUtility)inside another utility(ie,DataBaseUtility)here*/
	static FileUtility fLib = new FileUtility(); // In order to access inside this below static method,we are declaring this 'object' as 'static'.
													

	/**
	 * Connect to Database
	 * @throws Throwable
	 */
	public static void connectToDB() throws Throwable {
		Driver driverRef;
		try {
			driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			con = DriverManager.getConnection(fLib.getDataFromPropertiesFile("DBUrl"),
					fLib.getDataFromPropertiesFile("DB_Username"), fLib.getDataFromPropertiesFile("DB_Password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * close the DB connection
	 * 
	 * @throws SQLException
	 */
	public static void closeDB() throws SQLException {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
	
	

	/**
	 * getDataFromDB method to retrieve data from database as key value pairs
	 * 
	 * @param query
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static ResultSet executeQuery(String query) throws Throwable {
		try {
			// execute the query
			result = con.createStatement().executeQuery(query);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return result;
	}
	
	

	/**
	 * 
	 * @param query
	 * @param columnName
	 * @param expectedData
	 * @return
	 * @throws Throwable
	 */
	public static boolean executeQueryVerifyAndGetData(String query, int columnName, String expectedData)
			throws Throwable {
		boolean flag = false;
		result = con.createStatement().executeQuery(query);
		while (result.next()) {
			if (result.getString(columnName).equals(expectedData)) {
				flag = true;
				break;
			}
		}
		if(flag) {
			System.out.println(expectedData + "==>data verified in Database table");
			return true;
		}
		else {
			System.out.println(expectedData + "==>data not verified in Database table");
			return false;
		}

	}
}
