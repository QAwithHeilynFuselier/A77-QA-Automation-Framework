

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class actions extends BaseTest {


    @Test
    public void renamePlaylist() {

        String playlistName = "Heilyn";

        // Login
        WebElement emailField = getDriver().findElement(By.cssSelector("input[placeholder='Email Address']"));
        emailField.sendKeys("heilyn.fuselier@testpro.io");

        WebElement passwordField = getDriver().findElement(By.cssSelector("input[placeholder='Password']"));
        passwordField.sendKeys("HolaMundo@2025");

        getDriver().findElement(By.xpath("//button[text()='Log In']")).click();

        // Open playlist
        WebElement playlist = new WebDriverWait(getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".playlist:nth-child(3)")));
        playlist.click();

        // Rename playlist
        Actions actions = new Actions(getDriver());
        actions.contextClick(playlist).perform();

        WebElement editButton = new WebDriverWait(getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'Edit')]")));
        actions.moveToElement(editButton).click().perform();

        WebElement editingPlaylist = new WebDriverWait(getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.playlist.editing")));

        WebElement renameInput = editingPlaylist.findElement(By.tagName("input"));
        renameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        renameInput.sendKeys(Keys.DELETE);
        renameInput.sendKeys("Christmas");
        renameInput.sendKeys(Keys.ENTER);
    }
}