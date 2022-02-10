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
<title class="title">saleItem</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Sale Item</span>
</div>
<form id="folktaleCreationFrom" action="<c:url value='/itemSale'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Enter item information</legend>
        <div class="form-group">
            <label class="col-md-2 left-label required">Item Category </label>
            <label class="col-md-2 left-label required">Price per Qty(Nu)</label>
            <label class="col-md-4 left-label required">Qty</label>


        </div>

        <div class="form-group">
            <div class="col-md-2">
                <input type="text" class="form-control resetField" name="itemCode" id="itemCode" tabindex="1"
                       maxlength="10"/>
            </div>
            <div class="col-md-2">
                <input type="text" class="form-control resetField" placeholder="Auto get on item code enter" name="itemName" id="itemName" readonly tabindex="2"
                       maxlength="33"/>
            </div>
            <div class="col-md-2">
                <input type="text" class="form-control numeric resetField"   name="pricePerQty"  tabindex="3"
                       id="pricePerQty" maxlength="10">
            </div>
            <div class="col-md-1">
                <input type="button" class="btn btn-primary btn-block" value="Add" tabindex="4" id="addToGrid">
            </div>
        </div>
        <br>

        <div class="form-group">
            <div class="col-md-8">
                <table class="table table-bordered table-striped editable-grid tableGrid"
                       id="addDetailsGrid">
                    <thead>
                    <tr>
                        <th width="80px">Item Code</th>
                        <th width="180">Item Name</th>
                        <th width="40px">Price(Nu)</th>
                        <th width="5px">Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>

        </div>
        <div class="form-group" id="grandTotalArea" hidden="true">
            <div class=" col-sm-offset-4">
                &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <label>Grand Total:&nbsp;</label>
                <input type="text" style="border: hidden" class="resetField" name="itemCode" id="grandTotal" value="0" readonly/>
            </div>
        </div>
        <div class="form-group" >
            <div class="col-sm-offset-3 col-md-2">
                <input type="submit" class="btn btn-primary btn-block" value="Delete Confirm" id="btnDeleteConfirm">
            </div>
        </div>


    </fieldset>
</form>
</body>
</html>


