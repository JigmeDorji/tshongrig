<%--
  Created by IntelliJ IDEA.
  User: vcass
  Date: 11/30/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Sale Item</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">Sale Item</span>
</div>
<div id="dialog" style="display: none;">
    <div>
        <iframe id="frame"></iframe>
    </div>
</div>
<form id="saleItemForm" action="<c:url value='/saleItem'/> " class="form-horizontal globalForm" target="_blank">
    <input type="hidden" id="voucherNo" value="${voucherNo}" name="voucherNo">
    <input type="hidden" id="receiptMemoNo" value="${receiptMemoNo}" name="receiptMemoNo">
    <input type="hidden" id="companyName" value="${companyName}">
    <input type="hidden" id="contact" value="${contact}">
    <input type="hidden" id="email" value="${email}">
    <input type="hidden" id="companyAdd" value="${companyAdd}">
    <fieldset>
        <div class="form-group">
            <label class="col-md-3 left-label required">Counter/Supply</label>

            <div class="col-md-4">
                <select class="form-control select2" id="counterOrSupply" required="required">
                    <%--                    <option value="">---Please select---</option>--%>
                    <option value="1" id="counter">Counter</option>
                    <option value="2" id="supply">Supply</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 left-label required">Search By Item Code:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control  resetField right-align" id="itemCode"
                       name="itemCode"/>
            </div>
        </div>

        <div class="form-group">
            <label class="  col-md-3 left-label">Item Name:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control resetField  right-align" name="itemName"
                       id="itemName"
                       readonly/>
            </div>
        </div>

        <div class="form-group">
            <label class="  col-md-3 left-label">Selling Price:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control resetField  right-align" name="sellingPrice"
                       id="sellingPrice"
                       readonly/>
            </div>
        </div>

        <div class="form-group">
            <label class="  col-md-3 left-label">Date:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control  formatDate right-align" readonly
                       placeholder="DD.MM.YYYY" id="saleDate" name="saleDate" value="${date}"/>
            </div>

        </div>

        <div class="form-group">

            <label class="col-md-3 left-label required">UOM:</label>

            <div class="col-md-4">
                <input type="text" id="unitName" class="form-control resetField right-align" readonly/>
            </div>
        </div>
        <div class="form-group">

            <label class="col-md-3 left-label required">Qty:</label>

            <div class="col-md-4">
                <input type="text" id="qty" class="form-control decimal resetField right-align" name="qty"/>
            </div>
        </div>
    </fieldset>


    <fieldset>
        <legend>Sale Items Details</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" id="saleItemGrid">
                    <thead>
                    <tr>
                        <th width="5%">SL No.</th>
                        <th width="10%">Item Code</th>
                        <th width="20%">Item Name</th>
                        <th width="5%">UOM</th>
                        <th width="10%">Rate</th>
                        <th width="7%">Qty</th>
                        <th width="10%">Total Amount</th>
                        <th width="10%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                    </tfoot>
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
                    <label class="col-md-2 right-label col-lg-offset-7">Sale In</label>

                    <div class="col-md-3">
                        <select class="form-control" id="isCash" required="required"
                                name="isCash">
                            <option value="">---Please select---</option>
                            <option value="1" id="cashId">Cash</option>
                            <option value="2" id="bankId">Bank</option>
                            <option value="3" id="creditId">Credit</option>
                            <option value="4" id="cashBanKId">Both (Cash & Bank)</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 right-label col-lg-offset-7">Discount</label>

                    <div class="col-md-3">
                        <input type="text"
                               class="form-control right-align amount resetField"
                               value="0"
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
                <div class="form-group">

                    <label class="col-md-2 right-label col-lg-offset-7">Physical Invoice No:</label>

                    <div class="col-md-3">
                        <input type="text" id="invoiceNo" class="form-control resetField right-align" name="invoiceNo"/>
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
        </div>

        <div class="form-group">
            <div class=" col-md-2 col-lg-offset-3">
                <input type="button" class="btn btn-primary btn-block " value="Print" id="printBtn" disabled>
            </div>
        </div>
    </fieldset>

</form>
</body>
</html>





