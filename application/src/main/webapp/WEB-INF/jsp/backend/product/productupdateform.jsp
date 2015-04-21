<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>IP广义升级云端</title>
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
                             产品目录
                        </td>
                        <td>
                            <input name="productCategoryName" value="${product.productCategoryName}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             产品名称
                        </td>
                        <td>
                            <input name="productName" value="${product.productName}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             产品型号
                        </td>
                        <td>
                            <input name="productName" value="${product.productModel}" maxlength="30" style="width:200px;" readonly="true"/>&nbsp;
                        </td>
                    </tr>

                    <tr>
                        <td>
                             升级类型
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
                                        <font color="red">校验说明：</font>
                                        <li style="color: blue;">强制升级类型：1）普通升级 -> 服务端校验客户端的所有字段</li>
                                        <li style="color: blue;">强制升级类型：2）忽略固件版本比对的普通强制升级 -> 服务端校验客户端除固件版本的所有字段</li>
                                        <li style="color: blue;">强制升级类型：3）只比对产品型号 -> 服务端只校验客户端的产品型号</li>
                                        <li style="color: blue;">MAC范围：客户端MAC地址需在服务端MAC地址范围内通过校验,只填写一个会自动按照另一个值补齐</li>
                                        <li style="color: blue;">系统软件版本：服务端和客户端需保持一致可通过校验</li>
                                        <li style="color: blue;">MAC Filter：服务端的MAC地址的结尾需和客户端MAC地址保持一致</li>
                                        <li style="color: blue;">签名类型：服务端和客户端需保持一致可通过校验</li>
                                        <li style="color: blue;">固件版本：服务端版本高于客户端版本可通过校验</li>
                                        <li style="color: blue;">硬件版本：服务端和客户端需保持一致可通过校验</li>
                                        <li style="color: blue;">测试标记：服务端和客户端需保持一致可通过校验</li>

                                        <div id="dvb_validation_info" style="display: none">
                                            <li style="color: blue;">数字电视更新版本：服务端和客户端需保持一致可通过校验</li>
                                            <li style="color: blue;">运营商编码：服务端和客户端需保持一致可通过校验</li>
                                            <li style="color: blue;">CA类型：服务端和客户端需保持一致可通过校验</li>
                                            <li style="color: blue;">CA版本：服务端和客户端需保持一致可通过校验</li>
                                            <li style="color: blue;">依赖库版本：服务端和客户端需保持一致可通过校验</li>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         强制升级类型 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="updateType" value="0"/> 普通升级
                                        <spring-form:radiobutton path="updateType" value="1"/> 忽略固件版本比对的普通强制升级
                                        <spring-form:radiobutton path="updateType" value="2"/> 只比对产品型号
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         MAC范围
                                    </td>
                                    <td>
                                        <spring-form:input path="fromFilter" maxlength="17" cssStyle="width:200px;"/>&nbsp;到
                                        <spring-form:input path="toFilter" maxlength="17" cssStyle="width:200px;"/> &nbsp;MAC地址格式示例（字符全小写）:65:88:ff:73:6b:3f
                                        <spring-form:errors path="toFilter" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         系统软件版本 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="softwareVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp; Android API版本 如8代表Android 2.2
                                        <spring-form:errors path="softwareVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         MAC Filter
                                    </td>
                                    <td>
                                        <spring-form:input path="macFilter" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="macFilter" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         签名类型
                                    </td>
                                    <td>
                                        <spring-form:input path="signatureType" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="signatureType" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         固件版本比较方式
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="versionCompareWay" value="1"/> 云端版本大于客户端版本升级
                                        <spring-form:radiobutton path="versionCompareWay" value="0"/> 云端版本等于客户端版本升级
                                        <spring-form:radiobutton path="versionCompareWay" value="2"/> 组合判断升级
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         固件版本
                                    </td>
                                    <td>
                                        <spring-form:input path="guJianVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp; 长虹软件版本
                                        <spring-form:errors path="guJianVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         硬件版本
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
                                         启动方式
                                    </td>
                                    <td>
                                        <spring-form:radiobutton path="updateModel" value="1"/> 立即升级
                                        <spring-form:radiobutton path="updateModel" value="0"/> 延迟升级
                                    </td>
                                </tr>
                            </table>

                            <table id="dvb_table" style="display: none">
                                <tr>
                                    <td width="200px;">
                                         数字电视更新版本 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="dvbVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="dvbVersion" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         运营商编码
                                    </td>
                                    <td>
                                        <spring-form:input path="dvbProviderCode" maxlength="4" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="dvbProviderCode" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         CA类型
                                    </td>
                                    <td>
                                        <spring-form:input path="caType" maxlength="2" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="caType" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         CA版本
                                    </td>
                                    <td>
                                        <spring-form:input path="caVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="caVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         依赖库版本
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
                                        <font color="red">校验说明：</font>
                                        <li style="color: blue">包名：服务端版本高于客户端版本可通过校验</li>
                                        <li style="color: blue">兼容SDK范围：客户端版本需在服务端版本范围内，如：n&le;x&le;m</li>
                                        <li style="color: blue">程序版本 ：服务端和客户端需保持一致可通过校验</li>
                                        <li style="color: blue">签名类型 ：服务端和客户端需保持一致可通过校验</li>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         包名 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appPackage" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appPackage" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         兼容SDK范围 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appVersionRange" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appVersionRange" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         程序版本 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="appVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="appVersion" cssClass="required"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="200px;">
                                         签名类型
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
                                        <font color="red">校验说明：</font>
                                        <li style="color: blue">程序名：服务端版本高于客户端版本可通过校验</li>
                                        <li style="color: blue">程序版本 ：服务端和客户端需保持一致可通过校验</li>
                                        <li style="color: blue">签名类型 ：服务端和客户端需保持一致可通过校验</li>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         程序名 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="programName" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="programName" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         程序版本 <span class="required">*</span>
                                    </td>
                                    <td>
                                        <spring-form:input path="programVersion" maxlength="30" cssStyle="width:200px;"/>&nbsp;
                                        <spring-form:errors path="programVersion" cssClass="required"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="200px;">
                                         程序签名
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
                             测试标记
                        </td>
                        <td>
                            <spring-form:radiobutton path="testFlag" value="true"/> Yes
                            <spring-form:radiobutton path="testFlag" value="false"/> No
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" valign="top">
                             服务器升级文件(<1G)
                        </td>
                        <td>
                            <input type="file" id="productUploadFile" name="productUploadFile" class="file" style="width:200px;"/>
                            <c:if test="${productUpdate.updateUploadFileId > 0}">
                                <div id="attachedUploadFile">
                                    <br/>已上传数据文件:<br/>
                                    ${productUpdate.updateUploadFileName}
                                    <input type="button" value="删除" onclick="removeProductUpdateFile('${productUpdate.id}', '${productUpdate.updateUploadFileId}')"/>
                                    <%--<input type="button" value="下载" onclick="downloadProductUpdateFile('${productUpdate.id}', '${productUpdate.updateUploadFileId}')"/>--%>
                                </div>
                            </c:if>
                            <spring-form:errors path="updateUploadFileName" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" >
                             外链地址升级文件
                        </td>
                        <td>
                            <spring-form:input path="updateURL" maxlength="600" cssStyle="width:600px;"/>&nbsp;
                            <spring-form:errors path="updateURL" cssClass="required"/>
                            <br/>
                            说明：升级文件为update_固件版本.zip, 如 http://www.baidu.com/update_1.001.zip
                        </td>
                    </tr>

                    <tr>
                        <td>
                        </td>
                        <td>
                            <button type="button" class="thoughtbotform" onclick="window.location.href='${pageContext.request.contextPath}/backend/productupdatehistory.html?productId=${product.id}&updateWayFilter=${updateWayFilter}&versionFilter=${versionFilter}'">
                                返回
                            </button>
                        	<button name="" type="button" class="thoughtbotform" onclick="submitProductUpdate(this.form)">保存</button>
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
        jQuery('#commit_form_mask').mask("正在上传数据文件，请不要关闭该页面，耐心等待!");
        form.submit();
    }

    function removeProductUpdateFile(productUpdateId, updateUploadFileId) {
        if(confirm('确定要移除该升级文件?')) {
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