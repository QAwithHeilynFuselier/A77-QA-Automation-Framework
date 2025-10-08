




import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import refactory.BrowserFactory;

import java.util.HashMap;
import java.util.Map;


public class BaseTest {
    protected WebDriver driver;


    @BeforeSuite
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
     //   WebDriverManager.firefoxdriver().setup();
    }

    @BeforeMethod
    @Parameters({"BaseURL", "browser"})
    public void launchBrowser(String BaseURL, String browser) {
        ChromeOptions options = new ChromeOptions();
        if (browser.equalsIgnoreCase("chrome")) {


            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--remote-allow-origins=*");
        }

      //  driver = BrowserFactory.pickBrowser(System.getProperty("browser"));

         driver = BrowserFactory.pickBrowser(browser, options);

        System.out.println("Navigating to URL: " + BaseURL);
        driver.get(BaseURL);
    }
}





