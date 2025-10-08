import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {
@Test

    public void loginValidEmailPassword(){
        launchBrowser("https://qa.koel.app/");
        navigatetoPage();

    LoginPage loginPage = new LoginPage(driver);
    Homework22 homework22 = new Homework22(driver);
    loginPage.provideEmail("heilyn.fuselier@testpro.io");
    loginPage.providePassword("HolaMundo@2025");
    loginPage.clickSubmit();
    homework22.openPlaylist();
    homework22.renamePlaylist("Christmas");
    }

    }






