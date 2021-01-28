<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 25.01.2021
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Подключенные опции</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<form:form method="post">
    <c:if test="${options.size() > 0}">
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
                        <c:if test="${contract.blockedByClient == false && contract.blockedByAdmin == false}">
                            <button class="btn btn-outline-danger"
                                    onclick="return confirm('Вы уверены, что хотите отключить опцию?')"
                                    formaction="/profile/disconnectOption/${contract.id}/${option.id}"
                                    type="submit"
                            >Отключить
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${contract.blockedByAdmin == false && contract.blockedByClient == false}">
        <button class="btn btn-outline-primary" formmethod="get" formaction="/profile/connectOptions/${contract.id}">
            Подключить
            новые опции
        </button>
        <button class="btn btn-outline-primary" formaction="/profile/connectTariff/${contract.id}">Сменить тариф
        </button>
    </c:if>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/profile">
        Вернуться к списку
        контрактов
    </button>
</form:form>
</body>
</html>
