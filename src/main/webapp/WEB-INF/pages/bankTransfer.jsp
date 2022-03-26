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
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Bank Transfer</span>
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
            <form id="bankTransferForm" action="<c:url value='/bankTransfer/'/> " class="form-horizontal bankTransferForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Bank Transfer Detail</legend>

                    <div class="form-group row">

                        <label class="col-md-2 right-align required">Voucher Date </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm formatDate" name="autoVoucherDate"
                                   id="autoVoucherDate" required="true" placeholder="DD.MM.YYYY"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required" id="paidToText">From</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerFromId" required="required"
                                         name="bankLedgerFromId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required" id="descriptionText">To</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerToId" required="required"
                                         name="bankLedgerToId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Amount</label>
                        <div class="col-md-3">

                            <input type="text" tabindex="3" class="form-control form-control-sm text-right amount" name="amount" required
                                   id="amount"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2">
                        </div>
                        <div class="col-md-2">
                            <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9">
                        </div>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>
</div>

</body>
</html>
