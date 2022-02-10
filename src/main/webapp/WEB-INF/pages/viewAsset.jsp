<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/20/2022
  Time: 5:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 10/4/2021
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<title class="title">View Asset</title>
<body>
<div class="page_title">
    <span class="title">Fix Asset Management</span>
    <span class="subtitle">View Asset</span>
</div>
<form id="assetSetupForm" action="<c:url value='/viewAsset/'/> " class="form-horizontal globalForm">

    <fieldset>
        <legend>Asset Item List</legend>
        <br/>
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" id="fixedAssetListGrid">
                <thead>
                <tr>
                    <th width="2%">SL.</th>
                    <th width="20%">Particular</th>
                    <th width="15%">Group</th>
                    <th width="5%">Qty</th>
                    <th width="13%">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </fieldset>
</form>

<div class="modal fade" id="itemDetailModal" tabindex="-1" role="dialog"
     data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Item Details</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <table class="table table-bordered table-striped editable-grid"
                           Id="itemDetailGrid">
                        <thead>
                        <tr>
                            <th width="5%">Sl No.</th>
                            <th width="20%">Item Name</th>
                            <th width="20%">Asset Code</th>
                            <th width="20%">Price</th>
                            <th width="15%">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>