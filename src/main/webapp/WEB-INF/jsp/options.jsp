<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Все опции</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<h2>Доступные опции</h2>
<form:form method="post">
    <table class="table table-hover">
        <tr>
            <td>Название</td>
            <td>Цена</td>
            <td>Стоимость подключения</td>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>
                    <button class="btn btn-default" formaction="/deleteOption/${option.id}" type="submit">Удалить
                    </button>
                </td>
                <td>
                    <button class="btn btn-default" formaction="/editOption/${option.id}" type="submit">Изменить
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-default" formaction="/createOption">Добавить новую опцию</button>
</form:form>
</body>
</html>
