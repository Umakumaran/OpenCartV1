package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@Parameters({"browser","os"})
	@BeforeClass(groups= {"Regression", "Master", "Sanity"})
	public void setup(String br, String os) throws Exception
	{
		logger=LogManager.getLogger(this.getClass());
		
		//Loading config.properties file
		FileReader file= new FileReader("./src//test//resources//config.properties");
		//FileInputStream file = new FileInputStream("./src//test//resources"); //Another way to read file
		p=new Properties();
		p.load(file);
		
			
	System.setProperty("webdriver.chrome.driver", "E:\\prsnl\\Testing\\Software Copy\\109.0.5414.74\\chromedriver.exe");
	 Thread.sleep(5000);
	 
	 //To open remote env
	 if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
	 {
		 DesiredCapabilities capabilities = new DesiredCapabilities();
		 
		 //OS
		 if(os.equalsIgnoreCase("windows"))
		 {
			 capabilities.setPlatform(Platform.WIN8_1);
		 }
		 else if(os.equalsIgnoreCase("mac"))
		 {
			 capabilities.setPlatform(Platform.MAC);
		 }
		 else
		 {
			System.out.println("No matching os");
			return;
		 }
		 
		 //browser
			switch(br.toLowerCase())
			{
			case "chrome":capabilities.setBrowserName("chrome"); break;
			case "internet": capabilities.setBrowserName("internet explorer");break;
			default: System.out.println("Invalid browser"); return;
			}
			driver= new RemoteWebDriver(new URL("http://192.168.1.5:4444/wd/hub"),capabilities);
	 }
	 
			//To open local system
			if(p.getProperty("execution_env").equalsIgnoreCase("local"))
			{
		switch(br.toLowerCase())
		{
		case "chrome":driver=new ChromeDriver(); break;
		case "internet":driver=new InternetExplorerDriver() ;break;
		default: System.out.println("Invalid browser"); return;
		}
	        driver.manage().deleteAllCookies();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        driver.get(p.getProperty("url"));
	    	System.out.println("Opening application");
			}
	}

	@AfterClass(groups= {"Regression", "Master", "Sanity"})
	public void tearDown()
	{
	driver.quit();
	}


	//To generate Random data
	public String randomString()
	{
	//It's from commons library
	String generatedstring=RandomStringUtils.randomAlphabetic(5);
	return generatedstring;
	}

	public String randomNumber()
	{
	String generatednumber=RandomStringUtils.randomNumeric(10);
	return generatednumber;
	}

	public String randomAlphaNumberic()
	{
	String generatedstring=RandomStringUtils.randomAlphabetic(3);
	String generatednumber=RandomStringUtils.randomNumeric(3);
	return (generatedstring+generatednumber);
	}
	
	public String captureScreen(String tname) throws IOException
	{
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile= takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath= System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	
	}

}
	

