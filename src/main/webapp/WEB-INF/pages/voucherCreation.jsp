<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/7/2019
  Time: 8:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Voucher Creation</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Voucher</span>
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
            <form id="voucherCreationForm" action="<c:url value='/voucherCreation'/> "
                  class="form-horizontal globalForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Voucher Details</legend>
                    <div class="form-group row">
                        <label class="  col-md-2  required">Voucher Type:</label>

                        <div class="col-md-3" style="margin-left: -30px">
                            <form:select class="form-control form-control-sm select2" name="voucherTypeId"
                                         path="voucherTypeList"
                                         id="voucherTypeId" required="required" tabindex="1" autofocus="true">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${voucherTypeList}" itemValue="valueInteger"
                                              itemLabel="text"/>
                            </form:select>
                        </div>

                        <label class="col-md-2  required">Voucher No: </label>

                        <div class="col-md-1" style="margin-left: -30px">
                            <input type="text" class="form-control form-control-sm resetfield right-align"
                                   name="voucherNo"
                                   id="voucherNo" value="${currentVoucherNo}" readonly/>
                        </div>

                        <label class="col-md-2  required">Voucher Entry Date: </label>

                        <div class="col-md-2">
                            <input type="text" tabindex="2" class="form-control form-control-sm  formatDate right-align"
                                   name="voucherEntryDate"
                                   placeholder="DD.MM.YYYY" id="voucherEntryDate" required="true"/>
                        </div>
                    </div>

                    <div class="col-md-12 form-group row table-responsive" style="margin-top: 20px">
                        <table class="table table-bordered editable-grid tableGrid"
                               id="voucherCreationGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="7%"></th>
                                <th width="50%">Particulars</th>
                                <th width="20%">Debit</th>
                                <th width="20%">Credit</th>
                                <%--<th width="10%">Action</th>--%>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><select id="crOrDr1" class='form-control form-control-sm  crOrDr' disabled>
                                    <option value="1">Dr</option>
                                    <option value="0">Cr</option>
                                </select></td>
                                <td><select name='voucherDetailDTOList[0].ledgerId'
                                            id="ledgerId1" tabindex="3"
                                            class='form-control form-control-sm voucherDetail select2 ledgerId'
                                            disabled>
                                </select></td>
                                <td><input type='text' name='voucherDetailDTOList[0].debitAmount'
                                           id="debitAmount1" tabindex="3"
                                           class='form-control form-control-sm right-align voucherDetail debitAmount amount'
                                           disabled></td>
                                <td><input type='text' name='voucherDetailDTOList[0].creditAmount'
                                           id="creditAmount1"
                                           class='form-control form-control-sm   right-align voucherDetail creditAmount amount'
                                           disabled>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td></td>
                                <td><b>Total</b></td>
                                <td style="padding-right: 1px"><input type="text" name="totalDebit"
                                                                      class="form-control form-control-sm   right-align"
                                                                      value="0"
                                                                      id="totalDebit" readonly></td>
                                <td style="padding-right: 1px"><input type="text" name="totalCredit"
                                                                      class="form-control form-control-sm   right-align"
                                                                      value="0"
                                                                      id="totalCredit" readonly></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>


                    <div class="form-group row">

                        <label class="col-md-1 "> Narration: </label>

                        <div class="col-md-6">
                <textarea type="text" tabindex="7" class="form-control form-control-sm resetfield" name="narration"
                          id="narration"></textarea>
                        </div>
                    </div>

                    <div id="depreciationShow" hidden>
                        <div class="col-md-12 form-group row table-responsive">
                            <table class="table table-bordered table-striped editable-grid"
                                   id="depreciationGrid">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th width="10%">Date Of Purchase</th>
                                    <th width="50%">Particular</th>
                                    <th width="10%">Cost</th>
                                    <th width="10%">Qty</th>
                                    <th width="10%">Rate of Depreciation(%)</th>
                                    <th width="10%">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><input type="text" name='depreciationDTOList[0].dateOfPurchase'
                                               id="dateOfPurchase" class='form-control form-control-sm dateOfPurchase'
                                               placeholder="DD.MM.YYYY">
                                    </td>
                                    <td><input type='text' name='depreciationDTOList[0].itemName'
                                               id="itemName" class='form-control form-control-sm left-align itemName'>
                                    </td>


                                    <td><input type='text' name='depreciationDTOList[0].cost'
                                               id="cost" class='form-control form-control-sm  right-align cost'>
                                    </td>

                                    <td><input type='text' name='depreciationDTOList[0].qty'
                                               id="qty" class='form-control form-control-sm right-align qty'></td>

                                    <td><input type='text' name='depreciationDTOList[0].rateOfDepreciation'
                                               id="rateOfDepreciation"
                                               class='form-control form-control-sm  right-align rateOfDepreciation'>
                                    </td>

                                    <td><a class="btnRemoveRow"><span class="glyphicon glyphicon-minus-sign"/></a></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><a class="btnAddRow"><span class="glyphicon glyphicon-plus-sign"/></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2">
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
        </div>
    </div>
</div>

</body>
</html>




