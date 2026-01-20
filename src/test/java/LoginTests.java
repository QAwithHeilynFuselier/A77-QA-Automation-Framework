
import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pages.AlbumsSteps;
import pages.PlayingListSteps;
import pages.SmartList;

import static org.testng.AssertJUnit.assertTrue;


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

       AlbumsSteps actionsalbum = new AlbumsSteps(driver);
        actionsalbum.openAlbum();
       actionsalbum.Verifycountalbumnes();
      actionsalbum.verifyNoDuplicateAlbums();

        /*1-Albums cover (if exist) should be present
        2-If Album cover is not existing standard Koel album cover should be present */
           actionsalbum.Checkifcoveralbum();
          actionsalbum.Checkifcoveralbum();
       /*3-Album name should be displayed*/
         actionsalbum.VerifyAlbumName();
       /*4-Artist name should be displayed*/
      actionsalbum.VerifyAlbumArtistName();//yes
       /*5A-Songs count should be displayed*/
      actionsalbum.verifyAlbumSongsCountExternal(); //exc
       /*5-B and reflect the actual number of songs in the album*/
  actionsalbum.VerifyAlbumSongsCountInsidealbum("Airbit");//exc
       /*6-Shuffle icon should be present*/
     actionsalbum. VerifyShuffleiconVisible();
       /*6-BShuffle icon should be present playback is working*/
     actionsalbum.VerifyClickShuffleAndVerifyPlayback();

       /* 7-ADownload icon should be present  */

     actionsalbum.VerifyDownloadIconVisible();

       /* 7B dowloand functionality */
     actionsalbum.VerifyDownloadFunctionality();


   }
}