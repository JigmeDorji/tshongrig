<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/17/2021
  Time: 9:40 AM
  To change this template use File | Settings | File Templates.
--%>
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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('14-DELETE')" var="hasDeleteRole"/>
<html>
<title class="title">Material Purchase</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Material Purchase</span>
</div>
<input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
<form id="returnItemForm" action="<c:url value='/receivedItem'/> " class="form-horizontal globalForm">
    <input type="hidden" class="resetfield" id="itemId" name="itemId">
    <input type="hidden" class="common" id="purchaseId" name="purchaseId" value="${purchaseId}">
    <input type="hidden" class="" id="purchaseVoucherNo" name="voucherNo" value="${purchaseVoucherNo}">
    <input type="hidden" class="resetfield" id="supplierName" name="supplierName">
    <input type="hidden" class="resetfield" id="brandNameID" name="brandId">
    <input type="hidden" id="currentSerialNo">
    <input type="hidden" id="isOpeningEntry" class="form-control" name="isOpeningEntry"
           value="N"/>
    <fieldset>
        <legend>Purchase Detail</legend>
        <div class="form-group">
            <label class="  col-md-2 right-align required">Purchase Date:</label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control  formatDate right-align" value="${purchaseDate}"
                       placeholder="DD.MM.YYYY" id="purchaseDate" name="purchaseDate" required="required"/>
            </div>

            <label class=" col-md-2 right-align required"> &nbsp;Purchase Inv. No:</label>

            <div class="col-md-3">
                <input type="text" id="purchaseInvoiceNo" class="form-control " name="purchaseInvoiceNo"
                       required="required"/>
            </div>
        </div>
        <div class="form-group">
            <label class=" col-md-2 right-align required">Purchase In</label>

            <div class="col-md-3">
                <select class="form-control resetField" id="isCash" required="required"
                        name="isCash">
                    <option value="">---Please Select---</option>
                    <option value="1" id="cashId">Cash</option>
                    <option value="2" id="bankId">Bank</option>
                    <option value="3" id="creditId">Credit</option>
                </select>
            </div>

            <div class="" id="bankDetails" hidden>
                <label class=" col-md-2 right-align">Select Bank Account</label>

                <div class="col-md-3">
                    <form:select class="form-control resetField" path="bankList" id="bankLedgerId" required="required"
                                 name="bankLedgerId">
                        <form:option value="">---Please Select---</form:option>
                        <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                    </form:select>
                </div>
            </div>
            <div class="creditDetails" hidden>
                <label class="col-md-2 right-align required">Supplier Name:</label>

                <div class="col-md-3">
                    <select id="supplierId" required class="form-control " name="supplierId"></select>
                </div>
                <div class="col-md-1">
                    <input type="button" data-toggle="modal" class="btn btn-primary btn-xs" value="Add Supplier"
                           id="btnAddNewSupplier">
                </div>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend>Item Detail</legend>
        <div class="form-group">
            <label class="col-md-2 right-align">Brand</label>
            <div class="col-md-3">
                <input type="text" id="brandId" class="form-control common"
                />
            </div>
            <div class="col-md-1">
                <input type="button" data-toggle="modal" class="btn btn-primary btn-xs" value="New Brand"
                       id="btnAddBrand">
            </div>
            <label class="col-md-1 right-align">Serial:</label>
            <div class="col-md-1">
                <input type="text" tabindex="2" class="form-control  right-align common"
                       id="currentSerial" readonly/>
            </div>
        </div>
        <div class="form-group">
            <label class="  col-md-2 right-align">Item Code:</label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control  right-align common"
                       id="itemCode" name="itemCode"/>
            </div>
            <div class="col-md-3">
                <input type="hidden" id="type" class="form-control"
                       name="type" value="Material"/>
            </div>


            <%--Garment--%>
            <%--<div class="col-md-3">--%>
            <%--<input type="text" tabindex="2" class="form-control  right-align" value="${itemCode}" readonly--%>
            <%--id="itemCode" name="itemCode"/>--%>
            <%--</div>--%>
        </div>


        <div class="form-group">
            <label class=" col-md-2 right-align">Item Name</label>

            <div class="col-md-1" hidden>
                <input type="text"
                       id="prefixCode" class="form-control common" readonly
                       name="prefixCode"
                />
            </div>
            <div class="col-md-3">
                <input type="text" id="itemName" class="form-control common" name="itemName"/>
            </div>
            <div class="col-md-3">
                <input type="hidden" tabindex="2" class="form-control  right-align"
                       id="partNo" name="partNo" value="MA000"/>
            </div>

        </div>

        <div class="form-group">
            <label class=" col-md-2 right-align ">qty</label>

            <div class="col-md-1">
                <input type="text" id="qty" class="form-control decimal common" name="qty"/>
            </div>

            <label class=" col-md-1 right-align">Unit</label>
            <div class="col-md-1">
                <form:select class="form-control resetField" path="unitList" id="unitId"
                             name="unitId">
                    <form:option value="">----</form:option>
                    <form:options items="${unitList}" itemValue="value" itemLabel="text"/>
                </form:select>
            </div>



        </div>
        <div class="form-group">
            <label class=" col-md-2 right-align">Cost Price</label>

            <div class="col-md-3">
                <input type="text" id="costPrice" class="form-control amount common " name="costPrice"
                />
            </div>

        </div>


        <div class="form-group">
            <label class=" col-md-2 right-align">Location</label>
            <div class="col-md-3">
                <form:select class="form-control common" path="locationList" id="locationId"
                             name="locationId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${locationList}" itemValue="value" itemLabel="text"/>
                </form:select>
            </div>
            <div class="col-md-3">
                <input type="hidden" id="sellingPrice" class="form-control amount"
                       name="sellingPrice" value="0.00"/>
            </div>


        </div>
        <div class="form-group">
            <div class="col-md-2 col-lg-offset-7">
                <input type="button" class="btn btn-primary btn-block" value="Add" id="btnAdd">
            </div>
        </div>
        <br/>
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" id="purchaseItemTable">
                <thead>
                <tr>
                    <th width="2%">SL.</th>
                    <th width="10%">Item Code</th>
                    <th width="20%">Item Name</th>
                    <th width="10%" hidden>Part Number</th>
                    <th width="5%" hidden>SP</th>
                    <th width="5%">CP</th>
                    <th width="5%">Qty</th>
                    <th width="5%">Unit</th>
                    <th width="5%">Amount</th>
                    <th width="10%">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="form-group">
                <label class="col-md-2 right-label col-lg-offset-7">Total Amount</label>

                <div class="col-md-2">
                    <input type="text"
                           class="form-control right-align"
                           value="0" name="totalTranAmount"
                           id="grandTotalAmount" readonly>
                </div>
            </div>
            <div class="form-group row">
                <sec:authorize access="hasAuthority('14-ADD')">
                    <div class="col-md-2 col-lg-offset-7">
                        <input type="submit" disabled class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('14-DELETE')">
                    <div class="col-md-2 ">
                        <input type="submit" class="btn btn-danger btn-block" value="Delete" id="btnDelete">
                    </div>
                </sec:authorize>
            </div>
        </div>


        <%--
                    <div class="form-group">
                        &lt;%&ndash;<label class="  col-md-2 right-align required">Amount:</label>

                        <div class="col-md-2">
                            <input type="text" id="amount" class="form-control  "
                                   name="amount"/>
                        </div>&ndash;%&gt;
                        <label class=" col-md-2 right-align required"> Location</label>

                        <div class="col-md-2">
                            <Select type="text" id="cashOrBank" class="form-control "
                                    name="isCash" required="required">
                                <option value="">--Select--</option>
                                <option value="1">Cash</option>
                                <option value="0">Bank</option>
                            </Select>
                        </div>
                    </div>--%>

    </fieldset>
    <div class="form-group">
        <div class="col-md-2">
            <%--            <input type="button" class="btn btn-primary btn-block" value="Back" id="backBtn">--%>
        </div>


    </div>
