package CRMloginTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.ReadexcelLibrary;

public class DataDrivenLogin {
	WebDriver driver;

	@BeforeMethod
	public void launchBrowser() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\lalit\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://ui.freecrm.com/");

	}

	@DataProvider(name = "getData")
	public Object[][] getData() {
	ReadexcelLibrary reader = new ReadexcelLibrary(
				"C:\\Selenium\\CRM\\src\\main\\java\\testData\\crmdatadriven.xlsx");

		int rows = reader.totalRow(0);

		Object[][] data = new Object[rows][2];

		for (int i = 0; i < rows; i++) {

			data[i][0] = reader.getData(0, i, 0);
			data[i][1] = reader.getData(0, i, 1);

		}

		return data;

	}

	@Test(dataProvider = "getData")
	public void loginCRM(String email, String password) {

		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//div[contains(text(),'Login')]")).click();

		try {
			driver.findElement(By.xpath("//span[contains(text(),'Home')]")).click();
		} catch (Exception e) {
			System.out.println("not able to login");
		}

		String newUrl = driver.getCurrentUrl();
		Assert.assertEquals(newUrl, ("https://ui.freecrm.com/home"), "login fail");
		System.out.println("login1 method is pass");
		driver.findElement(By.xpath("//div[@role='listbox']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Log Out')]")).click();
		System.out.println("successfully logout");

	}

	@AfterMethod
	public void closeBrowser() {
		driver.close();
	}

}
