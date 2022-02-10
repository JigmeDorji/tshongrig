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
<div class="page_title">
    <span class="title">Human Resource</span>
    <span class="subtitle">Salary Sheet</span>
</div>
<form id="salarySheetId" action="<c:url value='/salarySheet'/> " class="form-horizontal globalForm">
    <input type="hidden" id="financialYearFrom" value="${currentUser.financialYearFrom}">
    <fieldset>
        <div class="form-group">
            <label class="col-md-2 text-right required">Month:</label>

            <div class="col-md-3">
                <form:select class="form-control resetfield" name="monthId" path="monthList"
                             id="monthId" tabindex="1" required="true">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${monthList}" itemValue="value"
                                  itemLabel="text"/>
                </form:select>
            </div>

            <label class="col-md-2 right-align required">Cost</label>
            <div class="col-md-3">
                <select class="form-control"  id="cost" required="required"
                        name="cost">
                    <option value="">---Please select---</option>
                    <option value="1" id="cashId">General</option>
                    <option value="2" id="bankId">Production</option>
                </select>
            </div>
        </div>
    </fieldset>
    <fieldset class="v-no-padding hidden" id="salarySheetDetail">
        <legend>Salary Sheet detail</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" Id="salarySheetTable" style="width:1500px !important;" >
                    <thead>
                    <tr>
<%--                        <th width="5%">Srl</th>--%>
                        <th class="hidden"></th>
                        <th>Name</th>
                        <th class="hidden"></th>
                        <th hidden>TPN No</th>
                        <th>Basic Salary</th>
                        <th>Allow.</th>
                        <th >Abst</th>
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
                    <tr role="row" class="odd" >
                        <th></th>
                        <th></th>
                        <th>Total</th>
                        <th></th>
                        <td><input type="text" class="form-control" readonly name="totalBasicSalary" id="totalBasicSalary"></td>
                        <td><input type="text" class="form-control" readonly name="totalAllowance" id="totalAllowance"></td>
                        <td><input type="text" class="form-control" readonly name="totalAllowance" id="absentDays"></td>
                        <td><input type="text" class="form-control" readonly name="totalDeduction" id="totalDeduction"></td>
                        <th><input type="text" class="form-control" readonly name="totalGrossSalary" id="totalGrossSalary"></th>
                        <th><input type="text" class="form-control" readonly name="totalPF" id="totalPF"></th>
                        <th><input type="text" class="form-control" readonly name="totalGIS" id="totalGIS"></th>
                        <th><input type="text" class="form-control" readonly name="totalNetSalary" id="totalNetSalary"></th>
                        <th><input type="text" class="form-control" readonly name="totalTDS" id="totalTDS"></th>
                        <th><input type="text" class="form-control" readonly name="totalHC" id="totalHC"></th>
                        <th><input type="text" class="form-control" readonly name="totalAdvance" id="totalAdvance"></th>
                        <th><input type="text" class="form-control" readonly name="totalTotalRecovery" id="totalTotalRecovery"></th>
                        <th><input type="text" class="form-control" readonly name="totalTakeHome" id="totalTakeHome"></th>
                        <th hidden></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        <div class="form-group">
            <div class=" col-md-2  col-lg-offset-3">
                <input type="button" class="btn btn-primary btn-block " value="Export" id="btnExport"
                       data-toggle="modal" data-target="#reportModal">
            </div>
            <div class=" col-md-2">
                <input type="submit" class="btn btn-primary col-lg-offset-4" id="btnSalarySheet" value="Generate Salary Sheet">
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
</body>
</html>


