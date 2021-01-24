<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Тарифы</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="parts/header.jsp" %>

</head>
<body>
<h2>Доступные тарифы</h2>
<form:form method="post">
    <table class="table table-hover">
        <tr>
            <td>Название</td>
            <td>Цена</td>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <button class="btn btn-outline-danger" onclick="return confirm('Вы уверены, что хотите удалить тариф?')" formaction="/deleteTariff/${tariff.id}"
                            type="submit"
                    >Удалить
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formaction="/editTariff/${tariff.id}" type="submit">
                        Изменить
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formaction="/showOptions/${tariff.id}" type="submit"
                            formmethod="get">Опции
                        по
                        данному тарифу
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-outline-primary" formaction="/addNewTariff">Добавить новый тариф</button>
</form:form>
</body>
</html>
