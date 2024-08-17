package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SampleTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "../../../Selenium/chromedriver-win64/chromedriver.exe");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * ウェブサイトを開く。
	 */
	@Test
	void testSample() {
		WebDriver driver = new ChromeDriver();
		driver.get("https://github.com/gsx-s1000f");
		

		driver.quit();
	}
	/**
	 * シークレットモードでウェブサイトを開く。
	 */
	@Test
	void testSecret() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");	// シークレットモード
		WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://github.com/gsx-s1000f");
		
		driver.quit();
	}
}
