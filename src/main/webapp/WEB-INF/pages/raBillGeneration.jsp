<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/13/2022
  Time: 11:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Running Accounting Bill</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Running Accounting Bill</span>
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
            <form id="raBillForm" action="<c:url value='/raBillGeneration'/> " class="form-horizontal raBillForm globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Bill Detail</legend>
                    <input type="text" class="form-control form-control-sm hidden" id="boqId" value="${boqId}"/>
                    <input type="text" class="form-control form-control-sm hidden" id="raBillId" name="raBillId"/>
                    <input type="text" class="form-control form-control-sm hidden" id="raSerialNo" value="${raSerialNo}"/>
                    <input type="text" class="form-control form-control-sm hidden" name="voucherNo"
                           id="voucherNo" value="${voucherNo}"/>

                    <input type="hidden" class="form-control form-control-sm totalBillAmount" name="totalBillAmount"/>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Work Order No:</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm select2" name="boqId" path="workOrderList"
                                         id="workOrderNo" required="required" tabindex="1" autofocus="true">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${workOrderList}" itemValue="valueBigInteger"
                                              itemLabel="text"/>
                            </form:select>
                        </div>

                        <label class=" col-md-2 right-align required">RA serial No.</label>

                        <div class="col-md-1">
                            <input type="text" class="form-control form-control-sm right-align" name="raSerialNo" readonly
                                   id="serialNo" required="true"/>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label class=" col-md-2 right-align required">RA Bill No</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm" name="raBillNo"
                                   id="raBillNo" required="true"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class=" col-md-2 right-align required">Bill Date</label>

                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm dateFormat2" name="billDate" value="${date}" readonly
                                   id="billDate" required="true"/>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="v-no-padding">
                    <legend class="text-uppercase font-size-sm font-weight-bold">BOQ Item List</legend>
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped editable-grid boqDataListGrid" Id="boqDataListGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="5%">Srl</th>
                                <th hidden></th>
                                <th hidden></th>
                                <th width="10%">Code</th>
                                <th width="20%">Description</th>
                                <th width="10%">Unit</th>
                                <th width="10%">Rate</th>
                                <th width="10%">Qty</th>
                                <th width="10%">Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td></td>
                                <td hidden></td>
                                <td hidden></td>
                                <td colspan="5"><strong>Total</strong></td>
                                <td><strong><p class="totalBillAmount"></p></strong></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </fieldset>
                <br>
                <div class="form-group row">
                    <div class="col-md-10"></div>
                    <div class="col-md-2">
                        <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
