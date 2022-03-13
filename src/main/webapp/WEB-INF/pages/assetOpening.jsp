<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 9/16/2021
  Time: 2:39 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<title class="title">Opening</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Fix Asset Management</a>
                <span class="breadcrumb-item active">Opening</span>
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
            <form id="openingAndBuyingAssetForm" action="<c:url value='/assetOpening'/> "
                  class="form-horizontal globalForm">
                <fieldset>
                    <%--        <legend>Purchase Detail</legend>--%>
                    <div class="form-group row">
                        <label class="  col-md-2 right-align required">As On:</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm  formatDate right-align"
                                   readonly
                                   value="${currentUser.financialYearFrom}" id="asOnDate" name="purchaseDate"
                                   required="required"/>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Opening Asset List</legend>
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid" id="fixedAssetOpeningGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="2%">SL.</th>
                                <th width="20%">Particular</th>
                                <th width="5%">Purchase Date</th>
                                <th width="5%">Opening Balance</th>
                                <th width="5%">Depreciation</th>
                                <th width="5%">Purchase Rate</th>
                                <th width="5%">Qty</th>
                                <th width="5%">Purchase Value</th>
                                <th width="11%">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>

                        <br>
                        <div class="form-group row">
                            <sec:authorize access="hasAuthority('14-ADD')">
                                <div class="col-md-2">
                                    <input type="submit" class="btn btn-primary btn-group-sm" value="Save" id="btnSave">
                                </div>
                            </sec:authorize>
                            <%--                <sec:authorize access="hasAuthority('14-DELETE')">--%>
                            <%--                    <div class="col-md-2 ">--%>
                            <%--                        <input type="submit" class="btn btn-danger btn-block" value="Delete" id="btnDelete">--%>
                            <%--                    </div>--%>
                            <%--                </sec:authorize>--%>
                        </div>
                    </div>
                </fieldset>
                <div class="form-group row">
                    <div class="col-md-2">
                        <%--            <input type="button" class="btn btn-primary btn-block" value="Back" id="backBtn">--%>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
</body>
</html>

