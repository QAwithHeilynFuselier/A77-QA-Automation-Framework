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
      findElement(EmailField).sendKeys(email);
    }

    public void providePassword(String password) {
      findElement(PasswordField).sendKeys(password);
    }

    public void clickSubmit() {
        findElement(Summitbtn).click();
    }

    public void login(){
        provideEmail("heilyn.fuselier@testpro.io");
        providePassword("HolaMundo@2025");
        clickSubmit();
    }



}








