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

            // Verificar URL inicial
            Assert.assertEquals(driver.getCurrentUrl(), url);

            // Click en el enlace de registro
            driver.findElement(By.cssSelector("a[href='/register']")).click();

            // Validar URL de registro
            Assert.assertEquals(driver.getCurrentUrl(), "https://qa.koel.app/register");


            // Cerrar el navegador al final
            driver.quit();

    }
}
















