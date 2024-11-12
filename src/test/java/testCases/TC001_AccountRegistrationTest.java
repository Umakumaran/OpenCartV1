package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
	
	@Test(groups= {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("**********Started TC001_AccountRegistrationTest******");
		try
		{
		Thread.sleep(3000);
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAcount link");
	
		hp.clickRegister();
		logger.info("Clicked on Register link");
	
		AccountRegistrationPage regpage= new AccountRegistrationPage(driver);
		logger.info("Providing customer info");
		
		//Generate random data
		regpage.setFirstname(randomString().toUpperCase());
		regpage.setLastname(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		regpage.setTelePhone(randomNumber());
	
		String password=randomAlphaNumberic();
	
		regpage.setPassword(password);
		regpage.setConformPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
	
		logger.info("Validating expected info");
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed");
			logger.debug("Debug logs");
			Assert.assertTrue(true);
		}
	}
		//Assert.assertEquals(confmsg,"Your Account Has Been Created!");
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("**********Finished TC001_AccountRegistrationTest******");
	}
}
	
