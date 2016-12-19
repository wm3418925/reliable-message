<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title><spring:message code="IndexPage" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
    <spring:message code="Hello" /> app1.<br/>

    <shiro:guest>
        <a href="${pageContext.request.contextPath}/login?backUrl=${pageContext.request.contextPath}"><spring:message code="Login" /></a>
    </shiro:guest>

    <shiro:authenticated>
        <spring:message code="Hello" /><shiro:principal/><spring:message code="Login" /><br/>
        <shiro:hasRole name="role1">
            <spring:message code="YouHaveRole" /> role1<br/>
        </shiro:hasRole>
        <shiro:lacksRole name="role1">
            <spring:message code="YouHaveNoRole" /> role1<br/>
        </shiro:lacksRole>
        <shiro:lacksRole name="role2">
            <spring:message code="YouHaveNoRole" /> role2<br/>
        </shiro:lacksRole>

        <h2><spring:message code="SetSessionAttribute" /></h2>
        <form action="${pageContext.request.contextPath}/attr" method="post">
            <spring:message code="Key" />: <input type="text" name="key">
            <spring:message code="Value" />: <input type="text" name="value">
            <input type="submit" value="<spring:message code='SetSessionAttribute' />">
        </form>
        <h2><spring:message code="GetSessionAttribute" /></h2>
        <form action="${pageContext.request.contextPath}/attr" method="get">
            <spring:message code="Key" />: <input type="text" name="key">
            <spring:message code="Value" />: <input type="text" value="${value}">
            <input type="submit" value="<spring:message code='GetSessionAttribute' />">
        </form>
    </shiro:authenticated>


	<a href="changeLanguage/zh-cn"><spring:message code="Chinese" /></a>
	<a href="changeLanguage/en"><spring:message code="English" /></a>

</body>
</html>
