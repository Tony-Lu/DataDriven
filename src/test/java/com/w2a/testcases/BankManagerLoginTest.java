package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {
		
//		try {
//			Assert.assertEquals("abc", "xyz");
//			System.out.println("after assertion");
//		} catch (Throwable t) {
//			System.out.println("Inside catch");
//		}
		
		verifyEquals("abc", "xyz");  // this soft assertion !!!
		Thread.sleep(3000);
		log.debug("inside Login Test");
//		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful !!!");
		
		log.debug("Login successfully executed ");
		Assert.fail("Login not successfull");	// this is hardcode failure
	}

}
