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
<title class="title">Barcode Generation</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Inventory</a>
                <span class="breadcrumb-item active">Barcode Generation</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="folktaleCreationFrom" action="<c:url value='/barcode/generateBarcode'/> "
                  class="form-horizontal globalForm"
                  target="_blank">
                <%--<input type="hidden" id="itemCategoryId" name="itemCategoryId">--%>
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Barcode generation criteria</legend>
                    <div class="form-group row col-md-12">
                        <label class="col-md-2 right-align required">Item Code</label>

                        <div class="col-md-5">
                            <form:select class="form-control form-control-sm resetField" path="itemList" id="itemCode"
                                         required="required"
                                         tabindex="1"
                                         name="itemCode">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${itemList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group row col-md-12">
                        <label class="col-md-2 right-align required">Qty</label>

                        <div class="col-md-5">
                            <input type="text" tabindex="2" class="form-control form-control-sm" name="qty" id="qty"
                                   required="required"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <div class="col-md-3">
                            <input type="submit" class="btn btn-primary btn-block" value="Generate Barcode"
                                   id="generateBarcodeBtn">
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>


