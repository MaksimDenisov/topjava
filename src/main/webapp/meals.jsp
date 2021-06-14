<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/functions.tld" prefix="f" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .isExcess {
            color: red;
        }

        .normal {
            color: green;
        }

        table {
            border-collapse: collapse;
        }

        table td {
            border: 1px solid black;
            border-inline-color: black;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<table style="border: 1px solid black;">
    <thead>
    <tr>
        <td>DateTime</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Update</td>
        <td>Delete</td>
    </tr>
    </thead>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${(meal.excess)?'isExcess':'normal'}">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>