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

    <script type="text/javascript">
        function loadCategories(){
        	jQuery("#dailog").dialog({
    			autoOpen: false,
    			show: "blind",
    			hide: "explode",
                width: "400",
                height: "200",
                title: "请选择产品的目录"
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
                jQuery("#productCategoryName").val(data.rslt.obj.attr("path"));
                jQuery("#productCategoryId").val(data.rslt.obj.attr("id"));
                jQuery("#dailog").dialog( "close" );
            });

        }
    </script>
</head>
<body>
<div class="action">
    &nbsp;
</div>

<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top">
            <spring-form:form commandName="product" method="POST" cssClass="form">
                <table cellspacing="0" width="100%">
                    <input type="hidden" name="id" value="${product.id}"/>

                    <tr>
                        <td width="200px;">
                             名称 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:input path="productName" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                            <spring-form:errors path="productName" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;">
                             型号 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:input path="productModel" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                            <spring-form:errors path="productModel" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                             描述 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:textarea path="productDescription" rows="10" cols="80"/>&nbsp;
                            <spring-form:errors path="productDescription" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            所属目录 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:input type="text" id="productCategoryName" path="productCategoryName" style="width:500px;" readonly="true"/>
                            <a id="siteImg" href="JavaScript:loadCategories();"><img src="${pageContext.request.contextPath}/theme/default/images/home.gif" onclick=></a>
                            <spring-form:input type="hidden" id="productCategoryId" path="productCategoryId" style="width:200px;"/>
                        	<spring-form:errors path="productCategoryId" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                        </td>
                        <td>
                            <button type="button" class="thoughtbotform" onclick="window.location.href='${pageContext.request.contextPath}/backend/productoverview.html?name=${name}&categoryId=${categoryId}&current=${current}'">
                                返回
                            </button>
                        	<button name="" type="submit" class="thoughtbotform">保存</button>
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

</body>
</html>