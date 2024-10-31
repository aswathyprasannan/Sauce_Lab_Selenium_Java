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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.SauceLogin;

public class SauceLoginTest {

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
	
	@Test(priority=1, dataProvider = "loginData")
	@Description("Verify the login form is working as expected")
	@Severity(SeverityLevel.NORMAL)
	public void LoginSubmission(String username, String password) throws Exception
	{
		SauceLogin sauceLogin =new SauceLogin(driver);
		sauceLogin.Login(username, password);
	}
	
	@DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce"},
            {"performance_glitch_user","secret_sauce"},
            {"problem_user", "secret_sauce"},
            {"locked_out_user", "secret_sauce"}
             
        };
    }
	 
	@AfterClass
	public void close()
	{
		driver.quit();
	}
}
