<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 10/20/2019
  Time: 6:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('13-EDIT')" var="hasEditRole"/>

<html>
<title class="title">Supplier Setup</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">Supplier Setup</span>
</div>
<form id="supplierSetupForm" action="<c:url value='/supplierSetup'/> " class="form-horizontal globalForm">
    <input type="hidden" tabindex="2" id="id" name="id"/>
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <fieldset>
        <legend>Credit Details</legend>
        <div class="form-group">
            <label class="col-md-3 text-right required">Supplier Name:</label>

            <div class="col-md-3">
                <input type="text" tabindex="2"
                       class="form-control" id="supplierName"
                       name="supplierName" required="required"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-righ required ">Address</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control" id="address"
                       name="address" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 text-right required">Email</label>

            <div class="col-md-3">
                <input type="text" required tabindex="4" class="form-control" id="email"
                       name="email"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right required">Contact No</label>

            <div class="col-md-3">
                <input type="text" required tabindex="5" class="form-control " id="contactNo" name="contactNo"/>
            </div>
        </div>

        <div class="form-group">
            <sec:authorize access="hasAuthority('13-ADD')">
                <div class="col-md-1 col-lg-offset-3">
                    <input type="submit" class="btn btn-primary btn-block" value="Save"
                           id="btnSave">
                </div>
            </sec:authorize>

            <div class="col-md-1">
                <input type="reset" class="btn btn-primary btn-block" value="Reset"
                       id="btnReset">
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend>Agency Details</legend>

        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid tableGrid" id="supplierListTable">
                    <thead>
                    <tr>
                        <th></th>
                        <th width="30%">Supplier Name</th>
                        <th width="35%">Address</th>
                        <th width="20%">Email</th>
                        <th width="20%">Contact No.</th>
                        <th width="5%"></th>
                    </tr>
                    </thead>
                    <tbody id="customerSearchBody">
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>



