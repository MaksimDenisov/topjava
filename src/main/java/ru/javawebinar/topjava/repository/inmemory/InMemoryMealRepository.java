package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<Integer, Meal> map = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            map.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return map.computeIfPresent(meal.getId(), (id, oldMeal)-> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null)
            return false;
        return map.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null)
            return null;
        return map.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null)
            return Collections.emptyList();
        return map.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

