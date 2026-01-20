package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.BasePage;

import java.time.Duration;
import java.util.List;

public class SmartList  extends BasePage {

    public SmartList (WebDriver driver) {
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

    //validate ruler created HeilynList
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




//User should be able to create a Smart playlist in app with multiple rules

    public void CreateSmartListWithMultipleRules(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        WebElement btnCrear = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")));
        btnCrear.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-smart']"))).click();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
        nameInput.sendKeys("ListaMultipleRules");

        String[][] reglas = {
                {"Artist", "contains", "Dan"},
                {"Album", "is", "Airbit"},
                {"Plays", "is greater than", "1"}
        };

        for (int i = 0; i < reglas.length; i++) {
            if (i > 0) {
                WebElement btnAddRule = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-add-rule")));
                js.executeScript("arguments[0].click();", btnAddRule);


                final int expectedCount = i + 1;
                wait.until(ExpectedConditions.numberOfElementsToBe(By.name("model[]"), expectedCount));
            }


            List<WebElement> models = driver.findElements(By.name("model[]"));
            new Select(models.get(i)).selectByVisibleText(reglas[i][0]);



            List<WebElement> operators = driver.findElements(By.name("operator[]"));
            new Select(operators.get(i)).selectByVisibleText(reglas[i][1]);


            List<WebElement> values = driver.findElements(By.name("value[]"));
            WebElement currentInput = values.get(i);

            wait.until(ExpectedConditions.elementToBeClickable(currentInput));
            currentInput.clear();
            currentInput.sendKeys(reglas[i][2]);
            currentInput.sendKeys(Keys.TAB);

            System.out.println("Regla " + (i + 1) + " configurada: " + reglas[i][0]);
        }

        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer button[type='submit']")));
        js.executeScript("arguments[0].click();", saveBtn);

        System.out.println("Playlist 'ListaMultipleRules'");
    }


   // User should be able to create a Smart playlist in app with Group

    public void  CreateRulerwithgroup(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement btnCrear = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")));
        btnCrear.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-smart']"))).click();
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        nameInput.sendKeys("ListCreateGroup");
        new Select(driver.findElements(By.name("model[]")).get(0)).selectByVisibleText("Artist");
        new Select(driver.findElements(By.name("operator[]")).get(0)).selectByVisibleText("contains");
        WebElement val0 = driver.findElements(By.name("value[]")).get(0);
        val0.clear();
        val0.sendKeys("Dan");
        WebElement btnGroup = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-add-group")));
        js.executeScript("arguments[0].click();", btnGroup);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.name("model[]"), 2));

        new Select(driver.findElements(By.name("model[]")).get(1)).selectByVisibleText("Title");
        new Select(driver.findElements(By.name("operator[]")).get(1)).selectByVisibleText("is");

        WebElement val1 = driver.findElements(By.name("value[]")).get(1);
        val1.clear();
        val1.sendKeys("Pluto");
        val1.sendKeys(Keys.TAB);
            WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("footer button[type='submit']")));
            js.executeScript("arguments[0].click();", saveBtn);

        System.out.println("Playlist 'ListCreateGroup' Create: (Artist contains Dan) + Group Title is Pluto)");
    }

    //After creating Smart playlist with a rule, related songs should appear in created Smart playlist
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




    //If a rule doesn't fit any existing song, empty Smart playlist should be created, 'No songs match the playlist's criteria' should be displayed
    public void createEmptySmartList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-smart']"))).click();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        nameInput.sendKeys("EmptySmartList");


        new Select(driver.findElement(By.name("model[]"))).selectByVisibleText("Artist");
        new Select(driver.findElement(By.name("operator[]"))).selectByVisibleText("contains");

        WebElement valueInput = driver.findElement(By.name("value[]"));
        valueInput.clear();
        valueInput.sendKeys("XYZ123NoExiste");


        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("footer button[type='submit']")));
        js.executeScript("arguments[0].click();", saveBtn);
    }

    public boolean verifyEmptySmartPlaylistMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        WebElement playlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[@id='playlists']//a[contains(text(), 'EmptySmartList')]")));
        js.executeScript("arguments[0].click();", playlist);


        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), \"No songs match the playlist's criteria\")]")
            ));
            System.out.println("Mensaje encontrado: " + msg.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


  //  Playlist name should have the same rules as a regular playlist (any special chars are allowed and 1 to 256 max)

    public void createSmartListWithSpecialName(String playlistName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-testid='playlist-context-menu-create-smart']"))).click();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));


        String specialName = "My$mart_List-#1! @2026 (test)";

        nameInput.sendKeys(specialName);


        new Select(driver.findElement(By.name("model[]"))).selectByVisibleText("Artist");
        new Select(driver.findElement(By.name("operator[]"))).selectByVisibleText("contains");
        WebElement val = driver.findElement(By.name("value[]"));
        val.clear();
        val.sendKeys("Dan");


        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("footer button[type='submit']")));
        js.executeScript("arguments[0].click();", saveBtn);
    }

    public boolean verifyPlaylistNameCreated(String playlistName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//section[@id='playlists']//a[contains(text(), '" + playlistName + "')]")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generateName(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("A");
        }
        return sb.toString();
    }










}
