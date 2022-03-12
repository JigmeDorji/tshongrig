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
<title class="title">Buying Asset</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Fix Asset Management</a>
                <span class="breadcrumb-item active">Asset Setup</span>
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
            <form id="assetSetupForm" action="<c:url value='/assetSetup'/> " class="form-horizontal globalForm">
                <sec:authorize access="hasAuthority('22-DELETE')" var="hasDeleteRole"/>
                <sec:authorize access="hasAuthority('22-EDIT')" var="hasEditRole"/>

                <input type="hidden" id="hasDeleteRole" value="${hasDeleteRole}">
                <input type="hidden" id="hasEditRole" value="${hasEditRole}">
                <input type="hidden" name="assetDetailId" id="assetDetailId">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Asset Setup</legend>
                    <div class="form-group row">

                        <label class=" col-md-2 right-align">Description</label>

                        <div class="col-md-3">

                            <select required class="form-control form-control-sm form-select"
                                    id="assetId" name="assetId">
                            </select>
                        </div>

                        <div class="col-md-1">
                            <input type="button" data-toggle="modal" class="btn btn-sm btn-primary btn-xs"
                                   value="Add Particular"
                                   id="btnAddParticular">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="  col-md-2 right-align">Particular</label>

                        <div class="col-md-3">
                <textarea type="text" tabindex="2" class="form-control form-control-sm  right-align common" required
                          id="particular" name="particular"></textarea>
                        </div>
                    </div>
                    <div class="form-group row row">
                        <div class="col-md-2"></div>
                        <sec:authorize access="hasAuthority('14-ADD')">
                            <div class="col-md-2 col-lg-offset-2">
                                <input type="submit" class="btn btn-primary btn-sm" value="Save" id="btnSave">
                            </div>
                        </sec:authorize>
                    </div>
                </fieldset>

                <fieldset class="hidden" id="initialTable">
                    <legend class="text-uppercase font-size-sm font-weight-bold">Asset Setup Detail</legend>
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid" id="fixedAssetItemDetailGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th hidden></th>
                                <th hidden></th>
                                <th hidden></th>
                                <th width="2%">SL.</th>
                                <th width="15%">Description</th>
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

            <%--<section class="content">--%>
            <form class="assetClassForm">
                <div class="modal fade" data-backdrop="static" id="assetTopParentModal" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h4 class="modal-title">Add Description</h4>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div class="form-group row row">
                                        <label class="col-sm-2 col-form-label">Group</label>
                                        <div class="col-sm-10">
                                            <form:select class="form-control form-control-sm resetfield" name="groupId"
                                                         path="groupList"
                                                         id="groupId">
                                                <form:option value="">---Please Select---</form:option>
                                                <form:options items="${groupList}" itemValue="valueInteger"
                                                              itemLabel="text"/>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group row row">
                                        <label class="col-sm-2 col-form-label">Asset No.</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control form-control-sm" name="assetNo"
                                                   id="assetNo" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group row row">
                                        <label class="col-sm-2 col-form-label">Description</label>
                                        <div class="col-sm-10">
                                <textarea type="text" class="form-control form-control-sm" name="description"
                                          id="descriptionName"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <div class="col-md-2">
                                </div>
                                <div class="col-2">
                                    <button type="submit" class="btn btn-primary" id="btnSaveDescriptionId">Save
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="modal fade show" id="itemDetailModal" tabindex="-1" role="dialog"
                 data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                <legend>Item Details</legend>
                            </h4>
                            <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <table class="table table-bordered table-striped editable-grid"
                                       id="itemDetailGrid">
                                    <thead>
                                    <tr class="bg-primary text-white">
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

            <div id="itemTxnModal" class="modal fade show" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Asset Transaction Details</h5>
                            <button type="button" class="close" id="btnClose" data-dismiss="modal">×</button>
                        </div>

                        <div class="modal-body">

                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">Particular</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control form-control-sm" id="modalParticular"
                                           readonly>
                                </div>
                                <label class="col-sm-1 col-form-label">Group</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control form-control-sm" readonly
                                           id="modalGroup"/>
                                </div>
                            </div>
                            <fieldset>
                                <legend class="text-uppercase font-size-sm font-weight-bold">Asset Transaction Detail
                                </legend>
                                <div class="form-group">
                                    <table class="table table-bordered table-striped editable-grid"
                                           Id="assetTxnGrid">
                                        <thead>
                                        <tr class="bg-primary text-white">
                                            <th width="5%">Sl No.</th>
                                            <th hidden></th>
                                            <th width="20%">Transaction Date</th>
                                            <th width="20%">Particular</th>
                                            <th width="20%">Rate</th>
                                            <th width="15%">qty</th>
                                            <th width="15%">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>