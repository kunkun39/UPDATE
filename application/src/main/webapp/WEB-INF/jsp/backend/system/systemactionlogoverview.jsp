<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>IP广义升级云端</title>
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
            <form action="${pageContext.request.contextPath}/backend/systemactionlogoverview.html" class="search_form" method="POST">
                <div class="search">
                    <span>
                        时间:
                        <select name="logYear" style="width: 100px;">
                            <option value="2013" <c:if test="${logYear==2013}">selected="true"</c:if>>2013</option>
                            <option value="2014" <c:if test="${logYear==2014}">selected="true"</c:if>>2014</option>
                            <option value="2015" <c:if test="${logYear==2015}">selected="true"</c:if>>2015</option>
                            <option value="2016" <c:if test="${logYear==2016}">selected="true"</c:if>>2016</option>
                            <option value="2017" <c:if test="${logYear==2017}">selected="true"</c:if>>2017</option>
                            <option value="2018" <c:if test="${logYear==2018}">selected="true"</c:if>>2018</option>
                        </select>
                        &nbsp;年
                    </span>
                    <span>
                        <select name="logMonth" style="width: 100px;">
                            <option value="1" <c:if test="${logMonth==1}">selected="true"</c:if>>1</option>
                            <option value="2" <c:if test="${logMonth==2}">selected="true"</c:if>>2</option>
                            <option value="3" <c:if test="${logMonth==3}">selected="true"</c:if>>3</option>
                            <option value="4" <c:if test="${logMonth==4}">selected="true"</c:if>>4</option>
                            <option value="5" <c:if test="${logMonth==5}">selected="true"</c:if>>5</option>
                            <option value="6" <c:if test="${logMonth==6}">selected="true"</c:if>>6</option>
                            <option value="7" <c:if test="${logMonth==7}">selected="true"</c:if>>7</option>
                            <option value="8" <c:if test="${logMonth==8}">selected="true"</c:if>>8</option>
                            <option value="9" <c:if test="${logMonth==9}">selected="true"</c:if>>9</option>
                            <option value="10" <c:if test="${logMonth==10}">selected="true"</c:if>>10</option>
                            <option value="11" <c:if test="${logMonth==11}">selected="true"</c:if>>11</option>
                            <option value="12" <c:if test="${logMonth==12}">selected="true"</c:if>>12</option>
                        </select>
                        &nbsp;月
                    </span>
                    <input type="button" value="查询" onclick="this.form.submit();"/>
                </div>
            </form>

            <table width="100%" cellpadding="0" cellspacing="0" class="list">
                <thead>
                    <td width="15%">&nbsp;&nbsp;操作时间</td>
                    <td width="15%">操作用户</td>
                    <td width="70%">描述</td>
                </thead>
                <tbody>
                <c:set var="turns" value="true"/>
                <c:forEach items="${logs}" var="log">
                    <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                    <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                    <c:set var="turns" value="${!turns}"/>
                        <td>&nbsp;&nbsp;
                            <ch:timeformat value="${log.time}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                        <td>${log.userName} </td>
                        <td>${log.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/systemactionlogoverview.html" showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>
</body>
</html>