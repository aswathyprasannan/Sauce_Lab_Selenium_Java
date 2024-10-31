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

public class SauceAddToCart {
	
    private WebDriver driver;

    // Locators
    private By cartIcon 				= 	By.cssSelector("a.shopping_cart_link.fa-layers.fa-fw");
    private By addToCartButton 			= 	By.cssSelector(".btn_primary.btn_inventory");
    private By productAdded 			= 	By.cssSelector(".inventory_item_name");
    private By productInCart 			= 	By.cssSelector(".inventory_item_name");
    private By removeButton 			= 	By.cssSelector(".btn_secondary.cart_button");
    private By continueShoppingButton 	= 	By.cssSelector("a.btn_secondary[href='./inventory.html']");

    // Constructor
    public SauceAddToCart(WebDriver driver) {
        this.driver = driver;
    }

    public void AddProductToCart() {
        String addedProductName = "";

        try {
            // Fetching the product name
            WebElement productNameElement = driver.findElement(productAdded);
            addedProductName = productNameElement.getText();

            // Click on the add to cart button
            WebElement addToCartBtn = driver.findElement(addToCartButton);
            addToCartBtn.click();

            Allure.step("Click on add to cart");
            takeAndAttachScreenshot("Step 1: Initial State");

            // Locating the cart count element and its count
            WebElement cartCountElement = driver.findElement(By.cssSelector(".fa-layers-counter.shopping_cart_badge"));
            String cartCountText = cartCountElement.getText();
            int cartCount = Integer.parseInt(cartCountText); // Convert to integer

            // Assert that the cart count got incremented as expected
            Assert.assertEquals(cartCount, 1, "The cart count did not increment as expected.");
            System.out.println("Current cart count: " + cartCount);

            Allure.step("Verify the count of cart increased");
            takeAndAttachScreenshot("Step 2: After Action 1");

            addToCartBtn = driver.findElement(By.cssSelector(".btn_secondary.btn_inventory"));

            // Assert that the button is displayed and its text is "REMOVE"
            Assert.assertTrue(addToCartBtn.isDisplayed(), "'Remove' button is not displayed.");
            Assert.assertEquals(addToCartBtn.getText(), "REMOVE", "The button text did not change to REMOVE.");

            System.out.println("Button text after adding to cart: " + addToCartBtn.getText());

            Allure.step("The add to cart button changed to Remove button");
            takeAndAttachScreenshot("Step 3: After Action 2");

            // Clicked cart icon
            driver.findElement(cartIcon).click();
            String actualUrl = driver.getCurrentUrl();

            Allure.step("Click on the cart");
            takeAndAttachScreenshot("Step 4: After Action 3");

            // Verifying the URL
            String expectedUrl = "https://www.saucedemo.com/v1/cart.html";
            Assert.assertEquals(actualUrl, expectedUrl, "Incorrect URL");

            Allure.step("Check the URL of the page");
            takeAndAttachScreenshot("Step 5: After Action 4");

            // Locate the product name in the cart
            WebElement cartProductElement = driver.findElement(productInCart);
            String cartProductName = cartProductElement.getText();

            // Verifying that the cart contains the product added
            Assert.assertEquals(cartProductName, addedProductName, "The product in the cart does not match the added product.");
            System.out.println("The product in the cart matches the added product: " + cartProductName);

            Allure.step("Checking the product displayed and added are the same");
            takeAndAttachScreenshot("Step 6: After Action 5");

            // Verifying the Remove and Continue Shopping buttons are displayed
            WebElement removeBtn = driver.findElement(removeButton);
            WebElement continueShoppingBtn = driver.findElement(continueShoppingButton);
            Assert.assertTrue(removeBtn.isDisplayed(), "'Remove' button is not displayed on the cart page.");
            Assert.assertTrue(continueShoppingBtn.isDisplayed(), "'Continue Shopping' button is not displayed on the cart page.");

            Allure.step("Check there is Remove option on cart page");
            takeAndAttachScreenshot("Step 7: After Action 6");

            Allure.step("Check there is Continue option on cart page");
            takeAndAttachScreenshot("Step 8: After Action 7");

        } 
        catch (NoSuchElementException e) {
            // Catch when an expected element is not found
            System.err.println("Element not found: " + e.getMessage());
            takeAndAttachScreenshot("Error during addProductToCart");
            Assert.fail("Element not found: " + e.getMessage());
        } 
        catch (Exception e) {
            // Handles other exceptions
            System.err.println("An error occurred: " + e.getMessage());
            takeAndAttachScreenshot("Error during addProductToCart");
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        } 
        finally {
            // No soft assertions, just cleanup if needed
            System.out.println("Add product to cart test completed.");
        }
    }

    private void takeAndAttachScreenshot(String stepName) {
        // Take a screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to Allure report with a step name
        Allure.addAttachment(stepName, new ByteArrayInputStream(screenshot));
    }
}
