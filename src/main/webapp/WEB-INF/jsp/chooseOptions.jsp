<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Опции</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="parts/header.jsp" %>
</head>
<body>
<h2>Доступные для подключения опции для тарифа "${addOptionTariff.name}"</h2>
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
                <td>
                    <button class="btn btn-outline-primary"
                            formaction="/addOption/${addOptionTariff.id}/${option.id}" type="submit">Добавить
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h3>Цена тарифа: ${addOptionTariff.price}</h3>
    <button class="btn btn-outline-primary" type="submit" formmethod="get" formaction="/tariffs">Завершить и перейти к
        списку тарифов
    </button>
</form:form>
</body>
</html>
