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
<title class="title">Daily Sale Report</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">Daily sale Report</span>
</div>
<form id="saleRecordFrom" action="<c:url  value='/saleRecord/generateReport'/> " class="form-horizontal globalForm"
      target="_blank">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <%--<input type="hidden" id="itemCategoryId" name="itemCategoryId">--%>
    <input type="hidden" id="permissionType" value="${permissionType}">
    <fieldset>
        <legend>Date range</legend>
        <div class="form-group">
            <label class="col-md-2 right-align required">From</label>

            <div class="col-md-2">
                <input type="text" tabindex="2" class="form-control datepicker" name="fromDate"
                       id="fromDate" required/>
            </div>

            <label class="col-md-1 right-align required">To</label>

            <div class="col-md-2">
                <input type="text" tabindex="3" class="form-control datepicker" name="toDate"
                       id="toDate" required/>
            </div>
        </div>
    </fieldset>
    <br>

    <div class="col-lg-offset-5 col-md-2">
        <input type="submit" class="btn btn-primary btn-block " value="View" id="generateReport">
    </div>
</form>
</body>
</html>


