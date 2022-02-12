<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 8/4/2019
  Time: 7:37 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Cash Flow</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Financial Statement</a>
                <span class="breadcrumb-item active">Cash Flow</span>
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
            <form id="ledgerForm" action="<c:url value='/accCashFlow'/> " class="form-horizontal globalForm">

                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-2  required">From Date</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker" name="fromDate"
                                   id="fromDate" required="required" value="${fromDate}"/>
                        </div>
                        <label class="col-md-2  required">To Date</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="1" class="form-control form-control-sm datepicker" name="toDate"
                                   id="toDate" required="required" value="${toDate}"/>
                        </div>
                        <%--  <div class="col-md-2">
                              <input type="button" class="btn btn-primary btn-xs fa-search" id="searchBtn" value=" Search">
                          </div>--%>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="col-md-12">
                        <table class="table table-bordered" id="cashFlowGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th></th>
                                <th width="70%" class="left-align">Particulars</th>
                                <th width="15%">Amount</th>
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
</body>
</html>



