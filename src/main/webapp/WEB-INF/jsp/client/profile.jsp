<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<h2>Hello, ${client.name}!</h2>
<c:if test="${contracts.size() > 0}">
    <h3>Your contracts</h3>
    <form method="get">
        <table class="table table-hover">
            <tr>
                <td>Number</td>
                <td>Tariff</td>
                <td>Price</td>
            </tr>
            <c:forEach var="contract" items="${contracts}">
                <tr>
                    <td>${contract.number}</td>
                    <td>${contract.tariff.name}</td>
                    <td>${contract.tariffPrice}</td>
                    <td>
                        <button class="btn btn-outline-primary"
                                formaction="/profile/contractOptions/${contract.id}"
                                type="submit">Connected options
                        </button>
                    </td>
                    <c:if test="${contract.blockedByAdmin == false}">
                        <td>
                            <c:if test="${contract.blockedByClient == false}">
                                <button class="btn btn-outline-danger"
                                        onclick="return confirm('Вы уверены, что хотите заблокировать контракт?')"
                                        formaction="/profile/blockContract/${contract.id}"
                                        type="submit">Block contract
                                </button>
                            </c:if>
                            <c:if test="${contract.blockedByClient == true}">
                                <button class="btn btn-outline-primary"
                                        formaction="/profile/unblockContract/${contract.id}"
                                        type="submit">Unblock contract
                                </button>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${contract.blockedByAdmin == true}">
                        <td>
                            <h5>The contract is blocked by the operator</h5>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </form>
</c:if>
<c:if test="${contracts.size() == 0}">
    <h3>You haven't got any contracts yet</h3>
</c:if>
</body>
</html>
