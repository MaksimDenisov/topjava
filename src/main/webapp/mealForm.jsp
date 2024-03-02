<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<h2><c:out value="${meal.id == 0 ? 'Add meal' : 'Update meal'}"/></h2>
<form method="POST" action='meals' name="AddMeal">
    <input type="hidden" name="id" value="${meal.id}"/>

    Description : <input type="text" name="description"
                         value="<c:out value="${meal.description}" />"/> <br/>
    Date Time : <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.dateTime}" />"/> <br/>
    Calories : <input
        type="number" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input
            type="submit" value="Submit"/>
</form>
</body>
</html>