<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-dark bg-dark">
    <a class="navbar-brand" href="/">eCare</a>
    <sec:authorize access="hasRole('USER')">
        <a class="navbar-brand" href="/cart">Cart</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <a class="navbar-brand" href="/logout">Log out</a>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
        <a class="navbar-brand" href="/login">Sign in</a>
        <a class="navbar-brand" href="/registration">Sign up</a>
    </sec:authorize>
</nav>