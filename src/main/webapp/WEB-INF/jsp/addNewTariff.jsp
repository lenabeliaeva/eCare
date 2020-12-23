<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Добавить новый тариф</title>
</head>
<body>
<h2>Новый тариф</h2>
<form:form method="post" modelAttribute="newTariff" action="/saveTariff">
    <form:label path="name">Введите название</form:label>
    <form:input type="text" path="name"/>
    <form:label path="price">Введите цену</form:label>
    <form:input type="number" path="price"/>
    <br><br>
    <button type="submit">Сохранить</button>
</form:form>
</body>
</html>
