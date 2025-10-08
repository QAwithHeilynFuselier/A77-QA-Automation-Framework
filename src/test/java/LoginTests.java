
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    @Parameters({"BaseURL"})
    public void loginEmptyEmailPassword(String BaseURL) {
        getDriver().get(BaseURL);
        Assert.assertEquals(getDriver().getCurrentUrl(), BaseURL);

    }
}

