<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Clients</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <form class="form-inline" method="get" action="/admin/searchClient">
        <label>
            <input type="text" name="number" class="form-control" placeholder="Enter client's number">
        </label>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>
</div>
<c:if test="${clients.size() == 0}">
    <h3>Clients are not found</h3>
    <form action="/admin">
        <button type="submit" class="btn btn-outline-primary">Back to the main page</button>
    </form>
</c:if>
<c:if test="${clients.size() > 0}">
    <form:form method="get">
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Last Name</td>
                <td>Birth Date</td>
                <td>Passport</td>
                <td>Address</td>
                <td>Email</td>
            </tr>
            <c:forEach var="client" items="${clients}">
                <tr>
                    <td>${client.name}</td>
                    <td>${client.lastName}</td>
                    <td>${client.birthDate}</td>
                    <td>${client.passport}</td>
                    <td>${client.address}</td>
                    <td>${client.email}</td>
                    <td>
                        <button class="btn btn-outline-primary" formaction="/admin/clientProfile/${client.id}"
                                type="submit">
                            Contracts
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</c:if>
</body>
</html>
