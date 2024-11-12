package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter; //UI of the Report
	public ExtentReports extent; //Populate Common info on the report
	//Creating test case entries in the report and update status of the test methods
	public ExtentTest test; 
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		//Create Time stamp
		/*SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt= new Date();
		String currentdatetimestamp=df.format(dt);*/
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		//Create report file in folder
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter= new ExtentSparkReporter(".\\reports\\"+repName); //Location of the folder
	  
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); //Title of report
		sparkReporter.config().setReportName("opencart Funactional Testing"); //Name of the Report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os= testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser= testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		 List<String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		 if(!includedGroups.isEmpty())
		 {
		extent.setSystemInfo("Groups", includedGroups.toString());
		 }
	}

	public void onTestSuccess(ITestResult result)
	{
		test= extent.createTest(result.getTestClass().getName()); //Create a new entry in the report 
		test.assignCategory(result.getMethod().getGroups()); 	// update the category of groups
		test.log(Status.PASS, "Test case PASSED is:"+result.getName()); //update status P/F/S
	  }

	public void onTestFailure(ITestResult result) 
	{
		test= extent.createTest(result.getTestClass().getName()); //Create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // update the category of groups
		
		test.log(Status.FAIL, "Test case FAILED is:"+result.getName()); //update status P/F/S
		test.log(Status.INFO, "Test case FAILED cause is:"+result.getThrowable().getMessage());	//Reason for test failure  
	
		//Capture the screenshot and attach it to report
	try {
		String imgPath= new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);
	}
	catch(IOException e1)
	{
		e1.printStackTrace();
	}
	}

	//After each test skipped
		public void onTestSkipped(ITestResult result) 
		{
			test= extent.createTest(result.getTestClass().getName()); //Create a new entry in the report
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP, "Test case SKIPPED is:"+result.getName());
			test.log(Status.INFO, "Test case FAILED cause is:"+result.getThrowable().getMessage());
		  }
		
		//After completion on all test
		public void onFinish(ITestContext context) 
		 {
			extent.flush();
			
			String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
			File extentReport = new File(pathOfExtentReport);
			
			//Open report automatically post test execution
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			
			//Send email of the report post execution
			/*
			 * try { URL url = new URL
			 * ("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			 * 
			 * //Create the email message ImageHtmlEmail email = new ImageHtmlEmail();
			 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
			 * email.setHostName("smtp.googlemail.com"); email.setSmtpPort(465);
			 * email.setAuthenticator(new
			 * DefaultAuthenticator("umasankarik11@gmail.com","password"));
			 * email.setSSLOnConnect(true); email.setFrom("umasankarik11@gmail.com");
			 * //sender email.setSubject("Test result");
			 * email.setMsg("Please find the attached Report");
			 * email.addTo("umapavi.k@gmail.com"); //Receiver
			 * email.attach(url,"extent report", "Please check the report"); email.send();
			 * //send the email } catch(Exception e) { e.printStackTrace(); }
			 */
			}
	 }
