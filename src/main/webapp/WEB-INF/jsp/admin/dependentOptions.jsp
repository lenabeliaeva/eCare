<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 18.02.2021
  Time: 18:37
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
<%@include file="../parts/alertMessage.jsp"%>
<c:if test="${dependent.size() > 0}">
    <h3>${first.name} depends on these options</h3>
    <form:form>
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Price</td>
                <td>Connection Cost</td>
            </tr>
            <c:forEach items="${dependent}" var="option">
                <tr>
                    <th scope="col">
                            ${option.name}
                    </th>
                    <th scope="col">
                            ${option.price}
                    </th>
                    <th scope="col">
                            ${option.connectionCost}
                    </th>
                    <th scope="col">
                        <button class="btn btn-outline-danger"
                                formaction="/admin/options/deleteDependent/${first.id}/${option.id}" type="submit">
                            Make
                            independent
                        </button>
                    </th>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</c:if>
<c:if test="${independent.size() > 0}">
    <h3>You can make ${first.name} dependent from these options</h3>
    <form:form>
        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td>Price</td>
                <td>Connection Cost</td>
            </tr>
            <c:forEach items="${independent}" var="option">
                <tr>
                    <th scope="col">
                        ${option.name}
                    </th>
                    <th scope="col">
                            ${option.price}
                    </th>
                    <th scope="col">
                            ${option.connectionCost}
                    </th>
                    <th scope="col">
                        <button class="btn btn-outline-primary"
                                formaction="/admin/options/dependent/${first.id}/${option.id}" type="submit">
                            Make dependent
                        </button>
                    </th>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</c:if>
</body>
</html>
