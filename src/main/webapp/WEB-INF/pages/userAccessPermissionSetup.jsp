<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">User Access Permission</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Setting</a>
                <span class="breadcrumb-item active">User Access Permission</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="folktaleCreationFrom" action="<c:url value='/userAccessPermission'/> " class="globalForm">
                <input type="hidden" id="locationSetUpId" name="locationSetUpId">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Set user permission based on user role
                        type
                    </legend>
                    <div class="form-group row">
                        <label class="col-md-2  required">User Roles</label>

                        <div class="col-md-3">
                            <form:select class="form-control select2 form-select" path="userRoleList"
                                         id="userRoleTypeId" name="userRoleTypeId">
                                <form:option
                                        value="">---Please Select---</form:option>
                                <form:options items="${userRoleList}" itemValue="valueInteger"
                                              itemLabel="text"/>
                            </form:select>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Permission Details</legend>
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped editable-grid" Id="userAccessPermissionGrid">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th class="">Screen Id</th>
                                <th class="">Screen Name</th>
                                <th class="text-center">
                                    View Access
                                    <div class="d-flex justify-content-center">
                                        <input type="checkbox"
                                               id="checkAllScreenAccessAllow">
                                    </div>
                                </th>
                                <th class="text-center">
                                    Edit Access
                                    <div class="d-flex justify-content-center">
                                        <input type="checkbox"
                                               id="checkAllEditAccessAllow">
                                    </div>
                                </th>
                                <th class="text-center">
                                    Delete Access
                                    <div class="d-flex justify-content-center">
                                        <input type="checkbox"
                                               id="checkAllDeleteAccessAllow">
                                    </div>
                                </th>
                                <th class="text-center">Save Access
                                    <div class="d-flex justify-content-center">
                                        <input type="checkbox"
                                               id="checkAllSaveAccessAllow">
                                    </div>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <sec:authorize access="hasAuthority('2-ADD')">
                            <div class="col-sm-offset-4 col-md-2 pt-3">
                                <input type="submit" tabindex="5" class="btn btn-primary btn-block" value="Save"
                                       id="btnSave">
                            </div>
                        </sec:authorize>
                    </div>

                </fieldset>
            </form>

        </div>
    </div>
</div>
</body>
</html>









