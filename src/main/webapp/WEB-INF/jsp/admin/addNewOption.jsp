<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Create new option</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
<h2>New option</h2>
<form:form method="post" modelAttribute="newOption" action="/saveOption">
    <div class="form-group">
        <form:input type="text" required="name" class="form-control" path="name" placeholder="Name"/>
        <form:errors cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="price">Price</form:label>
        <form:input type="number" required="price" min="1" class="form-control" path="price"/>
        <form:errors cssClass="error"/>
    </div>
    <div class="form-group">
        <form:label path="price">Connection Cost</form:label>
        <form:input type="number" required="cost" min="1" class="form-control" path="connectionCost"
                    placeholder="Connection Cost"/>
        <form:errors cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-outline-primary">Save</button>
</form:form>
</body>
</html>
