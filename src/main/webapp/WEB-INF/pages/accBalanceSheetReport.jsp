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
<title class="title">Financial Position</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Financial Statement</a>
                <span class="breadcrumb-item active">Financial Position</span>
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
            <form id="ledgerForm" action="<c:url value='/accBalanceSheetReport'/> " class="form-horizontal globalForm">
                <input type="hidden" id="ledgerId" name="ledgerId">
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-2  required right-label">As On.</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                                   id="toDate" required="required" value="${toDate}"/>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="table-responsive">
                        <table class="table navigatable_table" id="balanceSheetTable">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="70%" height="40px" class="left-align">Particular</th>
                                <th width="30%">Amount</th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>

                        </table>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>
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
</body>
</html>


