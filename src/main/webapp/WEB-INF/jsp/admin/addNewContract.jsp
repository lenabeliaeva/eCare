<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Заключить контракт</title>
    <%@include file="../parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#myForm').submit(function () {
                var form = $(this),
                    url = form.attr('action'),
                    tariffId = form.find('input[name="userId"]').val(),
                    tariff = JSON.stringify({"userId": userId});

                $.ajax({
                    url: url,
                    type: "POST",
                    traditional: true,
                    contentType: "application/json",
                    dataType: "json",
                    data: dat,
                    success: function (response) {
                        alert('success ' + response);
                    },
                    error: function (response) {
                        alert('error ' + response);
                    },
                });

                return false;
            });
        });
    </script>
</head>
<body>
<h3>Номер телефона ${newContract.number}</h3>
<form:form id="myForm" modelAttribute="newContract" action="/admin/signContract/save">
    <table class="table table-hover">
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/connectTariff/${tariff.id}/${newContract.id}"
                            type="submit"
                    >Выбрать
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</form:form>
</body>
</html>