package ru.netology;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker;


    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String cities[] = {"Москва", "Псков", "Владивосток"};
        int i = cities.length-1;
        int x = (int)(Math.random()*((i-0)+1))+0;
        String city = cities[x];
        return city;
    }

    public static String generateName(String locale) {
        faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().subscriberNumber();
        return phone;
    }


    public static class Registration {
        private Registration() {

        }


        public static UserInfo generateUser(String locale) {
            String city = generateCity();
            String name = generateName(locale);
            String phone = generatePhone(locale);
            DataGenerator.UserInfo user = new UserInfo(city, name, phone);
            return user;
        }

    }

    @Value

    public static class UserInfo {
        String city;
        String name;
        String phone;


    }
}
