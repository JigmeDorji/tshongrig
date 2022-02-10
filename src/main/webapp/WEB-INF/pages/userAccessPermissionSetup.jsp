<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">User Access Permission</title>

<body>
<div class="page_title">
    <span class="title">Setting</span>
    <span class="subtitle">User Access Permission</span>
</div>
<form id="folktaleCreationFrom" action="<c:url value='/userAccessPermission'/> " class="form-horizontal globalForm">
    <input type="hidden" id="locationSetUpId" name="locationSetUpId">
    <fieldset>
        <legend>Set user permission based on user role type</legend>
        <div class="form-group">
            <label class="col-md-2 text-right required">User Roles</label>

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
    <fieldset class="v-no-padding">
        <legend>Permission Details</legend>
        <div class="form-group">
            <div class="col-md-11">
                <table class="table table-bordered table-striped editable-grid" Id="userAccessPermissionGrid">
                    <thead>
                    <tr>
                        <th class="">Screen Id</th>
                        <th class="">Screen Name</th>
                        <th class="">View Access
                            <input type="checkbox"
                                   id="checkAllScreenAccessAllow"></th>
                        </th>
                        <th class="">Edit Access
                            <input type="checkbox"
                                   id="checkAllEditAccessAllow"></th>
                        <th class="">Delete Access
                            <input type="checkbox"
                                   id="checkAllDeleteAccessAllow"></th>
                        </th>
                        <th class="">Save Access
                            <input type="checkbox"
                                   id="checkAllSaveAccessAllow"></th>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <sec:authorize access="hasAuthority('2-ADD')">
                    <div class="col-sm-offset-4 col-md-2">
                        <input type="submit" tabindex="5" class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </sec:authorize>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>









