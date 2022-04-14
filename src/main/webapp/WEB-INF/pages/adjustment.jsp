<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/23/2022
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/23/2022
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Adjustment</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Accounting</a>
                <span class="breadcrumb-item active">Adjustment</span>
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
            <form id="adjustmentForm" action="<c:url value='/adjustment'/> " class="adjustmentForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Adjustment Detail</legend>
                    <input type="hidden" class="form-control form-control-sm" id="ledgerId" name="ledgerId">
                    <input type="hidden" class="form-control form-control-sm" id="expenditureHiddenText">

                    <div class="form-group row">

                        <label class="col-md-2 right-align required">Voucher Date </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm formatDate"
                                   name="autoVoucherDate"
                                   id="autoVoucherDate" required="true" placeholder="DD.MM.YYYY"/>
                        </div>
                    </div>

                    <%--  <div class="form-group row">
                          <label class="col-md-2 right-align required" id="paidToText">Adjusted against</label>

                          <div class="col-md-3">
                              <input type="text" class="form-control form-control-sm" id="adjustedAgainst"
                                     name="adjustedAgainst" required>
                          </div>
                      </div>--%>
                    <%-- <div class="form-group row costContent hidden">
                         <label class="col-md-2 right-align required">Cost</label>

                         <div class="col-md-3">
                             <select class="form-control form-control-sm select2 costType" id="costType" style="width:100%;"
                                     name="costId">
                                 <option value="">---Please select ---</option>
                                 <option value="1" id="generalId">General</option>
                                 <option value="2" id="productionId">Production</option>
                             </select>
                         </div>
                     </div>--%>
                    <!--region for multi voucher payment-->

                    <div id="multipleCost">
                        <div class="form-group row  multiPaymentVoucher">

                            <label class="col-md-2 right-align required">Adjusted against</label>
                            <div class="col-md-3">
                                <input type="text" tabindex="2"
                                       class="form-control costDescription form-control-sm autocomplete"
                                       name="multiVoucherDTO[0].adjustedAgainst"
                                       id="adjustedAgainst" required="true"/>

                                <input type="hidden" tabindex="2" class="form-control form-control-sm costLedgerId"
                                       name="multiVoucherDTO[0].costLedgerId"
                                       id="costLedgerId" required="true"/>
                            </div>


                            <label class="col-md-1 right-align required">Amount</label>

                            <div class="col-md-2">
                                <input type="text" tabindex="3"
                                       class="form-control costAmount form-control-sm text-right" id="costAmount"
                                       name="multiVoucherDTO[0].costAmount" required="true"/>
                            </div>

                            <label class="col-md-1 right-align required">Cost</label>

                            <div class="col-md-2">
                                <select class="form-control costId form-control-sm" id="costType" required
                                        name="multiVoucherDTO[0].costId">
                                    <option value="">---Please select ---</option>
                                    <option value="1">General</option>
                                    <option value="2">Production</option>
                                </select>
                            </div>

                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary btn-sm rounded-pill addMoreBtn"
                                        id="addMoreBtn">
                                    <i class="icon-plus2"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--endregion-->

                    <div class="form-group row">
                        <label class="col-md-2 right-align required" id="descriptionText">Adjusted From</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm select2" name="adjustedFrom"
                                         path="adjustedFormList"
                                         id="adjustedFrom" required="required" tabindex="1">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${adjustedFormList}" itemValue="id"
                                              itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>

                    <%-- <div class="form-group row">
                         <label class="col-md-2 right-align required">Amount</label>

                         <div class="col-md-3">

                             <input type="text" tabindex="3" class="form-control form-control-sm text-right"
                                    name="amount" required
                                    id="amount"/>
                         </div>
                     </div>--%>
                    <input type="text" tabindex="3" class="form-control form-control-sm text-right hidden"
                           name="amount"
                           id="amount"/>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">TDS Type</label>

                        <div class="col-md-3">
                            <select class="form-control form-control-sm" id="tdsType"
                                    name="tdsType">
                                <option value="">---Please select ---</option>
                                <option value="1">Bhutanese Contract</option>
                                <option value="2">Hiring</option>
                                <option value="3">Real Estate</option>
                                <option value="4">International contract</option>
                                <option value="5">Not applicable</option>
                            </select>
                        </div>

                        <label class="col-md-2 right-align required">TDS Amount</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="3" class="form-control form-control-sm" name="tdsAmount"
                                   readonly
                                   id="tdsAmount"/>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label class="col-md-2 right-align required">Adjustable Amount</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="3" class="form-control form-control-sm"
                                   readonly id="totalAdjustableAmount"/>
                        </div>
                        <label class="col-md-2 right-align required">Adjusted Amount</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="3" class="form-control form-control-sm" name="amountPaid"
                                   readonly
                                   id="amountPaid"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <div class="col-md-2">
                            <input type="submit" class="btn btn-primary btn-block" value="Save" tabindex="9">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

</body>
</html>

