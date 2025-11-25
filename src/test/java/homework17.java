
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class homework17 extends BaseTest {
    WebDriver driver;

    @Test
    public void addSongToPlaylist() throws InterruptedException {
        // 1. Open browser
        // open Chrome browser
        ChromeOptions options = new ChromeOptions();
//remove allow
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);


        // wait 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String url = "https://qa.koel.app/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

//login
        WebElement emailInput = driver.findElement(By.cssSelector("input[placeholder='Email Address']"));
        emailInput.sendKeys("heilyn.fuselier@testpro.io");
        WebElement passwordInput = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        passwordInput.sendKeys("HolaMundo@2025");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Log In']")
        )).click();

        // 4. Search for a song
        WebElement searchBox = driver.findElement(
                By.cssSelector("input[placeholder='Press F to search']")
        );

        searchBox.sendKeys("BossStatus");   // pick any song
        searchBox.sendKeys(Keys.ENTER);

//
// 8. Click the first song in the search results
//
        WebElement firstSong = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".song-item")
                )
        );
        firstSong.click();


        WebDriverWait wa = new WebDriverWait(driver, Duration.ofSeconds(10));

// 1️ Locate the song you want to right-click
        WebElement song = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".song-item")    // adjust to your actual locator
                )
        );

// 2️ Right-click (context menu)
        Actions actions = new Actions(driver);
        actions.contextClick(song).perform();

// 3️ Click "Add to" in the context menu
        WebElement addToMenu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(., 'Add to')]")
                )
        );
        addToMenu.click();

// 4️Choose the playlist “Heilyn”
        WebElement heilynPlaylist = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='Heilyn']")
                )
        );

        heilynPlaylist.click();

    WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Locate the playlist link by its name
        WebElement heilynPlaylistLink = w.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='Heilyn']")
                )
        );
        heilynPlaylistLink.click();


    }

    }














      //  driver.quit();








