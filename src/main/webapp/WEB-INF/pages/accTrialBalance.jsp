<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 7/30/2019
  Time: 8:52 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Trial Balance</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Financial Statement</a>
                <span class="breadcrumb-item active">Trial Balance Report</span>
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
            <form id="ledgerForm" action="<c:url value='/accTrialBalance'/> " class="form-horizontal globalForm">
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-2  required">From Date</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                                   name="fromDate"
                                   id="fromDate" required="required" value="${fromDate}"/>
                        </div>
                        <label class="col-md-2  required">To Date</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                                   name="toDate"
                                   id="toDate" required="required" value="${toDate}"/>
                        </div>
                    </div>
                </fieldset>

                <fieldset>
                    <div class="table-responsive">
                        <table class="table navigation_table" id="trialBalanceGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th rowspan="2"><p style="text-align:center">Particulars</p></th>
                                <th colspan="3" class="text-center">Closing Balance</th>
                            </tr>
                            <tr class="bg-primary text-white">
                                <th width="15%">Debit</th>
                                <th width="15%">Credit</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td>Grand Total</td>
                                <td>
                                    <label class="text-right parentText" id="totalDrAmount"></label>
                                </td>
                                <td>
                                    <label class="text-right parentText" id="totalCrAmount"></label>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

</body>
</html>



