package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.BasePage;

import java.time.Duration;

public class PlayingListSteps extends BasePage {

    public PlayingListSteps(WebDriver driver) {
        super(driver);
    }

    public void OpenPlaylist() {
        WebElement playlistSection = driver.findElement(By.cssSelector("#playlists"));
        playlistSection.click();
        System.out.println("Click in playlist.");
    }

    public void CreateNewPlayList() {

        //clic en el menu PlayList icon
        WebElement btnPlus = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")));
        btnPlus.click();
        //Clic en "New Playlist"
        WebElement optNewPlaylist = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-simple']")));
        optNewPlaylist.click();
        //create name list
        WebElement inputNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));

        inputNombre.sendKeys("ListaHeilyn");
        inputNombre.sendKeys(Keys.ENTER);
        System.out.println("PlayList" + inputNombre);

    }




}

