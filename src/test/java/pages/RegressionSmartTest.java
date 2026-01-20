package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.BasePage;

import java.time.Duration;
import java.util.List;


public class RegressionSmartTest extends BasePage {

    public RegressionSmartTest  (WebDriver driver) {
        super(driver);
    }

    public void OpenListCreated(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), 'heilynList')]")));
        js.executeScript("arguments[0].click();", playlist);
        System.out.println("Navegando a HeilynList...");

    }

    public void OpenEditPlaylist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), 'heilynList')]")));

        actions.contextClick(playlist).perform();

        WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(),'Edit') or contains(text(),'Edit playlist')]")));

        editOption.click();
    }



    //update

    public boolean editOrAddRule(String playlistName, int ruleIndex, String modelText, String operatorText, String valueText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), '" + playlistName + "')]")));
        js.executeScript("arguments[0].click();", playlist);


        Actions actions = new Actions(driver);
        actions.contextClick(playlist).perform();

        WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(),'Edit') or contains(text(),'Edit playlist')]")));
        editOption.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("model[]")));

        if (ruleIndex == -1) {
            WebElement addRuleBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.btn-add-rule")));
            js.executeScript("arguments[0].click();", addRuleBtn);


            wait.until(driver -> driver.findElements(By.name("model[]")).size() > 0);
        }


        List<WebElement> models = driver.findElements(By.name("model[]"));
        List<WebElement> operators = driver.findElements(By.name("operator[]"));
        List<WebElement> values = driver.findElements(By.name("value[]"));


        if (ruleIndex >= 0 && ruleIndex < models.size()) {
            new Select(models.get(ruleIndex)).selectByVisibleText(modelText);
            new Select(operators.get(ruleIndex)).selectByVisibleText(operatorText);


            WebElement valueField = values.get(ruleIndex);
            wait.until(ExpectedConditions.visibilityOf(valueField));
            wait.until(ExpectedConditions.elementToBeClickable(valueField));


            actions.moveToElement(valueField).click().perform();


            valueField.clear();
            valueField.sendKeys(valueText);

            System.out.println("Valor escrito correctamente: " + valueText);
        } else {
            System.out.println("La regla no existe en UI.");
            return false;
        }


        try {
            WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer button[type='submit']")));
            js.executeScript("arguments[0].click();", saveBtn);
            System.out.println("Save.");
        } catch (Exception e) {
            WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer button[type='submit']")));
            js.executeScript("arguments[0].click();", saveBtn);
            System.out.println("Save.");
        }

        return true;
    }


    //delete lIST CREATED

    public void deletePlaylist(String playlistName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(normalize-space(), '" + playlistName + "')]")));


        actions.contextClick(playlist).perform();

        WebElement deleteOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(),'Delete') or contains(text(),'Remove')]")));
        deleteOption.click();


        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Ok') or contains(text(),'OK') or contains(text(),'ok')]")));
            confirmBtn.click();
        } catch (Exception e) {

        }
    }



}
