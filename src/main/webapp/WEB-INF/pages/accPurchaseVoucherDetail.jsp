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

<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">Voucher Entry List</span>
</div>

<style type="text/css">
    .table-bordered > tfoot > tr > td, .table-bordered > tfoot > tr > th {
        border: none;
        padding: 2px;
    }
</style>
<form id="voucherListForm" action="<c:url value='/accPurchaseVoucherDetail/'/> "
      class="form-horizontal globalForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <fieldset>
        <legend>Voucher List Details</legend>

        <div class="form-group">
            <label class="col-md-2  text-right">&nbsp;<b>Ledger Name:</b></label>

            <div class="col-md-3" style="right: 6%">
                <input type="text" class="form-control resetfield left-align"
                       style="border-radius: 0; height: 28px !important;"
                       readonly name="ledgerName" id="ledgerName"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-hover" id="voucherListGrid">
                    <thead>
                    <tr>
                        <th width="10%">Date</th>
                        <th width="8%">Voucher No.</th>
                        <th width="20%">Particulars</th>
                        <th></th>
                        <th width="10%">Voucher Type</th>
                        <th width="10%">Debit</th>
                        <th width="10%">Credit</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td style="border-top: dotted;border-left: dotted;"></td>
                        <td style="border-top: dotted"></td>
                        <td style="border-top: dotted"></td>
                        <td hidden="hidden"></td>
                        <td class="right-align" style="border-top: dotted"><strong>Opening Balance</strong></td>
                        <td style="border-top: dotted">
                            <input type="text" name="openingBalance" class="form-control   right-align"
                                   style="border-radius: 0;height: 28px !important;" id="openingBalance" readonly>
                        </td>
                        <td style="border-top: dotted;border-right: dotted;"></td>

                    </tr>
                    <tr>
                        <td style="border-left: dotted;"></td>
                        <td></td>
                        <td></td>
                        <td hidden="hidden"></td>
                        <td class="right-align"><strong>Total</strong></td>
                        <td><input type="text" name="totalDr" class="form-control   right-align"
                                   style="border-radius: 0;height: 28px !important;" id="totalDr" readonly></td>
                        <td style="border-right: dotted;">
                            <input type="text" name="totalCr"
                                   class="form-control   right-align"
                                   style="border-radius: 0; height: 28px !important;"
                                   id="totalCr" readonly></td>
                    </tr>
                    <tr>
                        <td style="border-top: dotted;border-left: dotted;border-bottom: dotted;"></td>
                        <td style="border-top: dotted;border-bottom: dotted;"></td>
                        <td style="border-top: dotted;border-bottom: dotted;"></td>
                        <td hidden="hidden"></td>
                        <td class="right-align" style="border-top: dotted;border-bottom: dotted;"><strong>Closing
                            Balance</strong></td>

                        <td style="border-top: dotted;border-bottom: dotted">
                            <input type="text"
                                   name="totalClosingBalance"
                                   class="form-control   right-align"
                                   style="border-radius: 0;height: 28px !important;"
                                   id="totalClosingBalanceDr" readonly>
                        </td>

                        <td style="border-top: dotted;border-bottom: dotted;border-right: dotted;">
                            <input type="text"
                                   name="totalClosingBalance"
                                   class="form-control   right-align"
                                   style="border-radius: 0;height: 28px !important;"
                                   id="totalClosingBalanceCr" readonly>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>

