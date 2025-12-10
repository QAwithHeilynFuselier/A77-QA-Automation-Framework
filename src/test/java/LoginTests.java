
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

    loginPage.provideEmail("heilyn.fuselier@testpro.io");
    loginPage.providePassword("HolaMundo@2025");
    loginPage.clickSubmit();
    Homework22 homework22 = new Homework22(driver);

    homework22.openPlaylist();
      homework22.renamePlaylist("List2026");
        HomePage homepage = new HomePage(driver);
    Assert.assertTrue(homepage.getUserAvatar().isDisplayed());


    }

    }






