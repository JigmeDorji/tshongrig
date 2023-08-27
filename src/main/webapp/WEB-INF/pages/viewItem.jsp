<%--
  Created by IntelliJ IDEA.
  User: vcass
  Date: 11/30/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Stock Balance</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="index.html" class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Inventory</a>
                <span class="breadcrumb-item active">View Item</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>

<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="viewItemForm" action="<c:url value='/viewItem'/> ">
                <div class="form-group row">
                    <div class="col-md-7">
                    </div>
                    <label class="col-form-label col-lg-2 required text-right">As On</label>
                    <div class="col-md-3">
                        <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                               id="asOnDate" required="required" value="${toDate}"/>
                        <br>

                        <a href="<c:url value="/viewItem/exportAllItemsToExcel"/>" class="btn btn-sm btn-success">Exporta
                            All Items To Excel</a>


                    </div>

                </div>
                <div class="form-group">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Item List</legend>

                    <div class="table-responsive">
                        <table class="table" Id="itemTable">

                            <c:if test="${currentUser.businessType!=8}">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th></th>
                                    <th class="commonFields">Part Number</th>
                                    <th>Item Code</th>
                                    <th>Part Name</th>
                                    <th>Location</th>
                                    <th>Qty</th>
                                    <th>Unit</th>
                                    <th class="commonFields">Selling Price</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </c:if>


                            <c:if test="${currentUser.businessType==8}">
                                <thead>
                                <tr class="bg-primary text-white text-center">
                                    <th></th>
                                    <th></th>
                                    <th class="commonFields">SI.NO</th>
                                    <th>Item Code</th>
                                    <th>Item Name</th>
                                    <th>Location</th>
                                    <th>Qty</th>
                                    <th>Unit</th>
                                    <th class="commonFields">Selling Price</th>
                                    <th class="commonFields">Action</th>
                                </tr>
                                </thead>
                                <tbody class="text-center">
                                </tbody>
                            </c:if>


                        </table>
                    </div>

                </div>
            </form>

            <c:if test="${currentUser.businessType!=8}">
                <form id="viewBranWiseItemForm" action="<c:url value='/viewItem/viewBrandWiseItemDetail'/> "
                      target="_blank">
                    <legend class="text-uppercase font-size-sm font-weight-bold">View Brand Wise Item</legend>
                    <div class="form-group">
                        <div class="col-md-12">
                            <div class="form-group row">
                                <label class="col-lg-2">Brand</label>
                                <div class="col-md-3">
                                    <form:select class="form-control form-control-sm resetField" path="brandList"
                                                 id="brandId" required="required"
                                                 name="brandId">
                                        <form:option value="">---Please Select---</form:option>
                                        <form:option value="0">All</form:option>
                                        <form:options items="${brandList}" itemValue="value" itemLabel="text"/>
                                    </form:select>
                                </div>

                                <div class="col-md-2">
                                    <input type="submit" class="btn btn-sm btn-primary" value="View"
                                           id="viewBrandWiseItem">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </c:if>

        </div>
    </div>
    <!-- /form inputs -->

</div>
<!-- /content area -->

</body>
</html>


