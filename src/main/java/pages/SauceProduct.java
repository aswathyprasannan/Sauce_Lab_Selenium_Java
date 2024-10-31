package pages;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;

public class SauceProduct {
    public WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();

    private By loginButton 		= 	By.xpath("//input[@id='login-button']");
    private By username 		= 	By.xpath("//input[@id='user-name']");
    private By password 		= 	By.xpath("//input[@id='password']");

    public SauceProduct(WebDriver driver) {
        this.driver = driver; 
    }
    
    public void Login() throws InterruptedException 
    {
        try 
        {
        	//Entered user name
            driver.findElement(username).sendKeys("standard_user");
            Allure.step("Entered username");
            takeAndAttachScreenshot("Step 1: Initial State");
            
            //Entered password
            driver.findElement(password).sendKeys("secret_sauce");
            Allure.step("Entered password");
            takeAndAttachScreenshot("Step 2: After Action 1");

            //Clicked on Login
            driver.findElement(loginButton).click();
            Allure.step("Clicked on Login button");
            takeAndAttachScreenshot("Step 3: After Action 2");
        }
        catch (NoSuchElementException e) 
        {
            Assert.fail("Login elements not found: " + e.getMessage());
        }
        catch (Exception e) 
        {
            Assert.fail("An unexpected error occurred during login: " + e.getMessage());
        }
    }

    public void ProductList() 
    {
        try 
        {
            // Check all products are displayed 
            List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
            Assert.assertFalse(products.isEmpty(), "No products are displayed.");

            // Get the count of products displayed on the page
            int productCount = products.size();
            System.out.println("Number of products displayed on the page: " + productCount);

            // Verifying each product's details
            for (WebElement product : products) {
                // product name, description, price and add to cart button
                WebElement productName 			= 	product.findElement(By.cssSelector(".inventory_item_name"));
                WebElement productDescription 	= 	product.findElement(By.cssSelector(".inventory_item_desc"));
                WebElement productPrice 		= 	product.findElement(By.cssSelector(".inventory_item_price"));
                WebElement addToCartButton 		= 	product.findElement(By.cssSelector(".btn_inventory"));

                //Verifying that the product name is visible
                Assert.assertTrue(productName.isDisplayed(), "Product name is not displayed.");
                // Verifying that the product description is visible
                Assert.assertTrue(productDescription.isDisplayed(), "Product description is not displayed.");
                // Verifying that the product price is visible
                Assert.assertTrue(productPrice.isDisplayed(), "Product price is not displayed.");
                // Verifying that the "Add to Cart" button is visible
                Assert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart button is not displayed.");
            }
            Allure.step("Verifying all products and the total count of products displayed");
            takeAndAttachScreenshot("Step 1: Initial State");
        }
        catch (NoSuchElementException e) 
        {
            Assert.fail("Product list elements not found: " + e.getMessage());
        }
        catch (Exception e) 
        {
            Assert.fail("An unexpected error occurred while retrieving the product list: " + e.getMessage());
        }
    }

    public void ProductNameSorting() 
    {
        try 
        {
            WebElement sortDropdown = driver.findElement(By.cssSelector("select.product_sort_container"));

            // Select the ascending sort option
            Select select = new Select(sortDropdown);
            select.selectByVisibleText("Name (A to Z)");

            // Capture product names after sorting ascending
            List<WebElement> productElementsAsc = driver.findElements(By.cssSelector(".inventory_item_name"));
            List<String> productNamesAsc = new ArrayList<>();

            for (WebElement product : productElementsAsc) {
                productNamesAsc.add(product.getText());
            }

            Allure.step("Selected sorting products based on ascending order of the name");
            takeAndAttachScreenshot("Step 1: Initial State");

            // Created a sorted copy of the product names for ascending order
            List<String> sortedProductNamesAsc = new ArrayList<>(productNamesAsc);
            Collections.sort(sortedProductNamesAsc);

            // Validating the sorted are in ascending 
            Assert.assertEquals(productNamesAsc, sortedProductNamesAsc, "The products are not sorted in ascending order.");
            // Print the sorted product names in ascending order
            System.out.println("Products sorted in ascending order: " + sortedProductNamesAsc);

            Allure.step("Verified the products listed are sorted based on product name in ascending order");
            takeAndAttachScreenshot("Step 2: After Action 1");

            // Select the descending sort option
            select.selectByVisibleText("Name (Z to A)");

            // Capture product names after sorting descending
            List<WebElement> productElementsDesc = driver.findElements(By.cssSelector(".inventory_item_name"));
            List<String> productNamesDesc = new ArrayList<>();

            for (WebElement product : productElementsDesc) {
                productNamesDesc.add(product.getText());
            }

            Allure.step("Selected sorting products based on descending order of the name");
            takeAndAttachScreenshot("Step 3: After Action 2");

            // Create a sorted copy of the product names for descending order
            List<String> sortedProductNamesDesc = new ArrayList<>(productNamesDesc);
            Collections.sort(sortedProductNamesDesc, Collections.reverseOrder());

            //  Validating the sorted are in descending 
            Assert.assertEquals(productNamesDesc, sortedProductNamesDesc, "The products are not sorted in descending order.");
            // Print the sorted product names in descending order
            System.out.println("Products sorted in descending order: " + sortedProductNamesDesc);

            Allure.step("Verified the products listed are sorted based on product name in descending order");
            takeAndAttachScreenshot("Step 4: After Action 3");
        }
        catch (NoSuchElementException e) 
        {
            Assert.fail("Sorting elements not found: " + e.getMessage());
        }
        catch (Exception e) 
        {
            Assert.fail("An unexpected error occurred during product name sorting: " + e.getMessage());
        }
    }

