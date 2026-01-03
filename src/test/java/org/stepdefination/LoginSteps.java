package org.stepdefination;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps 
{
	WebDriver driver;
	
	@Given("I am on the SauceDemo login page")
	public void I_am_on_the_SauceDemo_login_page()
	{
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
	}
	
	@When("I login with {string} and {string}")
	public void i_login_with(String username, String password)
	{
		driver.findElement(By.id("user-name")).clear();
		driver.findElement(By.id("user-name")).sendKeys(username);
		
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		
		driver.findElement(By.id("login-button")).click();
	}
	
	@Then("the login result should be {string}")
	public void the_login_result_should_be(String expected) throws InterruptedException
	{
		String currentURL = driver.getCurrentUrl();
		
		//Valid Login
		
		if (expected.equalsIgnoreCase("success"))
		{
			boolean isSuccess = currentURL.contains("inventory.html");
			Assert.assertTrue("Expected successful login but URL was: " + currentURL, isSuccess);
			
		}
		
		
		// Invalid Login
		
		else if (expected.equalsIgnoreCase("invalid"))
		{
			WebElement Err = driver.findElement(By.xpath("//h3[@data-test='error']"));
			String msg = Err.getText().trim();
			
			String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
			
			Assert.assertEquals("Invalid login message did not match!", expectedMessage, msg);
				
		}
		
		// LOCKED USER MESSAGE
		
		else if (expected.equalsIgnoreCase("locked"))
		{
			WebElement Err = driver.findElement(By.xpath("//h3[@data-test='error']"));
			String msg = Err.getText().trim();
			
			String expectedMessage = "Epic sadface: Sorry, this user has been locked out.";
			
			Assert.assertEquals("Locked user message did not matach!", expectedMessage, msg);
			
		}
		Thread.sleep(2000);
		
		driver.quit();
		
	}
}
