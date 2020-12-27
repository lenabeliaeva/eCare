<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Админка</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<form:form>
    <button class="btn btn-default" formmethod="get" formaction="/tariffs">Просмотр тарифов</button>
<%--    <button class="btn btn-default" formaction="/clients">Просмотр пользователей</button>--%>
    <button class="btn btn-default" formmethod="get" formaction="/options">Просмотр опций</button>
<%--    <button class="btn btn-default" formaction="/createContract">Заключить контракт</button>--%>
</form:form>
</body>
</html>
