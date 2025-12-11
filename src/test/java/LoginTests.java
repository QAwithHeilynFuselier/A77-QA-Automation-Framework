
import base.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import pages.HomePage;
import pages.Homework23;


public class LoginTests extends BaseTest {
    @Parameters({"BaseUrl"})
    @Test

    public void loginValidEmailPassword(String BaseUrl){
        launchBrowser(BaseUrl);
        navigatetoPage();
        LoginPage loginPage = new LoginPage(driver);
        HomePage homepage = loginPage.login("heilyn.fuselier@testpro.io",("HolaMundo@2025"));
        Assert.assertTrue(homepage.isAvatarVisible(),"Login its fail");

        Homework23 homework23 = new Homework23(driver);

        homework23.openPlaylist();
         homework23.renamePlaylist("ChristmasMusic");


    }

}