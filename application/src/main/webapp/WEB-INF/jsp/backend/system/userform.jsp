<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>IP广义升级云端</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/jquery-ui-1.8.22.custom.css" type="text/css"/>
</head>
<body>
<div class="action">
    &nbsp;
</div>

<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td width="200" valign="top" style="background: #e8e8e8;border-right: 1px solid #CCC;">
            <jsp:include page="../systemtype.jsp"/>
        </td>
        <td valign="top">
            <spring-form:form commandName="user" method="POST" cssClass="form">
                <table cellspacing="0" width="100%">
                    <input type="hidden" name="id" value="${user.id}"/>

                    <tr>
                        <td width="200px;">
                             姓名 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:input path="name" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                            <spring-form:errors path="name" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                             编号(用户名) <span class="required">*</span>
                        </td>
                        <td>
                            <c:set var="justRead" value="false"/>
                            <c:if test="${user.username == 'chupdate_admin'}">
                                <c:set var="justRead" value="true"/>
                            </c:if>
                            <spring-form:input path="username" maxlength="30" cssStyle="width:200px;" readonly="${justRead}"/>&nbsp;
                            <spring-form:errors path="username" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            联系方式
                        </td>
                        <td>
                            <spring-form:input id="contactWay" path="contactWay" cssStyle="width:200px;"/>
                        </td>
                    </tr>

                    <c:if test="${user.username != 'chupdate_admin'}">
                        <tr>
                            <td valign="top">
                                管理的产品目录
                            </td>
                            <td>
                                <c:set var="existCategories" value=""/>
                                <div id="categories">
                                    <c:forEach var="category" items="${user.categories}">
                                        <c:set var="existCategories" value="${existCategories}_${category.id}|"/>
                                        <div id="manage_category_${category.id}">
                                            ${category.fullPath}
                                            <a onclick="removeManageCategory('${category.id}')" href="#">
                                                <input type="button" value="删除"/>
                                            </a>
                                        </div>
                                    </c:forEach>
                                    <div/>
                                </div>
                                <img alt="" src="${pageContext.request.contextPath}/images/ibtn-select-tree.png" onclick="loadCategories()">
                                <input type="hidden" name="category_ids" id="category_ids" value="${existCategories}"/>
                            </td>
                        </tr>
                    </c:if>

                    <tr>
                        <td>
                        </td>
                        <td>
                            <button type="button" class="thoughtbotform" onclick="window.location.href='${pageContext.request.contextPath}/backend/useroverview.html?filername=${filername}&current=${current}'">
                                返回
                            </button>
                        	<button name="" type="submit" class="thoughtbotform" >保存</button>
                        </td>
                    </tr>
                </table>
            </spring-form:form>
        </td>
    </tr>
</table>

<div id="dailog">
	<div id="categoryDailog"></div><br>
</div>

<script type="text/javascript">
    function removeManageCategory(categoryId) {
        jQuery("#manage_category_" + categoryId).remove();
        var removeIds = jQuery("#category_ids").val();
        removeIds = removeIds.replace("_" + categoryId + "|", "");
        jQuery("#category_ids").val(removeIds);
    }

    function loadCategories(){
        jQuery("#dailog").dialog({
            autoOpen: false,
            show: "blind",
            hide: "explode",
            width: "400",
            height: "200",
            title: "请选择用户管理的目录"
        });
        jQuery("#dailog").dialog("open");
        jQuery("#categoryDailog").jstree({
            "json_data":{
                "ajax": {
                    "url" :"${pageContext.request.contextPath}/backend/productcategorytree.html",
                    "data":function (n) {
                        return {
                            "id" : n.attr ? n.attr("id") : -1
                        };
                    }
                }
            },
            "themes" : {
                "theme" : "default",
                "dots" : true,
                "icons" : true
            },
            "plugins" : ["themes","json_data","ui","core"]
        }).bind("dblclick_node.jstree", function(e, data) {
            var categoryId = data.rslt.obj.attr("id");
            var catgorypath = data.rslt.obj.attr("path");

            var addIds = jQuery("#category_ids").val();
            if(addIds.indexOf(categoryId + "|") < 0) {
                addIds = addIds + "_" + categoryId + "|";
                jQuery("#category_ids").val(addIds);

                var addHtml = "<div id=\"manage_category_" + categoryId + "\">" + catgorypath + "<a onclick=\"removeManageCategory('" + categoryId + "')\" href=\"#\"><input type=\"button\" value=\"删除\"/></a></div>"
                jQuery("#categories div:last").before(addHtml);
            }

            jQuery("#dailog").dialog( "close" );
        });

    }
</script>
</body>
</html>