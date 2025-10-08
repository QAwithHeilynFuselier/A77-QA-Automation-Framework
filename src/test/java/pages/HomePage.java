package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage  extends BasePage {
    @FindBy(css = "img.avatar")
    private WebElement userAvatarIcon;


    public HomePage(WebDriver givenDriver) {
        super(givenDriver);

    }

    public boolean isAvatarVisible(){
        return findElement(userAvatarIcon).isDisplayed();
    }

    public void clickAvatar()
    {
        click(userAvatarIcon);
    }

}