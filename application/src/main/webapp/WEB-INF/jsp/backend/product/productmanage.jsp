<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>IP广义升级云端</title>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.hotkeys.js" type="text/javascript"></script>

	<script>
	jQuery(function() {
        jQuery.ajaxSetup({cache:false});
		jQuery("#tree").jstree({
			"json_data":{
				"ajax": {
					"method": "POST",
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
			contextmenu: {
				items: customMenu
			},
			"plugins" : ["themes","json_data","ui","core","crrm", "cookies","contextmenu"]
		}).bind("select_node.jstree", function (event, data) {
			loadProducts(data.rslt.obj.attr("id"));
        });

		settings = {
			align : 'center',									//Valid values, left, right, center
			top : 150, 											//Use an integer (in pixels)
			width : 600, 										//Use an integer (in pixels)
			padding : 10,										//Use an integer (in pixels)
	        backgroundColor : 'white', 						    //Use any hex code
	        source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
	        borderColor : '#333333', 							//Use any hex code
	        borderWeight : 4,									//Use an integer (in pixels)
	        borderRadius : 5, 									//Use an integer (in pixels)
	        fadeOutTime : 300, 									//Use any integer, 0 : no fade
	        disableColor : '#666666', 							//Use any hex code
	        disableOpacity : 40, 								//Valid range 0-100
	        loadingImage : '${pageContext.request.contextPath}/js/popup/loading.gif'
		};
		jQuery(document).keyup(function(event) {
			if (event.keyCode == 27) {
				closePopup(settings.fadeOutTime);
			}
		});

	});
	function openCategoryDalog(id, opt) {
		settings.source = '${pageContext.request.contextPath}/backend/categoryform.html?method=' + opt + '&id=' + id;
		openModalPopup(settings);
	}

	function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}

	function customMenu(node) {
	    var items = {
	    		"createItem": {
		            "label": "创建目录",
		            "action": function () {
		            	openCategoryDalog(node.attr("id"), "add");
		            }
		        },
	    		"editItem": {
		            "label": "修改目录",
		            "action": function (node) {
		            	openCategoryDalog(node.attr("id"), "edit");
		            }
		        },
		        "deleteItem": {
		            "label": "删除目录",
		            "action": function (node) {
                        if(confirm("你要删除该目录吗?")) {
                            $.ajax({
                                type: "post",
                                url: "${pageContext.request.contextPath}/backend/categorydelete.html",
                                data: {id:node.attr("id")},
                                success:function(result){
                                    if("true" == result.message) {
                                        var tree = jQuery.jstree._reference("#tree");
                                        var currentNode = tree._get_node(null, false);
                                        var parentNode = tree._get_parent(currentNode);
                                        tree.refresh(parentNode);
                                    } else {
                                        alert("对不起，该目录包含产品或子目录，不能删除!")
                                    }
                                }
                            });
                        }
		            }
		        }
	    };

	    if ($(node).hasClass("folder")) {
	        delete items.deleteItem;
	    }

	    return items;
	}

	function saveAppCategory(form) {
        var categoryName = jQuery("#name").val();
        if (categoryName == null || categoryName == '') {
            jQuery("#categoryName_help").css("display", "block");
        } else {
            jQuery("#categoryName_help").css("display", "none");
	    form.submit();
        }
    }

	function loadProducts(categoryId){
		jQuery.ajax({
			type: "post",
			url: "${pageContext.request.contextPath}/backend/productoverview.html",
			data: {categoryId: categoryId},
			success:function(){
				document.getElementById("products").contentWindow.location = "${pageContext.request.contextPath}/backend/productoverview.html?categoryId=" + categoryId;
			}
		});
	};
    </script>
</head>
<body on>
<div class="action">
    &nbsp;
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top" width="190px">
            <span>
        	    &nbsp;&nbsp;<div id="tree"></div>
            </span>
        </td>
        <td valign="top" width="*">
        	<iframe id="products" frameborder="0" width="100%" height="600" src="${pageContext.request.contextPath}/backend/productoverview.html"></iframe>
        </td>
    </tr>
	</table>

</body>
</html>