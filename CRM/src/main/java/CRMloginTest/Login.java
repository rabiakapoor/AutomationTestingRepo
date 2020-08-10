package CRMloginTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utility.ReadexcelLibrary;

public class Login {

	WebDriver driver;
	Properties prop;

	@BeforeMethod
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver",
				"\\Users\\lalit\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.get("https://ui.freecrm.com/");

	}
	
	// In this method login credentials are provided directly in the script
	@Test
	public void login1() {
		
		driver.findElement(By.name("email")).sendKeys("seleniumscripts.automation@gmail.com");
		driver.findElement(By.name("password")).sendKeys("Automation123");
		driver.findElement(By.xpath("//div[contains(text(),'Login')]")).click();

		try {
			driver.findElement(By.xpath("//span[contains(text(),'Home')]")).click();
		} catch (Exception e) {
			System.out.println("not able to login");
		}

		String newUrl = driver.getCurrentUrl();
		// Using Assertion to verify that we land on expected page or not
		Assert.assertEquals(newUrl, ("https://ui.freecrm.com/home"), "login fail");
		System.out.println("login1 method is pass");

		driver.findElement(By.xpath("//div[@role='listbox']")).click();

		driver.findElement(By.xpath("//span[contains(text(),'Log Out')]")).click();

		System.out.println("successfully logout");

	}
	
	/*
	 * In this method login credentials are taken from XML file, login credentials
	 * are not provided directly in script
	 */
	@Test
	@Parameters({ "email", "password" })
	public void login2(String email, String password) {
		
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//div[contains(text(),'Login')]")).click();

		driver.findElement(By.xpath("//span[contains(text(),'Home')]")).click();
		String newUrl = driver.getCurrentUrl();
		Assert.assertEquals(newUrl, ("https://ui.freecrm.com/home"), "can't login");
		System.out.println("login2 method is pass");

	}

	/*
	 * In this method login credentials are taken from a file with the help of
	 * Properties object
	 */
	@Test
	public void login3() {
		
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("C:\\Selenium\\CRM\\src\\main\\java\\loginCredentials");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("email")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.name("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.xpath("//div[contains(text(),'Login')]")).click();

		driver.findElement(By.xpath("//span[contains(text(),'Home')]")).click();
		String newUrl = driver.getCurrentUrl();
		Assert.assertEquals(newUrl, ("https://ui.freecrm.com/home"), "can't login");
		System.out.println("login3 method is pass");

	}
	
	/*
	 * In this method script is taking login credentials from excel file. one
	 * utility is being used for reading data from excel file.
	 */
	@Test
		public void login4() {
		
		ReadexcelLibrary reader = new ReadexcelLibrary(
				"C:\\Selenium\\CRM\\src\\main\\java\\testData\\CRMLoginData.xlsx");

		int Row = reader.totalRow(1);

		System.out.println(Row);

		for (int i = 0; i < Row; i++) {

			String email = reader.getData(1, i, 0);
			System.out.println(email);

			String password = reader.getData(1, i, 1);
			System.out.println(password);

			driver.findElement(By.name("email")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.xpath("//div[contains(text(),'Login')]")).click();

			driver.findElement(By.xpath("//span[contains(text(),'Home')]")).click();
			String newUrl = driver.getCurrentUrl();
			Assert.assertEquals(newUrl, ("https://ui.freecrm.com/home"), "can't login");
			System.out.println("login4 method is pass");

		}

	}

	@AfterMethod
	public void closeBrowser() {
		driver.close();
	}

}
