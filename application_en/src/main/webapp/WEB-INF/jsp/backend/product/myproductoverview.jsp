<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>IP Update Cloud Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/public.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/main.css" type="text/css"/>
</head>
<body>
<div class="action">
    &nbsp;
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top">
            <form action="${pageContext.request.contextPath}/backend/myresponsibleproduct.html" class="search_form" method="POST">
                <div class="search">
                    <span><label>Name:</label><input type="text" name="name" class="text" value="${paging.name}"/></span>
                    <input type="button" value="Search" onclick="this.form.submit();"/>
                </div>
            </form>

            <table width="100%" cellpadding="0" cellspacing="0" class="list">
                <thead>
                <td width="20%">&nbsp;&nbsp;Product Name</td>
                <td width="20%">Product Model</td>
                <td width="40%">Description</td>
                <td>Action</td>
                </thead>
                <tbody>
                <c:set var="turns" value="true"/>
                <c:forEach items="${products}" var="product">
                    <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                    <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                    <c:set var="turns" value="${!turns}"/>
                        <td>&nbsp;&nbsp;${product.productName}</td>
                        <td>${product.productModel}</td>
                        <td>${product.productDescription} </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/backend/productupdatehistory.html?productId=${product.id}" target="_blank"><button class="thoughtbot">Update Settings</button></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/myresponsibleproduct.html" showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>
</body>
</html>