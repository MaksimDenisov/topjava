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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MealServlet extends HttpServlet {

    private final int caloriesPerDay = 2000;

    private final MealDao dao = new MealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            dao.delete(Integer.valueOf(id));
            response.sendRedirect("meals");
        } else if ("update".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            request.setAttribute("meal", dao.getOne(Integer.valueOf(id)));
            request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
        } else if ("create".equalsIgnoreCase(action)) {
            request.setAttribute("meal",
                    new Meal(0, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 100));
            request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
        } else {
            List<MealTo> meals = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_DATE_TIME);
        int calories = Integer.parseInt(request.getParameter("calories"));
        dao.save(new Meal(id, dateTime, description, calories));
        response.sendRedirect("meals");
    }
}
