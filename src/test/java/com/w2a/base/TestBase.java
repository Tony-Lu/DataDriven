package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {	
	/*WebDriver
	 * Properties
	 * Logs - Log4j jar, .log file, log4j.properties, Logger class,
	 * ExtentReports
	 * DB
	 * Excel
	 * ReportNG, 
	 * Jenkins
	 * explicit wait
	 */
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger"); // devpinoyLogger, is a standard logger name
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
	public void setUp() throws InterruptedException {
		
		if(driver==null) {			
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//below if statement is for configuring Jenkins build: to select browser from Config.properties file:
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
				
				browser = System.getenv("browser");
			}else {
				
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);
			//========================================================================================================
			
			if(config.getProperty("browser").equals("firefox")) {
				//use below if use Selenium new version, 
//				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("Firefox launched !!!");
			}else if(config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				//below step is trying to fix the Jenkins issue: not launching chrome browser: but not work
//				ChromeOptions options = new ChromeOptions();
//				options.addArguments("enable-automation");
				// adding browser version: WebDriverManager.chromedriver().version("2.33").setup(); 
//				WebDriverManager.chromedriver().setup();
				//============================================
				driver = new ChromeDriver();					
				log.debug("Chrome launched !!!");
			}else if(config.getProperty("browser").equals("IE")) {
//				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				log.debug("IE launched !!!");
			}
//			}else if(config.getProperty("browser").equals("Edge")) {
//				System.setProperty("webdriver.edgedriver.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\MicrosoftWebDriver.exe");
////				WebDriverManager.edgedriver().setup();
//				driver = new EdgeDriver();
//				log.debug("Edge launched !!!");
//			}
			
			driver.manage().window().maximize();					
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : "+config.getProperty("testsiteurl"));	
			wait = new WebDriverWait(driver,5);			
		}
	}
	
	public void click(String locator) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : "+locator);
		
	}
	
	public void type(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in : "+locator+" entered value as "+value);
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {			
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));			
		}else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));			
		}else if(locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));			
		}		
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from the dropdown : " + locator + " value as " + value);
	}
	
	
	public boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
			
		} catch (Throwable t) {
			//ReportNG
			TestUtil.capturesScreenshot();
			Reporter.log("<br>"+"Verification failure : "+t.getMessage()+"<br>");			
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=300 width=300></img></a>");		
			Reporter.log("<br>");
			Reporter.log("<br>");
			//ExtentRports
			test.log(LogStatus.FAIL, " Verification failed with exception : "+t.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenshotName));				
			
		}
		
		
		
	}
	
	
	
	@AfterSuite
	public void tearDown() {
		if(driver!=null) {
			driver.close();
			driver.quit();
			driver=null;
		}
		
		log.debug("Test execution completed !!!");
		
	}

}
