<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manager page</title>
    <%@include file="parts/header.jsp"%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<form:form>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/tariffs">Tariffs</button>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/clients">Clients</button>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/admin/options">Options</button>
</form:form>
</body>
</html>
