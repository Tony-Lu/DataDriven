package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		//String firstName, String lastName, String postCode, String alertText, String runmode = Hashtable
		
		if(!data.get("runmode").equals("Y")){
			throw new SkipException("Skipped the test case as its runmode is NO");
		}
		
//		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		click("addCustBtn_CSS");
		log.debug("Add Customer button on Top clicked");
//		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
		type("firstname_CSS", data.get("firstname"));
		log.debug("exceldata: firstname inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
		type("lastname_XPATH", data.get("lastname"));
		log.debug("exceldata: lastname inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postCode);
		type("postcode_CSS", data.get("postcode"));
		log.debug("exceldata: postcode inserted");
//		driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
		click("addBtn_CSS");
		log.debug("Add Customer button at bottom clicked");
		
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		
		Reporter.log("AddCustomerTest executed!");
//		Assert.fail("Customer not added successfull");
		
		Thread.sleep(2000);
		
		
	}
	
	

}
