package com.weborder;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {

	public static int randNum(int a, int b) {
		Random rd = new Random();
		return rd.nextInt(b + 1 - a) + a;
	}
	
	public static String randomName() {
		  
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 4;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	    return generatedString;
	}
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver","/Users/gayeonbaeg/Documents/Selenium dependencies/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

		driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
		driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
		driver.findElement(By.name("ctl00$MainContent$login_button")).click();
		
		driver.navigate().to("http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/Process.aspx");
		
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).clear();		
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(randNum(1, 100) + "");
		
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys("John" + " " + randomName() + " " + "Smith");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys("123 Any st");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys("Anytown");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys("VA");
		
		String randZip = "" + randNum(0, 9) + randNum(0, 9) + randNum(0, 9) + randNum(0, 9) + randNum(0, 9);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(randZip);
		
		
		int randCard = randNum(1, 3);
		String selectedCard = "";
		StringBuilder cardNum = new StringBuilder();
		int cardLen = 0;

		switch (randCard) {
		case 1:
			selectedCard = "ctl00_MainContent_fmwOrder_cardList_0";
			cardNum.append(4);
			cardLen = 16-1;
			break;
		case 2:
			selectedCard = "ctl00_MainContent_fmwOrder_cardList_1";
			cardNum.append(5);
			cardLen = 16-1;
			break;
		case 3:
			selectedCard = "ctl00_MainContent_fmwOrder_cardList_2";
			cardNum.append(3);
			cardLen = 15-1;
			break;
		}

		for (int i = 0; i < cardLen; i++) {
			cardNum.append(randNum(0, 9));
		}

		driver.findElement(By.id(selectedCard)).click();

		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNum.toString());

		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys("08/22");

		driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

		String expected = "New order has been successfully added.";

		String actual = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_fmwOrder\"]/tbody/tr/td/div/strong")).getText();
	
		System.out.println(actual);
		if(expected.equals(actual))
			System.out.println("Expected result matches the actual result.");
		else
			System.out.println("Expected result does not match the actual result.");
		
		driver.close();

		
		
	}

}
