<%--
  Created by IntelliJ IDEA.
  User: Bikas Rai
  Date: 28-NoV-16
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('1-EDIT')" var="hasEditRole"/>
<sec:authorize access="hasAuthority('1-DELETE')" var="hasDeleteRole"/>

<html>
<title class="title">Create Users</title>

<body>
<div class="page_title">
    <span class="title">Stock Management</span>
    <span class="subtitle">Create Users</span>
</div>
<form id="createUsersForm" action="<c:url value='/createUser'/> " class="form-horizontal globalForm">
    <input type="text" id="hasEditRole" value="${hasEditRole}">
    <input type="text" id="hasDeleteRole" value="${hasDeleteRole}">
    <fieldset>
        <legend>Create Users</legend>
        <div class="form-group">
            <label class="col-md-3 text-right required">Login ID : </label>

            <div class="col-md-3">
                <input type="text" class="form-control" name="loginId" id="loginId" required="required" maxlength="20"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right required">User Name : </label>

            <div class="col-md-3">
                <input type="text" class="form-control" name="txtUserName" id="txtUserName" required="required"
                       maxlength="50"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Phone No : </label>

            <div class="col-md-3">
                <input type="text" class="form-control" name="userMobileNo" id="phoneNo"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Password : </label>

            <div class="col-md-3">
                <input type="password" class="form-control" name="txtPassword" id="txtPassword"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Confirm Password : </label>

            <div class="col-md-3">
                <input type="password" class="form-control" name="txtConfirmPassword" id="txtConfirmPassword"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 text-right">Date Created : </label>

            <div class="col-md-3">
                <input type="text" class="form-control datepicker" name="createdDate" id="createdDate"
                       value="${createdDate}" readonly="readonly"/>
            </div>

        </div>

        <div class="form-group">
            <label class="col-md-3  required">User Roles</label>

            <div class="col-md-3">
                <form:select class="form-control" tabindex="1" path="userRoleList" id="roleTypeId"
                             name="roleTypeId">
                    <form:option value="">---Please Select---</form:option>
                    <form:options items="${userRoleList}" itemValue="valueInteger"
                                  itemLabel="text"/>
                </form:select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2">
                <input type="reset" class="btn btn-primary btn-block" value="New" id="btnNew">
            </div>

            <div class="col-md-2">
                <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
            </div>

            <div class="col-md-2">
                <input type="button" disabled class="btn btn-primary btn-block" value="Delete" id="btnDelete">
            </div>
        </div>

        <fieldset class="v-no-padding">
            <div class="form-group">
                <div class="col-md-12">
                    <table class="table table-bordered table-striped editable-grid tableGrid" Id="createdUserGrid">
                        <thead>
                        <tr>
                            <th>Login Id</th>
                            <th>User Name</th>
                            <th>Date Created</th>
                            <th>Status</th>
                            <th>Is Admin</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>

                </div>
            </div>
        </fieldset>

    </fieldset>

    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header" style="background-color: #0480be">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="color: #ffffff">Confirmation</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <div style="text-align: center;"><h5>Are you sure you want to delete the user?</h5>
                        </div>
                    </div>
                    <div class="form-group">
                        <div style="text-align: center;"><h6>You will not be able to recover after update!</h6>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-4 col-md-3">
                            <input type="button" tabindex="4" class="btn btn-primary btn-block" value="Cancel"
                                   id="btnCancel">
                        </div>
                        <div class=" col-md-4">
                            <input type="button" tabindex="4" class="btn btn-primary btn-block"
                                   value="Confirm Delete"
                                   id="btnDeleteConfirm">
                        </div>
                    </div>
                    <div class="form-group">

                        <div class="col-md-3">

                        </div>
                        <div class="col-sm-offset-7 col-md-3">

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form>
</body>
</html>


