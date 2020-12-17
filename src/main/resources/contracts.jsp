<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        Контракт
    </title>
</head>
<body>
<table>
    <tr>
        <th>ID</th>
        <th>NUMBER</th>
        <th>TARIFF_ID</th>
    </tr>
    <c:forEach var="contract" items="${contracts}">
    <tr>
        <td>${contract.id}</td>
        <td>${contract.number}</td>
        <td>${contract.tariff}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>