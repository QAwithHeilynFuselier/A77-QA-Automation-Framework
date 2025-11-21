import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;


public class homework16 {

    @Test
    public void registrationNavigation() {

        ChromeOptions options  = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


            String url = "https://qa.koel.app/";
            driver.get(url);

            // Verify url
            Assert.assertEquals(driver.getCurrentUrl(), url);

            // Click in the link for register
            driver.findElement(By.cssSelector("a[href='/register']")).click();

            // validate to url
            Assert.assertEquals(driver.getCurrentUrl(), "https://qa.koel.app/register");


            // Closing
            driver.quit();

    }
}
















