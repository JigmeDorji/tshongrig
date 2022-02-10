<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 5/15/2021
  Time: 1:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Receipt</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Receipt</span>
</div>
<form id="receiptForm" action="<c:url value='/receipt'/> " class="form-horizontal receiptForm">
    <fieldset>
        <legend>Voucher</legend>

        <div class="form-group">

            <label class="col-md-2 text-right required">Voucher Date </label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control formatDate" name="autoVoucherDate"
                       id="autoVoucherDate" required="true" value="${paymentDate}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 left-label required" id="paidToText">Received From</label>

            <div class="col-md-3">
                <input type="text" class="form-control" id="receiveFrom" name="receiveFrom" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 left-label required">Received For</label>

            <div class="col-md-3">
                <select class="form-control" id="receivedFor" required
                        name="receivedFor">
                    <option value="">---Please select ---</option>
                    <option value="1" id="costId">Advance</option>
                    <option value="2" id="advanceId">loan</option>
                    <option value="3" id="equity">Equity</option>
                    <option value="4">Material Advance</option>
                    <option value="5">Mobilization Advance</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 left-label required" id="descriptionText">Description</label>
            <div class="generalContent">
                <div class="col-md-3">
                    <input type="text" tabindex="2" class="form-control" name="description"
                           id="description" required="true"/>

                    <input type="hidden" tabindex="2" class="form-control" name="ledgerId"
                           id="ledgerId" required="true"/>
                </div>
            </div>
            <div class="capitalContent hidden">
                <div class="col-md-3">
                    <select class="form-control" id="capitalLedgerName" style="width: 100%"
                            name="capitalLedgerName">
                        <option value="">---Please select ---</option>
                        <option value="Capital">Capital</option>
                        <option value="Equity">Equity</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 text-right required">Amount</label>

            <div class="col-md-3">

                <input type="text" tabindex="3" class="form-control text-right" name="amountReceived" required
                       id="amountReceived"/>
            </div>
            <label class="col-md-2 text-right required" id="paidInText">Received In </label>

            <div class="col-md-3">
                <select class="form-control" id="isCash" required
                        name="isCash">
                    <option value="" id="id">---Please select ---</option>
                    <option value="1" id="cashId">Cash</option>
                    <option value="2" id="bankId">Bank</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div id="bankDetails" hidden>
                <label class=" col-md-2 text-right required">Select Bank Account</label>

                <div class="col-md-3">
                    <form:select class="form-control resetField" path="bankList" id="bankLedgerId" required="required"
                                 cssStyle="width: 100%"
                                 name="bankLedgerId">
                        <form:option value="">---Please Select---</form:option>
                        <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                    </form:select>
                </div>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-2  col-lg-offset-2">
                <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9">
            </div>

            <div class="col-md-2">
                <input type="reset" class="btn btn-primary btn-block" value="Reset" tabindex="8" id="btnReset">
            </div>
        </div>
    </fieldset>
</form>

<div class="modal fade" id="loadAccountDetailModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Loan Details</h4>
            </div>
            <div class="modal-body">
                <form id="loanDetailForm" action="<c:url value='/receipt'/>" class="form-horizontal loanDetailForm">
                    <fieldset>
                        <legend>Loan Detail</legend>

                        <input type="hidden" tabindex="1" class="form-control"
                               name="loanId" id="loanId"/>

                        <div class="form-group">
                            <label class="col-md-3 text-right required">Loan Ledger Name</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control resetfield" autofocus
                                       name="loanLedgerName" id="loanLedgerName" required="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 text-right required">Loan Account No. </label>

                            <div class="col-md-4">
                                <input type="text" tabindex="2" class="form-control resetfield"
                                       name="loanAccNo"
                                       id="loanAccNo" required="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 text-right required">Bank</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="3" class="form-control resetfield" name="bank"
                                       id="bank" required="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 text-right">Branch </label>

                            <div class="col-md-4">
                                <input type="text" tabindex="4" class="form-control resetfield" name="branch"
                                       id="branch"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 text-right">Monthly EMI</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="5" class="form-control resetfield" name="monthlyEmi"
                                       id="monthlyEmi"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-3"></div>

                            <div class="col-md-2">
                                <input type="submit" tabindex="9" class="btn btn-primary btn-block resetfield"
                                       value="Save" id="saveBtn">
                            </div>
                            <div class="col-md-2">
                                <input type="reset" tabindex="10" class="btn btn-primary btn-block"
                                       value="Reset" id="reRestBtn">
                            </div>

                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group">
                            <div class="col-md-12"
                            >
                                <table style="white-space: nowrap;width: 800px"
                                       class="table table-bordered table-striped editable-grid"
                                       id="loanDetailGrid">
                                    <thead>
                                    <tr>
                                        <th hidden></th>
                                        <th>Loan Name</th>
                                        <th>Loan Account No.</th>
                                        <th>Bank</th>
                                        <th>Branch</th>
                                        <th>Monthly EMI</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
