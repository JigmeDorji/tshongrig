<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 4/7/2021
  Time: 6:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Salary Remittance</title>

<body>
<div class="page_title">
    <span class="title">Human Resource</span>
    <span class="subtitle">Salary Remittance</span>
</div>
<form id="folktaleCreationFrom" action="<c:url value='/salaryRemittance'/> " class="form-horizontal globalForm">
    <fieldset>
        <legend>Salary Remittance</legend>
        <div class="form-group">
            <label class="col-md-3 right-align required">Month</label>

            <div class="col-md-3">
                <form:select class="form-control form-select" path="monthList" id="monthId" name="month">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${monthList}" itemValue="value" itemLabel="text"/>
                </form:select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 right-align required">Cost</label>
            <div class="col-md-3">
                <select class="form-control"  id="cost" required="required"
                        name="cost">
                    <option value="">---Please select---</option>
                    <option value="1" id="cashId">General</option>
                    <option value="2" id="bankId">Production</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class=" col-md-3 right-align required">Select Bank Account</label>

            <div class="col-md-3">
                <form:select class="form-control resetField" path="bankList" id="bankLedgerId" required="required"
                             name="bankLedgerId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                </form:select>
            </div>
        </div>
    </fieldset>

    <fieldset class="v-no-padding">
        <legend>Employee detail</legend>
        <div class="form-group">
            <div class="col-md-12 table-responsive">
                <table class="table table-bordered table-striped editable-grid" Id="employeeSalarySheetList">
                    <thead>
                    <tr>
                        <th width="5%">Sl No.</th>
                        <th width="5%" class="hidden"></th>
                        <th width="30%">Name</th>
                        <th width="10%">Account No.</th>
                        <th width="20%">Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th class="hidden"></th>
                        <th></th>
                        <th>Total</th>
                        <th><input class="form-control text-center totalAmount"  readonly name="totalAmount" value="0"></th>
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


        <div class="form-group">
            <div class=" col-md-2  col-lg-offset-3">
                <input type="button" class="btn btn-primary btn-block " value="Export" id="btnExport"
                       data-toggle="modal" data-target="#reportModal">
            </div>
            <div class=" col-md-2">
                <input type="submit" class="btn btn-primary btn-block " value="Remit Salary" id="remitBtn">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>