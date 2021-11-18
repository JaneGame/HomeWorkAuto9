package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestPlan {
    private static Faker faker;
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver(2).exe");
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    String city = faker.address().city();
    String name = faker.name().fullName();
    String phone = faker.phoneNumber().subscriberNumber(11);
    Date date = faker.date().future(10, 3, TimeUnit.DAYS);


    @Test
    public void formTest() {
        open("http://localhost:9999");
        $$("[type='text']").exclude(hidden).first().setValue(city);
        $$("[type='tel']").exclude(hidden).first().setValue(String.valueOf(date));
        $$("[type='text']").exclude(hidden).last().setValue(name);
        $$("[type='tel']").exclude(hidden).last().setValue(phone);
        $(withText("соглашаюсь")).click();
        $(byText("Запланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $$("[type='tel']").exclude(hidden).first().setValue(String.valueOf(date));
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
    }
}
