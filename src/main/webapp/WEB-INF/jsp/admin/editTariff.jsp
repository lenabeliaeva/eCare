<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit tariff</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<h2>Enter new data</h2>
<form:form method="post" modelAttribute="editedTariff" action="/admin/saveEditedTariff">
    <form:input type="hidden" path="id"/>
    <div class="form-group">
        <form:input type="text" required="name" class="form-control" path="name" placeholder="Name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-outline-primary">Save</button>
</form:form>
</body>
</html>
