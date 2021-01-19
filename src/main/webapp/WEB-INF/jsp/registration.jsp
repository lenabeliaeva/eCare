<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Регистрация</title>
    <%@include file="parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<form:form method="post" modelAttribute="client" action="/registration">
    <div class="form-group">
        <form:label path="name">Введите имя</form:label>
        <form:input type="text" required="name" class="form-control" path="name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="lastName">Введите фамилию</form:label>
        <form:input type="text" required="lastName" class="form-control" path="lastName"/>
        <form:errors path="lastName" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Введите дату рождения</form:label>
        <form:input type="date" required="date" class="form-control" path="birthDate"/>
        <form:errors path="birthDate" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Введите серию и номер паспорта</form:label>
        <form:input required="passport" class="form-control" path="passport"/>
        <form:errors path="passport" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Введите адрес</form:label>
        <form:input required="address" class="form-control" path="address"/>
        <form:errors path="address" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Введите адрес электронной почты</form:label>
        <form:input type="text" required="email" class="form-control" path="email"/>
        <form:errors path="email" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Введите пароль</form:label>
        <form:input type="password" required="password" class="form-control" path="password"/>
        <form:errors path="password" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Подтвердите пароль</form:label>
        <form:input type="password" required="confirm" class="form-control" path="passwordConfirm"/>
        <form:errors path="passwordConfirm" cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-outline-primary">Зарегистрироваться</button>
</form:form>
</body>
</html>
