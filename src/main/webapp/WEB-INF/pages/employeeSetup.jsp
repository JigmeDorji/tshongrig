<%--
  Created by IntelliJ IDEA.
  User: vcass
  Date: 11/30/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Employee Detail</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Human Resources</a>
                <span class="breadcrumb-item active">Employee Setup</span>
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
            <form id="itemSetupFrom" action="<c:url value='/employeeSetup'/> " class="form-horizontal globalForm">
                <input type="hidden" id="empId" name="empId" class="resetfield">
                <input type="hidden" id="pfPercentage" value="${pfPercentage}">

                <fieldset class="v-no-padding">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Employee detail list</legend>
                    <div class="form-group row">
                        <div class="col-md-11"></div>
                        <button type="button" class="btn btn-sm btn-primary col-lg-offset-11" id="btnAddNewEmp">Add New</button>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-12">
                            <table class="table" Id="employeeSetUpList">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th width="5%">Srl</th>
                                    <th class="hidden"></th>
                                    <th width="25%">Employee Name</th>
                                    <th width="10%">Cost</th>
                                    <th width="20%">Date of Birth</th>
                                    <th width="10%">CID No.</th>
                                    <th width="10%">Contact No.</th>
                                    <th width="10%">TPN No.</th>
                                    <th width="20%">Basic Salary</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </fieldset>

                <div class="modal fade" id="employeeDetailModal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <fieldset>
                                    <legend class="text-uppercase font-size-sm font-weight-bold">Enter employee details</legend>
                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Name </label>

                                        <div class="col-md-3">
                                            <input type="text" tabindex="1" maxlength="50" class="form-control form-control-sm resetfield"
                                                   name="empName"
                                                   id="empName" required="required"/>
                                        </div>

                                        <label class="col-md-2 right-align required">Date of birth</label>

                                        <div class="col-md-3">
                                            <input type="text" tabindex="2"
                                                   class="form-control form-control-sm  dateFormat2 right-align resetfield"
                                                   placeholder="DD.MM.YYYY" name="dateOfBirth" id="dateOfBirth"
                                                   required="required"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">CID No.</label>

                                        <div class="col-md-3">
                                            <input type="text" tabindex="2" maxlength="11" class="form-control form-control-sm resetfield"
                                                   name="cidNo"
                                                   id="cidNo" required="required"/>
                                        </div>
                                        <label class="col-md-2 right-align required">Contact No.</label>

                                        <div class="col-md-3">
                                            <input type="text" tabindex="8" maxlength="8" class="form-control form-control-sm resetfield"
                                                   name="contactNo"
                                                   id="contactNo"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align">TPN No. </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="tpnNo" id="tpnNo"/>
                                        </div>

                                        <label class="col-md-2 right-align required">Date of appointment/Joining
                                            service </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm  dateFormat2 right-align resetfield"
                                                   name="dateOfAppointment"
                                                   required="required"
                                                   placeholder="DD.MM.YYYY"
                                                   id="dateOfAppointment"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Basic Salary </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield right-align" name="basicSalary"
                                                   required="required"
                                                   id="basicSalary"/>
                                        </div>

                                        <label class="col-md-2 right-align ">Designation </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="post" id="post"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align">Increment amount </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield right-align"
                                                   name="incrementAmount"
                                                   id="incrementAmount"/>
                                        </div>

                                        <label class="col-md-2 right-align">Increment effect date </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm dateFormat2 resetfield right-align"
                                                   name="incrementEffectDate"
                                                   id="incrementEffectDate" placeholder="DD.MM.YYYY"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Service type </label>

                                        <div class="col-md-3">
                                            <form:select class="form-control form-control-sm resetfield" name="serviceType"
                                                         path="serviceTypeList"
                                                         id="serviceType" tabindex="12">
                                                <form:option value="">---Please Select---</form:option>
                                                <form:options items="${serviceTypeList}" itemValue="valueInteger"
                                                              itemLabel="text"/>
                                            </form:select>
                                        </div>

                                        <label class="col-md-2 right-align ">Allowance</label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield right-align" name="allowance"
                                                   id="allowance" value="0" required/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align">Email Address </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="emailAddress"
                                                   id="emailAddress"/>
                                        </div>

                                        <label class="col-md-2 right-align required">Village </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="village"
                                                   required="required"
                                                   id="village"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Gewog </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="gewog" required="required"
                                                   id="gewog"/>
                                        </div>

                                        <label class="col-md-2 right-align required">Dzongkhag </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="dzongkhag"
                                                   required="required"
                                                   id="dzongkhag"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Account No </label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield" name="accNo" required="required"
                                                   id="accNo"/>
                                        </div>

                                        <label class="col-md-2 right-align right-align">PF</label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield amount " name="pF" readonly
                                                   required="required"
                                                   id="pF"/>
                                        </div>

                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-2 right-align">GIS</label>

                                        <div class="col-md-3">
                                            <input type="text" class="form-control form-control-sm resetfield amount" name="gIS"
                                                   id="gIS"/>
                                        </div>

                                        <label class="col-md-2 right-align required">Cost</label>

                                        <div class="col-md-3">
                                            <select class="form-control form-control-sm" id="cost" required="required"
                                                    name="cost">
                                                <option value="">---Please select---</option>
                                                <option value="1" id="cashId">General</option>
                                                <option value="2" id="bankId">Production</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-2 right-align required">Status</label>

                                        <div class="col-md-3">
                                            <select class="form-control form-control-sm" id="status" required="required"
                                                    name="status">
                                                <option value="A">Active</option>
                                                <option value="I">Inactive</option>
                                            </select>
                                        </div>
                                    </div>


                                    <div class="form-group row pt-4">
                                        <div class="col-md-2"></div>
                                        <div class="col-sm-offset-2 col-md-2">
                                            <input type="submit" tabindex="4" class="btn btn-primary btn-block" value="Save"
                                                   id="btnSave">
                                        </div>
                                        <div class=" col-md-2">
                                            <input type="reset" tabindex="5" class="btn btn-primary btn-block" value="Reset"
                                                   id="btnReset">
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

        </div>

    </div>


</div>
</body>
</html>


