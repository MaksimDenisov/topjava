package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {

    Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger();

    public MealDaoImpl() {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal getOne(Integer id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void delete(Integer id) {
        mealMap.remove(id);
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(counter.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
    }
}
