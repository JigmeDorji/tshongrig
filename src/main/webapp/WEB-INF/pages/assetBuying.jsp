<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 10/4/2021
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<title class="title">Buying Asset</title>

<body>
<div class="page_title">
    <span class="title">Fix Asset Management</span>
    <span class="subtitle">Buying Asset</span>
</div>
<form id="openingAndBuyingAssetForm" action="<c:url value='/assetBuying'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Purchase Detail</legend>
        <div class="form-group">
            <label class="col-md-2 right-align">Purchase Date:</label>
            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control  right-align common datepicker" name="purchaseDate"
                       value="${date}"
                       id="purchaseDate"/>
            </div>

            <label class="col-md-2 right-align">Purchase Invoice:</label>
            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control  right-align common" required
                       name="purchaseInvoiceNo" id="purchaseInvoiceNo"/>
            </div>
        </div>

        <br/>
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" id="fixedAssetBuyingGrid">
                <thead>
                <tr>
                    <th width="2%">SL.</th>
                    <th width="20%">Particular</th>
                    <%--                    <th width="10%">Group</th>--%>
                    <th width="5%">Rate</th>
                    <th width="5%">Qty</th>
                    <th width="8%">Total</th>
                    <th width="4%">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7">Total Amount</label>

                <div class="col-md-3">
                    <input type="text"
                           class="form-control right-align numeric resetField"
                           value="0" name="amount"
                           id="grandTotal" readonly>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7">Paid In</label>

                <div class="col-md-3">
                    <select class="form-control" id="isCash" required="required"
                            name="isCash">
                        <option value="">---Please select---</option>
                        <option value="1" id="cashId">Cash</option>
                        <option value="2" id="bankId">Bank</option>
                        <option value="3" id="creditId">Credit</option>
<%--                        <option value="4" id="cashBanKId">Both (Cash & Bank)</option>--%>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7">Discount</label>

                <div class="col-md-3">
                    <input type="text"
                           class="form-control right-align amount resetField"
                           value="0" readonly
                           id="discountRate" name="discountRate">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7" id="amountReceivedText">Amount Received</label>

                <div class="col-md-3">
                    <input type="text"
                           class="form-control right-align amount" name="amtReceived"
                           id="amtReceived" value="0">
                </div>
            </div>

            <div class="form-group" id="bankDetails" hidden>
                <label class="col-md-2 right-label col-lg-offset-7">Select Bank Account</label>

                <div class="col-md-3">
                    <form:select class="form-control resetField" path="bankList" id="bankLedgerId"
                                 required="required"
                                 name="bankLedgerId">
                        <form:option id="empyId" value="">---Please Select---</form:option>
                        <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                    </form:select>
                </div>
            </div>

            <div class="form-group" id="bankAmountId" hidden>
                <label class="col-md-2 right-label col-lg-offset-7">Amount in Bank</label>

                <div class="col-md-3">
                    <input type="text"
                           class="form-control right-align amount" name="amountReceivedInBank"
                           id="amountReceivedInBank" value="0">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7">Returnable Amount</label>

                <div class="col-md-3">
                    <input type="text" class="form-control   right-align" value="0" name="amtReturn"
                           id="amtReturn" readonly>
                </div>
            </div>

            <div class="creditDetails" hidden>
                <div class="form-group">
                    <label class="col-md-2 right-label col-lg-offset-7 required">Party Name</label>

                    <div class="col-md-3">
                        <input type="text" tabindex="2" class="form-control  resetField right-align" id="partyName"
                               name="partyName"/>
                    </div>
                </div>
                <div class="form-group creditSale">
                    <label class="col-md-2 right-label col-lg-offset-7 required">Address </label>

                    <div class="col-md-3">
                        <input type="text" tabindex="4" class="form-control resetfield" name="partyAddress"
                               id="partyAddress" required="true"/>
                    </div>

                </div>

                <div class="form-group creditSale">
                    <label class="col-md-2 right-label col-lg-offset-7 required">Contact No.</label>

                    <div class="col-md-3">
                        <input type="text" required="true" tabindex="5" class="form-control resetfield"
                               name="partyContactNo"
                               id="partyContactNo"/>
                    </div>
                </div>

                <div class="form-group creditSale">
                    <label class="col-md-2 right-label col-lg-offset-7">Email ID.</label>

                    <div class="col-md-3">
                        <input type="text" tabindex="6" class="form-control resetfield" name="partyEmail" required
                               id="partyEmail"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <sec:authorize access="hasAuthority('14-ADD')">
                <div class="col-md-2 col-lg-offset-7">
                    <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
                </div>
            </sec:authorize>
        </div>
    </fieldset>
</form>
</body>
</html>