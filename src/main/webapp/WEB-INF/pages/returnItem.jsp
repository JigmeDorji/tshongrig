<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 8/31/2019
  Time: 4:36 AM
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
<html>
<title class="title">Return</title>

<body>
<div class="page_title">
    <span class="title">Inventory</span>
    <span class="subtitle">Return/Exchange</span>
</div>
<div id="dialog" style="display: none;">
    <div>
        <iframe id="frame"></iframe>
    </div>
</div>
<%--<form id="saleItemForm" action="<c:url value='/returnItem'/> " class="form-horizontal globalForm" target="_blank">--%>
<%--  <fieldset>
      <div class="form-group">
          <label class="col-md-3 left-label required">Enter sales receipt No.:</label>
          <div class="col-md-4">
              <input type="text" tabindex="2" class="form-control  resetField right-align" id="receiptNo" name="receiptNo"/>
          </div>
      </div>
  </fieldset>
  <fieldset>
      <legend>Sale Items Details</legend>
      <div class="form-group">
          <div class="col-md-12">
              <table class="table table-bordered table-striped editable-grid" id="returnItemGrid">
                  <thead>
                  <tr>
                      <th width="10%">Item Code</th>
                      <th width="20%">Item Name</th>
                      <th width="10%">Rate</th>
                      <th width="10%">Qty</th>
                      <th width="10%">Total Amount</th>
                      <th width="10%">Return Qty</th>
                      <th width="10%">Action</th>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
              </table>
          </div>
      </div>

      <div class="form-group">
          <div class=" col-md-3 col-lg-offset-3">
              <input type="submit" class="btn btn-primary btn-block "  value="Update Return Item" id="updateBtn">
          </div>
      </div>
  </fieldset>--%>

<form id="saleItemReturnForm" action="<c:url value='/returnItem'/>" class="form-horizontal globalForm">
    <input type="hidden" id="sellingPrice" class="resetField">

    <fieldset>
        <legend>Replacement Of Item Details</legend>

        <div class="form-group">
            <label class="col-md-3 text-right required">Return Item Code</label>

            <div class="col-md-3">
                <input type="text" tabindex="1" class="form-control right-align resetField" autofocus
                       name="returnItemCode" id="returnItemCode"/>
            </div>

            <div class="col-md-3">
                <input type="text" tabindex="1" class="form-control right-align resetField"
                       name="returnItemName" id="returnItemName" readonly/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right required">Replace Item Code</label>

            <div class="col-md-3">
                <input type="text" tabindex="1" class="form-control right-align resetField" autofocus readonly
                       name="replaceItemCode" id="replaceItemCode"/>
            </div>

            <div class="col-md-3">
                <input type="text" tabindex="1" class="form-control right-align resetField"
                       name="replaceItemName" id="replaceItemName" readonly/>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend>Details of Return Items</legend>
        <div class="form-group">
            <div class="col-md-12">
                <table class="table table-bordered table-striped editable-grid"
                       id="itemReplacementGrid"
                       style="width:100%;">
                    <thead>
                    <tr>
                        <th width="40%">Particular</th>
                        <th width="20%">Qty</th>
                        <th width="20%">Rate</th>
                        <th width="30%">Amount</th>
                    </tr>
                    </thead>
                    <tbody id="tBodyId">
                    </tbody>
                    <tfoot>
                    <tr>
                        <td></td>
                        <td></td>
                        <td style="text-align: right"><strong>Net</strong></td>
                        <td><input id="totalAmount" class="form-control" style="text-align: center"></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>

        <div class="form-group">
            <div class=" col-md-3 col-lg-offset-3">
                <input type="submit" class="btn btn-primary btn-block " value="Update Return Item" id="updateBtn">
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>



