<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Опции данного тарифа</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="parts/header.jsp" %>
</head>
<body>
<form:form method="post">
    <table class="table table-hover">
        <tr>
            <td>Название</td>
            <td>Цена</td>
            <td>Стоимость подключения</td>
        </tr>
        <c:forEach var="option" items="${tariffOptions}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>
                    <button class="btn btn-outline-danger" formaction="/deleteOption/${tariffId}/${option.id}"
                            type="submit"
                    >Удалить
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-outline-primary" formaction="/addOption/${tariffId}">Добавить новую опцию</button>
</form:form>
</body>
</html>
