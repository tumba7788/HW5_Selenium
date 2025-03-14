import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class WebPageTests {
    WebDriver driver;
    private static final String WEB_FORM_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    // Метод для поиска элементов по локатору
    private List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    // Метод для чтения данных из CSV-файла
    static List<String> getTestData() throws IOException {
        // Читаем все строки из файла и возвращаем как список
        return Files.lines(Paths.get("src/test/resources/titlesWebPage.csv")).skip(1) // Пропускаем заголовок
                .collect(Collectors.toList());
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get(WEB_FORM_URL);
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @ParameterizedTest
    @DisplayName("Проверяем названия label со страницы с ожидаемым названием из csv файла [cssSelector, XPath]")
    @CsvFileSource(resources = "/titlesWebPage.csv", numLinesToSkip = 1)
    void labelsWebPageTests(String title) throws IOException {

        // Пример использования CSS-селектора
        By byCssSelector = By.cssSelector(".form-label, .form-check-label");
        List<WebElement> elementsByCss = findElements(byCssSelector);

        // Пример использования XPath
        By byXpath = By.xpath("//label[contains(@class, 'form-label') or contains(@class, 'form-check')]");
        List<WebElement> elementsByXpath = findElements(byXpath);

        // Загружаем данные из CSV файла в List
        List<String> expectedTitles = getTestData();

        // Проверяем, что количество ByCSS элементов на странице и данных из файла совпадают
        Assertions.assertEquals(expectedTitles.size(), elementsByCss.size(), "Количество элементов на странице не совпадает с количеством данных в файле.");
        // Проверяем, что количество ByXPath элементов на странице и данных из файла совпадают
        Assertions.assertEquals(expectedTitles.size(), elementsByXpath.size(), "Количество элементов на странице не совпадает с количеством данных в файле.");

        // Итерируем по ByCss элементам на странице и сравниваем их с данными из файла
        for (int i = 0; i < elementsByCss.size(); i++) {
            String actualLabel = elementsByCss.get(i).getText().split("\n")[0].trim(); // Получаем текст с элемента
            String expectedLabel = expectedTitles.get(i); // Берем соответствующее значение из файла
            Assertions.assertEquals(expectedLabel, actualLabel, "Название label не совпадает. Ожидалось: " + expectedLabel + ", найдено: " + actualLabel);
        }
        // Итерируем по ByXpath элементам на странице и сравниваем их с данными из файла
        for (int i = 0; i < elementsByXpath.size(); i++) {
            String actualLabel = elementsByXpath.get(i).getText().split("\n")[0].trim(); // Получаем текст с элемента
            String expectedLabel = expectedTitles.get(i); // Берем соответствующее значение из файла
            Assertions.assertEquals(expectedLabel, actualLabel, "Название label не совпадает. Ожидалось: " + expectedLabel + ", найдено: " + actualLabel);
        }
    }
}

