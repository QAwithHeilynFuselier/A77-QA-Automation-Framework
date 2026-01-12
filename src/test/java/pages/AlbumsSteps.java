package pages;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageFactory.BasePage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumsSteps extends BasePage {

    public AlbumsSteps(WebDriver driver) {
        super(driver);
    }

    public void openAlbum() {
        WebElement albums = driver.findElement(By.cssSelector("a.albums"));

        albums.click();

    }

    @Test



    public void Verifycountalbumnes(){

        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }
        System.out.println("VerifyaccountName");
        try {

            Set<String> nombresUnicos = new HashSet<>();

            By albumCardSelector = By.cssSelector("[data-test='album-card']");
            wait.until(ExpectedConditions.presenceOfElementLocated(albumCardSelector));
            List<WebElement> albums = driver.findElements(albumCardSelector);

            System.out.println("--- List albums (" + albums.size() + ") ---");
            for (WebElement album : albums) {
                String title = album.getAttribute("title");

                System.out.println("- " + title);

            }

            if (albums.size() > 0 ){
                System.out.println(albums.size() + " álbumnInside.");
            } else {
                System.out.println("Album its Emtpy.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: : " + e.getMessage());
        }
    }

    public void verifyNoDuplicateAlbums() {
        System.out.println("List duplicates");

        List<WebElement> albumElements = driver.findElements(By.cssSelector("[data-test='album-card']"));

        Set<String> nombresUnicos = new HashSet<>();
        List<String> duplicados = new ArrayList<>();

        for (WebElement album : albumElements) {
            String nombre = album.getAttribute("title");
            if (!nombresUnicos.add(nombre)) {
                duplicados.add(nombre);
            }
        }

        if (duplicados.isEmpty()) {
            System.out.println("No duplicates");
        } else {
            System.err.println( "duplicates" + duplicados);
        }
    }


    public void CheckifcoveralbumC() {
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }
        System.out.println("Cover album");

        List<WebElement> albums = driver.findElements(By.cssSelector("article.item, .album-item"));

        for (WebElement album : albums) {
            try {

                String albumName = album.findElement(By.cssSelector(".name")).getText().trim();


                WebElement cover = album.findElement(By.cssSelector("span.cover"));
                String style = cover.getAttribute("style");

                if (style != null && style.contains("unknown-album.png")) {
                    System.out.println("Álbum withoun  cover: " + albumName);
                } else {
                    System.out.println("Álbum with cover: " + albumName);
                }
            } catch (NoSuchElementException e) {

                System.out.println("No found.");
            }
        }
    }

    public void Checkifcoveralbum() {


        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }
        System.out.println("Cover album");
        List<WebElement> covers = driver.findElements(By.cssSelector("span.cover"));

        for (WebElement cover : covers) {
            String style = cover.getAttribute("style");

            if (style != null && style.contains("unknown-album.png")) {
                System.out.println("Album without cover (unknown-album.png)");
            } else {
                System.out.println("Album with cover");
            }
        }
    }

    public void VerifyAlbumNameA() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. SCROLL HASTA EL FINAL para cargar los álbumes ocultos
        // Lo hacemos un par de veces para asegurar que el scroll infinito reaccione
        for (int i = 0; i < 3; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        // 2. Usar un selector que atrape TODOS los items, no solo los .full
        // A veces los unknown no tienen la clase 'full' hasta que se cargan bien
        List<WebElement> albumCards = driver.findElements(By.cssSelector("article.item"));

        Set<String> albumIdsProcesados = new HashSet<>();
        int unknownAlbumCount = 0;

        for (WebElement card : albumCards) {
            try {
                // Buscamos el link del nombre del álbum
                WebElement albumLink = card.findElement(By.cssSelector("a.name"));
                String href = albumLink.getAttribute("href");

                // Si ya lo procesamos, saltar (evita los 36/45 duplicados)
                if (albumIdsProcesados.contains(href)) continue;
                albumIdsProcesados.add(href);

                String text = albumLink.getAttribute("textContent").trim();

                if (text.equalsIgnoreCase("Unknown Album") || text.isEmpty()) {
                    unknownAlbumCount++;
                    System.out.println("DETECTADO UNKNOWN -> ID: " + href);
                } else {
                    System.out.println("Álbum con nombre: " + text);
                }
            } catch (Exception e) {
                // Ignorar elementos que no son álbumes o no tienen link
            }
        }

        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println("Total de álbumes únicos encontrados: " + albumIdsProcesados.size());
        System.out.println("Total de UNKNOWN ALBUMS: " + unknownAlbumCount);
    }
    public void VerifyAlbumName() {

        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }
        System.out.println("VerifyAlbumName");

        List<WebElement> albumCards = driver.findElements(By.cssSelector("article.item"));
        Set<String> idsProcesados = new HashSet<>();
        int unknownCount = 0;

        for (WebElement card : albumCards) {
            try {

                String href = card.findElement(By.cssSelector("a.name")).getAttribute("href");
                if (idsProcesados.contains(href)) continue;
                idsProcesados.add(href);

                String albumName = card.findElement(By.cssSelector("a.name")).getAttribute("textContent").trim();
                String artistName = card.findElement(By.cssSelector("a.artist")).getAttribute("textContent").trim();


                if (albumName.isEmpty() ||
                        albumName.equalsIgnoreCase("Unknown Album") ||
                        albumName.equalsIgnoreCase(artistName)) {

                    unknownCount++;
                    System.out.println("[!] UNKNOWN DETECTADO -> ID: " + href + " | Texto en UI: " + albumName);
                } else {
                    System.out.println("Álbum Real: " + albumName + " | ID: " + href);
                }
            } catch (Exception e) {

            }
        }

        System.out.println("\n--- REPORTE FINAL ---");
        System.out.println("Total de álbumes analizados: " + idsProcesados.size());
        System.out.println("Total de UNKNOWN encontrados: " + unknownCount);
    }

    public void VerifyAlbumArtistName() {



        System.out.println("Verify Artist Name");


        JavascriptExecutor js = (JavascriptExecutor) driver;

        Set<String> albumesValidados = new HashSet<>();
        int totalEsperado = 22;
        int intentosSinProgreso = 0;
        int maxIntentos = 5;

        System.out.println("--- 22 álbums ---");

        while (albumesValidados.size() < totalEsperado && intentosSinProgreso < maxIntentos) {
            int tamañoAlInicio = albumesValidados.size();

            List<WebElement> currentAlbums = driver.findElements(By.cssSelector("article[data-test='album-card']"));

            for (WebElement album : currentAlbums) {
                String albumTitle = album.findElement(By.cssSelector(".name")).getText().trim();

                if (albumesValidados.contains(albumTitle)) continue;

                List<WebElement> artistElements = album.findElements(By.cssSelector(".artist"));
                String name = artistElements.isEmpty() ? "" : artistElements.get(0).getText().trim();

                if (name.isEmpty() || name.toLowerCase().contains("unknown artist")) {
                    System.out.println("  'album Title '" + albumTitle + "' unknown artist.");
                } else {
                    System.out.println("" + albumTitle + " - Album with  Artist name");
                }

                albumesValidados.add(albumTitle);
            }


            if (albumesValidados.size() == tamañoAlInicio) {

                intentosSinProgreso++;
            } else {

                intentosSinProgreso = 0;
            }

            if (albumesValidados.size() < totalEsperado && !currentAlbums.isEmpty()) {
                js.executeScript("arguments[0].scrollIntoView();", currentAlbums.get(currentAlbums.size() - 1));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                }
            }

            if (currentAlbums.size() == 0) break;
        }

        System.out.println("album Total: " + albumesValidados.size());


        if (albumesValidados.size() < totalEsperado) {
            System.out.println("album" + albumesValidados.size() +
                    " de los " + totalEsperado + " esperados.");
        }
    }
    public void VerifyAlbumArtistNameA() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Set<String> albumesValidados = new HashSet<>();
        int totalEsperado = 22;

        System.out.println("--- 22 álbums ---");


        while (albumesValidados.size() < totalEsperado) {

            List<WebElement> currentAlbums = driver.findElements(By.cssSelector("article[data-test='album-card']"));

            for (WebElement album : currentAlbums) {
                try {

                    String albumTitle = album.findElement(By.cssSelector(".name")).getAttribute("textContent").trim();

                    if (albumesValidados.contains(albumTitle)) continue;
                    String artistName = album.findElement(By.cssSelector(".artist")).getAttribute("textContent").trim();

                    //String name = album.findElement(By.cssSelector(".artist")).getAttribute("textContent").trim();

                    if (artistName.isEmpty() || artistName.toLowerCase().contains("unknown artist")) {
                        System.out.println("  'album Title '" + albumTitle + "' unknown artist.");
                    } else {
                        System.out.println(albumTitle + " - Album with Artist name- " + "* " + artistName);
                    }

                    albumesValidados.add(albumTitle);

                } catch (Exception e) {

                }
            }

            if (albumesValidados.size() < totalEsperado) {
                js.executeScript("window.scrollBy(0, 400);");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        }
    }
    public void VerifyAlbumArtistNameC() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Set<String> albumesValidados = new HashSet<>();
        int totalEsperado = 22;

        System.out.println("--- Validando 22 álbumes ---");

        while (albumesValidados.size() < totalEsperado) {
            List<WebElement> currentAlbums = driver.findElements(By.cssSelector("article[data-test='album-card']"));

            for (WebElement album : currentAlbums) {
                try {
                    String albumTitle = album.findElement(By.cssSelector(".name")).getAttribute("textContent").trim();
                    String artistName = album.findElement(By.cssSelector(".artist")).getAttribute("textContent").trim();

                    if (albumesValidados.contains(albumTitle)) continue;

                    // Si ambos son Unknown o el artista está vacío
                    if (artistName.isEmpty() || artistName.toLowerCase().contains("unknown")) {
                        System.out.println("  [!] DETECTADO: '" + albumTitle + "' - Sin artista (Unknown)");
                    } else {
                        System.out.println(albumTitle + " - Album with Artist name: " + artistName);
                    }

                    albumesValidados.add(albumTitle);

                } catch (Exception e) {
                    // El bucle sigue si algo falla por el scroll
                }
            }

            if (albumesValidados.size() < totalEsperado) {
                js.executeScript("window.scrollBy(0, 500);");
                try { Thread.sleep(1200); } catch (InterruptedException e) {}
            }
        }
    }



    public void VerifyAlbumSongsCountInsidealbum(String albumName) {
        System.out.println("Verify sound accountInsideAlbum");
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }

        try {
            WebElement albumLink = driver.findElement(
                    By.xpath("//a[@class='name' and text()='" + albumName + "']")
            );
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", albumLink
            );

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


            WebElement metaInfo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@id='albumWrapper']/header/div[2]/span")
                    )
            );

            String metaText = metaInfo.getText();


            Pattern pattern = Pattern.compile("(\\d+)\\s+songs");
            Matcher matcher = pattern.matcher(metaText);

            if (!matcher.find()) {
                throw new AssertionError("Song count not found in album header");
            }

            int displayedSongCount = Integer.parseInt(matcher.group(1));

            System.out.println(
                    "Album '" + albumName + "' tiene " + displayedSongCount + " canciones."
            );

            if (displayedSongCount <= 0) {
                throw new AssertionError("Invalid song count: " + displayedSongCount);
            }

        } catch (Exception e) {
            System.out.println(
                    "Album '" + albumName + "' could not be validated. Error: " + e.getMessage()
            );
        } finally {
            driver.navigate().back();
        }
    }
    public void verifyAlbumSongsCountExternal() {
        System.out.println("Verify sound accountExternal Album");
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("Not on Album page");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> albumArticles = driver.findElements(By.cssSelector("article.item.full"));

        for (WebElement album : albumArticles) {

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", album);

            //  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", album);

            try {
                WebElement songCountElement = album.findElement(By.cssSelector(".meta .left"));
                wait.until(driver -> {
                    String text = songCountElement.getText();
                    return text != null && !text.isEmpty();
                });
                String songCountText = songCountElement.getText();
                String songCount = (songCountText.contains(" ")) ? songCountText.split(" ")[0] : "0";
                System.out.println("Album: " + album.getAttribute("title"));
                System.out.println("Song Count: " + songCount);
            } catch (TimeoutException e) {
                System.out.println("Error: Time out: " + album.getAttribute("title"));
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se encontró el elemento meta para: " + album.getAttribute("title"));
            }
        }
    } //excelent
    public void VerifyShuffleiconVisible() {

        openAlbum();
        System.out.println("Verify Shuffle Icon");
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("its not album page");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        By albumSelector = By.cssSelector("a.name");
        //List<WebElement> albums = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(albumSelector));
        List<WebElement> albumContainers = driver.findElements(By.cssSelector("article.item.full"));
        boolean foundAtLeastOne = false;
        for (WebElement container : albumContainers) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
                actions.moveToElement(container).pause(Duration.ofMillis(300)).perform();
                WebElement shuffle = container.findElement(By.cssSelector(".shuffle-album"));
                wait.until(ExpectedConditions.visibilityOf(shuffle));

                if (shuffle.isDisplayed()) {

                    System.out.println(" Shuffle is visible in  " + container.getAttribute("title"));
                    foundAtLeastOne = true;
                    break;
                }

            } catch (Exception e) {
                System.out.println("Found...");
            }
        }
        if (!foundAtLeastOne){
            System.out.println("shuffle no found");
        }
    }
    public void VerifyClickShuffleAndVerifyPlayback() {
        openAlbum();
        System.out.println("Verify Shuffle Icon funcionality");
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("its not album page");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        By albumSelector = By.cssSelector("a.name");
        //List<WebElement> albums = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(albumSelector));
        List<WebElement> albumContainers = driver.findElements(By.cssSelector("article.item.full"));
        boolean foundAtLeastOne = false;
        for (WebElement container : albumContainers) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
                actions.moveToElement(container).pause(Duration.ofMillis(300)).perform();
                WebElement shuffle = container.findElement(By.cssSelector(".shuffle-album"));
                wait.until(ExpectedConditions.visibilityOf(shuffle));

                if (shuffle.isDisplayed()) {
                    System.out.println(" Shuffle is visible in  " + container.getAttribute("title"));
                    shuffle.click();
                    System.out.println(" Playback.");
                    foundAtLeastOne = true;
                    break;
                }


            } catch (Exception e) {
                System.out.println("Find...");
            }
        }
        if (!foundAtLeastOne){
            System.out.println("shuffle no found");
        }
    }
    public void VerifyDownloadIconVisible() {

        openAlbum();
        System.out.println("Verify dowloand Icon");
        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("No estamos en la página de álbumes");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> albumContainers = driver.findElements(By.cssSelector("article.item.full"));

        boolean downloadVisible = false;

        for (WebElement container : albumContainers) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
                actions.moveToElement(container).pause(Duration.ofMillis(300)).perform();
                WebElement downloadIcon = container.findElement(By.cssSelector(".download-album"));
                wait.until(ExpectedConditions.visibilityOf(downloadIcon));




                if (downloadIcon.isDisplayed()) {
                    String albumName = container.getAttribute("title");
                    System.out.println("Download icon shown for: " + albumName);
                    downloadIcon.click();
                    System.out.println("click Download" + container.getAttribute("title"));
                    downloadVisible = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("No dowloand found");
            }
        }

        if (!downloadVisible) {
            throw new RuntimeException("FAIL   dowloand is no visible.");
        }
    }
    public void VerifyDownloadFunctionality() {
        openAlbum();
        System.out.println("Verify dowlonad Icon funcionality");

        if (!driver.getCurrentUrl().contains("albums")) {
            throw new IllegalStateException("No estamos en la página de álbumes");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> albumContainers = driver.findElements(By.cssSelector("article.item.full"));

        boolean downloadVisible = false;

        for (WebElement container : albumContainers) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
                actions.moveToElement(container).pause(Duration.ofMillis(300)).perform();
                WebElement downloadIcon = container.findElement(By.cssSelector(".download-album"));
                wait.until(ExpectedConditions.visibilityOf(downloadIcon));

                if (downloadIcon.isDisplayed()) {
                    downloadIcon.click();
                    System.out.println("click Download" + container.getAttribute("title"));
                    downloadVisible = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("No dowloand found");
            }
        }

        if (!downloadVisible) {
            throw new RuntimeException("FAIL   dowloand is no visible.");
        }
    }


}










