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
                    <span>
                        时间:
                        <select id="reportYear" style="width: 100px;">
                            <option value="2013" <c:if test="${reportYear==2013}">selected="true"</c:if>>2013</option>
                            <option value="2014" <c:if test="${reportYear==2014}">selected="true"</c:if>>2014</option>
                            <option value="2015" <c:if test="${reportYear==2015}">selected="true"</c:if>>2015</option>
                            <option value="2016" <c:if test="${reportYear==2016}">selected="true"</c:if>>2016</option>
                            <option value="2017" <c:if test="${reportYear==2017}">selected="true"</c:if>>2017</option>
                            <option value="2018" <c:if test="${reportYear==2018}">selected="true"</c:if>>2018</option>
                        </select>
                        &nbsp;年
                    </span>
                    <span>
                        <select id="reportMonth" style="width: 100px;">
                            <option value="1" <c:if test="${reportMonth==1}">selected="true"</c:if>>1</option>
                            <option value="2" <c:if test="${reportMonth==2}">selected="true"</c:if>>2</option>
                            <option value="3" <c:if test="${reportMonth==3}">selected="true"</c:if>>3</option>
                            <option value="4" <c:if test="${reportMonth==4}">selected="true"</c:if>>4</option>
                            <option value="5" <c:if test="${reportMonth==5}">selected="true"</c:if>>5</option>
                            <option value="6" <c:if test="${reportMonth==6}">selected="true"</c:if>>6</option>
                            <option value="7" <c:if test="${reportMonth==7}">selected="true"</c:if>>7</option>
                            <option value="8" <c:if test="${reportMonth==8}">selected="true"</c:if>>8</option>
                            <option value="9" <c:if test="${reportMonth==9}">selected="true"</c:if>>9</option>
                            <option value="10" <c:if test="${reportMonth==10}">selected="true"</c:if>>10</option>
                            <option value="11" <c:if test="${reportMonth==11}">selected="true"</c:if>>11</option>
                            <option value="12" <c:if test="${reportMonth==12}">selected="true"</c:if>>12</option>
                        </select>
                        &nbsp;月
                    </span>
                    <input type="button" value="查询" onclick="generateReport(); "/>
                </div>
            </form>

            <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="container1" style="width: 90%; height: 350px; margin: 0 auto"></div>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--<td>--%>
                        <%--&nbsp;--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>--%>
                        <%--<div id="container2" style="width: 90%; height: 350px; margin: 0 auto"></div>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">

    function renew_sta_container(productModel, year, month) {
        if(productModel == '') {
            return;
        }

        SystemDWRHandler.obtainDailyClientUpdateAmountByMonth(productModel, year, month, function(result) {
            var statisticData = JSON.parse(result);
            var total = statisticData[0].total.split(",");

            var days = statisticData[0].days;
            if(parseInt(days) == 28) {
                sta_container1.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28'];
            } else if (parseInt(days) == 29) {
                sta_container1.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29'];
            } else if (parseInt(days) == 30) {
                sta_container1.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
            } else {
                sta_container1.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
            }

            var newData = new Array();
            var totalUpdateTimes = 0;
            for(var i=0; i<total.length; i++) {
                newData[i] = parseInt(total[i]);
                totalUpdateTimes = totalUpdateTimes + parseInt(total[i]);
            }
            sta_container1.series[0].data = newData;

            sta_container1.title.text = "产品(" + productModel + ")" + year + "年" + month + "月用户固件更新统计, 升级总次数" + totalUpdateTimes;
            new Highcharts.Chart(sta_container1);
        });

//        SystemDWRHandler.obtainDailyClientUpdateAmountByVersion(productModel, year, month, function(result) {
//            var statisticData = JSON.parse(result);
//            sta_container2.title.text = year + "年" + month + "月用户固件升级比例";
//
//            var newData = new Array();
//            for(var i=0; i<statisticData.length; i++) {
//                var inner = new Array();
//                inner[0] = statisticData[i].version;
//                inner[1] = statisticData[i].total;
//                newData[i] = inner;
//            }
//
//            sta_container2.series[0].data = newData;
//            new Highcharts.Chart(sta_container2);
//        });
    }

    function generateReport() {
        var productModel = $("#reportProduct").val();
        var year = $("#reportYear").val();
        var month = $("#reportMonth").val();
        renew_sta_container(productModel, year, month);
    }

    jQuery(function() {
        var today = new Date();
        var year = today.getYear() + 1900;
        var month = today.getMonth() + 1;
        renew_sta_container('', year, month);
    });

</script>

</body>
</html>