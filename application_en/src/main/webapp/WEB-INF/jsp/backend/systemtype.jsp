<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<div class="type">
    <div class="users">
        <a href="${pageContext.request.contextPath}/backend/useroverview.html">User Management</a>
    </div>

    <div class="logs">
        <a href="${pageContext.request.contextPath}/backend/systemactionlogoverview.html">System Log</a>
    </div>
</div>