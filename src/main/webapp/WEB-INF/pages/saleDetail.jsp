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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('22-DELETE')" var="hasDeleteRole"/>
<sec:authorize access="hasAuthority('22-EDIT')" var="hasEditRole"/>
<html>
<title class="title">Sale Detail</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Inventory</a>
                <span class="breadcrumb-item active">Issue Detail</span>
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
            <fieldset>
                <legend class="text-uppercase font-size-sm font-weight-bold">Issue Details</legend>

                <form id="folktaleCreationFrom" action="<c:url value='/saleDetail'/> "
                      class="form-horizontal globalForm">
                    <input type="hidden" id="locationSetUpId" name="locationSetUpId">
                    <input type="hidden" id="businessTypeId" value="${currentUser.businessType}">
                    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
                    <input type="hidden" id="hasEditRole" value="${hasEditRole}">


                    <div class="form-group row">
                        <label class="col-md-2  required">From</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker dynamic"
                                   name="fromDate"
                                   id="fromDate" required="required" value="${toDate}"/>
                        </div>
                        <label class="col-md-2  required">To</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker dynamic"
                                   name="toDate"
                                   id="toDate" required="required" value="${toDate}"/>
                        </div>

                    </div>
            </fieldset>
            <fieldset class="v-no-padding">
                <div class="form-group row">
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid" Id="saleDetailGrid">
                            <thead>
                            <tr class="text-white bg-primary">
                                <th hidden></th>
                                <th hidden></th>
                                <th width="5%">Sl No.</th>
                                <th width="20%">Sale Date</th>
                                <th width="15%">Receipt Memo No</th>
                                <th width="15%"> Invoice No.</th>
                                <th width="15%">Total Amount</th>
                                <th width="20%">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </fieldset>
            </form>
            <div class="modal fade" id="itemSaleDetailModal" tabindex="-1" role="dialog"
                 data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Details</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group row">
                                <table class="table table-bordered table-striped editable-grid"
                                       Id="saleDetailViewGrid">
                                    <thead>
                                    <tr>
                                        <th width="5%">Sl No.</th>
                                        <th width="20%">Item Code</th>
                                        <th width="20%">Item Name</th>
                                        <th width="20%">Selling Price</th>
                                        <th width="20%">Qty</th>
                                        <th width="20%">Total Amount</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


</body>
</html>


