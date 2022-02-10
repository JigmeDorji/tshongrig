<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/10/2019
  Time: 7:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: SonamPC
  Date: 26-Nov-18
  Time: 7:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Pending Bill</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Bill Ledger</span>
</div>
<div id="dialog" style="display: none;">
    <div>
        <iframe id="frame"></iframe>
    </div>
</div>
<form id="pendingBillForm" action="<c:url  value='/pendingBill'/> " target="_blank"
      class="form-horizontal globalForm">
    <fieldset>
        <legend>Pending Bills</legend>
        <div class=" form-group">
            <label class="col-md-2 right-align required">Agency Name</label>

            <div class="col-md-3">
                <form:select class="form-control" tabindex="1" path="agencyList" id="agencyId"
                             name="agencyId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${agencyList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
            <label class="col-md-1 right-align required">Status</label>

            <div class="col-md-2">
                <select class="form-control" tabindex="2" id="status"
                        name="agencyId">
                    <option value="">All</option>
                    <option value="P" selected>Not Paid</option>
                    <option value="B">Paid</option>
                </select>
            </div>
            <input type="button" class="btn btn-xs btn-primary" value="Search" id="searchBtn">
        </div>

        <div class="form-group">
            <div class="col-md-12 " style="overflow-x: scroll;white-space: nowrap; width: 1000px">
                <table class="table table-bordered table-hover" id="pendingBillGrid">
                    <thead>
                    <tr>
                        <th>Voucher No</th>
                        <th>Voucher Entry Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>




