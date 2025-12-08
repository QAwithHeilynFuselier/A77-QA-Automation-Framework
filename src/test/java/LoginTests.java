
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class LoginTests extends BaseTest {
    @Parameters({"BaseUrl"})
    @Test

    public void loginValidEmailPassword(String BaseUrl){
        launchBrowser(BaseUrl);
        navigatetoPage();

    LoginPage loginPage = new LoginPage(driver);
    Homework22 homework22 = new Homework22(driver);
    HomePage homepage = new HomePage(driver);
    loginPage.provideEmail("heilyn.fuselier@testpro.io");
    loginPage.providePassword("HolaMundo@2025");
    loginPage.clickSubmit();
    homework22.openPlaylist();
      homework22.renamePlaylist("List2026");
    Assert.assertTrue(homepage.getUserAvatar().isDisplayed());


    }

    }






