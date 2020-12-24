<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тарифы</title>
</head>
<body>
<h2>Доступные тарифы</h2>
<table>
    <tr>
        <td>Название</td>
        <td>Цена</td>
    </tr>
    <c:forEach var="tariff" items="${tariffs}">
        <tr>
            <td>${tariff.name}</td>
            <td>${tariff.price}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
