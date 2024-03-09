package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static int ID = 100002;
    public static Meal MEAL_1 = new Meal(ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static Meal MEAL_2 = new Meal(ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static Meal MEAL_3 = new Meal(ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static Meal MEAL_4 = new Meal(ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static Meal MEAL_5 = new Meal(ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static Meal MEAL_6 = new Meal(ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static Meal MEAL_7 = new Meal(ID + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static LocalDate SECOND_DAY = LocalDate.of(2020, Month.JANUARY, 31);

    public static List<Meal> MEALS = Arrays.asList(MEAL_7, MEAL_6, MEAL_5, MEAL_4,
            MEAL_3, MEAL_2, MEAL_1);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.MARCH, 30, 10, 0), "Новая еда", 5000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDateTime(LocalDateTime.of(2020, Month.MARCH, 30, 10, 0));
        updated.setDescription("updated");
        updated.setCalories(5000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
