<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="/">eCare</a>
<%--    <div class="collapse navbar-collapse" id="navbarNav">--%>
<%--        <ul class="navbar-nav">--%>
<%--            <c:if test="${role == 'ROLE_USER'}">--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link" href="/profile">Вернуться на главную <span--%>
<%--                            class="sr-only">(current)</span></a>--%>
<%--                </li>--%>
<%--            </c:if>--%>
<%--            <c:if test="${role == 'ROLE_ADMIN'}">--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link" href="/admin">Вернуться на главную <span--%>
<%--                            class="sr-only">(current)</span></a>--%>
<%--                </li>--%>
<%--            </c:if>--%>
<%--        </ul>--%>
<%--    </div>--%>
    <a class="navbar-brand" href="/logout">Выйти</a>
</nav>