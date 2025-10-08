package test;

import base.BaseTest;
import pages.ActionsPages;
import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;


public class LoginTests extends BaseTest {



    @Parameters({"BaseUrl"})
    @Test(groups = "Smoke")

    public void loginValidEmailPassword(String BaseUrl){
        launchBrowser(BaseUrl);
        navigatetoPage();

        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homepage = loginPage.login("heilyn.fuselier@testpro.io",("HolaMundo@2025"));
        Assert.assertTrue(homepage.isAvatarVisible(), "Login failed");


        ActionsPages actionsPages = new ActionsPages(getDriver());
        actionsPages.openPlaylist();
        actionsPages.renamePlaylist("ChristmasMusic");

    }


    @Parameters({"BaseUrl"})
    @Test(groups = {"Smoke", "Regression"})

    public void loginwithInValidEmailPassword(String BaseUrl) {

        launchBrowser(BaseUrl);

        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homepage = loginPage.login(
                "heilyn.fuselier@testpro.io",
                "HolaMundo@2024"
        );

        Assert.assertTrue(homepage.isAvatarVisible(), "Login failed");
    }



}



