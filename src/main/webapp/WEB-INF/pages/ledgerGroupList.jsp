<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/4/2019
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Balance Sheet Report</title>

<body>
<%--<style type="text/css">
    .table > tbody > tr > td {
        border-top: none;
        border-right: 1px solid #9d9d9d;
        border-left: 1px solid #9d9d9d;
        line-height: 1.42857;
        padding: 4px;
        vertical-align: top;
        text-align: center;
    }

    .table > thead {
        border-top: 1px solid #9d9d9d;
        border-right: 1px solid #9d9d9d;
        background-color: transparent;
        color: #000000;
    }

    .table > caption + thead > tr:first-child > td, .table > caption + thead > tr:first-child > th, .table > colgroup + thead > tr:first-child > td, .table > colgroup + thead > tr:first-child > th, .table > thead:first-child > tr:first-child > td, .table > thead:first-child > tr:first-child > th {
        border-top: 1px solid #9d9d9d;
        border-left: 1px solid #9d9d9d;
        border-right: 1px solid #9d9d9d;
    }
</style>--%>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">ledger Detail</span>
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
                <form id="ledgerForm" action="<c:url value='/ledgerGroupList'/> " class="form-horizontal globalForm">
                    <input type="hidden" id="ledgerId" name="ledgerId">
                    <div class="">
                        <table class="table table-bordered navigatable_table" id="ledgerGroupList">
                            <thead>
                            <tr class="text-white bg-primary">
                                <th></th>
                                <th width="70%" height="40px" class="left-align"><span class="center-label"
                                                                                       id="ledgerGroupHeadName"/></th>
                                <th width="30%">Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </form>


                <div class="col-sm-offset-1 col-md-2" style="padding-top: 20px">
                    <input type="button" class="btn btn-primary btn-block" value="Return" id="previousPage" tabindex="13">
                </div>

            </fieldset>
        </div>
    </div>
</div>

</body>
</html>


