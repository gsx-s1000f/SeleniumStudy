package test;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ブラウザのスクリーンショットを取得する。
 */
class ScreanShotTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// ChromeDriverをクラスパスに設定する。
		System.setProperty("webdriver.chrome.driver", "../../../Selenium/chromedriver-win64/chromedriver.exe");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		final Path tmpPath = Path.of("./tmp");
		Files.deleteIfExists(tmpPath);
		Files.createDirectory(tmpPath);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");	// シークレットモード

		WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://github.com/gsx-s1000f");
		
		TakesScreenshot screenshot = (TakesScreenshot)new Augmenter().augment(driver);
		Path from = Paths.get(screenshot.getScreenshotAs(OutputType.FILE).toURI());
		Path to = Paths.get("./tmp/shreenshot.png");
		try {
			Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
	}
}
