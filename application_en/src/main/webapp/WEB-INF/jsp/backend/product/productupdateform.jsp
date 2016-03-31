<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>IP Update Cloud Platform</title>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/ProductDWRHandler.js" type="text/javascript"></script>
    <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.min.js'></script>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.css'/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/public.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/main.css" type="text/css"/>

    <script type='text/javascript' src='${pageContext.request.contextPath}/js/processbar/jquery-ui-1.8.16.custom.min.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath}/js/processbar/processbar.js'></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/processbar/css/jquery-ui-1.8.16.custom.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/processbar/css/processbar.css" type="text/css"/>

    <script type="text/javascript">
        jQuery(function() {
            settings = {
                align : 'center',									//Valid values, left, right, center
                top : 50, 											//Use an integer (in pixels)
                width : 800, 										//Use an integer (in pixels)
                height : 600, 										//Use an integer (in pixels)
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

	function openSNListDalog(id) {
		settings.source = '${pageContext.request.contextPath}/backend/productupdatesnlist.html?updateId=' + id;
		openModalPopup(settings);
	}

	function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}
    </script>
</head>
<body>
<div class="action">
    &nbsp;
</div>

<div class="file_upload_tool" style="display: none">
    <div id="progress_bar">
        <div class="percent"></div>
        <div class="pbar"></div>
        <div class="elapsed"></div>
    </div>
</div>

<div id="commit_form_mask">

<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td valign="top">
            <spring-form:form id="productUpdateForm" commandName="productUpdate" method="POST" cssClass="form" enctype="multipart/form-data">
                <table cellspacing="0" width="100%">
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <input type="hidden" name="updateWayFilter" value="${updateWayFilter}"/>
                    <input type="hidden" name="versionFilter" value="${versionFilter}"/>
                    <input type="hidden" name="updateId" value="${productUpdate.id}"/>

                    <tr>
                        <td width="200px;">
                             Category Name
                        </td>
                        <td>
                            <input name="productCategoryName" value="${product.productCategoryName}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             Product Name
                        </td>
                        <td>
                            <input name="productName" value="${product.productName}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             Product Model
                        </td>
                        <td>
                            <input name="productName" value="${product.productModel}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             Update Type
                        </td>
                        <td>
                            <c:if test="${productUpdate.id > 0}">
                                <input type="hidden" name="updateWay" value="${productUpdate.updateWay}"/>
                                <input name="updateWayName" value="${productUpdate.updateWayName}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                            </c:if>
                            <c:if test="${productUpdate.id <= 0}">
                                <select id="updateWay" name="updateWay" style="width:212px;" onchange="showDifferentUpdateForm(this.value);">
                                    <c:forEach items="${productUpdateWays}" var="way">
                                        <option value="${way.value}" <c:if test="${productUpdate.updateWay == way.value}"> selected="true"</c:if>>${way.value} - ${way.label}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            <table id="gujian_chafen_table" style="display: none">
                                <tr>
                                    <td colspan="2">
                                        <font color="red">Validation Note：</font>
                                        <li style="color: blue;">Force Update Way：1）Normal Update -> Validation all fields</li>
                                        <li style="color: blue;">Force Update Way：2）Ignore Firmware Version -> Validation all field except firmware version</li>
                                        <li style="color: blue;">Force Update Way：3）Just Compare Product Model -> Just validation product model is equal or not</li>
                                        <li style="color: blue;">Mac Range Control：Box mac address should be in Mac setting range can pass validation</li>
                                        <li style="color: blue;">System Software Version ：Cloud system software version should be equal to box system software versoin can pass validation</li>
                                        <li style="color: blue;">Mac Filter：Box mac address should end with cloud setting can pass validation</li>
                                        <li style="color: blue;">Signature：Cloud signature should be equal to box singnature can pass validation</li>
                                        <li style="color: blue;">Firmware Version：Reference to Fore Update Way Field</li>
                                        <li style="color: blue;">Hardware Version：Cloud hardware version should be equal to box hardware version can pass validation</li>
                                        <li style="color: blue;">Test Flog：Cloud test flag should be equal to box test flag can pass validation</li>

                                        <div id="dvb_validation_info" style="display: none">
                                            <li style="color: blue;">DVB Version：Cloud dvb version should be equal to box dvb version can pass validation</li>
                                            <li style="color: blue;">Provider Code：Cloud provider code should be equal to box provider code can pass validation</li>
                                            <li style="color: blue;">CA Type：Cloud CA type should be equal to box ca type can pass validation</li>
                                            <li style="color: blue;">CA Version：Cloud CA version should be equal to box CA version can pass validation</li>
                                            <li style="color: blue;">Depend Lib Version：Cloud depend lib version should be equal to box depend lib version flag can pass validation</li>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Force Update Type <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="updateType" value="0"/> Normal Update
                                        <spring-form:radiobutton path="updateType" value="1"/> Ignore Firmware Version
                                        <spring-form:radiobutton path="updateType" value="2"/> Just Compare Product Model
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Update Mac List <span class="required">*</span>
                                    </td>
                                    <td>
                                        <input type="file" id="snUploadFileList" name="snUploadFileList" class="file" style="width:200px;"/>
                                        <spring-form:errors path="snUploadFile" cssClass="required"/>
                                        <c:if test="${productUpdate.snUploadFile != null && productUpdate.snUploadFile != ''}">
                                            <div id="attachedSnUploadFile">
                                                <br/>already upload file:devices.txt <a href="#" style="color: red;" onclick="openSNListDalog('${productUpdate.id}')">check mac list</a>
                                            </div>
                                        </c:if>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         System Software Version <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="softwareVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp; Android API Version, 8 Stand for Android 2.2
                                        <spring-form:errors path="softwareVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Mac Filter
                                    </td>
                                    <td>
                                        <spring-form:input path="macFilter" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="macFilter" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Signature
                                    </td>
                                    <td>
                                        <spring-form:input path="signatureType" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="signatureType" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Firmware Compare Type
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="versionCompareWay" value="1"/> Cloud firmware version bigger than box firmware version
                                        <spring-form:radiobutton path="versionCompareWay" value="0"/> Cloud firmware version equal to box firnware version
                                        <spring-form:radiobutton path="versionCompareWay" value="2"/> Combine compare
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Firmware Version
                                    </td>
                                    <td>
                                        <spring-form:input path="guJianVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp; Changhong Firmware Version
                                        <spring-form:errors path="guJianVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Firmware Version For Update
                                    </td>
                                    <td>
                                        <spring-form:input path="guJianVersionAfter" maxlength="30" cssStyle="width:200px;"/>&nbsp; Changhong Firmware Version
                                        <spring-form:errors path="guJianVersionAfter" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Hardware Version
                                    </td>
                                    <td>
                                        <spring-form:input path="yingJianVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="yingJianVersion" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         View
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="view" value="1"/> Yes
                                        <spring-form:radiobutton path="view" value="0"/> No
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Launch Way
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="updateModel" value="1"/> Update Immediately
                                        <spring-form:radiobutton path="updateModel" value="0"/> Delay Update in 30 Seconds
                                    </td>
                                </tr>
                            </table>

                            <table id="dvb_table" style="display: none">
                                <tr>
                                    <td width="200px;">
                                         DVB Update Version <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="dvbVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="dvbVersion" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         Provider Code
                                    </td>
                                    <td>
                                        <spring-form:input path="dvbProviderCode" maxlength="4" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="dvbProviderCode" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         CA Type
                                    </td>
                                    <td>
                                        <spring-form:input path="caType" maxlength="2" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="caType" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         CA Version
                                    </td>
                                    <td>
                                        <spring-form:input path="caVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="caVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Depend Lib Version
                                    </td>
                                    <td>
                                        <spring-form:input path="caDependVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="caDependVersion" cssClass="required"/>
                                    </td>
                                </tr>

                            </table>

                            <table id="app_table" style="display: none">
                                <tr>
                                    <td colspan="2">
                                        <font color="red">Validation Note：</font>
                                        <li style="color: blue">App Package：cloud app package should be equal to box app package can pass validation</li>
                                        <li style="color: blue">Android SDK Range：box android SDK version should in range of cloud setting can pass validation，such as：n&le;x&le;m</li>
                                        <li style="color: blue">App Version ：cloud app version should be equal to box app version can pass validation</li>
                                        <li style="color: blue">App Signature ：cloud app signature should be equal to box app signature can pass validation</li>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         App Package <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appPackage" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appPackage" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         Android SDK Range<span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appVersionRange" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appVersionRange" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         App Version <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         App Signature
                                    </td>
                                    <td>
                                        <spring-form:input path="appSignatureType" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appSignatureType" cssClass="required"/>
                                    </td>
                                </tr>

                            </table>

                            <table id="program_table" style="display: none">
                                <tr>
                                    <td colspan="2">
                                        <font color="red">Valiation Note：</font>
                                        <li style="color: blue">Program Name：cloud program name should be equal to box program name can pass validation</li>
                                        <li style="color: blue">Program Version ：cloud program version should be equal to box program version can pass validation</li>
                                        <li style="color: blue">Program Signature：cloud program signature should be equal to box program signature can pass validation</li>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         Program Name <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="programName" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="programName" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         Program Version <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="programVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="programVersion" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         Program Signature
                                    </td>
                                    <td>
                                        <spring-form:input path="programSignatureType" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;">
                             Test Flag
                        </td>
                        <td>
                            <spring-form:radiobutton path="testFlag" value="true"/> Yes
                            <spring-form:radiobutton path="testFlag" value="false"/> No
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" valign="top">
                             Upload File(small than 1G)
                        </td>
                        <td>
                            <input type="file" id="productUploadFile" name="productUploadFile" class="file" style="width:200px;"/>
                            <c:if test="${productUpdate.updateUploadFileId > 0}">
                                <div id="attachedUploadFile">
                                    <br/>Upload File:<br/>
                                    ${productUpdate.updateUploadFileName}
                                    <input type="button" value="Delete" onclick="removeProductUpdateFile('${productUpdate.id}', '${productUpdate.updateUploadFileId}')"/>
                                    <%--<input type="button" value="下载" onclick="downloadProductUpdateFile('${productUpdate.id}', '${productUpdate.updateUploadFileId}')"/>--%>
                                </div>
                            </c:if>
                            <spring-form:errors path="updateUploadFileName" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" >
                             External Update File URL
                        </td>
                        <td>
                            <spring-form:input path="updateURL" maxlength="600" cssStyle="width:600px;"/>&nbsp;
                            <spring-form:errors path="updateURL" cssClass="required"/>
                            <br/>
                            Note：update filename should be like update_fireware.zip, such as http://www.google.com/update_1.001.zip
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" >
                            Client APK Version
                        </td>
                        <td>
                            <spring-form:input path="clientVersion" maxlength="600" cssStyle="width:600px;"/>&nbsp;
                            <spring-form:errors path="clientVersion" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" >
                            Client APK Update URL
                        </td>
                        <td>
                            <spring-form:input path="apkUpdateURL" maxlength="200" cssStyle="width:600px;"/>&nbsp;
                            <spring-form:errors path="apkUpdateURL" cssClass="required"/>
                            <br/>
                            Not：if client update apk name update_xxxx.zip, such as http://www.baidu.com/update_1.0.zip
                        </td>
                    </tr>

                    <tr>
                        <td>
                        </td>
                        <td>
                            <button type="button" class="thoughtbotform" onclick="window.location.href='${pageContext.request.contextPath}/backend/productupdatehistory.html?productId=${product.id}&updateWayFilter=${updateWayFilter}&versionFilter=${versionFilter}'">
                                Return
                            </button>
                        	<button name="" type="button" class="thoughtbotform" onclick="submitProductUpdate(this.form)">Save</button>
                        </td>
                    </tr>
                </table>
            </spring-form:form>
        </td>
    </tr>
</table>
</div>

<div id="dailog">
	<div id="categoryDailog"></div><br>
</div>

<script type="text/javascript">

    function submitProductUpdate(form) {
        jQuery('.file_upload_tool').css('display', 'block');
        jQuery('#progress_bar').anim_progressbar();
        jQuery('#commit_form_mask').mask("uploading...");
        form.submit();
    }

    function removeProductUpdateFile(productUpdateId, updateUploadFileId) {
        if(confirm('Are you sure you want to delete this update file?')) {
            ProductDWRHandler.removeAssetAttachedFile(productUpdateId, updateUploadFileId, function(result) {
                jQuery('#attachedUploadFile').remove();
            });
        }
    }

    function downloadProductUpdateFile(productUpdateId, updateUploadFileId) {
        window.location.href = '${pageContext.request.contextPath}/backend/productupdatefiledownload.html?productUpdateId=' + productUpdateId + "&updateUploadFileId=" + updateUploadFileId;
    }

    function showDifferentUpdateForm(value) {
        if("1" == value || "2" == value) {
             jQuery("#gujian_chafen_table").css("display", "block");
            jQuery("#dvb_validation_info").css("display", "none");
             jQuery("#dvb_table").css("display", "none");
             jQuery("#app_table").css("display", "none");
             jQuery("#program_table").css("display", "none");
        } else if ("3" == value) {
             jQuery("#gujian_chafen_table").css("display", "block");
             jQuery("#dvb_validation_info").css("display", "block");
             jQuery("#dvb_table").css("display", "block");
             jQuery("#app_table").css("display", "none");
             jQuery("#program_table").css("display", "none");
        } else if ("4" == value) {
             jQuery("#gujian_chafen_table").css("display", "none");
             jQuery("#dvb_table").css("display", "none");
             jQuery("#app_table").css("display", "block");
             jQuery("#program_table").css("display", "none");
        } else {
             jQuery("#gujian_chafen_table").css("display", "none");
             jQuery("#dvb_table").css("display", "none");
             jQuery("#app_table").css("display", "none");
             jQuery("#program_table").css("display", "block");
        }
    }

    var updateWay = '${productUpdate.updateWay}';
    showDifferentUpdateForm(updateWay);

</script>

</body>
</html>