    public void ProductPriceSorting() {
        try {
            // Finding the sorting drop down
            WebElement sortDropdown = driver.findElement(By.cssSelector("select.product_sort_container"));

            // Select the "Price (low to high)" sort option
            Select select = new Select(sortDropdown);
            select.selectByVisibleText("Price (low to high)"); 

            // Capture product prices after sorting low to high
            List<WebElement> productElementsLowToHigh = driver.findElements(By.cssSelector(".inventory_item_price"));
            List<Double> productPricesLowToHigh = new ArrayList<>();

            for (WebElement product : productElementsLowToHigh) {
                String priceText = product.getText().replace("$", ""); 
                productPricesLowToHigh.add(Double.parseDouble(priceText)); 
            }

            Allure.step("Selected sorting products price based on low to high");
            takeAndAttachScreenshot("Step 5: After Action 4");

            // Create a sorted copy of the product prices for low to high
            List<Double> sortedPricesLowToHigh = new ArrayList<>(productPricesLowToHigh);
            Collections.sort(sortedPricesLowToHigh);

            // Validate low to high sorting
            Assert.assertEquals(productPricesLowToHigh, sortedPricesLowToHigh, "The products are not sorted in low to high order.");
            // Print the sorted product prices in low to high order
            System.out.println("Products sorted in low to high order: " + sortedPricesLowToHigh);

            Allure.step("Verified the products listed are sorted based on product price from low to high");
            takeAndAttachScreenshot("Step 6: After Action 5");

            //Select the "Price (high to low)" sort option
            select.selectByVisibleText("Price (high to low)"); 

            Allure.step("Selected sorting products price based on high to low");
            takeAndAttachScreenshot("Step 7: After Action 6");

            // Capture product prices after sorting high to low
            List<WebElement> productElementsHighToLow = driver.findElements(By.cssSelector(".inventory_item_price"));
            List<Double> productPricesHighToLow = new ArrayList<>();

            for (WebElement product : productElementsHighToLow) {
                String priceText = product.getText().replace("$", ""); 
                productPricesHighToLow.add(Double.parseDouble(priceText)); 
            }

            // Created a sorted copy of the product prices for high to low
            List<Double> sortedPricesHighToLow = new ArrayList<>(productPricesHighToLow);
            Collections.sort(sortedPricesHighToLow, Collections.reverseOrder());

            // Validated high to low sorting
            Assert.assertEquals(productPricesHighToLow, sortedPricesHighToLow, "The products are not sorted in high to low order.");
            // Print the sorted product prices in high to low order
            System.out.println("Products sorted in high to low order: " + sortedPricesHighToLow);

            Allure.step("Verified the products listed are sorted based on product price from high to low");
            takeAndAttachScreenshot("Step 8: After Action 7");
        }
        catch (NoSuchElementException e) 
        {
            Assert.fail("Sorting elements not found: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            Assert.fail("An unexpected error occurred during product price sorting: " + e.getMessage());
        }
    }

    public void takeAndAttachScreenshot(String step) 
    {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(step, new ByteArrayInputStream(screenshot));
        }
        catch (Exception e) 
        {
            softAssert.fail("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