</form>

<div class="modal fade" id="supplierModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Enter Supplier Details</h4>
            </div>
            <form id="supplierSetupForm" action="<c:url value='/supplierSetup'/> " class="form-horizontal supplierForm">
                <input type="hidden" tabindex="2" id="id" name="id"/>
                <fieldset>
                    <legend>Credit Details</legend>
                    <div class="form-group">
                        <label class="col-md-3 text-right required">Supplier Name:</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control" id="suppName"
                                   name="supplierName" required="required"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 text-righ required ">Address</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="3" class="form-control" id="address"
                                   name="address" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 text-right ">Email</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="4" class="form-control" id="email"
                                   name="email"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 text-right required">Contact No</label>

                        <div class="col-md-3">
                            <input type="text" required="required" tabindex="5" class="form-control numeric"
                                   id="contactNo"
                                   name="contactNo"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2 text-right"></div>

                        <div class="col-md-2">
                            <input type="submit" class="btn btn-primary btn-block" value="Save"
                                   id="btnSupplierSave">
                        </div>
                        <div class="col-md-2">
                            <input type="reset" class="btn btn-primary btn-block" value="Reset"
                                   id="btnReset">
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Agency Details</legend>

                    <div class="form-group">
                        <div class="col-md-12">
                            <table class="table table-bordered table-striped editable-grid tableGrid"
                                   id="supplierListTable">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th width="30%">Supplier Name</th>
                                    <th width="35%">Address</th>
                                    <th width="20%">Email</th>
                                    <th width="20%">Contact No.</th>
                                    <th width="5%"></th>
                                </tr>
                                </thead>
                                <tbody id="customerSearchBody">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>


<section class="content">
    <form class="brandForm">
        <div class="modal fade" data-backdrop="static" id="brandModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <h4 class="modal-title">Register Brand Detail</h4>
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="brandName" class="col-form-label">Brand Name</label>
                                <input type="text" required class="form-control" id="brandName" name="brandName">
                            </div>
                            <div class="form-group ">
                                <label for="brandPrefix" class="col-form-label">Brand Prefix</label>
                                <input type="text" required class="form-control" id="brandPrefix" name="brandPrefix">
                            </div>
                            <div class="form-group">
                                <label for="remarks" class="col-form-label">Remarks</label>
                                <textarea class="form-control" id="remarks" name="remarks"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer justify-content-between">
                            <div class="form-group row col-12">
                                <div class="col-2">
                                    <button type="submit" class="btn btn-primary" id="pModalButton">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</section>
</body>
</html>


