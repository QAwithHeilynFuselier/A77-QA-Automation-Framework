package refactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BrowserFactory {
    public static WebDriver pickBrowser(String browser, ChromeOptions options) {

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();
                  return new ChromeDriver(options);


            default:
                throw new IllegalArgumentException("Browser not support : " + browser);
        }
    }
}