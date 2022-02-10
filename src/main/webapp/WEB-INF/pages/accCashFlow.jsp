<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 8/4/2019
  Time: 7:37 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Cash Flow</title>

<body>

<div class="page_title">
    <span class="title">Financial Statement</span>
    <span class="subtitle">Cash Flow</span>
</div>
<form id="ledgerForm" action="<c:url value='/accCashFlow'/> " class="form-horizontal globalForm">

    <fieldset>
        <div class="form-group">
            <label class="col-md-2 text-right required">From Date</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control datepicker" name="fromDate"
                       id="fromDate" required="required" value="${fromDate}"/>
            </div>
            <label class="col-md-2 text-right required">To Date</label>

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
            <table class="table table-bordered" id="cashFlowGrid">
                <thead>
                <tr>
                    <th></th>
                    <th width="70%" class="left-align">Particulars</th>
                    <th width="15%">Amount</th>
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



