package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends BasePage {



    @FindBy(css = "[type='submit']")
    private WebElement submitButtonLocator;

    @FindBy(css = "[type='email']")
    private WebElement emailField;
    @FindBy(css = "[type='password']")
    private WebElement passwordField;
    @FindBy(css = "[data-testid='login-form']")
    private WebElement loginForm;
    @FindBy(id = "nprogress")
    private WebElement progressBar;

    public LoginPage(WebDriver givenDriver){
        super(givenDriver);
    }



    public LoginPage provideEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage providePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }
    public LoginPage clickSubmitBtn() {
        submitButtonLocator.click();
        return this;
    }
    public HomePage login(String email, String password){

        provideEmail(email);
        providePassword(password);
        clickSubmitBtn();
        return new HomePage(driver);

    }

    public boolean isLoginPageVisible() {
        return loginForm.isDisplayed();
    }
    public boolean isErrorMessageVisible() {
        return progressBar.isDisplayed();
    }



}