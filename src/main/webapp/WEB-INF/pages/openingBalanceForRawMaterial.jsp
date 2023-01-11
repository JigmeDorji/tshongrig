<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Opening</title>


<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Material</a>
                <span class="breadcrumb-item active">Opening</span>
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
            <form id="returnItemForm" action="<c:url value='/openingBalanceForRawMaterial'/> " class="form-horizontal globalForm">
                <input type="hidden"  id="storageModifier" name="storageModifier" value="openingBalance">

                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Enter item details</legend>
                    <div class="form-group row">
                        <label class="col-md-3 left-label required">Date:</label>

                        <div class="col-md-3">
<%--                            <input type="text" tabindex="2" class="form-control form-control-sm datepicker formatDate right-align" value="${currentUser.financialYearFrom}"--%>
<%--                                   placeholder="DD.MM.YYYY" id="openBalanceEntryDate" name="openBalanceEntryDate" readonly required="required"/> --%>
                            <input type="text" tabindex="2" class="form-control form-control-sm datepicker formatDate right-align" value="${currentUser.financialYearFrom}"
                                   placeholder="DD.MM.YYYY" id="openBalanceEntryDate" name="openBalanceEntryDate"  required="required"/>
                        </div>
                    </div>


                    <div class="form-group row">
                        <label class="col-md-3 left-label required">Name</label>

                        <div class="col-md-3">
                            <input type="text" id="Name" class="form-control form-control-sm common" name="rawMaterialParticularName"
                                   required="required"/>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">qty</label>

                        <div class="col-md-1">
                            <input type="text" id="qty" class="form-control form-control-sm amount common" name="rawMaterialParticularQty" required="required"/>
                            <input type="hidden" id="originalQty" class="form-control form-control-sm amount common"/>
                        </div>
                        <label class=" col-md-1 right-align">Unit</label>
                        <div class="col-md-1">
                            <form:select class="form-control form-control-sm resetField" path="unitList" id="rawMaterialParticularUnit" required="required"
                                         name="rawMaterialParticularUnit">
                                <form:option value="">----</form:option>
                                <form:options items="${unitList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group row">

                        <label class=" col-md-3 left-label required">Price</label>

                        <div class="col-md-3">
                            <input type="text" id="rawMaterialParticularPrice" class="form-control form-control-sm amount common" name="rawMaterialParticularPrice"
                                   required="required"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class=" col-md-3 left-label required">Location</label>

                        <div class="col-md-3">

                            <form:select class="form-control form-control-sm resetField common" path="rawMaterialLocationSetupList" id="rawMaterialParticularLocation"
                                         required="required"
                                         name="rawMaterialParticularLocation">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${rawMaterialLocationSetupList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>
                    <br/>
                </fieldset>
                <div class="form-group row">
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-3">
                        <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </div>
            </form>




        </div>
    </div>
</div>

</body>
</html>


