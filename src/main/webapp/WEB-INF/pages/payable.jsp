<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/23/2022
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Payable</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Payable</span>
</div>
<form id="payableForm" action="<c:url value='/payable'/> " class="form-horizontal payableForm">
    <fieldset>
        <legend>Voucher</legend>
        <input type="hidden" class="form-control" id="ledgerId" name="ledgerId">
        <input type="hidden" class="form-control" id="partyLedgerId" name="partyLedgerId">
        <input type="hidden" class="form-control" id="adjustedAgainstHiddenText">

        <div class="form-group">

            <label class="col-md-2 right-align required">Voucher Date </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control formatDate" name="autoVoucherDate"
                       id="autoVoucherDate" required="true" value="${paymentDate}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required" id="paidToText">Expenditure</label>

            <div class="col-md-3">
                <input type="text" class="form-control" id="expenditure" name="expenditure" required>
            </div>
        </div>
        <div class="form-group costContent hidden">
            <label class="col-md-2 right-align required">Cost</label>

            <div class="col-md-3">
                <select class="form-control select2" id="costType" style="width:100%;"
                        name="costId">
                    <option value="">---Please select ---</option>
                    <option value="1" id="generalId">General</option>
                    <option value="2" id="productionId">Production</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required" id="descriptionText">Party</label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control" name="partyName"
                       id="partyName" required="true"/>
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


