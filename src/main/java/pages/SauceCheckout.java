package pages;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.qameta.allure.Allure;

public class SauceCheckout {
    public WebDriver driver;

    private By checkoutProduct 			= 	By.cssSelector(".inventory_item_name");
    private By caneclCheckoutButton 	= 	By.cssSelector("a.cart_cancel_link.btn_secondary[href='./inventory.html']");
    private By checkoutButton 			= 	By.cssSelector("a.btn_action.cart_button[href='./checkout-complete.html']");
    private By productPrice 			= 	By.cssSelector("div.inventory_item_price");
    private By paymentInfomartion 		= 	By.cssSelector("div.summary_info_label");
    private By sauceCardNumber 			= 	By.cssSelector("div.summary_value_label");
    private By shippingInformation 		= 	By.xpath("(//div[@class='summary_info_label'])[2]");
    private By ponyExpressDelivery 		= 	By.xpath("(//div[@class='summary_value_label'])[2]");
    private By itemTotal 				= 	By.cssSelector("div.summary_subtotal_label");
    private By taxValue 				= 	By.className("summary_tax_label");
    private By summaryTotal 			= 	By.className("summary_total_label");
    private By thankyouMessage 			= 	By.className("complete-header");
    private By menuIcon 				= 	By.cssSelector(".bm-burger-button > button");
    private By logout 					= 	By.xpath("//a[contains(text(),'Logout')]");

    public SauceCheckout(WebDriver driver) {
        this.driver = driver; 
    }
    
