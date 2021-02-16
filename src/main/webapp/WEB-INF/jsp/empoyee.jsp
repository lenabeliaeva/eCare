<%--
  Created by IntelliJ IDEA.
  User: lenab
  Date: 09.02.2021
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Manager page</title>
    <%@include file="parts/header.jsp" %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<script src="${pageContext.request.contextPath}/res/js/showData.js" type="text/javascript"></script>
<div class="btn-group">
    <input onclick="showAllTariffs()" class="btn btn-outline-primary" id="showTariffs" type="submit"
           value="Tariffs"/>
</div>
<div class="btn-group">
    <input onclick="showAllClients()" class="btn btn-outline-primary" id="showClients" type="submit"
           value="Clients"/>
</div>
<div class="btn-group">
    <input onclick="showAllOptions()" class="btn btn-outline-primary" id="showOptions" type="submit"
           value="Options"/>
</div>
<div id="source"></div>
<div id="dialog"></div>
</body>
</html>

