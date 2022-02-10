<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 8/25/2019
  Time: 9:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('14-DELETE')" var="hasDeleteRole"/>
<sec:authorize access="hasAuthority('14-EDIT')" var="hasEditRole"/>
<html>
<title class="title">View Item Detail</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">View Item Detail</span>
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <input type="hidden" tabindex="1" class="form-control"
           id="businessType" required="required" value="${currentUser.businessType}"/>
</div>
<form id="viewItemForm" action="<c:url value='/viewItemDetail'/> " class="form-horizontal globalForm">

    <div class="form-group">
        <label class="col-md-2 right-align">Item Code</label>

        <div class="col-md-2">
            <input type="text" tabindex="2" class="form-control" value="${itemCode}"
                   id="itemCode" readonly/>
        </div>

        <label class="col-md-2 right-align">Item Name</label>

        <div class="col-md-2">
            <input type="text" tabindex="3" class="form-control" value="${itemName}"
                   id="itemName" readonly/>
        </div>

        <label class="col-md-2 text-right required right-label">As On.</label>
        <div class="col-md-2">
            <input type="text" tabindex="1" class="form-control datepicker"
                   id="asOnDate" required="required" readonly value="${asOnDate}"/>
        </div>
    </div>

    <fieldset class="v-no-padding">
        <legend>Item Purchase Detail</legend>


        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid tableGrid" Id="viewItemDetailTable">
                    <thead>
                    <tr>
                        <th>SL</th>
                        <th></th>
                        <th></th>
                        <th width="15%">Trans. Date</th>
                        <th width="30%">Particular</th>
                        <th width="10%">Qty</th>
                        <th width="10%">Rate</th>
                        <th width="15%">Balance</th>
                        <th width="5%"></th>
                        <th width="5%" hidden></th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>

    <div class="form-group">
        <div class="col-md-2">
            <input type="button" class="btn btn-primary btn-block" value="Return" id="backBtn">
        </div>
    </div>
</form>
</body>
</html>



