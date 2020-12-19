
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Добавить новый тариф</title>
</head>
<body>
<h2>Новый тариф</h2>
<form:form method="post" action="/tariffs">
    <table>
        <tr>
            <td><form:label path="name">Название</form:label></td>
        </tr>
        <tr>
            <td><form:label path="price">Цена</form:label></td>
        </tr>
        <tr>
            <td><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
