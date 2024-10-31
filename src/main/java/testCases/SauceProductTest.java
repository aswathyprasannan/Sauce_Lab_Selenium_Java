package testCases;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.SauceProduct;


public class SauceProductTest {

	public WebDriver driver;
	
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception 
	{
		if (browser.equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
			driver.get("https://www.saucedemo.com/v1/index.html");
		} 
		else if (browser.equalsIgnoreCase("chrome")) 
		{
			driver = new ChromeDriver();
			driver.get("https://www.saucedemo.com/v1/index.html");
		} 
		else if (browser.equalsIgnoreCase("Edge"))
		{
			driver = new EdgeDriver();
			driver.get("https://www.saucedemo.com/v1/index.html");
			
		} 
		else 
		{
			throw new Exception("Incorrect Browser");			
		}
		driver.manage().window().maximize();
	}
	
	@Test(priority=1)
	@Description("Verify the user can view all products on the home page")
	@Severity(SeverityLevel.NORMAL)
	public void LoginSubmission() throws Exception
	{
		SauceProduct sauceProd = new SauceProduct(driver);
		sauceProd.Login();
		sauceProd.ProductList();
	}
	
	@Test(priority=2)
	@Description("Verify the user can perform sorting options for the products based on price and name")
	@Severity(SeverityLevel.NORMAL)	
	public void Product() throws Exception
	{
		SauceProduct sauceProd = new SauceProduct(driver);
		sauceProd.ProductNameSorting();
		sauceProd.ProductPriceSorting();
	}
	
	@AfterClass
	public void close()
	{
		driver.quit();
	}	
}