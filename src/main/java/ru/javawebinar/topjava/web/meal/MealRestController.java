package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(new ArrayList<>(service.getAll(SecurityUtil.authUserId())), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal) {
        service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getTos(new ArrayList<>(service.getBetween(SecurityUtil.authUserId(), startDate, endDate)),
                        SecurityUtil.authUserCaloriesPerDay()).stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(),
                        (startTime == null) ? LocalTime.MIN : startTime,
                        (endTime == null) ? LocalTime.MAX : endTime))
                .collect(Collectors.toList());
    }
}