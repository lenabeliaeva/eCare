<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Добавить новый тариф</title>
</head>
<body>
<h2>Новый тариф</h2>
<form:form method="post" action="/saveTariff">
    <h3>Введите название</h3>
    <input type="text">
    <h3>Введите цену</h3>
    <input type="number">
    <br><br>
    <button type="submit">Сохранить</button>
</form:form>
</body>
</html>
