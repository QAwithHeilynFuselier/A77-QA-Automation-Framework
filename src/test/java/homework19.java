import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class homework19 extends BaseTest {

    @BeforeMethod
    @Parameters({"BaseUrl"})

    public void launchBrowser(String BaseURL) {
        //add chrome url
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);

        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        url = BaseURL;
        navigatetoPage();
    }

    public void navigatetoPage() {
        driver.get(url);
    }

    @Test
    public void deletePlaylist() throws InterruptedException {
        String playlistName = "Heilyn";
        provideEmail("heilyn.fuselier@testpro.io");
        providePassword("HolaMundo@2025");
        clickSubmit();
        openPlaylist("Heilyn");

        clickDeletePlaylistBtn();
       // Assert.assertEquals(getDeletedPlaylistMsg(), expectedPlaylistDe letedMessage);
    }

    public void provideEmail(String email) {
        WebElement emailfield = driver.findElement(By.cssSelector("input[placeholder='Email Address']"));
        emailfield.clear();
        emailfield.sendKeys(email);
    }

    public void providePassword(String password) {
        WebElement passwordfi = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        passwordfi.clear();
        passwordfi.sendKeys(password);

    }

    public void clickSubmit() {
        WebElement summit = driver.findElement(By.xpath("//button[text()='Log In']"));
        summit.click();
    }

    public void openPlaylist(String playlistName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // wait playlist show in the web
        WebElement playlistContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("playlists"))
        );

        // find this list exist
        List<WebElement> playlists = playlistContainer.findElements(
                By.xpath(".//li[contains(@class,'playlist')]")
        );

        boolean found = false;

        for (WebElement playlist : playlists) {
            String text = playlist.getText().trim();
            if (text.equals(playlistName)) {
                wait.until(ExpectedConditions.elementToBeClickable(playlist)).click();
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException(" playlist no exist '" + playlistName + "' please create one.");
        }
    }

    public void clickDeletePlaylistBtn() throws InterruptedException {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement button = driver.findElement(
                By.xpath("//*[@id='playlistWrapper']/header/div[3]/span/button")
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        //Thread.sleep(300);
            wait.until(ExpectedConditions.visibilityOf(button));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public String getDeletedPlaylistMsg() {
        // WebElement notificationMsg = driver.findElement(By.cssSelector("div.success.show"));
        // return notificationMsg.getText();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement notificationMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.success.show")
        ));

        // Wait until message text is not empty
        wait.until(driver -> !notificationMsg.getText().trim().isEmpty());

        return notificationMsg.getText().trim();
    }

}








