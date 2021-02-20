<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tariff options</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h2>"${tariff.name}" options</h2>
<table class="table table-hover">
    <tr>
        <td>Name</td>
        <td>Price</td>
        <td>Connection Cost</td>
    </tr>
    <c:forEach var="option" items="${options}">
        <tr>
            <td>${option.name}</td>
            <td>${option.price}</td>
            <td>${option.connectionCost}</td>
        </tr>
    </c:forEach>
</table>
<form:form>
    <button class="btn btn-outline-primary"
            formmethod="get"
            formaction="/admin/tariffs/options">Manage options
    </button>
</form:form>
</body>
</html>
