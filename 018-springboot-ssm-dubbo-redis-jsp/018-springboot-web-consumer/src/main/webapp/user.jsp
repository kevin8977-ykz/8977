<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>用户列表</title>
</head>
<body>

<%--${user}--%>
<table >
    <%--表头--%>
        <tbody>
    <c:forEach items="${user}" var="v"  >
        <tr>
            <td>${v}</td>
        </tr>
    </c:forEach>
        </tbody>
</table>


</body>
</html>
