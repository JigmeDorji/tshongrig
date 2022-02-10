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
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Cash Deposit/Withdrawal</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="cashDepositWithdrawalForm" action="<c:url value='/cashDepositWithdrawal'/> "
                  class="form-horizontal globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Voucher Detail</legend>

                    <div class="form-group row">

                        <label class="col-md-2 right-align required">Voucher Date </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm formatDate" name="autoVoucherDate"
                                   id="autoVoucherDate" required="true" value="${paymentDate}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Cash Deposit/Withdrawal</label>

                        <div class="col-md-3">
                            <select class="form-control form-control-sm" id="cashDepositWithdrawalType" required
                                    name="cashDepositWithdrawalType">
                                <option value="">---Please select ---</option>
                                <option value="1" id="costId">Deposit</option>
                                <option value="2" id="advanceId">Withdrawal</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-2 right-align required" id="depositWithdrawalId">Deposited to </label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerId"
                                         required="required"
                                         name="bankLedgerId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Amount </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm text-right" name="depositedAmount"
                                   id="depositedAmount" required="true"/>
                        </div>
                    </div>


                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <div class="col-md-2">
                            <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9" id="btnCashDepositWit">
                        </div>

                        <div class="col-md-2">
                            <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8" id="btnReset">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

</body>
</html>




