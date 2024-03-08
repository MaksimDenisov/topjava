package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(1, meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        Map<Integer, Meal> map = repository.computeIfAbsent(userId, m -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            map.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, int id) {
        Map<Integer, Meal> map = repository.get(userId);
        return map != null && map.remove(id) != null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        Map<Integer, Meal> map = repository.get(userId);
        return map == null ? null : map.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.getOrDefault(userId, Collections.emptyMap())
                .values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(Integer userId, LocalDate startDate, LocalDate endDate) {
        return repository.getOrDefault(userId, Collections.emptyMap())
                .values()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getDateTime().toLocalDate(),
                        (startDate == null) ? LocalDate.MIN : startDate,
                        (endDate == null) ? LocalDate.MAX : endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}


