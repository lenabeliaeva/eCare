<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 12.02.2021
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Cart</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<h2>Your cart</h2>
<form:form method="post">
    <c:forEach var="item" items="${sessionScope.get(\"cart\").cartItems}">
        <c:if test="${item.tariff != null}">
            <h3>"${item.tariff.name}" options</h3>
            <table class="table table-hover">
                <tr>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Connection Cost</td>
                </tr>
                <c:forEach var="option" items="${item.tariff.options}">
                    <tr>
                        <td>${option.name}</td>
                        <td>${option.price}</td>
                        <td>${option.connectionCost}</td>
                    </tr>
                </c:forEach>
            </table>
            <h3>Tariff price: ${item.price}</h3>
        </c:if>
        <br>
        <c:if test="${item.options.size() > 0}">
            <h3>Additional options</h3>
            <table class="table table-hover">
                <tr>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Connection Cost</td>
                </tr>
                <c:forEach var="option" items="${item.options}">
                    <tr>
                        <td>${option.name}</td>
                        <td>${option.price}</td>
                        <td>${option.connectionCost}</td>
                        <td>
                            <button class="btn btn-outline-danger"
                                    onclick="return confirm('Are you sure you want to delete the option from the cart?')"
                                    formaction="/cart/deleteOption/${option.id}/${item.contract.id}"
                                    type="submit"
                            >Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <h3>Connection cost: ${item.connectionCost}</h3>
        </c:if>
        <button class="btn btn-outline-danger"
                onclick="return confirm('Are you sure you want to delete the item from the cart?')"
                formaction="/cart/deleteItem/${item.contract.id}"
                type="submit"
        >Delete
        </button>
    </c:forEach>
    <c:if test="${sessionScope.get(\"cart\").cartItems.size() > 0}">
        <br>
        <div>
            <input class="btn btn-outline-primary" type="submit" value="Submit"/>
        </div>
    </c:if>
</form:form>
</body>
</html>
