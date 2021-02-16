<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <%@include file="parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/errorStyle.css">
</head>
<body>
<form:form method="post" modelAttribute="client" action="/registration">
    <div class="form-group">
        <form:label path="name">Name</form:label>
        <form:input type="text" required="name" class="form-control" path="name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="lastName">Last Name</form:label>
        <form:input type="text" required="lastName" class="form-control" path="lastName"/>
        <form:errors path="lastName" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Birth Date</form:label>
        <form:input type="date" required="date" class="form-control" path="birthDate"/>
        <form:errors path="birthDate" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Passport</form:label>
        <form:input required="passport" class="form-control" path="passport"/>
        <form:errors path="passport" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Address</form:label>
        <form:input required="address" class="form-control" path="address"/>
        <form:errors path="address" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Email</form:label>
        <form:input type="text" required="email" class="form-control" path="email"/>
        <form:errors path="email" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Password</form:label>
        <form:input type="password" required="password" class="form-control" path="password"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                    title="Password must contain at least one digit, one lowercase letter, one uppercase one and length must be not less than 8 symbols"/>
        <form:errors path="password" cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="name">Confirm password</form:label>
        <form:input type="password" required="confirm" class="form-control" path="passwordConfirm"/>
        <form:errors path="passwordConfirm" cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-outline-primary">Sign Up</button>
</form:form>
</body>
</html>
