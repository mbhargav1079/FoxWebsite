package com.svk.foxwebsite;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FoxWebsite {

	private static WebDriver driver;
	private static WebsiteProperties prop = new WebsiteProperties("DataFile.properities");

	public static void main(String[] args) throws IOException, InterruptedException {

		BrowserInit(prop.getProperty("Fox.URL"));
		// FoxSignup();
		FoxSignin();
		ClickTab(prop.getProperty("Fox.ClickTabName"));
		ClickTab("FOX"); // 
		ScrollDown("FOX Social");
		Thread.sleep(10000);
		SaveinExcel();
		ClickTab("FX");
		ScrollDown("FOX Social");
		ListShows("FX",1);
		
		ClickTab("National Geographic");
		ScrollDown("FOX Social");
		ListShows("National Geographic",2);
		ClickTab("FOX Sports");
		ScrollDown("FOX Social");
		ListShows("FOX Sports",3);
		ClickTab("All Shows");
		ScrollDown("FOX Social");
		ListShows("All Shows",4);

		

	}

	public static void FoxSignup() throws InterruptedException {
		driver.findElement(By.id("path-1")).click();
		driver.findElement(By.xpath("//button[@class='Account_signUp_3SpTs']")).click();
		driver.findElement(By.xpath("//input[@name='signupFirstName']"))
				.sendKeys(prop.getProperty("Fox.Signup.Username"));
		driver.findElement(By.xpath("//input[@name='signupLastName']"))
				.sendKeys(prop.getProperty("Fox.Signup.Password"));
		driver.findElement(By.xpath("//input[@name='signupEmail']")).sendKeys(prop.getProperty("Fox.Signup.Email"));
		driver.findElement(By.xpath("//input[@name='signupPassword']"))
				.sendKeys(prop.getProperty("Fox.Signup.Password"));
		driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div[1]/div[2]/div[10]/div[1]/div/div/div/span"))
				.click();
		if (prop.getProperty("Fox.Signup.Gender").equals("Male")) {
			WebElement e = driver.findElement(By.xpath(
					"//div[@class='Dropdown_itemContainer_1AOfB Dropdown_itemContainer_2sT-Z AccountSignupDropdown_itemContainer_2mUVx']/a[1]/div"));
			e.click();
		} else if (prop.getProperty("Fox.Signup.Gender").contains("Female")) {
			driver.findElement(By.xpath(
					"//div[@class='Dropdown_itemContainer_1AOfB Dropdown_itemContainer_2sT-Z AccountSignupDropdown_itemContainer_2mUVx']/a[2]/div"))
					.click();
		} else {
			driver.findElement(By.xpath(
					"//div[@class='Dropdown_itemContainer_1AOfB Dropdown_itemContainer_2sT-Z AccountSignupDropdown_itemContainer_2mUVx']/a[3]/div"))
					.click();
		}
		driver.findElement(By.xpath("//input[@placeholder='Birthdate']"))
				.sendKeys(prop.getProperty("Fox.Signup.Birthdate"));
		driver.findElement(By.xpath("//div[@class='Account_signupButtonDesktop_1PCXs']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='Account_signupSuccessButton_1mM7y']")).click();
	}

	public static void FoxSignin() throws InterruptedException {
		driver.get(prop.getProperty("Fox.URL"));
		driver.findElement(By.id("path-1")).click();
		driver.findElement(By.xpath("//button[@class='Account_signIn_Q0B7n']")).click();
		driver.findElement(By.xpath("//input[@name='signinEmail']")).sendKeys(prop.getProperty("Fox.Signin.Email"));
		driver.findElement(By.xpath("//input[@name='signinPassword']"))
				.sendKeys(prop.getProperty("Fox.Signin.Password"));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='Account_signupButton_3ZxXE']")).click();
		driver.manage().window().maximize();
	}

	public static WebDriver BrowserInit(String URL) {
		if (prop.getProperty("Browser.name").equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("Browser.Chrome.path"));
			driver = new ChromeDriver();
		} else {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("Browser.Firefox.path"));
			driver = new FirefoxDriver();
		}
		driver.get(URL);
		return driver;
	}
	
	public static void ClickTab(String tabName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[@href='/shows/']")).click();
		driver.findElement(By.linkText(tabName)).click();
		Thread.sleep(1000);
	}



	public static void ScrollDown(String elementName) throws InterruptedException  {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by link text and store in variable "Element"
		WebElement Element = driver.findElement(By.linkText(elementName));

		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
		Thread.sleep(2000); 
	}

	public static void SaveinExcel() {
		List<WebElement> TotalShows = driver
				.findElements(By.xpath("//a[@class='Tile_title_2XOxg MovieTile_title_1u6rs']"));
		System.out.println(TotalShows.size());
		Iterator<WebElement> itr = TotalShows.iterator();
		String last4Shows[][] = new String[5][1];
		last4Shows[0][0] = "Shows";
		for (int i = TotalShows.size(); itr.hasNext(); --i) {
			if (i < 5) {
				last4Shows[i][0] = itr.next().getText();
			} else {
				itr.next().getText();
			}
		}

		System.out.println(last4Shows);
		ExcelWorkBook book = new ExcelWorkBook(prop.getProperty("Excel.File"), "Fox");
		book.writeData(last4Shows);

	}
	public static void ListShows(String showName, int order) {
		List<WebElement> TotalShows = driver
				.findElements(By.xpath("//a[@class='Tile_title_2XOxg MovieTile_title_1u6rs']"));
		System.out.println(TotalShows.size());
		Iterator<WebElement> itr = TotalShows.iterator();
		String fourShows[][] = new String[4][2];
		fourShows[0][0] = "So You Think You Can Dance";
		fourShows[1][0] = "Meghan Markle: An American Princess";
		fourShows[2][0] = "Hypnotize Me";
		fourShows[3][0] = " 24 Hours To Hell & Back";
		fourShows[0][1] = "Not Displayed";
		fourShows[1][1] = "Not Displayed";
		fourShows[2][1] = "Not Displayed";
		fourShows[3][1] = "Not Displayed";

		while(itr.hasNext()) {
			String show = itr.next().getText();
			for (int i = 0; i<=3; i++) {
				if(show.contains(fourShows[i][0])) {			
					fourShows[i][1] = "Displayed";
				}
			}
		}


		System.out.println(fourShows);
		ExcelWorkBook book = new ExcelWorkBook(prop.getProperty("Excel.File"), showName);
		
		book.writeData(fourShows);
		

	}

}
