package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	//DataProvider1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path=".\\testData\\opencart_logindata.xlsx"; //taking xl file from testdata
		
		ExcelUtils xlutil=new ExcelUtils(path); //creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols]; //Created 2D array which can store the data from excel
		
		for(int i=1; i<totalrows;i++) //1   //read the data from xl storing in 2D array
		{
			for(int j=0;j<totalcols; j++) //0  //i is row j is col
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1",i,j); //1,0
			}
		}
		return logindata; //Returning 2D array
	}

}

//DataProvider2

//DataProvider3

//DataProvider4