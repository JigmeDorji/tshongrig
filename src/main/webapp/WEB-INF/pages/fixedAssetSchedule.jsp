<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 10/23/2021
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Fixed asset schedule</title>

<body>

<div class="page_title">
    <span class="title">Fixed asset Management</span>
    <span class="subtitle">Fixed asset schedule</span>
</div>
<form id="ledgerForm" action="<c:url value='/fixedAssetSchedule'/> " class="form-horizontal globalForm">
    <fieldset>
        <div class="form-group">
            <label class="col-md-2 text-right required">As on date</label>

            <div class="col-md-4">
                <input type="text" tabindex="1" class="form-control datepicker text-right" name="asOnDate"
                       id="asOnDate" required="required" value="${asOnDate}"/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <div class="asset-of-schedule">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th rowspan="2" width="20%">Particular</th>
                        <th colspan="7">Gross Block</th>
                        <th colspan="3">Depreciation</th>
                        <th rowspan="2">Net Value</th>
                    </tr>

                    <tr>
                        <th>Purchase date</th>
                        <th>Purchase Value</th>
                        <th>Rate of Dep.</th>
                        <th>As on ${financialYearFrom}</th>
                        <th>Addition</th>
                        <th>Disposal</th>
                        <th>As on <p class="asOnStartFinancialYearText"></p></th>
                        <th>As on ${financialYearFrom}</th>
                        <th>Current Year</th>
                        <th>As on <p class="asOnStartFinancialYearText"></th>
                    </tr>
                    </thead>
                    <tbody id="tableContent">
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>




