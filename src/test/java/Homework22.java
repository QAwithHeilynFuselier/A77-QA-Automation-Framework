
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;





public class Homework22 extends Basepage  {


    //contructor
public Homework22(WebDriver givenDriver){
 super(givenDriver);

}

  public void openPlaylist() {
      WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(3)"));
      emptyPlaylist.click();
  }


    public void renamePlaylist(String nuevoNombre) {

        WebElement playlist = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".playlist:nth-child(3)"))
        );


        actions.contextClick(playlist).perform();


        WebElement editButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'Edit')]"))
        );
        actions.moveToElement(editButton).click().perform();

        WebElement editingPlaylist = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.playlist.editing"))
        );


        WebElement renameInput = wait.until(
                ExpectedConditions.visibilityOfNestedElementsLocatedBy(editingPlaylist, By.tagName("input"))
        ).get(0);

        actions.moveToElement(renameInput).click().perform();


        renameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        renameInput.sendKeys(Keys.DELETE);


        renameInput.sendKeys(nuevoNombre);
        renameInput.sendKeys(Keys.ENTER);
    }
}