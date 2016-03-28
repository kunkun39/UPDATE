<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>IP广义升级云端</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/jquery-ui-1.8.22.custom.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/public.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/main.css" type="text/css"/>
</head>
<body>
<div class="action">
    &nbsp;
</div>
可以升级MAC列表
<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top">
            <div>
                <table width="100%" cellpadding="0" cellspacing="0" class="list">
                    <thead>
                        <td width="25%">SN</td>
                        <td width="25%">SN</td>
                        <td width="25%">SN</td>
                        <td width="25%">SN</td>
                    </thead>
                    <tbody>
                    <c:forEach items="${snLists}" var="sn" varStatus="counter">
                        <c:set var="another" value="${counter.count % 4}"/>
                        <c:if test="${another == 1}">
                            <tr>
                        </c:if>
                        <td>&nbsp;&nbsp;${sn}</td>
                        <c:if test="${another == 0}">
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
</table>

</body>
</html>