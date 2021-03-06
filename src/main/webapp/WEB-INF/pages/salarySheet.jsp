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
<title class="title">Salary Sheet</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Human Resources</a>
                <span class="breadcrumb-item active">Salary Sheet</span>
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
            <form id="salarySheetId" action="<c:url value='/salarySheet'/> " class="form-horizontal globalForm">
                <input type="hidden" id="financialYearFrom" value="${currentUser.financialYearFrom}">
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-2 text-right required">Month:</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm resetfield" name="monthId" path="monthList"
                                         id="monthId" tabindex="1" required="true">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${monthList}" itemValue="value"
                                              itemLabel="text"/>
                            </form:select>
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
                </fieldset>
                <fieldset class="v-no-padding hidden" id="salarySheetDetail">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Salary Sheet detail</legend>
                    <div class="form-group row">
                        <div class="col-md-12">
                            <table class="table table-bordered table-striped editable-grid" Id="salarySheetTable"
                                   style="width:1500px !important;">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <%--                        <th width="5%">Srl</th>--%>
                                    <th class="hidden"></th>
                                    <th>Name</th>
                                    <th class="hidden"></th>
                                    <th hidden>TPN No</th>
                                    <th>Basic Salary</th>
                                    <th>Allow.</th>
                                    <th>Abst</th>
                                    <th>Ded.</th>
                                    <th>Gross Salary</th>
                                    <th>PF</th>
                                    <th>GIS</th>
                                    <th>Net Salary</th>
                                    <th>TDS</th>
                                    <th>HC</th>
                                    <th>Adv.</th>
                                    <th hidden>Total Rec.</th>
                                    <th>Take Home</th>
                                    <th class="hidden"></th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr role="row" class="odd">
                                    <td></td>
                                    <td></td>
                                    <td>Total</td>
                                    <td></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalBasicSalary"
                                               id="totalBasicSalary"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalAllowance" id="totalAllowance">
                                    </td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalAllowance" id="absentDays"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalDeduction" id="totalDeduction">
                                    </td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalGrossSalary"
                                               id="totalGrossSalary"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalPF" id="totalPF"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalGIS" id="totalGIS"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalNetSalary" id="totalNetSalary">
                                    </td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalTDS" id="totalTDS"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalHC" id="totalHC"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalAdvance" id="totalAdvance"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalTotalRecovery"
                                               id="totalTotalRecovery"></td>
                                    <td><input type="text" class="form-control form-control-sm" readonly name="totalTakeHome" id="totalTakeHome">
                                    </td>
                                    <td hidden></td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class=" col-md-2  col-lg-offset-3">
                            <input type="button" class="btn btn-primary btn-block " value="Export" id="btnExport"
                                   data-toggle="modal" data-target="#reportModal">
                        </div>
                        <div class=" col-md-2">
                            <input type="submit" class="btn btn-primary col-lg-offset-4" id="btnSalarySheet"
                                   value="Generate Salary Sheet">
                        </div>

                    </div>
                </fieldset>

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
            </form>
        </div>
    </div>
</div>
</body>
</html>


