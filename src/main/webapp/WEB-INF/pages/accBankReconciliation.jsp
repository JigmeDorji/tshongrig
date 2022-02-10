<%--
&lt;%&ndash;
  Created by IntelliJ IDEA.
  User: Bcass Sawa
  Date: 8/3/2019
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
&ndash;%&gt;
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Bank Reconciliation</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Bank Reconciliation</span>
</div>
<form id="registrationForm" action="<c:url value='/bankReconciliation'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Bank Reconciliation</legend>
        <input type="hidden" name="isEdit" id="isEdit" value="0"/>

        <div class="form-group">
            <label class="col-md-3 text-right required">Book Balance</label>

            <div class="col-md-3">
                <input type="text" tabindex="1" class="form-control resetfield" name="bookBalance"
                       id="bookBalance" required="true"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Cheque Issued But Not Encash </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control resetfield" name="chequeIssuedNotEncash"
                       id="chequeIssuedNotEncash" required="true"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Direct Deposit</label>

            <div class="col-md-3">
                <input type="text" tabindex="3" class="form-control resetfield" name="directDeposit"
                       id="directDeposit" required="true"/>
            </div>


        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Direct Transfer </label>

            <div class="col-md-3">
                <input type="text" tabindex="4" class="form-control resetfield" name="directTransfer"
                       id="directTransfer" required="true"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Previous Month Cheque Encash</label>

            <div class="col-md-3">
                <input type="text" required="true" tabindex="5" class="form-control resetfield"
                       name="previousMonthChequeEncash" id="previousMonthChequeEncash"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right  required">Bank Reconciliation Amount</label>

            <div class="col-md-3">
                <input type="text" tabindex="6" class="form-control resetfield" name="bankReconciliationAmount"
                       required="true" id="bankReconciliationAmount"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-3 col-md-2">
                <input type="reset" tabindex="4" class="btn btn-primary btn-block" value="Reset"
                       id="reset">
            </div>
            <div class=" col-md-2">
                <input type="submit" tabindex="4" class="btn btn-primary btn-block"
                       value="Save" id="btnSave">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>
--%>
