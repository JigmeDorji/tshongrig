<%--
  Created by IntelliJ IDEA.
  User: vcass
  Date: 11/30/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">View Stock Value</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">View Stock Value</span>
</div>
<form id="viewItemForm" action="<c:url value='/viewStockValue'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Current Stock Value</legend>
    </fieldset>
</form>
</body>
</html>


