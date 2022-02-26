<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 4/11/2021
  Time: 7:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Statutory Remittance</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Human Resources</a>
                <span class="breadcrumb-item active">Statutory Remittance</span>
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
            <form id="folktaleCreationFrom" action="<c:url value='/statutoryRemittance'/> " class="globalForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Statutory Remittance</legend>
                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Month</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm form-select" path="monthList" id="monthId" name="month">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${monthList}" itemValue="value" itemLabel="text"/>
                            </form:select>
                        </div>

                        <label class=" col-md-3 right-align required">Select Bank Account</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetField" path="bankList" id="bankLedgerId" required="required"
                                         name="bankLedgerId">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group row">
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
                </fieldset>


                <fieldset class="v-no-padding">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Statutory detail</legend>
                    <div class="form-group row">
                        <div class="col-md-12 table-responsive">
                            <table class="table table-bordered table-striped editable-grid" Id="statutoryRemittanceGrid">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th class="hidden"></th>
                                    <th width="15%">Name</th>
                                    <th width="10%">TPN No.</th>
                                    <th width="10%">Basic salary</th>
                                    <th width="5%">Allow.</th>
                                    <th width="10%">Gross salary</th>
                                    <th width="7%">PF</th>
                                    <th width="5%">GIS</th>
                                    <th width="10%">Net salary</th>
                                    <th width="9%">TDS</th>
                                    <th width="9%">HC</th>
                                    <th width="17%">Total</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td class="hidden"></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>Total</td>
                                    <td><input class="form-control form-control-sm text-center tDS" readonly name="tDS" value="0"></td>
                                    <td><input class="form-control form-control-sm text-center hC" readonly name="hC" value="0"></td>
                                    <td><input class="form-control form-control-sm text-center totalAmount" readonly name="totalAmount" value="0">
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>


                    <!-- Modal -->
                    <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="reportModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title" id="reportModalLabel">Generate Report Type</h4>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">

                                    <label class=" text-left">Report Format : </label>
                                    <input type="radio" name="reportFormat" id="xls" value="xls" checked/>
                                    <label for="xls"/>XLS</label>
                                    <label class="text-right"></label>
                                    <input type="radio" name="reportFormat" class="pdf" id="pdf" value="pdf"/>
                                    <label for="pdf"/>PDF</label>
                                    <label class="col-sm-2 col-form-label text-right"></label>
                                    <input type="radio" name="reportFormat" id="docx" value="docx"/>
                                    <label for="docx"/>DOCx</label>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="generateReportBtn">Generate</button>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="form-group row">
                        <div class=" col-md-2  col-lg-offset-3">
                            <input type="button" class="btn btn-primary btn-block " value="Export" id="btnExport"
                                   data-toggle="modal" data-target="#reportModal">
                        </div>
                        <div class=" col-md-2">
                            <input type="submit" class="btn btn-primary btn-block " value="Remit" id="remitStatutoryBtn">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>
