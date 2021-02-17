<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Tariff change</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h2>Available tariffs</h2>
<form:form method="post">
    <div class="row">
        <c:forEach var="tariff" items="${tariffs}">
            <div class="col">
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <h5 class="card-title">${tariff.name}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">${tariff.price}</h6>
                        <ul>
                            <c:forEach var="option" items="${tariff.options}">
                                <li>
                                        ${option.name}
                                    <br>
                                    Price ${option.price}
                                    <br>
                                    Connection cost ${option.connectionCost}
                                </li>
                            </c:forEach>
                        </ul>
                        <button class="btn btn-outline-primary" type="submit"
                                formaction="/cart/connectTariff/${tariff.id}/${contract.id}">Add to the cart
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</form:form>
</body>
</html>