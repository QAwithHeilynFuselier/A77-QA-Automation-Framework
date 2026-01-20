
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pages.SmartList;




public class LoginTests extends BaseTest {


    @Test
    @Parameters({"BaseUrl"})

    public void loginValidEmailPassword(String BaseUrl) {
        launchBrowser(BaseUrl);
        Assert.assertEquals(driver.getCurrentUrl(), BaseUrl);
        navigatetoPage();
        LoginPage loginPage = new LoginPage(driver);
        HomePage homepage = loginPage.login("heilyn.fuselier@testpro.io", ("HolaMundo@2025"));
        Assert.assertTrue(homepage.isAvatarVisible(), "Login its fail");

        SmartList smartList = new SmartList(driver);
        // User should be able to create a Smart playlist in app with one rule

        smartList.ClickSmartList();
       smartList.OpenListCreated();
        smartList.OpenEditPlaylist();

        // User should be able to create a Smart playlist in app with multiple rules
       smartList.CreateSmartListWithMultipleRules();


        //User should be able to create a Smart playlist in app with Group

        smartList.CreateRulerwithgroup();

        //After creating Smart playlist with a rule, related songs should appear in created Smart playlist
       smartList.OpenListCreated();

      smartList.OpenEditPlaylist();

     smartList.VerifyOneRule();

        smartList.verifyMultipleRulesPlaylist();
        smartList.VerifyGroupRule();

       //If a rule doesn't fit any existing song, empty Smart playlist should be created, 'No songs match the playlist's criteria' should be displayed
        smartList.createEmptySmartList();
       smartList.verifyEmptySmartPlaylistMessage() ;

        //Playlist name should have the same rules as a regular playlist (any special chars are allowed and 1 to 256 max)
       String specialName =  "My$mart_List-#1!@2026(test)";
        smartList. createSmartListWithSpecialName(specialName);
        String name256 = smartList.generateName(256);
        smartList.verifyPlaylistNameCreated(name256);

    }
}