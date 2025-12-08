
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;


import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;


public class Basepage {

    String url;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;


//we are use in all pages
    public Basepage(WebDriver givenDriver){
        driver = givenDriver;
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        actions = new Actions(driver);



    }
    public WebElement findelement(By locator){
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }




















}
