<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
	<title>IP Update Cloud Platform</title>
    <link rel="Bookmark" href="${pageContext.request.contextPath}/images/favicon.gif">
    <link rel="Shortcut Icon" href="${pageContext.request.contextPath}/images/favicon.gif">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/login/style/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/login/style/login.css" />
</head>

<body>
    <form id="reportForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" name="form" runat="server">
        <div class="login-box" >
    	    <div class="login-form">

            	<dl>
                	<dt>Username: </dt>
                    <dd><input id="txtUserName" name="j_username" runat="server" type="text" class="login-textbox" style="margin-left: 10px"/></dd>
                </dl>
                <dl>
                	<dt>Password: </dt>
                    <dd><input id="txtUserPass" name="j_password" runat="server" type="password" class="login-textbox" style="margin-left: 10px"/></dd>
                </dl>
                <dl>
                	<dt></dt>
                </dl>
                <div class="login-action">
                    <input type="button" ID="btnLogin" runat="server" Text="" class="login-button" style="margin-left: 10px" onclick="this.form.submit();" />
                    <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                        <br/>
                        <br/>
                        <label ID="lblErrMsg" class="login-tip" >sorry, the username or password is not right!</label>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
</body>

</html>
