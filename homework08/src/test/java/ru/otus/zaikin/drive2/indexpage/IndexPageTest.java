package ru.otus.zaikin.drive2.indexpage;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.drive2.entity.CarBrendSet;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Log4j2
public class IndexPageTest extends DriverBase {

    private WebDriver driver;
    private IndexPagePO indexPagePO;
    private HibernateDao dao;
    private HibernateFactorySessionHolder sessionHolder;


    @BeforeMethod
    public void setup() throws MalformedURLException {

        sessionHolder = new HibernateFactorySessionHolder(
//            String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
                //String url = "jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD=";

                System.getProperty("DB_URL", "jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD="),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", "")
        );
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);

        driver = getDriver();
        indexPagePO = new IndexPagePO(dao);
        indexPagePO.openSite();
    }

    @Test
    public void shouldOpenSite() {
        assertThat(driver.findElement(By.cssSelector(".c-top__intro")).getText()).isEqualTo("Сообщество машин и людей");
    }

    @Test
    public void shouldReadBrends() {
        System.out.println("IndexPageTest.shouldReadBrends");
        List<WebElement> cars = driver.findElements(By.cssSelector("div.c-index-alt__brands >div>a"));
        assertThat(cars.size()).isEqualTo(54);
    }

    @Test
    public void shouldSaveBrends() {
        indexPagePO.saveBrends();
        List<CarBrendSet> list = dao.getAll(CarBrendSet.class);
        assertThat(list.size()).isEqualTo(54);
        list.forEach(log::debug);
    }

    @Test
    public void shouldOpenMazda() {
        List<WebElement> cars = driver.findElements(By.cssSelector("div.c-index-alt__brands >div>a"));
        Optional<WebElement> optionalWebElement = cars.stream().filter(e -> e.getText().equalsIgnoreCase("MAZDA")).findFirst();
        WebElement mazda = optionalWebElement.orElseThrow(() -> new RuntimeException("Mazda not found"));
        assertThat(mazda.getText()).isEqualTo("Mazda");
        mazda.click();
        System.out.println("here");
    }

    @Test
    public void shouldReadAllMazda() throws InterruptedException {
        List<WebElement> cars = driver.findElements(By.cssSelector("div.c-index-alt__brands >div>a"));
        Optional<WebElement> optionalWebElement = cars.stream().filter(e -> e.getText().equalsIgnoreCase("MAZDA")).findFirst();
        WebElement mazda = optionalWebElement.orElseThrow(() -> new RuntimeException("Mazda not found"));
        assertThat(mazda.getText()).isEqualTo("Mazda");
        mazda.click();
        List<WebElement> card = new ArrayList<>();
        int currentSize = -1;
        Thread.sleep(5000);
        Wait wait = new WebDriverWait(driver, 10);
        while (currentSize < card.size()) {
            currentSize = card.size();
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,5000)");
            try {
                wait.until(and(invisibilityOfElementLocated(By.cssSelector("button.[data-action='catalog.morecards']")), (numberOfElementsToBeMoreThan(By.cssSelector(".c-car-card-sa"), currentSize))));

            } catch (Exception e) {
                e.printStackTrace();
            }
            card = driver.findElements(By.cssSelector(".c-car-card-sa"));
        }
        card.forEach(log::debug);
        log.debug("cards size:" + card.size());
    }

    @Test
    public void shouldSaveCarsHref() {
        indexPagePO.saveCarsOfBrend("Mazda");
        List<CarEntitySet> list = dao.getAll(CarEntitySet.class);
        assertThat(list.size()).isGreaterThan(100);
        log.debug(list.get(0).getHref());
    }
}