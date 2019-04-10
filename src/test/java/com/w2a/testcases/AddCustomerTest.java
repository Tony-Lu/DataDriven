package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alerttext) throws InterruptedException {
		
//		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		click("addCustBtn_CSS");
		log.debug("Add Customer button on Top clicked");
//		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
		type("firstname_CSS", firstName);
		log.debug("exceldata: firstname inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
		type("lastname_XPATH", lastName);
		log.debug("exceldata: lastname inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postCode);
		type("postcode_CSS", postCode);
		log.debug("exceldata: postcode inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
		click("addBtn_CSS");
		log.debug("Add Customer button at bottom clicked");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alerttext));
		alert.accept();
		
		Reporter.log("AddCustomerTest executed!");
//		Assert.fail("Customer not added successfull");
		
		
	}
	
	

}
