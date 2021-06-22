package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public boolean delete(int userId, int id) {
        return checkNotFound(id, repository.delete(userId, id));
    }

    public Meal get(int userId, int id) {
        return checkNotFound(id, repository.get(userId, id));
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    private boolean checkNotFound(int id, boolean check) {
        if (!check)
            throw new NotFoundException("Not found with id = " + id);
        return true;
    }

    private <T> T checkNotFound(int id, T check) {
        if (check == null)
            throw new NotFoundException("Not found with id = " + id);
        return check;
    }
}