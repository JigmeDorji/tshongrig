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
<html>
<title class="title"> Raw Material Stock Balance</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="index.html" class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Raw Material</a>
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
            <form id="viewItemForm" action="<c:url value='/viewItemsForRawMaterial'/> ">
                <div class="form-group row">
                    <div class="col-md-7">
                    </div>
                    <label class="col-form-label col-lg-2 required text-right">As On</label>
                    <div class="col-md-3">
                        <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                               id="asOnDate" name="asDate" required="required" value="${toDate}"/>
                    </div>
                </div>
                <div class="form-group">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Item List</legend>

                    <div class="table-responsive">
                        <table class="table" Id="itemTable">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th></th>
                                <th>Name </th>
                                <th>Location</th>
                                <th>Qty</th>
                                <th>Unit</th>
                                <th class="commonFields">Price</th>
                                <th>Action</th>

                            </tr>
                            </thead>
                            <tbody>

                            </tbody>

                        </table>
                    </div>

                </div>
            </form>
        </div>
    </div>
    <!-- /form inputs -->

</div>
<!-- /content area -->

</body>
</html>


