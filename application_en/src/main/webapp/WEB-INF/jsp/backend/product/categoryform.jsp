<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1 align="left">
		<img alt=""	src="${pageContext.request.contextPath}/theme/default/images/wen.png">
		<c:if test="${category.id > 0}">
            Edit Update Category
        </c:if>
        <c:if test="${category.id <= 0}">
            Add Update Category
        </c:if>
	</h1>
	<hr />
	<form action="${pageContext.request.contextPath}/backend/categoryform.html?method=save" method="POST">
		<input type="hidden" name="id" value="${category.id}"/>
		<input type="hidden" name="parentId" value="${category.parentId}"/>
		<table >
			<tr>
                <td>Categoy Name:</td>
                <td>
                    <input type="text" id="name" name="name" value="${category.name}" style="width: 200px"/>
                    <span id="categoryName_help"style="display: none; color: red;">The category name field not allowed to be empty</span>
                </td>
            </tr>
			<tr>
                <td></td>
                <td><input type="button" onclick="saveAppCategory(this.form);" value="save"/></td>
            </tr>
		</table>
	</form>
</body>
</html>