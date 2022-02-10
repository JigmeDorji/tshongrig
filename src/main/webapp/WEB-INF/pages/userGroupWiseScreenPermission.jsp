<%--
  Created by IntelliJ IDEA.
  User: bikash.rai
  Date: 29-Apr-2016
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<title class="title">User Group Wise Screen Permission</title>

<body>
<br/><br/>

<form id="userGroupWiseScreenPermissionForm" action="<c:url value='/userGroupWiseScreenPermission'/> "
      class="form-horizontal globalForm">
    <div class="page_title">
        <span class="title">Department Of Road</span>
        <span class="subtitle">User Group Wise Screen Permission</span>
    </div>
    <fieldset>
        <fieldset>
            <legend>User Group Wise Screen Permission</legend>

            <div class="form-group">
                <label class="col-md-3 right-label required">Select Group </label>

                <div class="col-md-3">
                    <form:select class="form-control" path="userGroupList" id="groupId"
                                 name="groupId" required="required">
                        <form:option value="">---Please Select---</form:option>
                        <form:options items="${userGroupList}" itemValue="value"
                                      itemLabel="text"/>
                    </form:select></div>
            </div>
            <fieldset class="v-no-padding">
                <legend>Un-Assigned Screen List</legend>
                <div class="form-group">
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid tableGrid"
                               Id="unAssignedScreenGrid">
                            <thead>
                            <tr>
                                <th style="width:80px;">Screen Id</th>
                                <th style="width:250px;">Screen Name</th>
                                <th>View</th>
                                <th>Add</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>

                    </div>
                </div>
            </fieldset>

            <fieldset class="v-no-padding">
                <legend>Assigned Screen List</legend>
                <div class="form-group">
                    <div class="col-md-12">
                        <table class="table table-bordered table-striped editable-grid tableGrid"
                               Id="assignedScreenGrid">
                            <thead>
                            <tr>
                                <th style="width:80px;">Screen Id</th>
                                <th style="width:200px;">Screen Name</th>
                                <th>View</th>
                                <th>Add</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>

                    </div>
                </div>
            </fieldset>
            <div class="form-group">
                <div class="col-md-2">
                </div>
            </div>
            <div class="form-group">
                <security:authorize access="hasRole('1010-ADD')">
                    <div class="col-md-2">
                        <input type="submit" class="btn btn-primary btn-block" value="Save" id="btnSave">
                    </div>
                </security:authorize>

            </div>
        </fieldset>
    </fieldset>
</form>
</body>
</html>

