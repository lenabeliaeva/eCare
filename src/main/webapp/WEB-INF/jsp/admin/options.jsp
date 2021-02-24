<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Options</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<h2>Options</h2>
<%@include file="../parts/alertMessage.jsp"%>
<form:form>
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
                <td>
                    <button class="btn btn-outline-danger"
                            onclick="return confirm('Are you sure you want to delete the option?')"
                            formaction="/admin/deleteOption/${option.id}" type="submit">Delete
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/editOption/${option.id}"
                            type="submit">Edit
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formmethod="get"
                            formaction="/admin/options/incompatible/${option.id}">
                        Incompatible options
                    </button>
                </td>
                <td>
                    <button class="btn btn-outline-primary" formmethod="get"
                            formaction="/admin/options/dependent/${option.id}">
                        Dependent options
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/createOption">Add new option</button>
</form:form>
</body>
</html>
