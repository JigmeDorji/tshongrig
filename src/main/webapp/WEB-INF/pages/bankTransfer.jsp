<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/23/2022
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Bank Transfer</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Bank Transfer</span>
</div>
<form id="bankTransferForm" action="<c:url value='/bankTransfer/'/> " class="form-horizontal bankTransferForm">
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
            <label class="col-md-2 right-align required" id="paidToText">From</label>

            <div class="col-md-3">
                <form:select class="form-control resetField" path="bankList" id="bankLedgerFromId" required="required"
                             name="bankLedgerFromId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required" id="descriptionText">To</label>

            <div class="col-md-3">
                <form:select class="form-control resetField" path="bankList" id="bankLedgerToId" required="required"
                             name="bankLedgerToId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Amount</label>
            <div class="col-md-3">

                <input type="text" tabindex="3" class="form-control text-right" name="amount" required
                       id="amount"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2  col-lg-offset-2">
                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>
