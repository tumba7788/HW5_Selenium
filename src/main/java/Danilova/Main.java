package Danilova;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String WEB_FORM_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    public static void main(String[] args) {
       getPageLabelsTest();

    }

    //Этот метод нужен для того, чтобы автоматически получить названия всех лэйблов со страницы
    static void getPageLabelsTest() {
        WebDriver driver= new ChromeDriver();
            driver.get(WEB_FORM_URL);
            driver.manage().window().maximize();

        List<WebElement> fields = driver.findElements(By.xpath("//label[contains(@class,'form-label w-100') or contains(@class,'form-check-label w-100')]"));

        try (FileWriter writer = new FileWriter("titlesWebFormPage.txt", false)) {  // Appending to the file
            for (WebElement webElement : fields) {
                String field = webElement.getText().split("\n")[0].trim();//label возвращает текст из вложенных элементов, поэтому split("\n") — разбивает текст по строкам.[0] — берет первую строку.trim() — убирает пробелы.

                // Output and write the title to a file
                System.out.println("Page field: " + field);
                writer.write(field + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error during writing fields: " + e.getMessage());
        }
        driver.quit();
    }
}