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
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Ledger</span>
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
            <form id="ledgerForm" action="<c:url value='/ledger'/> " class="globalForm">
                <input type="hidden" id="ledgerId" name="ledgerId">
                <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
                <input type="hidden" id="hasEditRole" value="${hasEditRole}">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Enter ledger details</legend>
                    <div class="form-group row">
                        <label class="col-md-2  required">Ledger Name</label>

                        <div class="col-md-4">
                            <input type="text" tabindex="1" class="form-control form-control-sm" name="ledgerName"
                                   id="ledgerName" required="required"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-2  required">Under</label>

                        <div class="col-md-4">
                            <form:select class="form-control form-control-sm resetField" name="accTypeId"
                                         path="accTypeList" tabindex="2"
                                         id="accTypeId" required="required">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${accTypeList}" itemValue="value"
                                              itemLabel="text"/>
                            </form:select>
                        </div>

                    </div>
                    <div class="bankAccDetail" hidden>
                        <div class="form-group row">
                            <label class="col-md-2  bankAccDetail">Reconciliation Date</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="3" class="form-control form-control-sm datepicker"
                                       name="reconciliationDate"
                                       id="reconciliationDate"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-2  ">Bank account holder details</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="4" class="form-control form-control-sm"
                                       name="bankAccHolderDetail"
                                       id="bankAccHolderDetail"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-2  ">Account holder name</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="5" class="form-control form-control-sm"
                                       name="accHolderName"
                                       id="accHolderName"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-2  required">Account No.</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="6" class="form-control form-control-sm numeric"
                                       name="accNo" required
                                       id="accNo"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-2 ">Bank Name</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="7" class="form-control form-control-sm " name="bankName"
                                       id="bankName"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-md-2 ">Branch</label>

                            <div class="col-md-4">
                                <input type="text" tabindex="8" class="form-control form-control-sm " name="branch"
                                       id="branch"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
<%--                        <c:if test = "${accTypeList.get(11)=""}}">--%>


                        <label class="col-md-2  required">Opening Balance</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="9" class="form-control form-control-sm right-align"
                                   name="openingBal"
                                   id="openingBal" required="required" value="0"/>
                        </div>
<%--                        </c:if>--%>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2">
                        </div>
                        <div class="col-md-2">
                            <input type="reset" tabindex="5" class="btn btn-sm btn-primary btn-block" value="Reset"
                                   id="btnReset">
                        </div>
                        <div class="col-md-2">
                            <input type="submit" tabindex="4" class="btn btn-sm btn-primary btn-block" value="Save"
                                   id="btnSave">
                        </div>

                    </div>
                </fieldset>
                <fieldset class="v-no-padding">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Ledger List</legend>
                    <div class="form-group row">
                        <div class="col-md-12">
                            <table class="table" Id="ledgerTable">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th width="10%">Sl</th>
                                    <th hidden></th>
                                    <th width="60%">Ledger Name</th>
                                    <th hidden></th>
                                    <th class="text-center" width="30%">Action</th>
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
                            <%--                            <h4 class="modal-title">Edit Ledger Details</h4>--%>
                        </div>
                        <div class="modal-body">
                            <form id="ledgerEditForm" class="form-horizontal ledgerEditForm" method="POST">
                                <input type="hidden" id="editLedgerId" name="ledgerId">
                                <legend class="text-uppercase font-size-sm font-weight-bold">Ledger Details</legend>
                                <div class="form-group row">
                                    <label class="col-md-4  required">Ledger Name</label>

                                    <div class="col-md-4">
                                        <input type="text" tabindex="1" class="form-control form-control-sm"
                                               id="editLedgerName"
                                               name="ledgerName" required="required"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-4  required">Under</label>

                                    <div class="col-md-4">
                                        <form:select class="resetField form-control form-control-sm select2" name="accTypeId" path="accTypeList"
                                                     tabindex="2"
                                                     id="editAccTypeId" required="required">
                                            <form:option value="">---Please Select---</form:option>
                                            <form:options items="${accTypeList}" itemValue="value"
                                                          itemLabel="text"/>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="editBankAccDetail">
                                    <div class="form-group row">
                                        <label class="col-md-4  bankAccDetail">Reconciliation Date</label>
                                        <input type="hidden" tabindex="2" class="form-control form-control-sm"
                                               id="bankId" name="bankId"/>
                                        <div class="col-md-4">

                                            <input type="text" tabindex="2"
                                                   class="form-control form-control-sm datepicker"
                                                   id="editReconciliationDate"
                                                   name="reconciliationDate"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label class="col-md-4 text-righ ">Bank account holder details</label>

                                        <div class="col-md-4">
                                            <input type="text" tabindex="3" class="form-control form-control-sm"
                                                   id="editBankAccHolderDetail"
                                                   name="bankAccHolderDetail"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-4  ">Account holder name</label>

                                        <div class="col-md-4">
                                            <input type="text" tabindex="4" class="form-control form-control-sm "
                                                   id="editAccHolderName"
                                                   name="accHolderName"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-4  required">Account No.</label>

                                        <div class="col-md-4">
                                            <input type="text" required tabindex="5"
                                                   class="form-control form-control-sm numeric" id="editAccNo"
                                                   name="accNo"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-4  bankAccDetail">Bank Name</label>

                                        <div class="col-md-4">
                                            <input type="text" tabindex="6" class="form-control form-control-sm "
                                                   id="editBankName"
                                                   name="bankName"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-md-4 ">Branch</label>

                                        <div class="col-md-4">
                                            <input type="text" tabindex="7" class="form-control form-control-sm"
                                                   id="editBranch" name="branch"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-md-4  required">Opening Balance</label>

                                    <div class="col-md-4">
                                        <input type="text" tabindex="8" class="form-control form-control-sm amount"
                                               name="openingBal"
                                               id="editOpeningBal" required="required" value="0"/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-2">
                                        <input type="submit" tabindex="9" class="btn btn-sm btn-primary btn-block"
                                               value="Update"
                                               id="editBtnSave">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>


