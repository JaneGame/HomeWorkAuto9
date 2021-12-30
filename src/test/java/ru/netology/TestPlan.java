package ru.netology;


import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestPlan {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void formTest() {
        open("http://localhost:9999");
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] .input__control").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] .input__control").setValue(validUser.getName());
        $("[data-test-id='phone'] .input__control").setValue(validUser.getPhone());
        $(withText("соглашаюсь")).click();
        $(byText("Запланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
    }
}
