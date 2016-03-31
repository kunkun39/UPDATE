<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>IP Update Cloud Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/public.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/main.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/ProductDWRHandler.js" type="text/javascript"></script>
</head>
<body>
<div class="action">
    &nbsp;
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top">
            <div style="float: left; padding-right: 5px;">
                <a href="${pageContext.request.contextPath}/backend/productform.html?name=${name}&current=${current}&categoryId=${categoryId}"><button class="thoughtbot">Add Product</button></a>
            </div>
            <form action="${pageContext.request.contextPath}/backend/productoverview.html" class="search_form" method="POST">
                <div class="search">
                    <span><label>Name:</label><input type="text" name="name" class="text" value="${paging.name}"/></span>
                    <input type="button" value="Search" onclick="this.form.submit();"/>
                </div>
            </form>

            <div>
                <table width="100%" cellpadding="0" cellspacing="0" class="list">
                    <thead>
                    <td width="15%">&nbsp;&nbsp;Product Name</td>
                    <td width="15%">Model</td>
                    <td width="40%">Description</td>
                    <td>Actions</td>
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
                                <a href="${pageContext.request.contextPath}/backend/productform.html?productId=${product.id}&name=${name}&current=${current}&categoryId=${categoryId}"><button class="thoughtbot">Edit</button></a>
                                <a href="#" onclick="return productDeleteConfirm('${product.id}', '${name}', '${current}', '${categoryId}');"><button class="thoughtbot">Delete</button></a>
                                <a href="${pageContext.request.contextPath}/backend/productupdatehistory.html?productId=${product.id}" target="_blank"><button class="thoughtbot">Update Setting</button></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/productoverview.html" showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function productDeleteConfirm(productId, productName, current, categoryId) {
        ProductDWRHandler.isProductContainUpdates(productId, function(result) {
            var deleteProduct = false;
            if(result) {
                if(confirm('This product already config update setting, are you sure you want to delete this product?')) {
                    deleteProduct = true;
                }
            } else {
                if(confirm('Are you sure you want to delete this product?')) {
                    deleteProduct = true;
                }
            }
            if(deleteProduct) {
                window.location.href = '${pageContext.request.contextPath}/backend/productdelete.html?productId=' + productId + '&name=' + productName + '&current=' + current + '&categoryId=' + categoryId;
            }
        });
    }
</script>

</body>
</html>