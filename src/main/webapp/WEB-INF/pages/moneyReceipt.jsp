<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 10/27/2020
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Money Receipt</title>

<body>
<input type="hidden" id="businessType" value="${currentUser.businessType}">
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Money Receipt</span>
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

            <c:if test="${currentUser.businessType !=7}">
                <%--This form is all for the business type axcept NGO--%>
                <form id="registrationForm" action="<c:url value='/moneyReceipt'/> " class="form-horizontal globalForm">
                    <fieldset>
                        <legend class="text-uppercase font-size-sm font-weight-bold">Money Receipt Generation</legend>
                        <div class="form-group row">
                            <label class="col-md-3  required">Receipt No.</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm resetfield"
                                       name="receiptNo" readonly
                                       id="receiptNo" value="${moneyReceiptNo}" required="true"/>
                            </div>
                            <label class="col-md-2  required">Receipt Date </label>

                            <div class="col-md-3">
                                <input type="text" tabindex="2" class="form-control form-control-sm  formatDate "
                                       value="${currentDate}"
                                       placeholder="DD.MM.YYYY" id="receiptDate" name="receiptDate" required="required"
                                       />
                            </div>
                        </div>


                        <div class="form-group row">
                            <label class="col-md-3  required">Received with thanks from </label>

                            <div class="col-md-4">
                                <form:select class="form-control form-control-sm select2 resetField" path="partyList"
                                             id="partyLedgerId"
                                             required="required"
                                             name="partyLedgerId">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${partyList}" itemValue="id" itemLabel="text"/>
                                </form:select>
                            </div>


                        </div>
                        <div class="form-group row">
                            <label class=" col-md-3 left-label required">Received Amount</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount"
                                       name="amount"
                                       id="amount"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class=" col-md-3 left-label required">TDS Type</label>

                            <div class="col-md-4">
                                <select class="form-control form-control-sm" id="tDSType">
                                    <option value="">---Please Select---</option>
                                    <option value="1">Bhutanese contract</option>
                                    <option value="2">Real estate rental</option>
                                    <option value="3">International party</option>
                                    <option value="4">N/A</option>
                                </select>
                            </div>
                                <%-- <label class=" col-md-1 left-label">TDS %</label>

                                 <div class="col-md-1">
                                     <input type="text" tabindex="1" class="form-control form-control-sm  amount" readonly
                                            id="tDSPercentage"  placeholder="Auto"/>
                                 </div>--%>

                            <label class=" col-md-1 left-label">TDS Amt</label>

                            <div class="col-md-2">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount" readonly
                                       name="tDSAmount"
                                       id="tDSAmount" placeholder="Auto"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class=" col-md-3 left-label">Retention Amount</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount"
                                       name="retentionAmount"
                                       id="retentionAmount"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class=" col-md-3 left-label">Mobilization Amount</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount"
                                       name="mobilizationAdvAmount"
                                       id="mobilizationAdvAmount"/>
                            </div>
                            <label class=" col-md-1 left-label">Party</label>

                            <div class="col-md-4">
                                <form:select class="form-control form-control-sm select2 resetField"
                                             path="mobilizationAdvPartyList"
                                             id="mobilizationAdvPartyLedgerId"
                                             name="mobilizationAdvPartyLedgerId">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${mobilizationAdvPartyList}" itemValue="id" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class=" col-md-3 left-label">Material Advance</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount"
                                       name="materialAdvAmount"
                                       id="materialAdvAmount"/>
                            </div>
                            <label class=" col-md-1 left-label">Party</label>

                            <div class="col-md-4">
                                <form:select class="form-control form-control-sm select2 resetField"
                                             path="materialAdvPartyList"
                                             id="materialAdvPartyLedgerId"
                                             name="materialAdvPartyLedgerId">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${materialAdvPartyList}" itemValue="id" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class=" col-md-3 left-label required">Received In</label>

                            <div class="col-md-4">
                                <select class="form-control form-control-sm select2 resetField" id="isCash"
                                        required="required"
                                        name="isCash">
                                    <option value="">---Please Select---</option>
                                    <option value="1" id="cashId">Cash</option>
                                    <option value="2" id="bankId">Bank</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row" id="bankDetails" hidden>
                            <label class=" col-md-3 left-label">Select Bank Account</label>

                            <div class="col-md-3">
                                <form:select class="form-control form-control-sm resetField" path="bankList"
                                             id="bankLedgerId" required="required"
                                             name="bankLedgerId">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>


                        <div class="form-group row col-md-12">
                            <div class="col-md-2 col-lg-offset-3">
                                <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8"
                                       id="btnReset">
                            </div>
                            <div class="col-md-2" id="saveBtnDiv">
                                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9"
                                       id="btnSave">
                            </div>
                        </div>

                    </fieldset>
                </form>
            </c:if>
            <c:if test="${currentUser.businessType ==7}">
                <%--This form is used for only NGO business Type--%>
                <form id="registrationForm" action="<c:url value='/moneyReceipt'/> " class="form-horizontal globalForm">
                    <fieldset>
                        <legend class="text-uppercase font-size-sm font-weight-bold">Money Receipt Generation</legend>
                        <div class="form-group row">
                            <label class="col-md-3  required">Receipt No.</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm resetfield"
                                       name="receiptNo" readonly
                                       id="receiptNo" value="${moneyReceiptNo}" required="true"/>
                            </div>
                            <label class="col-md-2  required">Receipt Date </label>

                            <div class="col-md-3">
                                <input type="text" tabindex="2" class="form-control form-control-sm  formatDate "
                                       value="${currentDate}"
                                       placeholder="DD.MM.YYYY" id="receiptDate" name="receiptDate" required="required"/>
                            </div>
                        </div>


                        <div class="form-group row">
                            <label class="col-md-3  required">Received with thanks from </label>

                            <div class="col-md-4">
                                <input type="text" class="form-control form-control-sm" name="receivedFrom"
                                       id="receivedFrom" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-3  required">Description </label>

                            <div class="col-md-4">
                                <input type="text" class="form-control form-control-sm" name="ngoDescription"
                                       id="ngoDescription" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class=" col-md-3 left-label required">Received Amount</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control form-control-sm  amount"
                                       name="amount" required
                                       id="amount"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class=" col-md-3 left-label required">Received In</label>

                            <div class="col-md-4">
                                <select class="form-control form-control-sm select2 resetField" id="isCash"
                                        required="required"
                                        name="isCash">
                                    <option value="">---Please Select---</option>
                                    <option value="1" id="cashId">Cash</option>
                                    <option value="2" id="bankId">Bank</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row" id="bankDetails" hidden>
                            <label class=" col-md-3 left-label">Select Bank Account</label>

                            <div class="col-md-3">
                                <form:select class="form-control form-control-sm resetField" path="bankList"
                                             id="bankLedgerId" required="required"
                                             name="bankLedgerId">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group row col-md-12">
                            <div class="col-md-2 col-lg-offset-3">
                                <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8"
                                       id="btnReset">
                            </div>
                            <div class="col-md-2" id="saveBtnDiv">
                                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9"
                                       id="btnSave">
                            </div>
                        </div>

                    </fieldset>
                </form>
            </c:if>




        </div>
    </div>
</div>
</body>
</html>




