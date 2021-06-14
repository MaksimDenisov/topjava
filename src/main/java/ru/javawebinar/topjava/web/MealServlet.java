package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

//https://danielniko.dev/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

public class MealServlet extends HttpServlet {
    private static final String JSP_MEALS = "meals.jsp";
    private static final String JSP_CREATE_UPDATE = "mealForm.jsp";

    private static final String PARAM_ID = "id";
    private static final String PARAM_ACTION = "action";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_CREATE = "create";
    private static final String ACTION_UPDATE = "update";

    private static final String ATTR_MEALS = "meals";
    private static final String ATTR_ONE_MEAL = "meal";

    private static final String POST_PARAM_ID = "id";
    private static final String POST_PARAM_DATETIME = "dateTime";
    private static final String POST_PARAM_DESCRIPTION = "description";
    private static final String POST_PARAM_CALORIES = "calories";

    private static final Meal NEW_MEAL = new Meal(LocalDateTime.now(), "New Meal", 150);

    private final MealDao dao = new MealDaoImpl();

    @Override
    public void init() throws ServletException {
        dao.addAll(MealsUtil.MEALS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter(PARAM_ACTION);
        Meal meal = null;
        switch (action != null ? action : "") {
            case ACTION_UPDATE:
                if (request.getParameter(PARAM_ID) != null) {
                    meal = dao.get(Integer.parseInt(request.getParameter(PARAM_ID)));
                }
            case ACTION_CREATE:
                request.setAttribute(ATTR_ONE_MEAL, ((meal != null) ? meal : NEW_MEAL));
                forward = JSP_CREATE_UPDATE;
                break;
            case ACTION_DELETE:
                if (request.getParameter(PARAM_ID) != null) {
                    dao.delete(Integer.parseInt(request.getParameter(PARAM_ID)));
                }
            default:
                request.setAttribute(ATTR_MEALS, getMealsTo());
                forward = JSP_MEALS;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = getMealFromRequest(request);
        if (meal.getId() == null) {
            dao.add(meal);
        } else {
            dao.update(meal.getId(), meal);
        }
        request.setAttribute(ATTR_MEALS, getMealsTo());
        request.getRequestDispatcher(JSP_MEALS).forward(request, response);
    }

    private Meal getMealFromRequest(HttpServletRequest request) {
        String id = request.getParameter(POST_PARAM_ID);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter(POST_PARAM_DATETIME));
        String description = request.getParameter(POST_PARAM_DESCRIPTION);
        int calories = Integer.parseInt(request.getParameter(POST_PARAM_CALORIES));
        if ("".equals(id)) {
            return new Meal(dateTime, description, calories);
        } else {
            return new Meal(Integer.parseInt(id), dateTime, description, calories);
        }
    }

    private List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }
}
