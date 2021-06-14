package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealDao {
    Meal add(Meal meal);
    void addAll(Collection<Meal> meal);
    Meal get(int id);
    List<Meal> getAll();
    Meal update(int id,Meal meal);
    void delete(int id);
}
