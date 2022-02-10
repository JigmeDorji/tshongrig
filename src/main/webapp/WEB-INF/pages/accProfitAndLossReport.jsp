<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/4/2019
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Income & Expenditure</title>

<body>

<div class="page_title">
    <span class="title">Financial Statement</span>
    <span class="subtitle">Income & Expenditure</span>
</div>
<form id="ledgerForm" action="<c:url value='/accProfitAndLossReport'/> " class="form-horizontal globalForm">
    <input type="hidden" id="ledgerId" name="ledgerId">
    <fieldset>
        <div class="form-group">
            <label class="col-md-2 text-right required">From</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control datepicker" name="fromDate"
                       id="fromDate" required="required" value="${fromDate}"/>
            </div>
            <label class="col-md-2 text-right required">To</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control datepicker" name="toDate"
                       id="toDate" required="required" value="${toDate}"/>
            </div>
          <%--  <div class="col-md-2">
                <input type="button" class="btn btn-primary btn-xs fa-search" id="searchBtn" value=" Search">
            </div>--%>
        </div>
    </fieldset>
    <fieldset>
        <div class="col-md-12">
            <table class="table table-bordered navigatable_table" id="pnlTable">
                <thead>
                <tr>
                    <th width="70%" height="40px" class="left-align">Particular</th>
                    <th width="30%">Amount</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </fieldset>
</form>
</body>
</html>


