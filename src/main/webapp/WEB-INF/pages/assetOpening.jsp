<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 9/16/2021
  Time: 2:39 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<title class="title">Opening</title>

<body>
<div class="page_title">
    <span class="title">Fix Asset Management</span>
    <span class="subtitle">Opening</span>
</div>
<form id="openingAndBuyingAssetForm" action="<c:url value='/assetOpening'/> " class="form-horizontal globalForm">
    <fieldset>
<%--        <legend>Purchase Detail</legend>--%>
        <div class="form-group">
            <label class="  col-md-2 right-align required">As On:</label>

            <div class="col-md-3">
                <input type="text" tabindex="2" class="form-control  formatDate right-align" readonly
                       value="${currentUser.financialYearFrom}" id="asOnDate" name="purchaseDate"
                       required="required"/>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend>Opening Asset List</legend>
        <br/>
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" id="fixedAssetOpeningGrid">
                <thead>
                <tr>
                    <th width="2%">SL.</th>
                    <th width="20%">Particular</th>
                    <th width="5%">Purchase Date</th>
                    <th width="5%">Opening Balance</th>
                    <th width="5%">Depreciation</th>
                    <th width="5%">Purchase Rate</th>
                    <th width="5%">Qty</th>
                    <th width="5%">Purchase Value</th>
                    <th width="10%">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>

            <div class="form-group row">
                <sec:authorize access="hasAuthority('14-ADD')">
                    <div class="col-md-2 col-lg-offset-7">
                        <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </sec:authorize>
                <%--                <sec:authorize access="hasAuthority('14-DELETE')">--%>
                <%--                    <div class="col-md-2 ">--%>
                <%--                        <input type="submit" class="btn btn-danger btn-block" value="Delete" id="btnDelete">--%>
                <%--                    </div>--%>
                <%--                </sec:authorize>--%>
            </div>
        </div>
    </fieldset>
    <div class="form-group">
        <div class="col-md-2">
            <%--            <input type="button" class="btn btn-primary btn-block" value="Back" id="backBtn">--%>
        </div>
    </div>
</form>
</body>
</html>

