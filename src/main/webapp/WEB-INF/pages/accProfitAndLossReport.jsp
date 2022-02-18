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
<title class="title">Income & Expenditure</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Ledgers</span>
</div>


<html>
<title class="title">Income & Expenditure</title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Financial Statement</a>
                <span class="breadcrumb-item active">Income & Expenditure</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card" id="printableArea">
        <div class="card-body">
            <div class="row">
                <div class="col-sm-12 printMe">
                    <div class="mb-4">
                        <div class="text-sm-center">
                            <h4 class="text-primary mb-2 mt-lg-2">${currentUser.companyName}</h4>
                            <ul class="list list-unstyled mb-0">
                                <li>Address: <span class="font-weight-semibold">${currentUser.companyAdd}</span></li>
                                <li>Email: <span class="font-weight-semibold">${currentUser.email}</span></li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <form id="ledgerForm" action="<c:url value='/accProfitAndLossReport'/> " class="form-horizontal globalForm">
                <input type="hidden" id="ledgerId" name="ledgerId">
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-1">From</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                                   name="fromDate"
                                   id="fromDate" required="required" value="${fromDate}"/>
                        </div>
                        <label class="col-md-1">To</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker"
                                   name="toDate"
                                   id="toDate" required="required" value="${toDate}"/>
                        </div>
                        <%--  <div class="col-md-2">
                              <input type="button" class="btn btn-primary btn-xs fa-search" id="searchBtn" value=" Search">
                          </div>--%>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="table-responsive">
                        <table class="table table-bordered navigatable_table" id="pnlTable">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="70%" height="40px" class="left-align">Particular</th>

                                <th width="30%">Amount</th>
                                <th class="hidden"></th>
                                <th class="hidden"></th>
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
    <div class="col-md-12 row mt-5">
        <div class="col-md-11"></div>
        <input type="button"  class="btn btn-outline-info fa fa-print" onclick="printDiv('printableArea')" value="Print" />
    </div>
</div>
<script>
    function printDiv(divName) {
        var printContents = document.getElementById(divName).innerHTML;
        var originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;

        window.print();

        document.body.innerHTML = originalContents;
    }
</script>
</body>
</html>


