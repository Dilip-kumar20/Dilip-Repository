package myTestProject;

import java.util.concurrent.TimeUnit;

import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AmazonAddToCart {
	@Test(description="Test Amazon fuctionality",priority=1)
	public static void test() throws InterruptedException
	{
		 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dilip\\OneDrive\\Documents\\New folder1\\chrome\\chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get("https://www.amazon.in/");  //https://www.amazon.com/ 
         driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
         WebElement amazonSearchBox = driver.findElement(By.id("twotabsearchtextbox"));
         amazonSearchBox.sendKeys("toys");
         driver.findElement(By.id("nav-search-submit-button")).click();
         Thread.sleep(5000);
         List<WebElement> products = driver.findElements(By.cssSelector(".s-main-slot s-result-list s-search-results sg-row"));
         WebElement firstProduct = products.get(0).findElement(By.cssSelector("h2 a"));
         WebElement secondProduct = products.get(1).findElement(By.cssSelector("h2 a"));
         
         String firstProductPrice = products.get(0).findElement(By.cssSelector(".a-price-whole")).getText();
         String secondProductPrice = products.get(1).findElement(By.cssSelector(".a-price-whole")).getText();
         firstProduct.click();
         String firstProductPriceInDetails = driver.findElement(By.id("priceblock_ourprice")).getText();
         driver.findElement(By.id("add-to-cart-button")).click();
         driver.navigate().back();
         Thread.sleep(5000);
         secondProduct.click();
         String secondProductPriceInDetails = driver.findElement(By.id("priceblock_ourprice")).getText();
         driver.findElement(By.id("add-to-cart-button")).click();
         driver.findElement(By.id("nav-cart")).click();

         List<WebElement> cartItems = driver.findElements(By.cssSelector(".sc-list-item"));
         String firstProductPriceInCart = cartItems.get(0).findElement(By.cssSelector(".sc-product-price")).getText();
         String secondProductPriceInCart = cartItems.get(1).findElement(By.cssSelector(".sc-product-price")).getText();
         
         //validate
         System.out.println("First Product Price: " + firstProduct);
         System.out.println("First Product Price in Details: " + firstProductPriceInDetails);
         System.out.println("First Product Price in Cart: " + firstProductPriceInCart);
         
         System.out.println("Second Product Price in Search: " + secondProductPrice);
         System.out.println("Second Product Price in Details: " + secondProductPriceInDetails);
         System.out.println("Second Product Price in Cart: " + secondProductPriceInCart);


         driver.quit();
         
         




	}
}