    public void Checkout() {
        try {
            String actualUrl = driver.getCurrentUrl();
            String expectedUrl = "https://www.saucedemo.com/v1/checkout-step-two.html";
            Assert.assertEquals(actualUrl, expectedUrl, "Incorrect URL");

            WebElement checkProduct = driver.findElement(checkoutProduct);
            String checkoutProductName = checkProduct.getText();

            // Verifying that the cart contains the product added
            Assert.assertEquals(checkoutProductName, "Sauce Labs Backpack", "The product in the cart does not match");
            System.out.println("The product in the cart matches the added product: " + checkoutProductName);
            Allure.step("Verifying the user redirected to correct page");
            takeAndAttachScreenshot("Step 1: Initial State");

            // Verifying that the item price text is as expected
            WebElement itemPrice = driver.findElement(productPrice);
            String productPriceText = itemPrice.getText();
            Assert.assertEquals(productPriceText, "$29.99", "The product price is not displayed as expected.");
            System.out.println("The Product price is displayed correctly: " + productPriceText);
            Allure.step("Verifying product is present");
            takeAndAttachScreenshot("Step 2: After Action 1");

            // Verifying that the text matches "Payment Information"
            WebElement paymentInfo = driver.findElement(paymentInfomartion);
            String paymentInfoText = paymentInfo.getText();
            Assert.assertEquals(paymentInfoText, "Payment Information:", "The payment information label is not displayed as expected.");
            Allure.step("Verifying that the product details are correct");
            takeAndAttachScreenshot("Step 3: After Action 2");

            // Verifying that the text matches "SauceCard #31337"
            WebElement sauceCardNo = driver.findElement(sauceCardNumber);
            String sauceCardText = sauceCardNo.getText();
            Assert.assertEquals(sauceCardText, "SauceCard #31337", "The SauceCard label is not displayed as expected.");

            // Verifying that the text matches "Shipping Information:"
            WebElement shippingInfo = driver.findElement(shippingInformation);
            String shippingInfoText = shippingInfo.getText();
            Assert.assertEquals(shippingInfoText, "Shipping Information:", "The Shipping Information label is not displayed as expected.");
            System.out.println("The Shipping Information label is displayed correctly: " + shippingInfoText);

            // Verifying that the text matches "FREE PONY EXPRESS DELIVERY!"
            WebElement ponyDelivery = driver.findElement(ponyExpressDelivery);
            String freeDeliveryText = ponyDelivery.getText();
            Assert.assertEquals(freeDeliveryText, "FREE PONY EXPRESS DELIVERY!", "The Free Pony Express Delivery label is not displayed as expected.");
            System.out.println("The Free Pony Express Delivery label is displayed correctly: " + freeDeliveryText);

            // Verifying the element is displayed on the screen
            WebElement itemTotalValue = driver.findElement(itemTotal);
            Assert.assertTrue(itemTotalValue.isDisplayed(), "'Item total' label is not displayed on the screen.");
            String expectedText = "Item total: $29.99";
            String actualText = itemTotalValue.getText();
            Assert.assertEquals(actualText, expectedText, "The 'Item total' text does not match the expected value.");
            System.out.println("Verified that the 'Item total' is displayed correctly: " + actualText);
            double itemTotal = Double.parseDouble(actualText.replaceAll("[^\\d.]", ""));

            // Verifying the element is displayed on the screen
            WebElement taxLabelElement = driver.findElement(taxValue);
            Assert.assertTrue(taxLabelElement.isDisplayed(), "Tax value is not displayed on the screen.");
            String expectedTaxText = "Tax: $2.40";
            String actualTaxText = taxLabelElement.getText();
            Assert.assertEquals(actualTaxText, expectedTaxText, "The tax label text is not as expected.");
            double taxValue = Double.parseDouble(actualTaxText.replaceAll("[^\\d.]", ""));

            double calculatedTotal = itemTotal + taxValue;

            WebElement summaryTotalValue = driver.findElement(summaryTotal);
            Assert.assertTrue(summaryTotalValue.isDisplayed(), "Total value is not displayed on the screen.");
            String actualTotalText = summaryTotalValue.getText();
            String expectedTotalText = "Total: $" + String.format("%.2f", calculatedTotal);
            Assert.assertEquals(actualTotalText, expectedTotalText, "The total label text is not as expected.");
            System.out.println("Verified that the total is displayed: " + actualTotalText + " as expected " + expectedTotalText);

            Allure.step("Verifying that the product price calculated are correctly shown");
            takeAndAttachScreenshot("Step 4: After Action 3");

            // Verifying that the CANCEL button is displayed
            WebElement cancelBtn = driver.findElement(caneclCheckoutButton);
            Assert.assertTrue(cancelBtn.isDisplayed(), "Cancel button is not displayed on the screen");
            System.out.println("Cancel button is displayed on the screen.");

            // Verifying that the Checkout button is displayed
            WebElement webCheckoutButton = driver.findElement(checkoutButton);
            Assert.assertTrue(webCheckoutButton.isDisplayed(), "Checkout button is not displayed on the screen");
            System.out.println("Checkout button is displayed on the screen.");
            webCheckoutButton.click();

            // Verifying that the message is displayed on the screen
            WebElement thankYouMsg = driver.findElement(thankyouMessage);
            Assert.assertTrue(thankYouMsg.isDisplayed(), "'THANK YOU FOR YOUR ORDER' message is not displayed on the screen.");
            String expectedHeaderText = "THANK YOU FOR YOUR ORDER";
            String actualHeaderText = thankYouMsg.getText();
            Assert.assertEquals(actualHeaderText, expectedHeaderText, "The message does not match the expected value.");
            System.out.println("Verified that the message is displayed correctly: " + actualHeaderText);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement menuElement = wait.until(ExpectedConditions.elementToBeClickable(menuIcon));
            menuElement.click();

            WebElement logoutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(logout));
            logoutElement.click();

        }
        catch (NoSuchElementException e) 
        {
            System.err.println("Element not found: " + e.getMessage());
        }
        catch (AssertionError e) 
        {
            System.err.println("Assertion failed: " + e.getMessage());
        }
        catch (Exception e) 
        {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }


    private void takeAndAttachScreenshot(String stepName) 
    {
        // Take a screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to the Allure report
        Allure.addAttachment(stepName, new ByteArrayInputStream(screenshot));
    }
}