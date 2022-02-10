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
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Payable</span>
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
            <form id="payableForm" action="<c:url value='/payable'/> " class="form-horizontal payableForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Payable Booking Detail</legend>
                    <input type="hidden" class="form-control form-control-sm" id="ledgerId" name="ledgerId">
                    <input type="hidden" class="form-control form-control-sm" id="partyLedgerId" name="partyLedgerId">
                    <input type="hidden" class="form-control form-control-sm" id="adjustedAgainstHiddenText">

                    <div class="form-group row">

                        <label class="col-md-2 right-align required">Voucher Date </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm formatDate"
                                   name="autoVoucherDate"
                                   id="autoVoucherDate" required="true" value="${paymentDate}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required" id="paidToText">Expenditure</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm" id="expenditure" name="expenditure"
                                   required>
                        </div>
                    </div>
                    <div class="form-group row costContent hidden">
                        <label class="col-md-2 right-align required">Cost</label>

                        <div class="col-md-3">
                            <select class="form-control form-control-sm select2" id="costType" style="width:100%;"
                                    name="costId">
                                <option value="">---Please select ---</option>
                                <option value="1" id="generalId">General</option>
                                <option value="2" id="productionId">Production</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required" id="descriptionText">Party</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm" name="partyName"
                                   id="partyName" required="true"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Amount</label>

                        <div class="col-md-3">

                            <input type="text" tabindex="3" class="form-control form-control-sm text-right"
                                   name="amount" required
                                   id="amount"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2"></div>
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


