package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ブラウザでの、もどる（ブラウザバック）、すすむの動作確認
 */
class FowardBackTest {

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
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");	// シークレットモード
		WebDriver driver = new ChromeDriver(options);
		
		try {
			driver.get("https://github.com/gsx-s1000f");
			
			// リポジトリタブを探す。
//			List<WebElement> elements = driver.findElements(By.xpath("//a[@href='/gsx-s1000f?tab=repositories']"));
			List<WebElement> elements = driver.findElements(By.xpath("//a[@class='UnderlineNav-item js-responsive-underlinenav-item js-selected-navigation-item']"));
//			assertThat(elements.size(), is(1));
			for(WebElement element: elements) {
				// Repositoriesをクリック。
				String text = element.getText();
				if (!text.startsWith("Repositories")) {
					continue;
				}
				element.click();
				
				// URLがかわるの待つ。
				Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));	// Max5秒
				wait.until(d -> d.getCurrentUrl().equals("https://github.com/gsx-s1000f?tab=repositories"));
				// Titleに Repositoriesが含まれるかチェック。
				assertThat(driver.getTitle(), containsString("Repositories"));
			}
			// 戻る。
			driver.navigate().back();
			// URLとタイトルがかわっているのを確認
			assertThat(driver.getCurrentUrl(), is("https://github.com/gsx-s1000f"));
			assertThat(driver.getTitle(), not(containsString("Repositories")));
			
			// 進む
			driver.navigate().forward();
			// URLがかわるの待つ。
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));	// Max5秒
			wait.until(d -> d.getCurrentUrl().equals("https://github.com/gsx-s1000f?tab=repositories"));
			// URLとタイトルがかわっているのを確認
			assertThat(driver.getCurrentUrl(), is("https://github.com/gsx-s1000f?tab=repositories"));
			assertThat(driver.getTitle(), is(containsString("Repositories")));
			
			// リロード
			driver.navigate().refresh();	// どうやってテストするかな…。
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.quit();
		}
	}
}
