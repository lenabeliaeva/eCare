<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Админка</title>
    <%@include file="parts/header.jsp"%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<form:form>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/tariffs">Просмотр тарифов</button>
    <button class="btn btn-default-primary" formaction="/clients">Просмотр пользователей</button>
    <button class="btn btn-outline-primary" formmethod="get" formaction="/options">Просмотр опций</button>
<%--    <button class="btn btn-default" formaction="/createContract">Заключить контракт</button>--%>
</form:form>
</body>
</html>
