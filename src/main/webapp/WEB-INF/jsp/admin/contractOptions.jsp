<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 25.01.2021
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Подключенные опции</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<form:form>
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
                    <button class="btn btn-outline-danger"
                            onclick="return confirm('Вы уверены, что хотите отключить опцию?')"
                            formaction="/admin/disconnectOption/${contract.id}/${option.id}"
                            type="submit"
                    >Отключить
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${contract.blockedByAdmin == false && contract.blockedByClient == false}">
        <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/connectOptions/${contract.id}">
            Подключить
            новую опцию
        </button>
        <button class="btn btn-outline-primary" formaction="/admin/connectTariff/${contract.id}">
            Сменить тариф пользователю
        </button>
    </c:if>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/clientProfile/${contract.client.id}">
        Вернуться к списку
        контрактов
    </button>
</form:form>
</body>
</html>
