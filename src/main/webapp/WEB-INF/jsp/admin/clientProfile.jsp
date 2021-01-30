<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Client contract</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <%@include file="../parts/header.jsp" %>
</head>
<body>
<c:if test="${clientContracts.size() > 0}">
    <form method="get">
        <table class="table table-hover">
            <tr>
                <td>Number</td>
                <td>Tariff</td>
                <td>Tariff Price</td>
            </tr>
            <c:forEach var="contract" items="${clientContracts}">
                <tr>
                    <td>${contract.number}</td>
                    <td>${contract.tariff.name}</td>
                    <td>${contract.tariffPrice}</td>
                    <td>
                        <button class="btn btn-outline-primary"
                                formaction="/admin/showContractOptions/${contract.id}"
                                type="submit"
                                formmethod="get">Connected options
                        </button>
                    </td>
                    <td>
                        <c:if test="${contract.blockedByClient == false && contract.blockedByAdmin == false}">
                            <button class="btn btn-outline-danger"
                                    onclick="return confirm('Вы уверены, что хотите заблокировать контракт?')"
                                    formaction="/admin/blockContract/${contract.id}"
                                    type="submit">Block contract
                            </button>
                        </c:if>
                        <c:if test="${contract.blockedByClient == true || contract.blockedByAdmin == true}">
                            <button class="btn btn-outline-primary"
                                    formaction="/admin/unblockContract/${contract.id}"
                                    type="submit">Unblock contract
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</c:if>
<c:if test="${clientContracts.size() == 0}">
    <p>The client hasn't got any contracts</p>
</c:if>
<form action="/admin/signContract/${client.id}">
    <button type="submit" class="btn btn-outline-primary">Sign new contract</button>
</form>
</body>
</html>
