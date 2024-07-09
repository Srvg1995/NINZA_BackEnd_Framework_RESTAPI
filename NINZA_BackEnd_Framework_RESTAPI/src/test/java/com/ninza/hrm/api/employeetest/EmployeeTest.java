package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.Driver;
import com.ninza.hrm.api.BaseClass.BaseAPIClass;
import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;
import com.ninza.hrm.api.pojoclass.EmployeePOJO;
import com.ninza.hrm.api.pojoclass.ProjectPOJO;
import com.ninza.hrm.constants.endpoints.IEndPoint;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class EmployeeTest extends BaseAPIClass {   
/*In our TestCase Excel sheet,it is asking us to "verify the status of the Project in GUI" after JDBC program but,this is not done here,bcz here our concentration only on Backend Automation like,API layer & DB layer(WhyBcz we know how to write selenium automation program already(ie,how to launch a browser,how to login to an app,how to search for a project & how to put a validation)*/
	  
	
//TESTCASE#:1(available in Excel sheet)
//	JavaUtility jLib=new JavaUtility();
//	FileUtility fLib=new FileUtility();
//	DataBaseUtility dbLib=new DataBaseUtility();
	
	
	/*Both 'BASEURI' & '.assertThat().contentType(ContentType.JSON)' has removed in below 2 test cases by providing Request & Response Spec Builder object inside spec()(ie,'spec(specReqObj)' & 'spec(specRespObj)') */
	@Test
	public void addEmployeeTest() throws Throwable {
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		String projectName = "Jio_"+jLib.getRandomNumber();
		String userName = "user_"+jLib.getRandomNumber();
		
		//API-1==>add a project inside the Server
		ProjectPOJO pObj=new ProjectPOJO(projectName, "Created", "GAGAN", 10);
	    given()
		.spec(specReqObj)
		.body(pObj)
		.when()
		.post(IEndPoint.ADDProj)//Here,'BASEURI' coming from Property file & 'IEndPoint.ADDProj' coming from 'IEndPoint' Interface. 
		.then()
		.spec(specRespObj)
		.log().all();
		
         
       //API-2==>add Employee to the same project
         EmployeePOJO empObj=new EmployeePOJO("Arhitect", "24/06/1947", "gogo@gmail.com", userName, 18, "9888000876", projectName, "ROLE_EMPLOYEE", userName); //This class is there in 'com.ninza.hrm.api.pojoclass' package.
         given()
         .spec(specReqObj)
 		.body(empObj)
 		.when()
 		.post(IEndPoint.ADDEmp)//Here,'BASEURI' coming from Property file & 'IEndPoint.ADDEmp' coming from 'IEndPoint' Interface. 
 		.then()
         .assertThat().statusCode(201)  //validating the Status Code   //.assertThat().contentType(ContentType.JSON)==>validating the ContentType
         .and()
         .time(Matchers.lessThan(3000L))  //validating the Response Time
         .spec(specRespObj)
         .log().all();
         
         
    //Verify Employee name in DB layer
        boolean flag = dbLib.executeQueryVerifyAndGetData("select * from employee", 5, userName);
 		Assert.assertTrue(flag,"Employee/User in DB is not verified");  //This msg("Project in DB is not verified") will get display only in case of assertion failure. 
	}
	

	
	
	
	
//TESTCASE#:2(available in Excel sheet)
	@Test
	public void addEmployeeWithOutEmailTest() throws Throwable {
		
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		Random random=new Random();
		int ranNum = random.nextInt(5000);
		
		String projectName = "Jio_"+ranNum;
		String userName = "user_"+ranNum;
		
		//API-1==>add a project inside the Server
		ProjectPOJO pObj=new ProjectPOJO(projectName, "Created", "GAGAN", 10);
	    given()
	    .spec(specReqObj)
		.body(pObj)
		.when()
		.post(IEndPoint.ADDProj)//Here,'BASEURI' coming from Property file & 'IEndPoint.ADDProj' coming from 'IEndPoint' Interface. 
		.then()
		.spec(specRespObj)
		.log().all();
		
         
         
       //API-2==>add Employee to the same project
         EmployeePOJO empObj=new EmployeePOJO("Architect", "24/06/1947", "", userName, 18, "9888000876", projectName, "ROLE_EMPLOYEE", userName); //This class is there in 'com.ninza.hrm.api.pojoclass' package.
         given()
        .spec(specReqObj)
 		.body(empObj)
 		.when()
 		.post(IEndPoint.ADDEmp) //Here,'BASEURI' coming from Property file & 'IEndPoint.ADDEmp' coming from 'IEndPoint' Interface. 
 		.then()
         .assertThat().statusCode(500)  //validating the Status Code & //validating the ContentType 
         .spec(specRespObj)
         .log().all();
	}
	
}