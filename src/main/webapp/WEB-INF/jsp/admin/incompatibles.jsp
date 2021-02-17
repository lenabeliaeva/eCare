<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 17.02.2021
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${first.name}</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<c:if test="${incompatible.size() >0}">
    <h3>Incompatible options</h3>
    <form:form>
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Price</td>
                <td>Connection Cost</td>
            </tr>
            <c:forEach var="option" items="${incompatible}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td>
                        <button class="btn btn-outline-primary"
                                formaction="/admin/options/deleteIncompatible/${first.id}/${option.id}" type="submit">
                            Make
                            compatible
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</c:if>
<c:if test="${compatible.size() > 0}">
    <h3>You can add to incompatible these options</h3>
    <form:form>
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Price</td>
                <td>Connection Cost</td>
            </tr>
            <c:forEach var="option" items="${compatible}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td>
                        <button class="btn btn-outline-danger"
                                onclick="return confirm('Are you sure you want to make the option incompatible?')"
                                formaction="/admin/options/incompatible/${first.id}/${option.id}" type="submit">
                            Make
                            incompatible
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</c:if>
</body>
</html>
