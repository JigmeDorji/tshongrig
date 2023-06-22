<%@ page import="com.spms.mvc.dto.CompanyCreationDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.spms.mvc.library.helper.CurrentUser" %><%--

  Created by IntelliJ IDEA.
  User: user
  Date: 5/23/2021
  Time: 11:03 AM
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Opening inventory items</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Inventory</a>
                <span class="breadcrumb-item active">Item Opening</span>
            </div>

<%--            <input hidden aria-label="currentUserBusinessType" type="number" id="currentUserBusinessType"--%>
<%--                   value="${currentUser.businessType}">--%>
<%--            <input hidden aria-label="companyCode" type="text" id="companyCode" value="${currentUser.loginId}">--%>
<%--            <input hidden aria-label="companyId" type="number" id="companyId" value="${currentUser.companyId}">--%>
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

            <form id="returnItemForm" action="<c:url value='/openingBalanceInventory'/> "
                  class="form-horizontal globalForm">
                <input type="hidden" class="resetfield" id="itemId" name="itemId">
                <input type="hidden" id="purchaseId" name="purchaseDTOS[0].purchaseId" value="${purchaseId}">
                <input type="hidden" id="purchaseVoucherNo" name="voucherNo" value="${purchaseVoucherNumber}">
                <input type="hidden" id="supplierName" name="supplierName">
                <input type="hidden" id="brandNameID" name="purchaseDTOS[0].brandId">
                <input type="hidden" id="isOpeningEntry" class="form-control form-control-sm " name="isOpeningEntry"
                       value="Y">
                <input type="hidden" id="isCash" class="form-control form-control-sm " name="isCash"
                       value="0"/>
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Enter item details</legend>
                    <div class="form-group row">
                        <label class="  col-md-3 left-label required">Entry Date:</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm  right-align"
                                   value="${currentUser.financialYearFrom}"
                                   placeholder="DD.MM.YYYY" id="purchaseDate" name="purchaseDate" readonly
                                   required="required"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-3">
                            <input type="hidden" id="purchaseInvoiceNo" class="form-control form-control-sm "
                                   name="purchaseInvoiceNo"
                                   value="0"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3  required">Brand</label>
                        <div class="col-md-3">
                            <input type="text" id="brandId" class="form-control form-control-sm "
                                   required="required"/>
                        </div>
                        <div class="col-md-2">
                            <input type="button" data-toggle="modal" class="btn btn-sm btn-primary btn-xs"
                                   value="New Brand"
                                   id="btnAddBrand">
                        </div>
                        <label class="col-md-1 left-label">Serial:</label>

                        <div class="col-md-1">
                            <input type="text" tabindex="2" class="form-control form-control-sm  right-align"
                                   id="currentSerial" readonly/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="  col-md-3 left-label">Item Code:</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm  right-align"
                                   id="itemCode" name="purchaseDTOS[0].itemCode"/>
                        </div>

                        <%--Garment--%>
                        <%--<div class="col-md-3">--%>
                        <%--<input type="text" tabindex="2" class="form-control form-control-sm  right-align" value="${itemCode}" readonly--%>
                        <%--id="itemCode" name="itemCode"/>--%>
                        <%--</div>--%>


                    </div>


                    <div class="form-group row">
                        <label class="col-md-3  required">Type</label>
                        <div class="col-md-3">
                            <input type="text" id="type" class="form-control form-control-sm "
                                   required="required" name="purchaseDTOS[0].type"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="  col-md-3 left-label">Part Number:</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm  right-align"
                                   id="partNo" name="purchaseDTOS[0].partNo"/>
                        </div>
                    </div>
                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">Item Name</label>

                        <div class="col-md-1">
                            <input type="text"
                                   id="itemNamePrefix" class="form-control form-control-sm" readonly
                                   name="purchaseDTOS[0].prefixCode"
                                   required="required"/>
                        </div>
                        <div class="col-md-4" style="margin-left: -30px">
                            <input type="text" id="itemName" class="form-control form-control-sm common"
                                   name="purchaseDTOS[0].itemName"
                                   required="required"/>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">qty</label>

                        <div class="col-md-1">
                            <input type="text" id="qty" class="form-control form-control-sm amount common"
                                   name="purchaseDTOS[0].qty" required="required"/>
                            <input type="hidden" id="originalQty"
                                   class="form-control form-control-sm amount common"/>
                        </div>
                        <label class=" col-md-1 right-align">Unit</label>
                        <div class="col-md-1">
                            <form:select class="form-control form-control-sm resetField" path="unitList" id="unitId"
                                         required="required"
                                         name="purchaseDTOS[0].unitId">
                                <form:option value="">----</form:option>
                                <form:options items="${unitList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">Cost Price</label>

                        <div class="col-md-3">
                            <input type="text" id="costPrice" class="form-control form-control-sm amount common"
                                   name="purchaseDTOS[0].costPrice"
                                   required="required"/>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">Selling Price</label>

                        <div class="col-md-3">
                            <input type="text" id="sellingPrice" class="form-control form-control-sm amount common"
                                   required="required"
                                   name="purchaseDTOS[0].sellingPrice"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class=" col-md-3 left-label required">Location</label>

                        <div class="col-md-3">

                            <form:select class="form-control form-control-sm resetField common" path="locationList"
                                         id="locationId"
                                         required="required"
                                         name="purchaseDTOS[0].locationId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${locationList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>
                    <br/>

                    <div class="form-group row creditDetails" hidden>
                        <label class="col-md-3  required">Supplier Name:</label>

                        <div class="col-md-3">
                            <select id="supplierId" required class="form-control form-control-sm "
                                    name="supplierId"></select>
                        </div>

                        <div class="col-md-3">
                            <input type="button" data-toggle="modal" class="btn btn-primary btn-xs"
                                   value="Add Supplier"
                                   id="btnAddNewSupplier">
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

                    <%--
                                <div class="form-group row">
                                    &lt;%&ndash;<label class="  col-md-2 left-label required">Amount:</label>

                                    <div class="col-md-2">
                                        <input type="text" id="amount" class="form-control form-control-sm  "
                                               name="amount"/>
                                    </div>&ndash;%&gt;
                                    <label class=" col-md-2 left-label required"> Location</label>

                                    <div class="col-md-2">
                                        <Select type="text" id="cashOrBank" class="form-control form-control-sm "
                                                name="isCash" required="required">
                                            <option value="">--Select--</option>
                                            <option value="1">Cash</option>
                                            <option value="0">Bank</option>
                                        </Select>
                                    </div>
                                </div>--%>

                </fieldset>
                <div class="form-group row">
                    <div class="col-md-3">
                        <%--            <input type="button" class="btn btn-primary btn-block" value="Back" id="backBtn">--%>
                    </div>
                    <div class="col-md-3">
                        <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
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
                        <form id="supplierSetupForm" action="<c:url value='/supplierSetup'/> "
                              class="form-horizontal supplierForm">
                            <input type="hidden" tabindex="2" id="id" name="id"/>
                            <fieldset>
                                <legend>Credit Details</legend>
                                <div class="form-group row">
                                    <label class="col-md-3  required">Supplier Name:</label>

                                    <div class="col-md-3">
                                        <input type="text" tabindex="2" class="form-control form-control-sm"
                                               id="suppName"
                                               name="supplierName" required="required"/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-md-3 text-righ required ">Address</label>

                                    <div class="col-md-3">
                                        <input type="text" tabindex="3" class="form-control form-control-sm"
                                               id="address"
                                               name="address" required="required"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-3  required">Email</label>

                                    <div class="col-md-3">
                                        <input type="text" tabindex="4" class="form-control form-control-sm" id="email"
                                               required="required"
                                               name="email"/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-md-3  required">Contact No</label>

                                    <div class="col-md-3">
                                        <input type="text" required="required" tabindex="5"
                                               class="form-control form-control-sm numeric"
                                               id="contactNo"
                                               name="contactNo"/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-md-3"></div>

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

                                <div class="form-group row">
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
                                        <div class="form-group row">
                                            <label for="brandName" class="col-form-label">Brand Name</label>
                                            <input type="text" required class="form-control form-control-sm"
                                                   id="brandName" name="brandName">
                                        </div>
                                        <div class="form-group row ">
                                            <label for="brandPrefix" class="col-form-label">Brand Prefix</label>
                                            <input type="text" required class="form-control form-control-sm"
                                                   id="brandPrefix" name="brandPrefix">
                                        </div>
                                        <div class="form-group row">
                                            <label for="remarks" class="col-form-label">Remarks</label>
                                            <textarea class="form-control form-control-sm" id="remarks"
                                                      name="remarks"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer justify-content-between">
                                        <div class="form-group row row col-12">
                                            <div class="col-2">
                                                <button type="submit" class="btn btn-primary" id="pModalButton">Save
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>

</body>
</html>

