package com.ninza.hrm.api.BaseClass;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {
	
	public JavaUtility jLib=new JavaUtility();
	public FileUtility fLib=new FileUtility();
	public DataBaseUtility dbLib=new DataBaseUtility();
	public static RequestSpecification specReqObj;  //We made these variables as 'public' ,sothat we can access those variables from outside of this class also & we made it as 'static',sothat we need not to create an object to use it everytime in the outside(But,if we want to call any other methods using this variable,then we can't use it as static-we should make it non-static only).
	public static ResponseSpecification specRespObj; //We made these variables as 'public' ,sothat we can access those variables from outside of this class also & we made it as 'static',sothat we need not to create an object to use it everytime in the outside(But,if we want to call any other methods using this variable,then we can't use it as static-we should make it non-static only).
	
	@BeforeSuite
	public void configBS() throws Throwable {
		dbLib.connectToDB();
		System.out.println("==============Connect to DB=============");
		
		RequestSpecBuilder builder=new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
		     //builder.setAuth(basic("username", "password"));//Actually we used OAUTH2.0 in our RMG HRM Application(not the basic Auth),so for that also we can set the Auth in this same way only.//This is just to show as an example only that where and all we can use it& not required for these testcases.
		     //builder.addHeader("", ""); //Currently we don't have a common header for each request,so only we kept empty here.//This is just to show as an example only that where and all we can use it& not required for these testcases.
		builder.setBaseUri(fLib.getDataFromPropertiesFile("BASEUri"));
		specReqObj = builder.build(); //This 'build()'is a must to end.
	
		
	
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
		specRespObj = resBuilder.build(); //This 'build()' is a must to end.
	}
	
	
	
	
	@AfterSuite
	public void configAS() throws SQLException {
		dbLib.closeDB();
		System.out.println("==============DisConnect to DB=============");
	}
}
