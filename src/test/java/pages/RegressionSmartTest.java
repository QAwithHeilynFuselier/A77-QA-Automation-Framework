package pages;

import org.openqa.selenium.*;
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


    public void ClickSmartList(){


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement btnCrear = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")));
        btnCrear.click();

        WebElement smartListBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-smart']")));
        smartListBtn.click();
        System.out.println("Clic in smar list");

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
        nameInput.clear();
        nameInput.sendKeys("heilynList");

        System.out.println("Smart List  'heilynList' created.");

        WebElement modelSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("model[]")));
        new Select(modelSelect).selectByVisibleText("Artist");

        WebElement operatorSelect = driver.findElement(By.name("operator[]"));
        new Select(operatorSelect).selectByVisibleText("contains");


        WebElement valueInput = driver.findElement(By.cssSelector("input[name='value[]']"));
        valueInput.clear();
        valueInput.sendKeys("Dan");

        valueInput.sendKeys(Keys.TAB);
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Save']")));
            js.executeScript("arguments[0].click();", saveBtn);
            System.out.println("User should be able to create a Smart playlist in app with one rule");
        } catch (Exception e) {
            WebElement saveBtnFallback = driver.findElement(By.cssSelector("button[type='submit']"));
            js.executeScript("arguments[0].click();", saveBtnFallback);
        }
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

    public boolean VerifyOneRule() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            String model = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("model[]")))).getFirstSelectedOption().getText();

            String operator = new Select(driver.findElement(By.name("operator[]")))
                    .getFirstSelectedOption().getText();

            String value = driver.findElement(By.cssSelector("input[name='value[]']"))
                    .getAttribute("value");

            System.out.println("Model: " + model);
            System.out.println("Operator: " + operator);
            System.out.println("Value: " + value);

            return model.equals("Artist") &&
                    operator.equals("contains") &&
                    value.equals("Dan");

        } catch (Exception e) {
            System.out.println(" Rule not displayed in edit modal");
            return false;
        }
    }
    public boolean verifyMultipleRulesPlaylist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), 'ListaMultipleRules')]")));
        js.executeScript("arguments[0].click();", playlist);


        Actions actions = new Actions(driver);
        actions.contextClick(playlist).perform();

        WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(),'Edit') or contains(text(),'Edit playlist')]")));
        editOption.click();


        String[][] expectedRules = {
                {"Artist", "contains", "Dan"},
                {"Album", "is", "Airbit"},
                {"Plays", "is greater than", "1"}
        };


        wait.until(ExpectedConditions.numberOfElementsToBe(By.name("model[]"), 3));

        List<WebElement> models = driver.findElements(By.name("model[]"));
        List<WebElement> operators = driver.findElements(By.name("operator[]"));
        List<WebElement> values = driver.findElements(By.name("value[]"));

        for (int i = 0; i < expectedRules.length; i++) {
            String model = new Select(models.get(i)).getFirstSelectedOption().getText();
            String operator = new Select(operators.get(i)).getFirstSelectedOption().getText();
            String value = values.get(i).getAttribute("value");

            System.out.println("Regla " + (i + 1) + " -> " + model + " " + operator + " " + value);

            if (!model.equals(expectedRules[i][0]) ||
                    !operator.equals(expectedRules[i][1]) ||
                    !value.equals(expectedRules[i][2])) {
                return false;
            }
        }

        return true;
    }
    public boolean VerifyGroupRule() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), 'ListCreateGroup')]")));
        js.executeScript("arguments[0].click();", playlist);


        Actions actions = new Actions(driver);
        actions.contextClick(playlist).perform();

        WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(text(),'Edit') or contains(text(),'Edit playlist')]")));
        editOption.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.name("model[]"), 2));


        List<WebElement> models = driver.findElements(By.name("model[]"));
        List<WebElement> operators = driver.findElements(By.name("operator[]"));
        List<WebElement> values = driver.findElements(By.name("value[]"));


        String model0 = new Select(models.get(0)).getFirstSelectedOption().getText();
        String operator0 = new Select(operators.get(0)).getFirstSelectedOption().getText();
        String value0 = values.get(0).getAttribute("value");

        String model1 = new Select(models.get(1)).getFirstSelectedOption().getText();
        String operator1 = new Select(operators.get(1)).getFirstSelectedOption().getText();
        String value1 = values.get(1).getAttribute("value");

        System.out.println("Regla 1: " + model0 + " " + operator0 + " " + value0);
        System.out.println("Regla 2: " + model1 + " " + operator1 + " " + value1);

        return model0.equals("Artist") && operator0.equals("contains") && value0.equals("Dan")
                && model1.equals("Title") && operator1.equals("is") && value1.equals("Pluto");
    }


}