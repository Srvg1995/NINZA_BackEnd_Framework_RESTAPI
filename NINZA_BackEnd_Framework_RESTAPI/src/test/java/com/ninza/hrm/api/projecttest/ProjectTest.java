package com.ninza.hrm.api.projecttest;

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
import com.ninza.hrm.api.pojoclass.ProjectPOJO;
import com.ninza.hrm.constants.endpoints.IEndPoint;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ProjectTest extends BaseAPIClass{
	
/*In our TestCase Excel sheet,it is asking us to "verify the status of the Project in GUI" after JDBC program but,this is not done here,bcz here our concentration only on Backend Automation like,API layer & DB layer(WhyBcz we know how to write selenium automation program already(ie,how to launch a browser,how to login to an app,how to search for a project & how to put a validation)*/
	
//TESTCASE#:1(available in Excel sheet)
	
//	JavaUtility jLib=new JavaUtility();
//	FileUtility fLib=new FileUtility();
//	DataBaseUtility dbLib=new DataBaseUtility();
	
	
	ProjectPOJO pObj; //This we made it Global,so that we can access the same variable in my 2nd TestCase also.
	
/*Both 'BASEURI' & '.assertThat().contentType(ContentType.JSON)' has removed in below 2 test cases by providing Request & Response Spec Builder object inside spec()(ie,'spec(specReqObj)' & 'spec(specRespObj)') also, 'spec(specReqObj)'==>should write this statement nextafter the given() and 'spec(specRespObj)'==>should write this statement before log().all() (ie,at the last)in each testcases. */
	@Test 
	public void addSingleProjectWithCreatedStatusTest() throws Throwable {

		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		String expMsg = "Successfully Added";
		String projectName = "ABCDE_"+jLib.getRandomNumber();

	    pObj=new ProjectPOJO(projectName, "Created", "GAGAN", 10);

		//Verify the projectName in API layer
		Response resp = given()
						.spec(specReqObj)
						.body(pObj)
						.when()
						.post(BASEURI+IEndPoint.ADDProj); //Here,'BASEURI' coming from Property file & 'IEndPoint.ADDProj' coming from 'IEndPoint' Interface. 
					resp.then()
					.assertThat().statusCode(201)  //validating the Status Code
					.assertThat().time(Matchers.lessThan(3000L))  //validating the Response Time
					.spec(specRespObj)                                         // .assertThat().contentType(ContentType.JSON)  //validating the ContentType
					.log().all();

		String actMsg = resp.jsonPath().get("msg");
		Assert.assertEquals(expMsg, actMsg);


		//Verify the projectName in DB layer
	    boolean flag = dbLib.executeQueryVerifyAndGetData("select * from project", 4, projectName);
		Assert.assertTrue(flag,"Project in DB is not verified");  //This msg("Project in DB is not verified")of 2nd argument will get display only in case of assertion failure. 
	}
	
	
	
	
	
	
//TESTCASE#:2(available in Excel sheet)	
	@Test(dependsOnMethods = "addSingleProjectWithCreatedStatusTest") //In order to avoid multiple lines of codes,I'm creating a Dependency for 1st Testcase,so that I can achieve CodeOptimization(So.I need not to write the same program for Creating a Project here).
	public void addDuplicateProjectTest() throws Throwable {
		
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		given()
		.spec(specReqObj)
		.body(pObj)
		.when()
		.post(IEndPoint.ADDProj)//Here,'BASEURI' coming from Property file & 'IEndPoint.ADDProj' coming from 'IEndPoint' Interface. 
		.then()
		.assertThat().statusCode(409)  //validating the Status Code
		.spec(specRespObj)
		.log().all();
	}
}
