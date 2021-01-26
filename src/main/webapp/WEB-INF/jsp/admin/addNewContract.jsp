<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Заключить контракт</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<h3>Номер телефона ${newContract.number}</h3>
<form:form id="myForm" modelAttribute="newContract" action="/admin/signContract/save">
    <table class="table table-hover">
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/connectTariff/${newContract.id}/${tariff.id}"
                            type="submit"
                    >Выбрать
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</form:form>
</body>
</html>