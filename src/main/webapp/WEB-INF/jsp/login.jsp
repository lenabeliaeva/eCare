<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Логин</title>
    <%@include file="parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<form:form action="/login" class="form-signin">
    <div class="form-group">
        <span>${message}</span>
        <form:label path="email">Введите адрес электронной почты</form:label>
        <form:input type="text" required="email" class="form-control" path="email" placeholder="Email" autofocus="true"/>
    </div>
    <div class="form-group">
        <span>${error}</span>
        <form:label path="name">Введите пароль</form:label>
        <form:input type="password" required="password" class="form-control" path="password" placeholder="Пароль"/>
    </div>
    <button type="submit" class="btn btn-outline-primary">Войти</button>
    <h4 class="text-center"><a href="${pageContext.request.contextPath}/registration">Зарегистрироваться</a></h4>
</form:form>
</body>
</html>
