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
<title class="title">Daily Sale Report</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Inventory</a>
                <span class="breadcrumb-item active">Daily sale Report</span>
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
            <form id="saleRecordFrom" action="<c:url  value='/saleRecord/generateReport'/> "
                  class="form-horizontal globalForm"
                  target="_blank">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <%--<input type="hidden" id="itemCategoryId" name="itemCategoryId">--%>
                <input type="hidden" id="permissionType" value="${permissionType}">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Sale date range</legend>
                    <div class="form-group row">
                        <label class="col-md-2 right-align required">From</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="2" class="form-control form-control-sm datepicker"
                                   name="fromDate"
                                   id="fromDate" required
                                   value="${date}"
                            />
                        </div>

                        <label class="col-md-1 right-align required">To</label>

                        <div class="col-md-2">
                            <input type="text" tabindex="3" class="form-control form-control-sm datepicker"
                                   name="toDate"
                                   id="toDate" required
                                   value="${date}"

                            />
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <div class="col-md-2">
                            <input type="submit" class="btn btn-primary btn-block " value="View" id="generateReport">
                        </div>
                    </div>
                </fieldset>


            </form>

            <style>
                table#database th, table#database td {
                    border: 1px solid black;
                    padding: 2px;
                }

                table#database th {
                    background-color: #f2f2f2;
                }
            </style>


        <div class="table-responsive">
                <table class="table" id="database">
                    <thead>
                    <tr class="text-center">
                        <th>SI.NO</th>
                        <th>Item Code</th>
                        <th>Item Name</th>
                        <th>Qty</th>
                        <th>Rate</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    <tbody class="text-center"></tbody>
                    <tfoot>
                    <tr class="text-right" id="inventorySaleRow">
                        <td colspan="5">Inventory Sale</td>
                        <td>
                            <span id="inventorySaleTotalAmount"></span>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Invoice Sale</td>
                        <td id="totalAmtInvoiceSale">
                            ${totalAmtInvoiceSale}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Total Sale</td>
                        <td>
                            <span id="totalSale"></span>
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Discount</td>
                        <td>
                            ${totalTotalDiscount}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Return/Exchange</td>
                        <td>
                            ${saleReplaceDiffAmt}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Credit</td>
                        <td>
                            ${totalAmtCredit}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Bank</td>
                        <td>
                            ${totalAmtBank}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    <tr class="text-right">
                        <td colspan="5">Cash</td>
                        <td>
                            ${totalAmtCash}
                            <%-- Total amount --%>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>






        <hr/>

    </div>
</div>
</body>
</html>


