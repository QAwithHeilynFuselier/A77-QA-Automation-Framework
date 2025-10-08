import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

   public class LoginPage extends Basepage{

      public LoginPage(WebDriver givenDriver){
                super(givenDriver);
}


By EmailField = By.cssSelector("input[placeholder='Email Address']");
By PasswordField =By.cssSelector("input[placeholder='Password']");
By Summitbtn =By.xpath("//button[text()='Log In']");

    public void provideEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Email Address']")));

        emailField.clear();
        emailField.sendKeys(email);
    }

    public void providePassword(String password) {
        WebElement passwordfi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Password']")));
        passwordfi.clear();
        passwordfi.sendKeys(password);
    }

    public void clickSubmit() {
        WebElement summit = driver.findElement(By.xpath("//button[text()='Log In']"));
        summit.click();
    }

    public void login(){
        provideEmail("heilyn.fuselier@testpro.io");
        providePassword("HolaMundo@2025");
        clickSubmit();
    }



}








