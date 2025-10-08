

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;

import java.util.Map;


public class Homework21 extends BaseTest {


    @BeforeMethod
    @Parameters({"BaseUrl"})
    public void launchBrowser(String BaseUrl) {
        //add chrome url
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        url = BaseUrl;
        navigatetoPage();
    }

    public void navigatetoPage() {
        driver.get(url);
    }

    @Test
    public void renamePlaylist() {
        String playlistName = "Heilyn";
        provideEmail("heilyn.fuselier@testpro.io");
        providePassword("HolaMundo@2025");
        clickSubmit();
        openPlaylist();

        renamePlaylist("Christmas");

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

    public void openPlaylist() {
        WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(3)"));
        emptyPlaylist.click();
    }



    public void renamePlaylist(String nuevoNombre) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        // 1. Locate playlist (3rd item)
        WebElement playlist = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".playlist:nth-child(3)"))
        );

        // 2. Open context menu with Actions (right-click)
        actions.contextClick(playlist).perform();

        // 3. Wait for "Edit" menu item to be clickable and click it using Actions
        WebElement editButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'Edit')]"))
        );
        actions.moveToElement(editButton).click().perform();

        // 4. Wait until playlist enters edit mode (class 'editing')
        WebElement editingPlaylist = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.playlist.editing"))
        );

        // 5. Find the input inside the editing playlist and click it via Actions
        WebElement renameInput = wait.until(
                ExpectedConditions.visibilityOfNestedElementsLocatedBy(editingPlaylist, By.tagName("input"))
        ).get(0);

        actions.moveToElement(renameInput).click().perform();

        // 6. Clear existing text (send CTRL+A + DELETE)
        renameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        renameInput.sendKeys(Keys.DELETE);

        // 7. Type the new name and send ENTER to save
        renameInput.sendKeys(nuevoNombre);
        renameInput.sendKeys(Keys.ENTER);
    }
}