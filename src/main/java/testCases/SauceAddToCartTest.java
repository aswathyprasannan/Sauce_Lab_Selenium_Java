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

import pages.SauceAddToCart;
import pages.SauceProduct;

public class SauceAddToCartTest {

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
	@Description("Verify the user can add a product to the cart and verify the product is same in the cart")
	@Severity(SeverityLevel.NORMAL)
	public void ProductAddToCart() throws Exception
	{
		SauceAddToCart addCart 		=	new SauceAddToCart(driver);
		SauceProduct sauceProd		=	new SauceProduct(driver);
		
		sauceProd.Login();
		addCart.AddProductToCart();
	}
	
	@AfterClass
	public void close()
	{
		driver.quit();
	}
}