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
            <div style="float: left; padding-right: 5px; padding-top: 5px; padding-left: 1px;">
                <a href="#" onclick="window.location.href='${pageContext.request.contextPath}/backend/productupdatehistoryform.html?productId=${productId}'"><button class="thoughtbot">Add Update</button></a>
            </div>
            <form action="${pageContext.request.contextPath}/backend/productupdatehistory.html" class="search_form" method="POST">
                <div class="search">
                    <span><label>Product Category:</label><input type="text" name="name" class="text" value="${product.productCategoryName}" style="width: 180px;"/></span>
                    <span><label>Product Name and Model:</label><input type="text" name="name" class="text" value="${product.productName}：${product.productModel}" style="width: 180px;"/></span>
                    <span><label>Update Type:</label>
                        <select id="updateWayFilter" name="updateWayFilter" style="width:100px; height:23px;">
                            <option value="ALL" <c:if test="${updateWayFilter == 'ALL'}"> selected="true"</c:if>>All</option>
                            <c:forEach items="${updateWays}" var="way">
                                <option value="${way.value}" <c:if test="${updateWayFilter == way.value}"> selected="true"</c:if>>${way.value} - ${way.label}</option>
                            </c:forEach>
                        </select>
                    </span>
                    <span><label>Version:</label>
                        <input type="text" name="versionFilter" class="text" value="${versionFilter}" style="width: 100px;"/>
                    </span>
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <input type="button" value="Search" onclick="this.form.submit();"/>
                </div>
            </form>

            <table width="100%" cellpadding="0" cellspacing="0" class="list">
                <thead>
                <td width="8%">&nbsp;&nbsp;Create Time</td>
                <td width="10%">Update Type</td>
                <td width="5%">Version</td>
                <td width="28%">Json Configuration</td>
                <td width="28%">Update File Configuration</td>
                <td>Actions</td>
                </thead>
                <tbody>
                <c:set var="turns" value="true"/>
                <c:forEach items="${updates}" var="update">
                    <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                    <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                    <c:set var="turns" value="${!turns}"/>
                        <td>&nbsp;&nbsp;<ch:timeformat value="${update.uploadTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>${update.updateWay} </td>
                        <td>${update.updateVersion} </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/web/upload${update.jsonPath}" target="_blank">
                                ${update.jsonPath}
                            </a>
                        </td>
                        <td>
                            <c:if test="${update.updateUploadFileId > 0}">
                                <a href="${pageContext.request.contextPath}/web/upload${update.dataPath}" target="_blank">
                                    ${update.dataPath}
                                </a>
                            </c:if>
                        </td>
                        <td>
                            <a href="#" onclick="window.location.href='${pageContext.request.contextPath}/backend/productupdatehistoryform.html?productId=${product.id}&productUpdateId=${update.id}&updateWayFilter=${updateWayFilter}&versionFilter=${versionFilter}'"><button class="thoughtbot">Edit</button></a>
                            <a href="${pageContext.request.contextPath}/backend/productupdatehistorydelete.html?productId=${product.id}&productUpdateId=${update.id}&updateWayFilter=${updateWayFilter}&versionFilter=${versionFilter}" onclick="return updateDeleteConfirm();"><button class="thoughtbot">Delete</button></a>
                            <%--<c:if test="${update.updateUploadFileId > 0}">--%>
                                <%--<a href="${pageContext.request.contextPath}/backend/productupdatefiledownload.html?productUpdateId=${update.id}&updateUploadFileId=${update.updateUploadFileId}" class="btns"><span>下载</span></a>--%>
                            <%--</c:if>--%>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/productupdatehistory.html" showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function updateDeleteConfirm() {
        return confirm('Are you sure you want to delete this product setting?');
    }
</script>

</body>
</html>