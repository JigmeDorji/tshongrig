<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/21/2022
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">RA Bill Detail</title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">RA Bill Detail</span>
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
            <fieldset class="v-no-padding">
                <legend class="text-uppercase font-size-sm font-weight-bold">RA Bill List</legend>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped editable-grid" Id="raBillDetailGrid">
                        <thead>
                        <tr class="bg-primary text-white">
                            <th width="5%">Srl</th>
                            <th hidden></th>
                            <th hidden></th>
                            <th width="8%">RA Serial No</th>
                            <th width="10%">Work Order No</th>
                            <th width="20%">RA Bill No</th>
                            <th width="20%">Bill Date</th>
                            <th width="8%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>

