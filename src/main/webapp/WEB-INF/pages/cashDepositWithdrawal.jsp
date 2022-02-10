<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 5/15/2021
  Time: 1:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Cash Deposit Withdrawal</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Cash Deposit/Withdrawal</span>
</div>
<form id="cashDepositWithdrawalForm" action="<c:url value='/cashDepositWithdrawal'/> "
      class="form-horizontal globalForm">
    <fieldset>
        <legend>Voucher</legend>

        <div class="form-group">

            <label class="col-md-2 right-align required">Voucher Date </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control formatDate" name="autoVoucherDate"
                       id="autoVoucherDate" required="true" value="${paymentDate}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Cash Deposit/Withdrawal</label>

            <div class="col-md-3">
                <select class="form-control" id="cashDepositWithdrawalType" required
                        name="cashDepositWithdrawalType">
                    <option value="">---Please select ---</option>
                    <option value="1" id="costId">Deposit</option>
                    <option value="2" id="advanceId">Withdrawal</option>
                </select>
            </div>
        </div>

        <div class="form-group">

            <label class=" col-md-2 right-align required" id="depositWithdrawalId">Deposited to </label>

            <div class="col-md-3">
                <form:select class="form-control resetField" path="bankList" id="bankLedgerId"
                             required="required"
                             name="bankLedgerId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Amount </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control text-right" name="depositedAmount"
                       id="depositedAmount" required="true"/>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-2  col-lg-offset-2">
                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9" id="btnCashDepositWit">
            </div>

            <div class="col-md-2">
                <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8" id="btnReset">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>




