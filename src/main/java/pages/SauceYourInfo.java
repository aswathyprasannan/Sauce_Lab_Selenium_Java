package pages;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.qameta.allure.Allure;

public class SauceYourInfo {
    public WebDriver driver;  
    
    private By checkoutButton 	= 	By.cssSelector("a.btn_action.checkout_button");
    private By firstname 		= 	By.xpath("//input[@id='first-name']");
    private By lastname 		= 	By.xpath("//input[@id='last-name']");
    private By postalcode 		= 	By.xpath("//input[@id='postal-code']");
    private By yourInfoContinue = 	By.xpath("//input[@value='CONTINUE']");
    private By errorMessage		=	By.cssSelector("h3[data-test='error']");
    
    // Constructor
    public SauceYourInfo(WebDriver driver) 
    {
        this.driver = driver;
    }

    public void AddYourInformation() {
        try {
            // Clicked checkout button
            WebElement checkoutBtn = driver.findElement(checkoutButton);
            Assert.assertTrue(checkoutBtn.isDisplayed(), "'CHECKOUT' button is not displayed.");
            checkoutBtn.click();

            // Clicked form submission button to continue
            driver.findElement(yourInfoContinue).click();
            Allure.step("Clicked on Continue button leaving the form empty");

            // Verifying the error message
            WebElement errorMessageElement 	= 	driver.findElement(errorMessage);
            String actualErrorMessage 		= 	errorMessageElement.getText();
            String expectedErrorMessage 	= 	"Error: First Name is required";            
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch");
            Allure.step("Verified the validation message");

            // Entered first name
            driver.findElement(firstname).sendKeys("Test");
            Allure.step("Entered firstname");
            takeAndAttachScreenshot("Step 1: Initial State");

            // Entered last name
            driver.findElement(lastname).sendKeys("Test r");
            Allure.step("Entered lastname");
            takeAndAttachScreenshot("Step 2: After Action 1");

            // Entered postal code
            driver.findElement(postalcode).sendKeys("B7YY GTD");
            Allure.step("Entered postalcode");
            takeAndAttachScreenshot("Step 3: After Action 2");

            // Clicked form submission continue button
            driver.findElement(yourInfoContinue).click();
            Allure.step("Clicked on Continue button");
            takeAndAttachScreenshot("Step 4: After Action 3");
        }
    	catch (NoSuchElementException e) 
        {
            System.err.println("Element not found: " + e.getMessage());
            takeAndAttachScreenshot("Error during AddYourInformation");
            Assert.fail("Element not found: " + e.getMessage());
        }
        catch (Exception e) 
        {
            System.err.println("An error occurred: " + e.getMessage());
            takeAndAttachScreenshot("Error during AddYourInformation");
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
        finally 
        {           
            System.out.println("Add your information test completed.");
        }
    }

    private void takeAndAttachScreenshot(String stepName) 
    {
        // Take a screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to Allure report with a step name
        Allure.addAttachment(stepName, new ByteArrayInputStream(screenshot));
    }
}
