package pages;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;

import io.netty.handler.timeout.TimeoutException;
import io.qameta.allure.Allure;

public class SauceLogin {
    public WebDriver driver;

    private By loginButton 			= 	By.xpath("//input[@id='login-button']");
    private By username 			= 	By.xpath("//input[@id='user-name']");
    private By password 			= 	By.xpath("//input[@id='password']");

    public SauceLogin(WebDriver driver) {
        this.driver = driver; 
    }
    
    public void Login(String username, String password) 
    {
        try 
        {
            // Enter user name
            driver.findElement(this.username).sendKeys(username);
            Allure.step("Entered username");
            takeAndAttachScreenshot("Step 1: Entered Username");

            // Enter password
            driver.findElement(this.password).sendKeys(password);
            Allure.step("Entered password");
            takeAndAttachScreenshot("Step 2: Entered Password");

            // Click login button
            driver.findElement(loginButton).click();
            takeAndAttachScreenshot("Step 3: Clicked Login Button");

            // Wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Validating login outcomes
            if (username.equals("standard_user") || username.equals("performance_glitch_user") || username.equals("problem_user")) 
            {
                wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/v1/inventory.html"));
                String actualUrl = driver.getCurrentUrl();
                Assert.assertEquals(actualUrl, "https://www.saucedemo.com/v1/inventory.html", "Incorrect URL after successful login");
            } 
            else if (username.equals("locked_out_user")) 
            {
                WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
                String errorMessage = errorMessageElement.getText();
                Assert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.", "Incorrect error message for locked-out user");
            } 
            else 
            {
                WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(.,'Epic sadface: Username is required')]")));
                String errorMessage = errorMessageElement.getText();
                Assert.assertEquals(errorMessage, "Epic sadface: Username is required", "Incorrect error message for empty username");
            }
        } 
        catch (NoSuchElementException e) 
        {
            Allure.step("Element not found: " + e.getMessage());
            takeAndAttachScreenshot("Error: Element Not Found");
            Assert.fail("Test failed due to a missing element: " + e.getMessage());
        } 
        catch (TimeoutException e) 
        {
            Allure.step("Timeout waiting for element: " + e.getMessage());
            takeAndAttachScreenshot("Error: Timeout Exception");
            Assert.fail("Test failed due to timeout: " + e.getMessage());
        }
        catch (Exception e) 
        {
            Allure.step("An unexpected error occurred: " + e.getMessage());
            takeAndAttachScreenshot("Error: Unexpected Exception");
            Assert.fail("Test failed due to an unexpected error: " + e.getMessage());
        }
        finally 
        {
            try 
            {
                driver.findElement(By.xpath("//*[@id=\"menu_button_container\"]/div/div[3]/div/button")).click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id=\"logout_sidebar_link\"]"))).click();
            }
            catch (Exception e) 
            {
                Allure.step("Logout failed: " + e.getMessage());
                takeAndAttachScreenshot("Error: Logout Exception");
            }
        }
    }

    private void takeAndAttachScreenshot(String stepName) {
        // Take a screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to Allure report with a step name
        Allure.addAttachment(stepName, new ByteArrayInputStream(screenshot));
    }
}
