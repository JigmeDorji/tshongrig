<%--
  Created by IntelliJ IDEA.
  User: Bcass Sawa
  Date: 5/18/2019
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Voucher Entry List</title>
<style type="text/css">
    .table-bordered > tfoot > tr > td, .table-bordered > tfoot > tr > th {
        border: none;
        padding: 2px;
    }
</style>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Ledger Details</span>
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
            <legend class="text-uppercase font-size-sm font-weight-bold">Ledger details</legend>

            <form id="voucherListForm" action="<c:url value='/voucherGroupList'/>" class="form-horizontal globalForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <fieldset>
                    <div class="form-group row">
                        <label class="col-md-2">Ledger Name:</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control form-control-sm resetfield"
                                   readonly id="ledgerName"/>
                        </div>

                        <label class="col-md-1 right-align  col-md-offset-1">From</label>
                        <div class="col-md-2">
                            <input type="text" tabindex="2"
                                   class="form-control form-control-sm  datepicker right-align dateRange"
                                   value="${fromDate}"
                                   id="fromDate"/>
                        </div>
                        <label class="col-md-1 right-align ">To</label>
                        <div class="col-md-2">
                            <input type="text" tabindex="2"
                                   class="form-control form-control-sm right-align datepicker dateRange"
                                   value="${toDate}"
                                   id="toDate"/>
                        </div>
                    </div>
                    <div class="partyDetail hidden">
                        <div class="form-group row">
                            <label class="col-md-2 right-label"><b>Address</b> </label>

                            <div class="col-md-3">
                                <input type="text" tabindex="4" class="form-control form-control-sm resetfield"
                                       name="partyAddress"
                                       readonly
                                       id="partyAddress"/>
                            </div>

                        </div>

                        <div class="form-group row">
                            <label class="col-md-2 right-label"><b>Contact No.</b></label>

                            <div class="col-md-3">
                                <input type="text" tabindex="5" class="form-control form-control-sm resetfield" readonly
                                       name="partyContactNo"
                                       id="partyContactNo"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-md-2 right-label"><b>Email ID.</b></label>

                            <div class="col-md-3">
                                <input type="text" tabindex="6" class="form-control form-control-sm resetfield"
                                       name="partyEmail"
                                       readonly
                                       id="partyEmail"/>
                            </div>
                        </div>


                    </div>


                    <div class="form-group row">
                        <div class="col-md-12">
                            <table class="table datatable-scroller-api" id="voucherListGrid">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th width="10%">Date</th>
                                    <th width="8%">Voucher No.</th>
                                    <th width="40%">Particulars</th>
                                    <th hidden></th>
                                    <th width="10%">Dr</th>
                                    <th width="10%">Cr</th>
                                    <th width="4%">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td style="border-top: dotted;border-left: dotted;"></td>
                                    <td style="border-top: dotted"></td>
                                    <td class="right-align" style="border-top: dotted"><strong>Opening Balance</strong>
                                    </td>
                                    <td hidden="hidden"></td>
                                    <td style="border-top: dotted">
                                        <input type="text"
                                               class="form-control form-control-sm openingBalance  right-align"
                                               style="border-radius: 0;height: 28px !important;" readonly>
                                    </td>
                                    <td style="border-top: dotted;border-right: dotted;">
                                        <input type="text"
                                               class="form-control form-control-sm openBalanceAmount  right-align"
                                               style="border-radius: 0;height: 28px !important;" readonly>
                                    </td>

                                </tr>
                                <tr id="currentEarningId">
                                    <td style="border-left: dotted;"></td>
                                    <td></td>
                                    <td class="right-align"><strong>Current</strong></td>
                                    <td hidden="hidden"></td>
                                    <td><input type="text" class="form-control form-control-sm   right-align"
                                               style="border-radius: 0;height: 28px !important;" id="totalDr" readonly>
                                    </td>
                                    <td style="border-right: dotted;">
                                        <input type="text"
                                               class="form-control form-control-sm   right-align"
                                               style="border-radius: 0; height: 28px !important;"
                                               id="totalCr" readonly></td>
                                </tr>
                                <tr id="retainedEarningArea" hidden>
                                    <td style="border-left: dotted;"></td>
                                    <td></td>
                                    <td class="right-align"><strong>Retained Earning</strong></td>
                                    <td hidden="hidden"></td>
                                    <td><input type="text" class="form-control form-control-sm   right-align"
                                               style="border-radius: 0;height: 28px !important;" id="retainedEarningDr"
                                               readonly>
                                    </td>
                                    <td style="border-right: dotted;" >
                                        <input type="text"
                                               class="form-control form-control-sm   right-align"
                                               style="border-radius: 0; height: 28px !important;"
                                               id="retainedEarningCr" readonly></td>
                                </tr>
                                <tr>
                                    <td style="border-top: dotted;border-left: dotted;border-bottom: dotted;"></td>
                                    <td style="border-top: dotted;border-bottom: dotted;"></td>
                                    <td class="right-align" style="border-top: dotted;border-bottom: dotted;"><strong>Closing
                                        Balance</strong></td>
                                    <td hidden="hidden"></td>

                                    <td style="border-top: dotted;border-bottom: dotted">
                                        <input type="text"
                                               class="form-control form-control-sm   right-align"
                                               style="border-radius: 0;height: 28px !important;"
                                               id="totalClosingBalanceDr" readonly>
                                    </td>

                                    <td style="border-top: dotted;border-bottom: dotted;border-right: dotted;">
                                        <input type="text"
                                               class="form-control form-control-sm   right-align"
                                               style="border-radius: 0;height: 28px !important;"
                                               id="totalClosingBalanceCr" readonly>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>


                    </div>
                    <br/>
                    <div class="form-group row">
                        <div class="col-sm-offset-1  col-md-2">
                            <input type="button" class="btn btn-primary btn-block" value="Return"
                                   id="previousPage">
                        </div>
                    </div>

                    <div class="col-sm-offset-8  col-md-4 " style="padding-left: 4.5%">
                        <input type="button" tabindex="4" class="btn btn-primary btn-block hidden"
                               value="Reconcil Bank Amount"
                               id="reconciliationId" readonly>
                    </div>
                </fieldset>

                <div id="reconciliationShow" hidden>
                    <fieldset>
                        <legend class="text-uppercase font-size-sm font-weight-bold">Bank Reconciliation</legend>

                        <div class="form-group row">
                            <label class="col-md-3 text-right required">Book Balance</label>

                            <div class="col-md-3">
                                <input type="text" class="form-control form-control-sm resetfield right-align"
                                       name="bookBalance"
                                       id="bookBalance" readonly/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-md-3 text-right">Cheque Issued But Not Encash </label>

                            <div class="col-md-3">
                                <input type="text" class="form-control form-control-sm resetfield right-align" value="0"
                                       name="chequeIssuedNotEncash" id="chequeIssuedNotEncash"/>
                            </div>

                        </div>

                        <div class="form-group row">
                            <label class="col-md-3 text-right">Direct Deposit</label>

                            <div class="col-md-3">
                                <input type="text" tabindex="3"
                                       class="form-control form-control-sm resetfield right-align"
                                       name="directDeposit"
                                       value="0"
                                       id="directDeposit">
                            </div>


                        </div>

                        <div class="form-group row">
                            <label class="col-md-3 text-right">Direct Transfer </label>

                            <div class="col-md-3">
                                <input type="text" tabindex="4"
                                       class="form-control form-control-sm resetfield right-align"
                                       name="directTransfer"
                                       value="0"
                                       id="directTransfer"/>
                            </div>

                        </div>

                        <div class="form-group row">
                            <label class="col-md-3 text-right">Previous Month Cheque Encash</label>

                            <div class="col-md-3">
                                <input type="text" tabindex="5"
                                       class="form-control form-control-sm resetfield right-align" value="0"
                                       name="previousMonthChequeEncash" id="previousMonthChequeEncash"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-md-3 text-right  required">Bank Reconciliation Amount</label>

                            <div class="col-md-3">
                                <input type="text" tabindex="6"
                                       class="form-control form-control-sm resetfield right-align"
                                       name="bankReconciliationAmount" id="bankReconciliationAmount"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-offset-3 col-md-2">
                                <input type="reset" tabindex="4" class="btn btn-primary btn-block" value="Reset"
                                       id="reset">
                            </div>
                            <div class=" col-md-2">
                                <input type="button" tabindex="4" class="btn btn-primary btn-block"
                                       value="Save" id="btnSaveReconcilAmount">
                            </div>
                        </div>
                    </fieldset>

                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

