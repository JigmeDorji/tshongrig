<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/16/2021
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('14-DELETE')" var="hasDeleteRole"/>
<html>
<title class="title">Fixed Asset Dispose</title>

<body>
<div class="page_title">
    <span class="title">Fixed Asset Management</span>
    <span class="subtitle">Asset Dispose</span>
</div>
<form id="fixedAssetSaleForm" action="<c:url value='/fixedAssetSale'/> " class="form-horizontal globalForm"
      target="_blank">
    <fieldset>
        <div class="form-group">
            <label class="col-md-3 left-label required">Date</label>
            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control  formatDate right-align" value="${date}" readonly
                       placeholder="DD.MM.YYYY" id="saleDate" name="saleDate" required="required"/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <div class="form-group">
            <label class="  col-md-3 left-label">Item Name:</label>
            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control  right-align" name="itemName"
                       id="itemName"/>
            </div>
        </div>

        <div class="form-group">
            <label class="  col-md-3 left-label">Asset Code:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control resetField  right-align" name="assetCode"
                       id="assetCode"/>
            </div>
        </div>

        <div class="form-group">
            <label class="  col-md-3 left-label">Group:</label>

            <div class="col-md-4">
                <input type="text" tabindex="2" class="form-control   right-align"
                       id="group"
                       readonly/>
            </div>
        </div>
        <%--        <div class="form-group">--%>
        <%--            <label class="col-md-3 left-label required">Qty:</label>--%>

        <%--            <div class="col-md-4">--%>
        <%--                <input type="text" id="qty" class="form-control decimal resetField right-align" readonly/>--%>
        <%--            </div>--%>
        <%--        </div>--%>

        <div class="form-group">
            <label class="col-md-3 left-label required">Price:</label>

            <div class="col-md-4">
                <input type="text" id="sellingPrice" class="form-control decimal resetField right-align"/>
            </div>
        </div>
    </fieldset>


    <fieldset>
        <legend>Sale Item List</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid" id="faSaleItemGrid">
                    <thead>
                    <tr>
                        <th width="5%">SL No.</th>
                        <th width="10%">Asset Name</th>
                        <th width="10%">Asset Code</th>
                        <th width="20%">Group</th>
                        <th width="10%">Price</th>
                        <th width="7%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                    </tfoot>
                </table>
                <div class="form-group">
                    <label class="col-md-2 right-label col-lg-offset-7">Total Amount</label>

                    <div class="col-md-3">
                        <input type="text"
                               class="form-control right-align numeric resetField"
                               value="0" name="amount"
                               id="grandTotal" readonly>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 right-label col-lg-offset-7">Paid In</label>

                    <div class="col-md-3">
                        <select class="form-control" id="isCash" required="required"
                                name="isCash">
                            <option value="">---Please select---</option>
                            <option value="1" id="cashId">Cash</option>
                            <option value="2" id="bankId">Bank</option>
                            <option value="3" id="creditId">Credit</option>
                            <option value="4" id="notApplicableId">N/A</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" hidden>
                    <label class="col-md-2 right-label col-lg-offset-7">Discount</label>

                    <div class="col-md-3">
                        <input type="text"
                               class="form-control right-align amount resetField"
                               value="0"
                               id="discountRate" name="discountRate">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 right-label col-lg-offset-7" id="amountReceivedText">Amount Received</label>

                    <div class="col-md-3">
                        <input type="text"
                               class="form-control right-align amount" name="amtReceived"
                               id="amtReceived" value="0">
                    </div>
                </div>

                <div class="form-group" id="bankDetails" hidden>
                    <label class="col-md-2 right-label col-lg-offset-7">Select Bank Account</label>

                    <div class="col-md-3">
                        <form:select class="form-control resetField" path="bankList" id="bankLedgerId"
                                     required="required"
                                     name="bankLedgerId">
                            <form:option id="empyId" value="">---Please Select---</form:option>
                            <form:options items="${bankList}" itemValue="id" itemLabel="text"/>
                        </form:select>
                    </div>
                </div>

                <div class="form-group" id="bankAmountId" hidden>
                    <label class="col-md-2 right-label col-lg-offset-7">Amount in Bank</label>

                    <div class="col-md-3">
                        <input type="text"
                               class="form-control right-align amount" name="amountReceivedInBank"
                               id="amountReceivedInBank" value="0">
                    </div>
                </div>
                <div class="form-group" hidden>
                    <label class="col-md-2 right-label col-lg-offset-7">Returnable Amount</label>

                    <div class="col-md-3">
                        <input type="text" class="form-control   right-align" value="0" name="amtReturn"
                               id="amtReturn" readonly>
                    </div>
                </div>

                <div class="creditDetails" hidden>
                    <div class="form-group">
                        <label class="col-md-2 right-label col-lg-offset-7 required">Party Name</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control  resetField right-align" id="partyName"
                                   name="partyName"/>
                        </div>
                    </div>
                    <div class="form-group creditSale">
                        <label class="col-md-2 right-label col-lg-offset-7 required">Address </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="4" class="form-control resetfield" name="partyAddress"
                                   id="partyAddress" required="true"/>
                        </div>

                    </div>

                    <div class="form-group creditSale">
                        <label class="col-md-2 right-label col-lg-offset-7 required">Contact No.</label>

                        <div class="col-md-3">
                            <input type="text" required="true" tabindex="5" class="form-control resetfield"
                                   name="partyContactNo"
                                   id="partyContactNo"/>
                        </div>
                    </div>

                    <div class="form-group creditSale">
                        <label class="col-md-2 right-label col-lg-offset-7">Email ID.</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="6" class="form-control resetfield" name="partyEmail" required
                                   id="partyEmail"/>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <div class="form-group">
            <div class=" col-md-2 col-lg-offset-3">
                <input type="button" class="btn btn-primary btn-block " value="Save" id="printBtn" disabled>
            </div>
        </div>
    </fieldset>

</form>
</body>
</html>


