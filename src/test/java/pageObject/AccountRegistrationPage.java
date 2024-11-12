package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AccountRegistrationPage extends BasePage
{
	public AccountRegistrationPage(WebDriver driver)
	{
	super(driver);
	}

	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;

	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;

	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement lnkConfirmPassword;

	@FindBy(xpath="//input[@name='agree']")
	WebElement chkPrivacyPolicy;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath="(//h1[normalize-space()='Your Account Has Been Created!'])[1]")
	WebElement msgConfirmation;

	public void setFirstname(String fname)
	{
	txtFirstname.sendKeys(fname);
	}

	public void setLastname(String lname)
	{
	txtLastname.sendKeys(lname);
	}

	public void setEmail(String email)
	{
	txtEmail.sendKeys(email);
	}
	
	public void setTelePhone(String tele)
	{
	txtTelephone.sendKeys(tele);
	}

	public void setPassword(String pwd)
	{
	txtPassword.sendKeys(pwd);
	}

	public void setConformPassword(String pwd)
	{
	lnkConfirmPassword.sendKeys(pwd);
	}

	public void setPrivacyPolicy()
	{
	chkPrivacyPolicy.click();
	}

	public void clickContinue()
	{
	//Sol1
	btnContinue.click();

	//sol2
//	btnContinue.submit();
//
	//sol3
//	Actions act=new Actions(driver);
//	act.moveToElement(btnContinue).click().perform();
//
	//sol4
//	JavascriptExecutor js=(JavascriptExecutor)driver;
//	js.executeScript("arguments[0].click()",btnContinue);
//
	//sol5
//	btnContinue.sendKeys(Keys.RETURN);
//
	//sol6
//	WebDriverWait mywait= new WebDriverWait(driver, Duration.ofSeconds(10));
//	mywait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}

	public String getConfirmationMsg()
	{
	try{
	return(msgConfirmation.getText());
	}
	catch(Exception e)
	{
	return(e.getMessage());
	}
	}
}
