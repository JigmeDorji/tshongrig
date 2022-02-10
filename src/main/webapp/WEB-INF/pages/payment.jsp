<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Payment</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Payment</span>
</div>
<form id="paymentForm" action="<c:url value='/payment'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Payment</legend>

        <div class="form-group">

            <label class="col-md-2 right-align required">Voucher Date </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control formatDate" name="autoVoucherDate"
                       id="autoVoucherDate" required value="${paymentDate}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required" id="paidToText">Paid To</label>

            <div class="col-md-3">
                <input type="text" class="form-control" id="paidTo" name="paidTo" required>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Paid For</label>

            <div class="col-md-3">
                <select class="form-control" id="paidForId" required
                        name="paidForTypeId">
                    <option value="">---Please select ---</option>
                    <option value="1" id="costId">Cost</option>
                    <option value="2" id="advanceId">Advance</option>
                    <option value="3" id="repaymentId">Repayment</option>
                    <option value="4" id="remittance">Remittance</option>
                    <option value="5" id="payable">Payable</option>
                </select>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required" id="descriptionText">Description</label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control autocomplete" name="description"
                       id="description" required="true"/>

                <input type="hidden" tabindex="2" class="form-control" name="ledgerId"
                       id="ledgerId" required="true"/>
            </div>
            <div class="hidden costContent">
                <label class="col-md-2 right-align required">Cost</label>

                <div class="col-md-3">
                    <select class="form-control" id="costType" style="width: 100%" required
                            name="costId">
                        <option value="">---Please select ---</option>
                        <option value="1" id="generalId">General</option>
                        <option value="2" id="productionId">Production</option>
                    </select>
                </div>

            </div>
        </div>

        <div class="form-group">

            <label class="col-md-2 right-align required">Amount</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control text-right" name="amount"
                       id="amount" required="true"/>
            </div>

        </div>


        <div class="form-group">
            <label class="col-md-2 right-align required">TDS Type</label>

            <div class="col-md-3">
                <select class="form-control" id="tdsType"
                        name="tdsType">
                    <option value="">---Please select ---</option>
                    <option value="1">Bhutanese Contract</option>
                    <option value="2">Hiring</option>
                    <option value="3">Real Estate</option>
                    <option value="4">International contract</option>
                    <option value="5">Not applicable</option>
                </select>
            </div>

            <label class="col-md-2 right-align required">TDS Amount</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control" name="tdsAmount" readonly
                       id="tdsAmount"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Deducted From</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control" name="deductedFrom"
                       id="deductedFrom"/>
            </div>

            <label class="col-md-2 right-align required">Deducted Amount </label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control" name="deductedAmount"
                       id="deductedAmount"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-2 right-align required">Amount Paid</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control" name="amountPaid" readonly
                       id="amountPaid"/>
            </div>
            <label class="col-md-2 right-align required" id="paidInText">Paid In </label>

            <div class="col-md-3">
                <select class="form-control" id="isCash" required
                        name="isCash">
                    <option value="" id="id">---Please select ---</option>
                    <option value="1" id="cashId">Cash</option>
                    <option value="2" id="bankId">Bank</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div id="bankDetails" hidden>
                <label class=" col-md-2 right-align required">Select Bank Account</label>

                <div class="col-md-3">
                    <form:select class="form-control resetField" path="bankList" id="bankLedgerId"
                                 cssStyle="width: 100%"
                                 required="required"
                                 name="bankLedgerId">
                        <form:option value="">---Please Select---</form:option>
                        <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                    </form:select>
                </div>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-2  col-lg-offset-2" id="saveBtnDiv">
                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9" id="btnSave">
            </div>

            <div class="col-md-2">
                <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8" id="btnReset">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>



