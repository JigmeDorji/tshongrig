<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/4/2019
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('3-DELETE')" var="hasDeleteRole"/>
<sec:authorize access="hasAuthority('3-EDIT')" var="hasEditRole"/>
<html>
<title class="title">Ledger</title>

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Ledger</span>
</div>
<form id="ledgerForm" action="<c:url value='/ledger'/> " class="form-horizontal globalForm">
    <input type="hidden" id="ledgerId" name="ledgerId">
    <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
    <input type="hidden" id="hasEditRole" value="${hasEditRole}">
    <fieldset>
        <legend>Enter ledger details</legend>
        <div class="form-group">
            <label class="col-md-2 text-right required">Ledger Name</label>

            <div class="col-md-4">
                <input type="text" tabindex="1" class="form-control" name="ledgerName"
                       id="ledgerName" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 text-right required">Under</label>

            <div class="col-md-4">
                <form:select class="form-control resetField" name="accTypeId" path="accTypeList" tabindex="2"
                             id="accTypeId" required="required">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${accTypeList}" itemValue="value"
                                  itemLabel="text"/>
                </form:select>
            </div>
        </div>
        <div class="bankAccDetail" hidden>
            <div class="form-group">
                <label class="col-md-2 text-right bankAccDetail">Reconciliation Date</label>

                <div class="col-md-4">
                    <input type="text" tabindex="3" class="form-control datepicker"
                           name="reconciliationDate"
                           id="reconciliationDate"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 text-right ">Bank account holder details</label>

                <div class="col-md-4">
                    <input type="text" tabindex="4" class="form-control" name="bankAccHolderDetail"
                           id="bankAccHolderDetail"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 text-right ">Account holder name</label>

                <div class="col-md-4">
                    <input type="text" tabindex="5" class="form-control" name="accHolderName"
                           id="accHolderName"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 text-right required">Account No.</label>

                <div class="col-md-4">
                    <input type="text" tabindex="6" class="form-control numeric" name="accNo" required
                           id="accNo"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 text-right">Bank Name</label>

                <div class="col-md-4">
                    <input type="text" tabindex="7" class="form-control " name="bankName"
                           id="bankName"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 text-right">Branch</label>

                <div class="col-md-4">
                    <input type="text" tabindex="8" class="form-control " name="branch"
                           id="branch"/>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 text-right required">Opening Balance</label>

            <div class="col-md-3">
                <input type="text" tabindex="9" class="form-control right-align" name="openingBal"
                       id="openingBal" required="required" value="0"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-md-1">
                <input type="reset" tabindex="5" class="btn btn-primary btn-block" value="Reset" id="btnReset">
            </div>
            <div class="col-md-1">
                <input type="submit" tabindex="4" class="btn btn-primary btn-block" value="Save" id="btnSave">
            </div>

        </div>
    </fieldset>
    <fieldset class="v-no-padding">
        <legend>Ledger List</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" Id="ledgerTable">
                    <thead>
                    <tr>
                        <th width="2%">Sl</th>
                        <th></th>
                        <th width="30%">Ledger Name</th>
                        <th></th>
                        <th width="10%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

            </div>
        </div>
    </fieldset>
</form>


<div class="modal fade" id="editLedgerModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit Ledger Details</h4>
            </div>
            <div class="modal-body">
                <form id="ledgerEditForm" class="form-horizontal ledgerEditForm" method="POST">
                    <input type="hidden" id="editLedgerId" name="ledgerId">
                    <fieldset>
                        <legend>Ledger Details</legend>
                        <div class="form-group">
                            <label class="col-md-2 text-right required">Ledger Name</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="1" class="form-control" id="editLedgerName"
                                       name="ledgerName" required="required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 text-right required">Under</label>

                            <div class="col-md-4">
                                <form:select class="resetField select2" name="accTypeId" path="accTypeList"
                                             style="width:285px"
                                             tabindex="2"
                                             id="editAccTypeId" required="required">
                                    <form:option value="">---Please Select---</form:option>
                                    <form:options items="${accTypeList}" itemValue="value"
                                                  itemLabel="text"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="editBankAccDetail">
                            <div class="form-group">
                                <label class="col-md-2 text-right bankAccDetail">Reconciliation Date</label>
                                <input type="hidden" tabindex="2" class="form-control" id="bankId" name="bankId"/>
                                <div class="col-md-4">

                                    <input type="text" tabindex="2"
                                           class="form-control datepicker" id="editReconciliationDate"
                                           name="reconciliationDate"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-righ ">Bank account holder details</label>

                                <div class="col-md-4">
                                    <input type="text" tabindex="3" class="form-control" id="editBankAccHolderDetail"
                                           name="bankAccHolderDetail"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-right ">Account holder name</label>

                                <div class="col-md-4">
                                    <input type="text" tabindex="4" class="form-control " id="editAccHolderName"
                                           name="accHolderName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-right required">Account No.</label>

                                <div class="col-md-4">
                                    <input type="text" required tabindex="5" class="form-control numeric" id="editAccNo"
                                           name="accNo"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-right bankAccDetail">Bank Name</label>

                                <div class="col-md-4">
                                    <input type="text" tabindex="6" class="form-control " id="editBankName"
                                           name="bankName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-right">Branch</label>

                                <div class="col-md-4">
                                    <input type="text" tabindex="7" class="form-control" id="editBranch" name="branch"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 text-right required">Opening Balance</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="8" class="form-control amount"
                                       name="openingBal"
                                       id="editOpeningBal" required="required" value="0"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-2"></div>
                            <div class="col-md-2">
                                <input type="submit" tabindex="9" class="btn btn-primary btn-block" value="Update"
                                       id="editBtnSave">
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


