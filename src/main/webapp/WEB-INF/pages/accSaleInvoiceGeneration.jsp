<%--
  Created by IntelliJ IDEA.
  User: Bcass Sawa
  Date: 10/24/2019
  Time: 7:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Sale Invoice Generation</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Sale Invoice Generation</span>
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
            <form id="registrationForm" action="<c:url value='/saleInvoiceGeneration'/> "
                  class="form-horizontal globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Sale Invoice Generation</legend>

                    <div class="form-group row">
                        <label class="col-md-2 required">Invoice No. </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="1" class="form-control form-control-sm" name="invoiceNo"
                                   readonly
                                   id="invoiceNo" value="${saleInvoiceNo}" required="true"/>
                        </div>

                        <label class="col-md-2 right-align required">Invoice Date</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm formatDate"
                                   name="invoiceDate"
                                   readonly
                                   id="invoiceSaleDate" required="true" value="${invoiceDate}"/>
                        </div>
                    </div>

                    <div class="col-md-12 form-group row table-responsive">
                        <table class="table table-bordered table-striped editable-grid" id="saleInvoiceGenerationGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="50%">Particular</th>
                                <th width="10%">Amount</th>
                                <th width="10%">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><input type='text' name='accSaleInvoiceGenerationListDTOList[0].particular' required
                                           id="itemName" class='form-control form-control-sm left-align particular'
                                           tabindex="1">
                                </td>

                                <td><input type='text' name='accSaleInvoiceGenerationListDTOList[0].amount' tabindex="1"
                                           required
                                           id="cost" class='form-control form-control-sm  right-align amount'>
                                </td>

                                <td>
                                    <button type="button" class="btn btn-danger btn-sm btnRemoveRow"><span
                                            class="icon-minus-circle2"></span></button>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <button type="button" class="btn btn-primary btn-sm btnAddRow">
                                        <span class="icon-add"></span>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2 left-label right-label col-lg-offset-7">Total Amount</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm right-align  resetField"
                                   value="0" name="amount" id="grandTotal" readonly>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2  right-label col-lg-offset-7">Received In</label>

                        <div class="col-md-3">
                            <select class="form-control form-control-sm" id="isCash" required="required"
                                    name="isCash">
                                <option value="">---Please select---</option>
                                <option value="1" id="cashId">Cash</option>
                                <option value="2" id="bankId">Bank</option>
                                <option value="3" id="creditId">Credit</option>
                                <option value="4" id="cashBanKId">Both (Cash & Bank)</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7">Discount</label>

                        <div class="col-md-3">
                            <input type="text"
                                   class="form-control form-control-sm right-align amount resetField"
                                   value="0"
                                   id="discountRate" name="discountRate">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7" id="amountReceivedText">Amount
                            Received</label>

                        <div class="col-md-3">
                            <input type="text"
                                   class="form-control form-control-sm right-align amount" name="amtReceived"
                                   id="amtReceived" value="0">
                        </div>
                    </div>

                    <div class="form-group row" id="bankDetails" hidden>
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7">Select Bank Account</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList"
                                         id="bankLedgerId"
                                         required="required"
                                         name="bankLedgerId">
                                <form:option id="empyId" value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row" id="bankAmountId" hidden>
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7">Amount in Bank</label>

                        <div class="col-md-3">
                            <input type="text"
                                   class="form-control form-control-sm right-align amount" name="amountReceivedInBank"
                                   id="amountReceivedInBank" value="0">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7">Returnable Amount</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm   right-align" value="0"
                                   name="amtReturn"
                                   id="amtReturn" readonly>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6"></div>
                        <label class="col-md-2 right-label col-lg-offset-7">Physical Invoice No</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm   right-align"
                                   name="physicalInvoiceNo"
                                   id="physicalInvoiceNo">
                        </div>
                    </div>

                    <div class="creditDetails" hidden>

                        <div class="form-group row">
                            <div class="col-md-6"></div>
                            <label class="col-md-2 right-label col-lg-offset-7 required">Party Name</label>

                            <div class="col-md-3">
                                <input type="text" tabindex="2" required
                                       class="form-control form-control-sm  resetField right-align"
                                       id="partyName"
                                       name="partyName"/>
                            </div>
                        </div>
                        <div class="form-group row creditSale">
                            <div class="col-md-6"></div>
                            <label class="col-md-2 right-label col-lg-offset-7 required">Address </label>

                            <div class="col-md-3">
                                <input type="text" tabindex="4" class="form-control form-control-sm resetfield"
                                       name="partyAddress"
                                       id="partyAddress" required="true"/>
                            </div>

                        </div>

                        <div class="form-group row creditSale">
                            <div class="col-md-6"></div>
                            <label class="col-md-2 right-label col-lg-offset-7 required">Contact No.</label>

                            <div class="col-md-3">
                                <input type="text" required="true" tabindex="5"
                                       class="form-control form-control-sm resetfield"
                                       name="partyContactNo"
                                       id="partyContactNo"/>
                            </div>
                        </div>

                        <div class="form-group row creditSale">
                            <div class="col-md-6"></div>
                            <label class="col-md-2 right-label col-lg-offset-7">Email ID.</label>

                            <div class="col-md-3">
                                <input type="text" tabindex="6" class="form-control form-control-sm resetfield"
                                       name="partyEmail"
                                       id="partyEmail"/>
                            </div>
                        </div>
                    </div>


                    <%--        <div class="form-group row" id="bankDetails" hidden>--%>
                    <%--            <label class=" col-md-2  right-label col-lg-offset-7">Select Bank Account</label>--%>

                    <%--            <div class="col-md-3">--%>
                    <%--                <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerId" required="required"--%>
                    <%--                             name="bankLedgerId">--%>
                    <%--                    <form:option value="">---Please Select---</form:option>--%>
                    <%--                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>--%>
                    <%--                </form:select>--%>
                    <%--            </div>--%>
                    <%--        </div>--%>

                    <%--        <div class="form-group row creditSale">--%>
                    <%--            <label class="col-md-2 text-right required  right-label col-lg-offset-7">Party Name </label>--%>

                    <%--            <div class="col-md-3">--%>
                    <%--                <input type="text" tabindex="3" class="form-control form-control-sm" name="partyName"--%>
                    <%--                       id="partyName" required="true"/>--%>
                    <%--            </div>--%>


                    <%--        </div>--%>

                    <%--        <div class="form-group row creditSale">--%>
                    <%--            <label class="col-md-2 text-right required  right-label col-lg-offset-7">Address </label>--%>

                    <%--            <div class="col-md-3">--%>
                    <%--                <input type="text" tabindex="4" class="form-control form-control-sm resetfield" name="partyAddress"--%>
                    <%--                       id="partyAddress" required="true"/>--%>
                    <%--            </div>--%>

                    <%--        </div>--%>

                    <%--        <div class="form-group row creditSale">--%>
                    <%--            <label class="col-md-2 text-right required  right-label col-lg-offset-7">Contact No.</label>--%>

                    <%--            <div class="col-md-3">--%>
                    <%--                <input type="text" required="true" tabindex="5" class="form-control form-control-sm resetfield" name="partyContactNo"--%>
                    <%--                       id="contactNo"/>--%>
                    <%--            </div>--%>
                    <%--        </div>--%>

                    <%--        <div class="form-group row creditSale">--%>
                    <%--            <label class="col-md-2 text-right  right-label col-lg-offset-7">Email ID.</label>--%>

                    <%--            <div class="col-md-3">--%>
                    <%--                <input type="text" tabindex="6" class="form-control form-control-sm resetfield" name="partyEmail"--%>
                    <%--                       id="sectionId"/>--%>
                    <%--            </div>--%>
                    <%--        </div>--%>

                    <div class="form-group row col-md-12">
                        <div class="col-md-2 col-lg-offset-2" id="saveBtnDiv">
                            <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9"
                                   id="btnSave">
                        </div>
                        <div class="col-md-2">
                            <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8"
                                   id="btnReset">
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>



