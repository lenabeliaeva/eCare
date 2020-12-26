<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Тарифы</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

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
                    <button class="btn btn-default" formaction="/deleteTariff/${tariff.id}" type="submit">Удалить
                    </button>
                </td>
                <td>
                    <button class="btn btn-default" formaction="/showOptions/${tariff.id}" type="submit">Опции по
                        данному тарифу
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-default" formaction="/addNewTariff">Добавить новый тариф</button>
</form:form>
</body>
</html>
