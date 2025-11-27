
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class Homework18 extends BaseTest {

    WebDriver driver;


    @Test
    public void PlayNextSong() throws InterruptedException {

        // 1. Open browser
        // open Chrome browser
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Log In']"))).click();

//Click «Play next song» (media player controls), then the Play button, to play a song.

        // WebElement nextButton = driver.findElement(By.cssSelector("i[title='Play next song']")).click();
        WebElement nextButton = driver.findElement(By.cssSelector("i[title='Play next song']"));
        nextButton.click();
        // Click "Play"
        WebElement playButton = driver.findElement(By.cssSelector("span[data-testid='play-btn']"));
        playButton.click();
        // Validate song is playing by checking Pause button


        boolean isPlaying = driver.findElement(By.cssSelector("span[data-testid='pause-btn']")).isDisplayed();

        if (isPlaying) {
            System.out.println("Song is playing.");
        } else {
            System.out.println("Song is NOT playing.");
        }

    }
}