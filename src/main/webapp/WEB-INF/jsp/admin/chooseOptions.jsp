<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Options</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h3>Available for "${tariff.name}" options</h3>
<form:form>
    <table class="table table-hover">
        <tr>
            <td>Name</td>
            <td>Price</td>
            <td>Connection cost</td>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/tariffs/options/${tariff.id}/${option.id}" type="submit">Add
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h3>Tariff price: ${tariff.price}</h3>
    <c:if test="${selectedOptions.size() > 0}">
        <h3>Selected options</h3>
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Price</td>
                <td>Connection cost</td>
            </tr>
            <c:forEach var="option" items="${selectedOptions}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <button class="btn btn-outline-primary" type="submit" formmethod="get" formaction="/admin/tariffs">Submit and go to
        tariff list
    </button>
</form:form>
</body>
</html>
