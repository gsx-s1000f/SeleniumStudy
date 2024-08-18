package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * ファイルをダウンロードする
 * Seleniumでダウンロードできると考えていたが、Seleniumには選択して保存の実装が無い様なので、Javaでごりごりやるしかなかった。
 * JUnitまわりは、static importのせいで「そのメソッド何クラスだよ！？」って初心者のハードルを上げている様に思う。
 * 違うクラスに同じメソッドがあったりもするので、まわりに逆らっても極力staticインポートはしないようにした方がいいのかと考えることもある。
 */
class FileDownloadTest {

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
		final Path outPath = Path.of("./tmp/hoge.png");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");	// シークレットモード
		options.addArguments("--headless=new");	// ヘッドレスモード
		WebDriver driver = new ChromeDriver(options);
		
		driver.get("https://github.com/gsx-s1000f/SeleniumStudy");
		
		List<WebElement> elements = driver.findElements(By.xpath("//li/a/img[@alt='setup test folder']"));
		
		System.out.println(elements.size());
		for(WebElement element: elements) {
			System.out.println(String.valueOf(element));
			String src = element.getAttribute("src");
			System.out.println(src);
			
			try {
				URI uri = new URI(src);
				HttpURLConnection con = (HttpURLConnection)uri.toURL().openConnection();
				con.setRequestMethod("GET");
				con.connect();
				
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					String contentType = con.getContentType();
					System.out.println(contentType);
					try(
						BufferedInputStream bin = new BufferedInputStream(con.getInputStream());
						BufferedOutputStream bout = new BufferedOutputStream(Files.newOutputStream(outPath));
					){
						byte[] bytes = new byte[4096];
						for(int read = 0; (read = bin.read(bytes)) > -1;) {
							bout.write(bytes, 0, read);
						}
					}
				}
				if (Files.notExists(outPath)) {
					Assert.fail();
				}
			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				Assertions.assertThrows(e.getClass(), null);
			}
		}
		driver.quit();
	}
}
