<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/6/2022
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">BOQ Setup</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">BOQ Setup</span>
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
            <form id="xlsFileForm" class="form-horizontal xlsFileForm globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">BOQ details</legend>

                    <input type="text" class="form-control form-control-sm hidden" name="boqId"
                           id="boqId" value="${boqId}"/>

                    <div class="form-group row">
                        <label class=" col-md-2 right-align required">Work Order No</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-sm" name="workOrderNo"
                                   id="workOrderNo" required="true"/>
                        </div>
                        <label class=" col-md-2 right-align required">Employing Agency</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-sm" name="employingAgency"
                                   id="employingAgency" required="true"/>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label class=" col-md-2 right-align required">Name of Work</label>

                        <div class="col-md-4">
                <textarea class="form-control form-control-sm" name="nameOfWork"
                          id="nameOfWork" required="true"></textarea>
                        </div>

                        <label class=" col-md-2 right-align required">Work Order Date</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-sm dateFormat2" name="workOrderDate" placeholder="DD.MM.YYYY"
                                   id="workOrderDate" required="true"/>
                        </div>
                    </div>


                    <div class="form-group row">
                        <label class=" col-md-2 right-align required">Work Start Date</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-sm dateFormat2" name="workStartDate" placeholder="DD.MM.YYYY"
                                   id="workStartDate" required="true"/>
                        </div>

                        <label class=" col-md-2 right-align required">Completion Date</label>

                        <div class="col-md-4">
                            <input type="text" tabindex="1" class="form-control form-control-sm dateFormat2" name="completionDate"
                                   placeholder="DD.MM.YYYY"
                                   id="completionDate" required="true"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Select Excel File</label>

                        <div class="col-md-4">
                            <input type="file" class="form-control " name="excelMultipartFile"
                                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                   id="fileXlsId" required="required"/>
                        </div>
                    </div>
                </fieldset>
            </form>

            <fieldset class="v-no-padding">
                <legend class="text-uppercase font-size-sm font-weight-bold">BOQ Item List</legend>
                <div class="form-group row">
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid" Id="boqDataListGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th width="5%">Srl</th>
                                <th width="10%">Code</th>
                                <th width="20%">Description</th>
                                <th width="10%">Unit</th>
                                <th width="10%">Qty</th>
                                <th width="10%">Unit Price</th>
                                <th width="10%">Unit Price (In words)</th>
                                <th width="10%">Total Price (In figures)</th>
                                <th width="10%">Total Price (In words)</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </fieldset>

            <br>
            <div class="form-group row">
                <div class="col-md-10"></div>
                <div class="col-md-2">
                    <input type="button" class="btn btn-primary btn-block" value="Save" id="btnSave">
                </div>
            </div>
        </div>
    </div>
</div>



</body>
</html>



