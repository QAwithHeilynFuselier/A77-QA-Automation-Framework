



import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class LoginTests extends BaseTest {

    @Parameters({"BaseURL", "browser"})
    @Test
    public void verifyLoginPage(String BaseURL, String browser) {

        assert driver.getCurrentUrl().equals(BaseURL)
                : "Did not land on correct URL";
    }
}

