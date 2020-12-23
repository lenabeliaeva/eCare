<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тарифы</title>
</head>
<body>
<h2>Доступные тарифы</h2>
<c:forEach var="tariff" items="${tariffs}">
    <p>${tariff.name}</p>
    <p>${tariff.price}</p>
</c:forEach>
<table>
    <tr>
        <td>Название</td>
        <td>Цена</td>
    </tr>

</table>
</body>
</html>
