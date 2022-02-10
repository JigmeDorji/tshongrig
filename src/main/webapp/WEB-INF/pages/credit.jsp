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
<title class="title">Credit</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Credit</span>
</div>
<form id="credit" action="<c:url value='/credit'/> " class="form-horizontal globalForm">
    <input type="hidden" id="creditId" name="creditId">
    <fieldset>
        <legend>Enter credit detail</legend>
        <div class="form-group">
            <label class="col-md-2 text-right required">Creditor Name </label>

            <div class="col-md-3">
                <input type="text" tabindex="1"  class="form-control resetfield" name="creditorName"
                       id="creditorName" required="true" />
            </div>
            <label class="col-md-2 text-right required">Contact Number</label>

            <div class="col-md-3">
                <input type="text" tabindex="1"  class="form-control resetfield" name="phoneNumber"
                       id="phoneNumber" />
            </div>

        </div>
        <div class="form-group">

            <label class="col-md-2 text-right required">Total Amount</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" required="true"  class="form-control numeric resetfield" name="totalAmt"
                       id="totalAmt"/>
            </div>
            <label class="col-md-2 text-right">Amount Released</label>

            <div class="col-md-3">
                <input type="text" tabindex="3"  class="form-control numeric resetfield" name="amtPaid"
                       id="amtPaid"/>
            </div>

        </div>
        <div class="form-group">
            <label class="col-md-2 text-right required">Credit On Date </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control datepicker resetfield" name="date"
                       id="date" required="true" />
            </div>

            <label class="col-md-2 text-right  ">Description </label>

            <div class="col-md-3">
                <textarea rows="8" class="form-control resetfield" name="description" id="description" ></textarea>
            </div>

        </div>
        <div class="form-group">

            <div class="col-sm-offset-2 col-md-1">
                <input type="submit" tabindex="4" class="btn btn-primary btn-block" value="Save" id="btnSave">
            </div>
            <div class=" col-md-1">
                <input type="reset" tabindex="5" class="btn btn-primary btn-block" value="Reset" id="btnReset">
            </div>

        </div>
    </fieldset>


    <fieldset class="v-no-padding">
        <legend>List of creditor</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" Id="creditListTable">
                    <thead>
                    <tr>
                        <th width="5%">Srl</th>
                        <th width="20%">Creditor Name</th>
                        <th width="10%">Contact No.</th>
                        <th width="10%">Total Amt</th>
                        <th width="10%">Amt Paid</th>
                        <th width="10%">Amt Due</th>
                        <th width="15%">Credited Date</th>
                        <th width="25%">Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <label class="col-lg-offset-4">You have credit in total:&nbsp;&nbsp;&nbsp;</label><span>NU</span>
                <input type="text" id="totalCredit" readonly style="border: hidden" value="0">
            </div>
        </div>

    </fieldset>
</form>

</body>
</html>


