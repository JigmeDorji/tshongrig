<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 9/16/2021
  Time: 9:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Stock Issue</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Material</a>
                <span class="breadcrumb-item active">Stock Issue</span>
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
            <div id="dialog" style="display: none;">
                <div>
                    <iframe id="frame"></iframe>
                </div>
            </div>
            <form id="stockIssueForm" action="<c:url value='/stockIssue'/> " class="form-horizontal globalForm">

                <input type="hidden" id="voucherNo" value="${voucherNo}" name="voucherNo">
                <input type="hidden" id="receiptMemoNo" value="${receiptMemoNo}" name="receiptMemoNo">

                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Issue Detail</legend>
                    <div class="form-group row">
                        <label class="  col-md-3 right-align required">Date:</label>

                        <div class="col-md-4">
                            <input type="text" tabindex="2" class="form-control form-control-sm  formatDate right-align"
                                   placeholder="DD.MM.YYYY" id="saleDate" name="saleDate" required="required"/>
                        </div>

                        <label class=" col-md-2 right-align required"> &nbsp;Issue To:</label>

                        <div class="col-md-3">
                            <input type="text" id="issueTo" class="form-control form-control-sm" name="issueTo"
                                   required="required"/>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-3 left-label required">Search By Item Code:</label>

                        <div class="col-md-4">
                            <input type="text" tabindex="2" class="form-control form-control-sm  resetField right-align"
                                   id="itemCode"
                                   name="itemCode"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 left-label required">Item Name:</label>

                        <div class="col-md-4">
                            <input type="text" tabindex="2" class="form-control form-control-sm  resetField right-align"
                                   id="itemName" readonly
                                   name="itemName"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3 left-label required">UOM:</label>
                        <div class="col-md-4">
                            <input type="text" id="unitName" class="form-control form-control-sm resetField right-align"
                                   readonly/>
                        </div>
                    </div>
                    <div class="form-group row">

                        <label class="col-md-3 left-label required">Qty:</label>

                        <div class="col-md-4">
                            <input type="text" id="qty" class="form-control form-control-sm resetField right-align"/>
                        </div>
                    </div>
                </fieldset>


                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Issue Items Details</legend>
                    <div class="form-group row">
                        <div class="col-md-12">
                            <table class="table table-bordered table-striped editable-grid" id="issueItemGrid">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th width="5%">SL No.</th>
                                    <th width="20%">Item Name</th>
                                    <th width="5%">UOM</th>
                                    <th width="7%">Qty</th>
                                    <th width="10%">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                </tfoot>
                            </table>
                            <%--                <div class="form-group row">--%>
                            <%--                    <label class="col-md-2 right-label col-lg-offset-7">Total Amount</label>--%>

                            <%--                    <div class="col-md-3">--%>
                            <%--                        <input type="text"--%>
                            <%--                               class="form-control form-control-sm right-align numeric resetField"--%>
                            <%--                               value="0" name="amount"--%>
                            <%--                               id="grandTotal" readonly>--%>
                            <%--                    </div>--%>
                            <%--                </div>--%>

                            <div class="form-group row pt-2">
                                <label class="col-md-2 right-label col-lg-offset-7">Issue Note</label>

                                <div class="col-md-3">
                        <textarea class="form-control form-control-sm right-align resetField" name="invoiceNo"
                                  id="invoiceNo"></textarea>
                                </div>
                            </div>
                        </div>


                    </div>
                    <div class="form-group row">
                        <div class="col-md-2"></div>

                        <div class="col-md-2">
                            <div class="col-md-2"></div>
                            <input type="button" class="btn btn-primary btn-block" value="Save"
                                   id="btnSave"
                                   disabled>
                        </div>
                    </div>

                </fieldset>

            </form>
        </div>
    </div>
</div>

</body>
</html>
