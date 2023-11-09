import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

public class TestCases {

	WebDriver driver = new ChromeDriver();
	String Url = "https://global.almosafer.com/ar";
	Random Rand = new Random();

	@BeforeTest
	public void MySetUp() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		driver.manage().window().maximize();
		int RandomUrlIndex = Rand.nextInt(2);// for 2 lang(ar,en)
		String[] myUrls = { "https://global.almosafer.com/ar", "https://global.almosafer.com/en" };
		driver.get(myUrls[RandomUrlIndex]);

		// the box that show on loading the page
		if (driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div")).isDisplayed()) {
			driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div/button[1]")).click();
		}
	}

//	enabled=false ==>stop to work

	// check the language

	@Test(groups = "mid", enabled = false)
	public void CheckTheLanguage() {

		if (driver.getCurrentUrl().contains("ar")) {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(ActualLanguage, "ar");// check if it the same or not
		} else {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			System.out.println(ActualLanguage);
			Assert.assertEquals(ActualLanguage, "en");

		}
	}
	// check the Currancy

	@Test(groups = "low", enabled = false)
	public void CheckTheCurrancy() {
		WebElement Currency = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/div[1]/div/button"));
		String ActaulCurrancy = Currency.getText();
		Assert.assertEquals(ActaulCurrancy, "SAR");
	}

	// check the CotactNumber
	@Test(groups = "high", enabled = false)
	public void CheckTheContactNumber() {
		WebElement ContactNumber = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/a[2]/strong"));
		String ActaulContactNumber = ContactNumber.getText();
		Assert.assertEquals(ActaulContactNumber, "+966554400000");
	}

	// hotel search tap
	@Test(enabled = false)
	public void HotelSearch() throws InterruptedException {
		WebElement Hotel = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
		String[] citiesEn = { "Dubai", "Jeddah", "Riyadh" };
		int RandomEnhIndex = Rand.nextInt(citiesEn.length);
		String[] citiesAr = { "دبي", "جدة" };
		int RandomArIndex = Rand.nextInt(citiesAr.length);
		Hotel.click();

		if (driver.getCurrentUrl().contains("ar")) {
			WebElement SearchInput = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));
			SearchInput.sendKeys(citiesAr[RandomArIndex]);
			Thread.sleep(3000);
			WebElement AutoCompleteList = driver.findElement(By.cssSelector(".phbroq-4.UzzIN.AutoComplete__List"));
			List<WebElement> myList = AutoCompleteList.findElements(By.tagName("div"));
			SearchInput.clear();
			// choose the first element
			myList.get(1).click();

			// Random select
			Thread.sleep(10000);
			WebElement MySelector = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[3]/div/select"));
			Select Selector = new Select(MySelector);
			Selector.selectByIndex(Rand.nextInt(2));

			Thread.sleep(2000);
			WebElement SearchBtn = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button"));
			SearchBtn.click();

			WebElement LowBtn = driver
					.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]/section[1]/div/button[2]"));
			LowBtn.click();
			Thread.sleep(7000);
			WebElement Contanier = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]"));
			List<WebElement> Prices = Contanier.findElements(By.className("Price__Value"));

			// SORTING BY LOWER PRICES
			for (int i = 0; i < Prices.size(); i++) {
				String FirstElement = Prices.get(0).getText();
				String LastElement = Prices.get(Prices.size() - 1).getText();
				int FirstElementAsNumber = Integer.parseInt(FirstElement);
				int LastElementAsNumber = Integer.parseInt(LastElement);
				System.out.println(FirstElementAsNumber);
				System.out.println(LastElementAsNumber);
				Assert.assertEquals(FirstElementAsNumber < LastElementAsNumber, true);
			}
			// Check fully loaded
			Assert.assertEquals(LowBtn.isEnabled(), true);
		} else {
			WebElement SearchInput = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));
			SearchInput.sendKeys(citiesEn[RandomEnhIndex]);
			Thread.sleep(3000);
			WebElement AutoCompleteList = driver.findElement(By.cssSelector(".phbroq-4.UzzIN.AutoComplete__List"));
			List<WebElement> myList = AutoCompleteList.findElements(By.tagName("div"));
			SearchInput.clear();
			// choose the first element
			myList.get(1).click();

			// Random select
			Thread.sleep(10000);
			WebElement MySelector = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div[2]/div/div[3]/div/select"));
			Select Selector = new Select(MySelector);
			Selector.selectByIndex(Rand.nextInt(2));

			Thread.sleep(2000);
			WebElement SearchBtn = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button"));
			SearchBtn.click();

			// SORTING BY LOWER PRICES
			Thread.sleep(7000);
			WebElement LowBtn = driver
					.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]/section[1]/div/button[2]"));
			LowBtn.click();
			WebElement Contanier = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]"));
			List<WebElement> Prices = Contanier.findElements(By.className("Price__Value"));
			for (int i = 0; i < Prices.size(); i++) {
				String FirstElement = Prices.get(0).getText();
				String LastElement = Prices.get(Prices.size() - 1).getText();
				int FirstElementAsNumber = Integer.parseInt(FirstElement);
				int LastElementAsNumber = Integer.parseInt(LastElement);
				System.out.println(FirstElementAsNumber);
				System.out.println(LastElementAsNumber);
				Assert.assertEquals(FirstElementAsNumber < LastElementAsNumber, true);
			}
			// Check fully loaded
			Assert.assertEquals(LowBtn.isEnabled(), true);

		}
	}

//	check the search btn not selected by default
	@Test()
	public void CheckTheBtn() {
		WebElement HotelBtn = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
		Assert.assertEquals(HotelBtn.getAttribute("aria-selected"), "false",
				"The hotel button selected by default.");
	}

	@Test()

	public void qitafLogo() {
		if (driver.findElement(By.tagName("footer")).isDisplayed()) {
			Assert.assertEquals(driver.findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ciodno.lkfeIG")).isDisplayed(), true,
					"The qitafLogo should not excisit.");
		}
	}

	@Test(enabled = false)

	public void date()

	{
		WebElement GoDate = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[1]/span[2]"));
		String GoDateText = GoDate.getText();

		WebElement ReturnDate = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[2]/span[2]"));
		String ReturnDateText = ReturnDate.getText();

		// Calculate the expected go and return dates
		LocalDate today = LocalDate.now();
		LocalDate expectedDepartureDate = today.plusDays(1);
		LocalDate expectedReturnDate = today.plusDays(2);

		// Format the expected dates as strings
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
		String expectedGoDateString = expectedDepartureDate.format(dateFormatter);
		String expectedReturnDateString = expectedReturnDate.format(dateFormatter);

		System.out.println("Expected Departure Date: " + expectedGoDateString);
		System.out.println("Actual Departure Date: " + GoDateText);
		System.out.println("Expected Return Date: " + expectedReturnDateString);
		System.out.println("Actual Return Date: " + ReturnDateText);

		// Verify that the extracted dates match the expected dates
		Assert.assertEquals(GoDateText, expectedGoDateString, "Departure date mismatch");
		Assert.assertEquals(ReturnDateText, expectedReturnDateString, "Return date mismatch");// hard assert
	}


	@AfterTest
	public void PostTesting() {
	}

}
