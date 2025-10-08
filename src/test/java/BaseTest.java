
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.remote.RemoteWebDriver;


import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {
    public static WebDriver driver = null;
    public static String url = null;


    public static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
// create getdriverpage11
    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    @BeforeMethod
    @Parameters({"browser", "BaseURL"})

    public void setupBrowser(String browser, String BaseURL) throws MalformedURLException {
        threadDriver.set(pickBrowser(browser));
        getDriver().get(BaseURL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        url = BaseURL;
        navigateToPage();
    }


    public void closeBrowser() {
        driver.quit();
    }

    public  void navigateToPage() {
        getDriver().get(url);
    }


    public WebDriver  lambdaTestfirefox() throws MalformedURLException {
        String hubURL = "https://hub.lambdatest.com/wd/hub";

        FirefoxOptions browserOptions = new FirefoxOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("dev");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "heilynfuselier");
        ltOptions.put("accessKey", "LT_2hl9qrKvYdt1A2KE2wN7cRxJRtGyn37PeSR6hWlnwUEIWwZ");
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("project", "FirefoxTest01");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", ltOptions);

        return new RemoteWebDriver(URI.create(hubURL).toURL(), browserOptions);
    }


    public WebDriver  lambdaTestEdge() throws MalformedURLException {
        String hubURL = "https://hub.lambdatest.com/wd/hub";

        EdgeOptions browserOptions = new EdgeOptions();
        browserOptions.setPlatformName("Windows 11");
        browserOptions.setBrowserVersion("latest"); // Replace with a specific version if needed

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "heilynfuselier");
        ltOptions.put("accessKey", "LT_2hl9qrKvYdt1A2KE2wN7cRxJRtGyn37PeSR6hWlnwUEIWwZ"); // Hardcoded access key
        ltOptions.put("project", "EdgeTestProject");
        ltOptions.put("name", "Edge Test - " + System.currentTimeMillis());
        ltOptions.put("build", "Selenium4");
        browserOptions.setCapability("LT:Options", ltOptions);
        return new RemoteWebDriver(new URL(hubURL),  browserOptions);
    }

    public WebDriver  lambdaTestChrome() throws MalformedURLException {
        String hubURL = "https://hub.lambdatest.com/wd/hub";

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("dev");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "heilynfuselier");
        ltOptions.put("accessKey", "LT_2hl9qrKvYdt1A2KE2wN7cRxJRtGyn37PeSR6hWlnwUEIWwZ");
        ltOptions.put("build", "Selenium 4");
        ltOptions.put("project", "ChromeTest01");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", ltOptions);

        return new RemoteWebDriver(URI.create(hubURL).toURL(), browserOptions);
    }


    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser parameter is missing! Check your TestNG XML.");
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridUrl = "http://192.168.100.226:4444";

        switch(browser){
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
            //    edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--headless");

                driver.manage().window().maximize();
                return driver;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return driver = new FirefoxDriver();
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                return driver = new ChromeDriver(chromeOptions);
            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridUrl).toURL(), caps);
            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridUrl).toURL(), caps);
            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridUrl).toURL(), caps);

            case "cloud-edge":
                return lambdaTestEdge();
            case "cloud-firefox":
                return lambdaTestfirefox();
            case "cloud-Chrome":
                return lambdaTestChrome();

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions defaultOptions = new ChromeOptions();
                defaultOptions.addArguments("--remote-allow-origins=*");
                return driver = new ChromeDriver(defaultOptions);
        }
    }
    @AfterMethod
    public void tearDown(){
        threadDriver.get().close();
        threadDriver.remove();
    }




}