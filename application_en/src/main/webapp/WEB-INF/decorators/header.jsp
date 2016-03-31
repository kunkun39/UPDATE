<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<div class="header">
    <h1 class="logo"><a title="ST" href="${pageContext.request.contextPath}/backend/dashboard.html" onfocus="blur()"></a></h1>

    <div class="title">
    </div>

    <c:set value="${pageContext.request.requestURI}" var="pageUrl"/>
    <div class="account">
        <a href="#">
            <img src="${pageContext.request.contextPath}/images/clients.gif" alt=""/><ch:user/>
        </a>
        |
        <a href="${pageContext.request.contextPath}/backend/userchangepassword.html" style="margin-left: 0">
            <img src="${pageContext.request.contextPath}/images/password.gif" alt="Change Password"/> Change Password
        </a>
        |
        <a href="${pageContext.request.contextPath}/j_spring_security_logout" style="margin-left: 0">
            <img src="${pageContext.request.contextPath}/images/logout.gif" alt="Logout"/> Quit
        </a>
    </div>

    <div class="menu">
        <a href="${pageContext.request.contextPath}/backend/dashboard.html" <c:if test="${'DASHBOARD' eq BROSWER_LOCATION}">style="font-weight:bold;background:#5d5c5c;"</c:if>>Dashboard</a>

        <security:authorize ifAnyGranted="ROLE_SYSTEM">
            <a href="${pageContext.request.contextPath}/backend/productmanagement.html" <c:if test="${'PRODUCT' eq BROSWER_LOCATION}">style="font-weight:bold;background:#5d5c5c;"</c:if>>Product Management</a>
        </security:authorize>

        <security:authorize ifAnyGranted="ROLE_DEVICE">
            <a href="${pageContext.request.contextPath}/backend/myresponsibleproduct.html" <c:if test="${'MY' eq BROSWER_LOCATION}">style="font-weight:bold;background:#5d5c5c;"</c:if>>Update Management</a>
        </security:authorize>

        <a href="${pageContext.request.contextPath}/backend/systemclientupdatereport.html" <c:if test="${'STA' eq BROSWER_LOCATION}">style="font-weight:bold;background:#5d5c5c;"</c:if>>Statistic</a>

        <security:authorize ifAnyGranted="ROLE_SYSTEM">
            <a href="${pageContext.request.contextPath}/backend/useroverview.html" <c:if test="${'USER' eq BROSWER_LOCATION}">style="font-weight:bold;background:#5d5c5c;"</c:if>>System Setting</a>
        </security:authorize>
    </div>

    <div class="toolbar">
        <img src="${pageContext.request.contextPath}/theme/default/images/left.gif"/> &nbsp;
    </div>

</div>
