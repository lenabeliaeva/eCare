<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tariffs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h2>Tariffs</h2>
<form:form method="post">
    <table class="table table-hover">
        <tr>
            <td>Name</td>
            <td>Price</td>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <button class="btn btn-outline-danger"
                            onclick="return confirm('Are you sure you want to delete the tariff?')"
                            formaction="/admin/tariffs/delete/${tariff.id}"
                            type="submit"
                    >Delete
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary"
                            formmethod="get"
                            formaction="/admin/tariffs/edit/${tariff.id}"
                            type="submit">
                        Edit
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formaction="/admin/showOptions/${tariff.id}" type="submit"
                            formmethod="get">Tariff options
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
        <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/addNewTariff">Add new tariff</button>
</form:form>
</body>
</html>
