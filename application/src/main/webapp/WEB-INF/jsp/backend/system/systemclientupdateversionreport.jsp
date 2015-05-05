<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>IP广义升级云端</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/highcharts.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/exporting.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/dark-green.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/report/clientgujian_update.js"></script>
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/SystemDWRHandler.js" type="text/javascript"></script>
</head>
<body>
<div class="action">
    &nbsp;
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td width="200" valign="top" style="background: #e8e8e8;border-right: 1px solid #CCC;">
            <jsp:include page="../statistic.jsp"/>
        </td>
        <td valign="top">
            <form action="#" class="search_form" method="POST">
                <div class="search">
                    <span>
                        产品:
                        <select id="reportProduct" style="width: 200px;">
                            <c:forEach items="${products}" var="product">
                                <option value="${product.model}">${product.name} : ${product.model}</option>
                            </c:forEach>
                        </select>
                        &nbsp;
                    </span>
                    <%--<span>--%>
                        <%--升级类型:--%>
                        <%--<select id="updateWay" style="width: 100px;">--%>
                            <%--<option value="1" <c:if test="${updateWay=='1'}">selected="true"</c:if>>固件升级</option>--%>
                            <%--<option value="2" <c:if test="${updateWay=='2'}">selected="true"</c:if>>差分升级</option>--%>
                            <%--<option value="3" <c:if test="${updateWay=='3'}">selected="true"</c:if>>数字电视应用升级</option>--%>
                            <%--<option value="4" <c:if test="${updateWay=='4'}">selected="true"</c:if>>应用升级</option>--%>
                            <%--<option value="5" <c:if test="${updateWay=='5'}">selected="true"</c:if>>二进制数据包升级</option>--%>
                        <%--</select>--%>
                        <%--&nbsp;--%>
                    <%--</span>--%>
                    <span>
                        升级成功/失败:
                        <select id="updateSuccess" style="width: 100px;">
                            <option value="1" <c:if test="${updateSuccess=='1'}">selected="true"</c:if>>成功升级</option>
                            <option value="0" <c:if test="${updateSuccess=='0'}">selected="true"</c:if>>失败升级</option>
                        </select>
                        &nbsp;
                    </span>

                    <span>
                       版本升级情况
                    </span>
                    <%--<span>--%>
                        <%--时间:--%>
                        <%--<select id="reportYear" style="width: 80px;">--%>
                            <%--<option value="2013" <c:if test="${reportYear==2013}">selected="true"</c:if>>2013</option>--%>
                            <%--<option value="2014" <c:if test="${reportYear==2014}">selected="true"</c:if>>2014</option>--%>
                            <%--<option value="2015" <c:if test="${reportYear==2015}">selected="true"</c:if>>2015</option>--%>
                            <%--<option value="2016" <c:if test="${reportYear==2016}">selected="true"</c:if>>2016</option>--%>
                            <%--<option value="2017" <c:if test="${reportYear==2017}">selected="true"</c:if>>2017</option>--%>
                            <%--<option value="2018" <c:if test="${reportYear==2018}">selected="true"</c:if>>2018</option>--%>
                            <%--<option value="2019" <c:if test="${reportYear==2019}">selected="true"</c:if>>2019</option>--%>
                            <%--<option value="2020" <c:if test="${reportYear==2020}">selected="true"</c:if>>2020</option>--%>
                        <%--</select>--%>
                        <%--&nbsp;年--%>
                    <%--</span>--%>
                    <%--<span>--%>
                        <%--<select id="reportMonth" style="width: 80px;">--%>
                            <%--<option value="0" <c:if test="${reportMonth==0}">selected="true"</c:if>>全年</option>--%>
                            <%--<option value="1" <c:if test="${reportMonth==1}">selected="true"</c:if>>1</option>--%>
                            <%--<option value="2" <c:if test="${reportMonth==2}">selected="true"</c:if>>2</option>--%>
                            <%--<option value="3" <c:if test="${reportMonth==3}">selected="true"</c:if>>3</option>--%>
                            <%--<option value="4" <c:if test="${reportMonth==4}">selected="true"</c:if>>4</option>--%>
                            <%--<option value="5" <c:if test="${reportMonth==5}">selected="true"</c:if>>5</option>--%>
                            <%--<option value="6" <c:if test="${reportMonth==6}">selected="true"</c:if>>6</option>--%>
                            <%--<option value="7" <c:if test="${reportMonth==7}">selected="true"</c:if>>7</option>--%>
                            <%--<option value="8" <c:if test="${reportMonth==8}">selected="true"</c:if>>8</option>--%>
                            <%--<option value="9" <c:if test="${reportMonth==9}">selected="true"</c:if>>9</option>--%>
                            <%--<option value="10" <c:if test="${reportMonth==10}">selected="true"</c:if>>10</option>--%>
                            <%--<option value="11" <c:if test="${reportMonth==11}">selected="true"</c:if>>11</option>--%>
                            <%--<option value="12" <c:if test="${reportMonth==12}">selected="true"</c:if>>12</option>--%>
                        <%--</select>--%>
                        <%--&nbsp;月--%>
                    <%--</span>--%>
                    <span>
                        报表类型:
                        <select id="reportType" style="width: 100px;">
                            <option value="1" <c:if test="${reportType=='1'}">selected="true"</c:if>>柱状图</option>
                            <option value="0" <c:if test="${reportType=='0'}">selected="true"</c:if>>饼状图</option>
                        </select>
                        &nbsp;
                    </span>
                    <input type="button" value="统计" onclick="generateReport(); "/>
                </div>
            </form>

            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr id="container3_tr" style="width: 90%; height: 350px; ">
                    <td>
                        <div id="container3" style="width:80%; height: 350px; padding-left: 20px"></div>
                    </td>
                </tr>
                <tr id="container4_tr" style="width: 90%; height: 350px;">
                    <td>
                        <div id="container4" style="width:80%; height: 350px; padding-left: 20px"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">

    function renew_sta_container(productModel, updateSuccess, reportType) {
        if(productModel == '') {
            return;
        }

        if("1" == reportType) {
            jQuery("#container3_tr").show();
            jQuery("#container4_tr").hide();

                SystemDWRHandler.obtainVersionClientUpdateAmountByResult(productModel, updateSuccess, function(result) {
                    var statisticData = JSON.parse(result);
                    var version = statisticData[0].version.split(",");
                    var total=statisticData[1].total.split(",");

                    sta_container3.xAxis.categories=version;

                    var newData = new Array();
                    for(var i=0; i<total.length; i++) {
                        newData[i]=parseInt(total[i]);
                    }
                    sta_container3.series[0].data =newData;

                    var successfulString = "";
                    if("1" == updateSuccess) {
                        var successfulString = "成功";
                    } else {
                        var successfulString = "失败";
                    }

                    sta_container3.title.text = "产品(" + productModel + ")"+"各版本用户升级"+successfulString+"统计";

                    new Highcharts.Chart(sta_container3);
                });

        } else {
            jQuery("#container4_tr").show();
            jQuery("#container3_tr").hide();

            SystemDWRHandler.obtainVersionClientUpdateAmountByResult(productModel, updateSuccess, function(result) {
                var statisticData = JSON.parse(result);
                var version = statisticData[0].version.split(",");
                var total=statisticData[1].total.split(",");

                var newData = new Array();
                for(var i=0; i<total.length; i++) {
                    var inner = new Array();
                    inner[0]=version[i];
                    inner[1]=parseInt(total[i]);
                    newData[i]=inner;
                }

                sta_container4.series[0].data = newData;

                var successfulString = "";
                if("1" == updateSuccess) {
                    var successfulString = "成功";
                } else {
                    var successfulString = "失败";
                }
                sta_container4.title.text = "产品(" + productModel + ")"+"各版本用户升级"+successfulString+"统计";
                new Highcharts.Chart(sta_container4);
            });
        }
    }

    function generateReport() {
        var productModel = jQuery("#reportProduct").val();
        var updateSuccess = jQuery("#updateSuccess").val();
        var reportType = jQuery("#reportType").val();
        renew_sta_container(productModel, updateSuccess, reportType);
    }

</script>

</body>
</html>