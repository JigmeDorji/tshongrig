<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 7/30/2019
  Time: 8:52 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Trial Balance</title>

<body>
<div class="page_title">
    <span class="title">Financial Statement</span>
    <span class="subtitle">Trial Balance Report</span>
</div>
<form id="ledgerForm" action="<c:url value='/accTrialBalance'/> " class="form-horizontal globalForm">
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
        </div>
    </fieldset>

    <fieldset>
        <div class="col-md-12">
            <table class="table navigatable_table" id="trialBalanceGrid">
                <thead>
                <tr>
                    <th rowspan="2"><p style="text-align:center">Particulars</p></th>
                    <th colspan="3">Closing Balance</th>
                </tr>
                <tr>
                    <th width="15%">Debit</th>
                    <th width="15%">Credit</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
                <tfoot>
                <tr>
                    <th>Grand Total</th>
                    <th class="right-align"><input type="text" class="right-align" id="totalDrAmount"
                                                   style="border: none;font-size:15px;"></th>
                    <th class="right-align"><input type="text" class="right-align" id="totalCrAmount"
                                                   style="border: none;font-size:15px;"></th>
                    <th></th>
                </tr>
                </tfoot>
            </table>
        </div>
    </fieldset>
</form>
</body>
</html>



