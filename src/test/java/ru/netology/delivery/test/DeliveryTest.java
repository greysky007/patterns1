package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("span [placeholder = \"Город\"]").setValue(validUser.getCity());
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $("span [name=\"phone\"]").setValue(validUser.getPhone());
        $("span [name=\"name\"]").setValue(validUser.getName());
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//div[(text()='Встреча успешно запланирована на ')]").should(visible);
        $x("//div[(text()='Встреча успешно запланирована на ')]")
                .shouldHave(exactText("Встреча успешно запланирована на "+ firstMeetingDate));
        $(".button__text").click();
        $("span [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        $("span [placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        $x("//span[@class='button__text'][text()='Перепланировать']").click();
        $x("//div[(text()='Встреча успешно запланирована на ')]").should(visible);
        $x("//div[(text()='Встреча успешно запланирована на ')]")
                .shouldHave(exactText("Встреча успешно запланирована на "+ secondMeetingDate));


    }
}
