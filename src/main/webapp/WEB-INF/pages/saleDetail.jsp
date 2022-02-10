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
<title class="title">Detail</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">Issue Detail</span>
</div>
<form id="folktaleCreationFrom" action="<c:url value='/saleDetail'/> " class="form-horizontal globalForm">
    <input type="hidden" id="locationSetUpId" name="locationSetUpId">
    <input type="hidden" id="businessTypeId" value="${currentUser.businessType}">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">

    <fieldset>
        <div class="form-group">
            <label class="col-md-2 text-right required">From</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control datepicker dynamic" name="fromDate"
                       id="fromDate" required="required" value="${toDate}"/>
            </div>
            <label class="col-md-2 text-right required">To</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control datepicker dynamic" name="toDate"
                       id="toDate" required="required" value="${toDate}"/>
            </div>

        </div>
    </fieldset>
    <fieldset class="v-no-padding">
        <legend>Details</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" Id="saleDetailGrid">
                    <thead>
                    <tr>
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
                <div class="form-group">
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


</body>
</html>


