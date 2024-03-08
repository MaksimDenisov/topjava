package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Integer userId, Meal meal);

    // false if meal does not belong to userId
    boolean delete(Integer userId, int id);

    // null if meal does not belong to userId
    Meal get(Integer userId, int id);

    // ORDERED dateTime desc
    List<Meal> getAll(Integer userId);

    List<Meal> getBetween(Integer userId, LocalDate startDate, LocalDate endDate);
}
