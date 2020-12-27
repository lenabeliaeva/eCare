<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Добавить новую опцию</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<h2>Новая опция</h2>
<form:form method="post" modelAttribute="newOption" action="/saveOption">
    <div class="form-group">
        <form:label path="name">Введите название</form:label>
        <form:input type="text" required="name" class="form-control" path="name"/>
    </div>
    <div class="form-group">
        <form:label path="price">Введите цену</form:label>
        <form:input type="number" required="price" min="1" class="form-control" path="price"/>
    </div>
    <div class="form-group">
        <form:label path="connectionCost">Введите цену за подключение</form:label>
        <form:input type="number" required="cost" min="1" class="form-control" path="connectionCost"/>
    </div>
    <button type="submit" class="btn btn-default">Сохранить</button>
</form:form>
</body>
</html>
