<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 25.01.2021
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Опции тарифа</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h2>Опции тарифа "${tariff.name}"</h2>
<form:form method="post">
    <table class="table table-hover">
        <tr>
            <td>Название</td>
            <td>Цена</td>
            <td>Стоимость подключения</td>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
            </tr>
        </c:forEach>
    </table>
<%--    <c:if test="${contract.blockedByClient == false && contract.blockedByAdmin == false}">--%>
<%--        <button class="btn btn-outline-primary" formaction="/profile/{contractId}">Сменить тариф</button>--%>
<%--    </c:if>--%>
</form:form>
</body>
</html>
