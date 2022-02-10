<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 11/14/2020
  Time: 6:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Year Closing</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Year Closing</span>
</div>
<div id="dialog" style="display: none;">
    <div>
        <iframe id="frame"></iframe>
    </div>
</div>
<form id="pendingBillForm" action="<c:url  value='/yearClosing'/> "
      class="form-horizontal globalForm">
    <div class="col-md-10">
        <fieldset>
            <legend>Current Year</legend>
            <div class="form-group">
                <label class="col-md-2 text-right required right-label">From Date</label>

                <div class="col-md-2">
                    <input type="text" class="form-control datepicker" name="fromDate" readonly
                           id="fromDate" required="required" value="${fromDate}"/>
                </div>
                <label class="col-md-2 text-right right-label required">To Date</label>

                <div class="col-md-2">
                    <input type="text" class="form-control datepicker" name="toDate" readonly
                           id="toDate" required="required" value="${toDate}"/>
                </div>

                <div class="col-md-2">
                    <input type="submit" class="btn btn-primary btn-sm" value="Close" id="btnYearClose">
                </div>
            </div>

        </fieldset>
    </div>
</form>
</body>
</html>





