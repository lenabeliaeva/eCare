<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create new tariff</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/errorStyle.css">
</head>
<body>
<h2>New tariff</h2>
<form:form method="post" modelAttribute="newTariff" action="/admin/tariffs">
    <div class="form-group">
        <form:input type="text" required="name" class="form-control" path="name" placeholder="Name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <br>
    <button type="submit" class="btn btn-outline-primary">Go to options</button>
</form:form>
</body>
</html>
