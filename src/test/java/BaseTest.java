import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class BaseTest {

    String url;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;


    @BeforeSuite

    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }



    public void launchBrowser(String BaseUrl) {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        //options.addArguments("--disable-notifications");
       // options.addArguments("--disable-save-password-bubble");
        //options.addArguments("--incognito");

        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        url = BaseUrl;
        driver.get(url);
    }

    public void navigatetoPage() {

        driver.get(url);
    }

   @AfterMethod
    public void closetobrowser(){
      driver.quit();
    }
}