package stepDefinition;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.time.Duration;

public class LoginStepDefinitions {
    WebDriver driver;
    WebDriverWait wait;



    @Before
    public void iOpenBrowser(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
}

    @After
    public void CloseBrowser(){
        driver.quit();

    }

    @Given("I open Koel Login Page")
    public void  OpenKoelLoginPage() {
        // Write code here that turns the phrase above into concrete actions
        driver.get("https://qa.koel.app/");
    }


    @When("I enter email {string}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")))
                .sendKeys(email);
    }

    @And("I enter password {string}")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("input[type='password']")))
                .sendKeys(password);
    }

    @And("I submit")
    public void clickSubmit() {
        wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("button[type='submit']")))
                .click();
    }

    @Then("I am Logged in")
    public void userIsLoggedIn() {
        Assert.assertTrue(
                wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.cssSelector("img.avatar")))
                        .isDisplayed()
        );
    }


}
