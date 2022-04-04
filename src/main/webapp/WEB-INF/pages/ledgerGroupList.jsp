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
<title class="title"> Accounting Ledgers </title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Ledger Detail</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->

<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="ledgerForm" action="<c:url value='/ledgerGroupList'/> " class="form-horizontal globalForm">
                <input type="hidden" id="ledgerId" name="ledgerId">
                <div class="col-md-12">
                    <fieldset>
                        <div class="table-responsive">
                            <table class="table navigatable_table" id="ledgerGroupList">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th></th>
                                    <th width="70%" height="40px" class="left-align">
                                        <span class="center-label" id="ledgerGroupHeadName"/></th>
                                    <th width="30%">Amount</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>
                </div>
            </form>
            <div class="col-sm-offset-1 col-md-2" style="padding-top: 20px">
                <input type="button" class="btn btn-primary btn-block" value="Return" id="previousPage"
                       tabindex="13">
            </div>

        </div>
    </div>
</div>
</body>
</html>


