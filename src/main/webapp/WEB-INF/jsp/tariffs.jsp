<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Тарифы</title>
</head>
<body>
<h2>Доступные тарифы</h2>
<form:form method="post">
<table>
    <tr>
        <td>Название</td>
        <td>Цена</td>
    </tr>
    <c:forEach var="tariff" items="${tariffs}">
        <tr>
            <td>${tariff.name}</td>
            <td>${tariff.price}</td>
            <td>
                <button formaction="/deleteTariff/${tariff.id}" type="submit">Удалить</button>
            </td>
            <td>
                <button>Опции по данному тарифу</button>
            </td>
        </tr>
    </c:forEach>
</table>
    <button formaction="/addNewTariff">Добавить новый тариф</button>
</form:form>
</body>
</html>
