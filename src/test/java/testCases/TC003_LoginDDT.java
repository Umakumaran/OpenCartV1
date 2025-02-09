package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	

	//Provide the class name if the DP is from different package/class
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class ,groups="DataDriven")
	public void verify_loginDDT(String email, String pwd, String exp)
	{
	logger.info("********Started TC003_LoginDDT**************");
	try
	{
	//HomePage
			HomePage hp= new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			

			//MyAccountPage
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage= macc.isMyAccountPageExist();
			
			/*
			Data is valid - login is success - test pass - logout
			Data is valid - login failed- test fail
			*/
			if(exp.equalsIgnoreCase("Valid"))
			{
			if(targetPage==true)
			{
			macc.clickLogout();
			Assert.assertTrue(true);
			}
			else
			{
			Assert.assertTrue(false);
			}
			}	

			/*
			Data is invalid - login is success - test fail - logout
			Data is invalid - login failed- test pass
			*/
			if(exp.equalsIgnoreCase("Invalid"))
			{
			if(targetPage==true)
			{
			macc.clickLogout();
			Assert.assertTrue(false);
			}
			else
			{
			Assert.assertTrue(true);
			}
			}
	}
			catch(Exception e)
			{
			Assert.fail();
			}	
			logger.info("**********Finished TC003_LoginDDT***********");	
}
}
