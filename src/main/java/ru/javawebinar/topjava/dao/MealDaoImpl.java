package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal add(Meal meal) {
        return repository.computeIfAbsent(counter.incrementAndGet(), k -> {
            meal.setId(k);
            return meal;
        });
    }

    @Override
    public void addAll(Collection<Meal> meal) {
        meal.forEach(this::add);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Meal update(int id, Meal meal) {
        return repository.computeIfPresent(meal.getId(),(key,value)->meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }
}
