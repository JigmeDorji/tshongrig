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
<title class="title">Registration</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Registration</span>
</div>
<form id="registrationForm" action="<c:url value='/registration'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Enter registration detail</legend>
        <input type="hidden"   name="isEdit" id="isEdit" value="0"/>
        <div class="form-group">
            <label class="col-md-2 text-right required">Registration Number</label>

            <div class="col-md-2">
                <input type="text" tabindex="1" class="form-control resetfield" name="registration_no"
                       id="registration_no" required="true"/>
            </div>

            <%-- <div class="col-md-2">
                 <input type="button" class="btn btn-primary btn-block" value="Search"
                        id="searchBtnId">
             </div>--%>

            <div class="col-md-3">
                <input type="button" class="btn btn-primary btn-block" value="Generate Registration No."
                       id="autoGeneRegNo">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Customer Name </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control resetfield" name="customer_name"
                       id="customer_name" required="true"/>
            </div>


        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Vehicle No </label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control resetfield" name="vehicle_no"
                       id="vehicleNo" required="true"/>
            </div>


        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Vehicle Type </label>

            <div class="col-md-3">
                <input type="text" tabindex="4" class="form-control resetfield" name="vehicle_type"
                       id="vehicleTypeId" required="true"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Service Type</label>

            <div class="col-md-3">
                <input type="text" required="true" tabindex="5" class="form-control resetfield" name="service_type"
                       id="serviceType"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right  required">Section</label>

            <div class="col-md-3">
                <input type="text" tabindex="6" class="form-control resetfield" name="section" required="true"
                       id="sectionId"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Contact Number</label>

            <div class="col-md-3">
                <input type="text" tabindex="7" class="form-control numeric  resetfield" name="contact_no"
                       maxlength="8"
                       id="contact_no" required="true"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Payment Mode</label>

            <div class="col-md-3">
                <input type="radio" value="1" tabindex="8" id="cash" name="paymentMode" class="resetfield" checked>
                <label for="cash">Cash</label>
                <input type="radio" id="bill_no" tabindex="9" value="0" name="paymentMode" class="resetfield">
                <label for="bill_no">Bill Number</label>
            </div>
        </div>

        <div id="billNo" class="form-group">
            <label class="col-md-2 text-right" style="padding-left: 173px"></label>
            <fieldset class="col-md-3">
                <legend>Bill No</legend>
                <div class="col-md-12">
                    <input type="text" tabindex="10" class="form-control resetfield" name="bill_no"
                           placeholder="Enter bill number"
                           id="bill_no_id" required="true"/>
                </div>
            </fieldset>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Registration Date</label>

            <div class="col-md-3">
                <input type="text" class="form-control datepicker resetfield" name="registration_date"
                       id="registration_date" readonly value="${date}"/>
            </div>

            <label class="col-md-2 text-right required">Registration Time</label>

            <div class="col-md-3">
                <input type="text" class="form-control datepicker resetfield" name="registration_time"
                       id="registration_time" readonly value="${formattedTime}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right \">Due date for payment/Promise Date</label>

            <div class="col-md-3">
                <input type="text" tabindex="11" class="form-control datepicker resetfield" name="promise_date"
                       id="promise_date"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 left-label">Mechanic in charge</label>

            <div class="col-md-3">
                <form:select class="form-control resetfield" name="mechanicalInCharge" path="mechanicalList"
                             id="mechanicalInCharge" tabindex="12">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${mechanicalList}" itemValue="value"
                                  itemLabel="text"/>
                </form:select>
            </div>
            <div class="col-md-1">
                <input type="button" class="btn btn-primary btn-block btn-xs resetfield" value="Add" tabindex="13"
                       id="addToServiceGrid">
            </div>
            <div class="col-md-6">
                <table class="table table-bordered table-striped editable-grid tableGrid"
                       id="serviceDetailsGrid">
                    <thead>
                    <tr>
                        <th width="40%">Mechanic Name</th>
                        <th width="7%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <div class=" form-group">
            <label class="col-md-2 right-align">Agency Name</label>

            <div class="col-md-3">
                <form:select class="form-control resetfield" tabindex="1" path="agencyList" id="agencyId"
                             name="agencyId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${agencyList}" itemValue="id"
                                  itemLabel="text"/>
                </form:select>
            </div>

            <label class="col-md-2 right-align">Department</label>

            <div class="col-md-3">
                <input type="text" tabindex="11" class="form-control resetfield" name="department"
                       id="department"/>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-2"></div>

            <div class="col-md-2">
                <input type="reset" tabindex="15" class="btn btn-primary btn-block" value="Reset" id="reRestBtn">
            </div>

            <div class="col-md-2">
                <input type="submit" tabindex="14" class="btn btn-primary btn-block resetfield" value="Save" id="saveBtn">
            </div>
            <div class="col-md-2">
                <input type="button" tabindex="16" class="btn btn-primary btn-block" disabled="true" value="Edit" id="btnEdit">
            </div>

            <%-- <div class="col-md-3">
                 <input type="button" class="btn btn-primary btn-block" value="Add Work Order Detail"
                        id="addOrderNo">
             </div>--%>
        </div>

    </fieldset>

    <fieldset>
        <legend>Agency wise registration</legend>
        <div class="form-group">
            <div class="col-md-12 " style="overflow-x: scroll;white-space: nowrap; width: 1030px">
                <table class="table table-bordered table-hover" id="agencyWiseWorkOrderDetailGrid">
                    <thead>
                    <tr>
                        <th width="10%">Action</th>
                        <th hidden="true">Agency Id</th>
                        <th width="20%">Agency Name</th>
                        <th width="10%">Registration Number</th>
                        <th width="10%">Customer Name</th>
                        <th width="10%">Vehicle No</th>
                        <th width="10%">Vehicle Type</th>
                        <th width="10%">Service Type</th>
                        <th width="10%">Payment Mode</th>
                        <th width="10%">Registration Date</th>
                        <th width="10%">Mechanic</th>
                        <th width="10%">Service Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend>Registration</legend>
        <div class="form-group">
            <div class="col-md-12 ">
                <table class="table table-bordered table-striped editable-grid tableGrid"
                       id="registrationInListTable">
                    <thead>
                    <tr>
                        <th width="10%">Action</th>
                        <th width="10%">Registration Number</th>
                        <th width="10%">Customer Name</th>
                        <th width="10%">Vehicle No</th>
                        <th width="10%">Vehicle Type</th>
                        <th width="10%">Service Type</th>
                        <th width="10%">Payment Mode</th>
                        <th width="10%">Registration Date</th>
                        <th width="10%">Mechanic</th>
                        <th width="10%">Service Status</th>

                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>

    <div class="modal fade" id="myModal" role="dialog" style="width: 50%;">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header" style="background-color: #0480be">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="color: #ffffff">Confirmation</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <div style="text-align: center;"><h5>Are you sure you want to delete?</h5>
                        </div>
                    </div>
                    <div class="form-group">
                        <div style="text-align: center;"><h6>You will not be able to recover after deletion!</h6>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-4 col-md-3">
                            <input type="button" tabindex="4" class="btn btn-primary btn-block" value="Cancel"
                                   id="cancelBtn">
                        </div>
                        <div class=" col-md-4">
                            <input type="submit" tabindex="4" class="btn btn-primary btn-block"
                                   value="Confirm"
                                   id="btnConfirm">
                        </div>
                    </div>
                    <div class="form-group">

                        <div class="col-md-3">

                        </div>
                        <div class="col-sm-offset-7 col-md-3">

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</form>
</body>
</html>


