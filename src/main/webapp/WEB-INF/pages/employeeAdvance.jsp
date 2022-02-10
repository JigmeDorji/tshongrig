<%--
  Created by IntelliJ IDEA.
  User: jigme
  Date: 10/27/2020
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Employee Advance</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Human Resources</a>
                <span class="breadcrumb-item active">Employee Advance</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="registrationForm" action="<c:url value='/employeeAdvance'/> " class="form-horizontal globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Advance Details</legend>

                    <div class="form-group row">
                        <label class="col-md-3  required">Date </label>

                        <div class="col-md-5">
                            <input type="text" tabindex="2" class="form-control form-control-sm  formatDate " value="${currentDate}"
                                   placeholder="DD.MM.YYYY" id="advanceDate" name="advanceDate" required="required" readonly/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3  required">Name</label>

                        <div class="col-md-5">
                            <form:select class="form-control form-control-sm resetField" path="employeeList" id="empId" required="required"
                                         name="empId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${employeeList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>


                    </div>
                    <div class="form-group row">
                        <label class=" col-md-3 left-label required">Amount</label>

                        <div class="col-md-5">
                            <input type="text" tabindex="1" class="form-control form-control-sm  amount" name="amount"
                                   id="amount" required="true"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class=" col-md-3 left-label required">Paid In</label>

                        <div class="col-md-5">
                            <select class="form-control form-control-sm resetField" id="isCash" required="required"
                                    name="isCash">
                                <option value="">---Please Select---</option>
                                <option value="1" id="cashId">Cash</option>
                                <option value="2" id="bankId">Bank</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group row" id="bankDetails" hidden>
                        <label class=" col-md-3 left-label">Select Bank Account</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerId" required="required"
                                         name="bankLedgerId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>


                    <div class="form-group row col-md-12">
                        <div class="col-md-3"></div>
                        <div class="col-md-2 col-lg-offset-3">
                            <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8" id="btnReset">
                        </div>
                        <div class="col-md-2" id="saveBtnDiv">
                            <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9" id="btnSave">
                        </div>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>
</div>
</body>
</html>




