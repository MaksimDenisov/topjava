package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal getOne(Integer id);
    List<Meal> getAll();
    void delete(Integer id);
    void save(Meal meal);
}
