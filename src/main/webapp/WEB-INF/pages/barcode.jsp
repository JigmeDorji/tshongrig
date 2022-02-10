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
<title class="title">Barcode</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Barcode Generation</span>
</div>
<form id="folktaleCreationFrom" action="<c:url value='/barcode/generateBarcode'/> " class="form-horizontal globalForm"
      target="_blank">
    <%--<input type="hidden" id="itemCategoryId" name="itemCategoryId">--%>
    <fieldset>+
        <legend>Barcode generation criteria</legend>
        <div class=" form-group col-md-12">
            <label class="col-md-2 right-align required">Item Code</label>

            <div class="col-md-5">
                <form:select class="form-control resetField" path="itemList" id="itemCode" required="required" tabindex="1"
                             name="itemCode">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${itemList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
        </div>
        <div class=" form-group col-md-12">
            <label class="col-md-2 right-align required">Qty</label>

            <div class="col-md-5">
                <input type="text" tabindex="2" class="form-control" name="qty" id="qty" required="required"/>
            </div>
        </div>
        <div class="col-md-2 col-lg-offset-3">
            <input type="submit" class="btn btn-primary btn-block" value="Generate Barcode" id="generateBarcodeBtn">
        </div>
        </div>
    </fieldset>
</form>
</body>
</html>


