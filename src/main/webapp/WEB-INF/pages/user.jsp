<%--
  Created by IntelliJ IDEA.
  User: jigme.dorji
  Date: 10/5/2020
  Time: 10:45 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('1-EDIT')" var="hasEditRole"/>
<sec:authorize access="hasAuthority('1-DELETE')" var="hasDeleteRole"/>

<html>
<title class="title">Create Users</title>

<body>

<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Setting</a>
                <span class="breadcrumb-item active">User Creation</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <legend class="text-uppercase font-size-sm font-weight-bold">User Detail</legend>
            <form id="userFormId" action="<c:url value='/user'/>"
                  class="form-horizontal globalForm userFormId">
                <input type="hidden" id="statusActive"
                       value="${statusActive}">
                <input type="hidden" id="statusInactive"
                       value="${statusInactive}">

                <input type="hidden" id="userId" name="userId">
                <input type="hidden" id="loginCompanyId" value="${currentUser.companyId}">
                <input type="hidden" id="companyName" value="${currentUser.companyName}">

                <div class="form-group row">
                    <label class="col-form-label col-lg-2 required " >Username</label>
                    <div class="col-lg-4">
                        <div class="input-group">
                            <input  type="text" class="form-control  form-control-sm col-md-8"  required name="username"
                                   id="username" autocomplete="off">
                            <span class="input-group-prepend">
<%--								<span class="input-group-text" id="companyAbbreviation"></span>--%>
<%--                                <span class="input-group-text" id="companyAbbreviations">@${currentUser.loginId}</span>--%>
<%--                                <span class="input-group-text" id="companyAbbreviations">@${currentUserLoginDetail.get(0).companyLoginId}</span>--%>
                                <span class="input-group-text" id="companyAbbreviations">@${currentCompanyLoginId}</span>

							</span>
                        </div>
                    </div>
                    <label class="col-form-label col-lg-2 required ">Full Name</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-control-sm" required name="userFullName"
                               id="userFullName">
                    </div>
                </div>


                <div class="form-group row">
                    <label class="col-form-label col-lg-2 required">Password</label>

                    <div class="col-md-4">
                        <input type="password" name="userPassword" required
                               id="userPassword" class="form-control form-control-sm field" autocomplete="off" >
                    </div>
                    <label class="col-form-label col-lg-2 required ">Confirm
                        Password</label>

                    <div class="col-md-4">
                        <input type="password" name="txtConfirmPassword"
                               id="txtConfirmPassword" required
                               class="form-control form-control-sm field">
                    </div>
                </div>
                <div class="form-group mb-1 row">

                    <label class="col-form-label col-lg-2 required ">Status</label>

                    <div class="col-md-4">
                        <form:select required="true" path="statusList"
                                     class="form-control form-control-sm select2 field"
                                     id="userStatus" name="userStatus">
                            <form:option
                                    value="">---Please Select---</form:option>
                            <form:options items="${statusList}"
                                          itemValue="valueChar"
                                          itemLabel="text"/>
                        </form:select>
                    </div>


                    <label class="col-form-label col-lg-2 required ">Creation Date</label>

                    <div class="col-md-4">
                        <input type="text" value="${currentDate}"
                               readonly="readonly" name="createdDate" required
                               id="createdDate" class="form-control form-control-sm">
                    </div>
                </div>
                <div class="form-group  row">

                    <label class="col-form-label col-lg-2 required ">User
                        Role</label>

                    <div class="col-md-4">
                        <form:select required="true" path="userRoleList"
                                     class="form-control form-control-sm form-select select2 field"
                                     id="userRoleTypeId"
                                     name="userRoleTypeId">
                            <form:option
                                    value="">---Please Select---</form:option>
                            <form:options items="${userRoleList}"
                                          itemValue="valueInteger"
                                          itemLabel="text"/>
                        </form:select>
                    </div>

                    <%--                    <div class="companyHiddenId">--%>
                   <%-- <label class="col-form-label col-lg-2 required companyHiddenId ">Company</label>

                    <div class="col-md-4 companyHiddenId">
                        <form:select class="form-control form-control-sm select2"
                                     path="loginCompany"
                                     id="companyId"
                                     name="companyId">
                            <form:option
                                    value="">---Please Select---</form:option>
                            <form:options items="${loginCompany}" itemValue="value"
                                          itemLabel="text"/>
                        </form:select>
                    </div>--%>
                    <%--                    </div>--%>
                </div>

                <%--<div class="form-group mb-3 row" id="mappingId" hidden>
                    <label class="col-form-label col-lg-2 required">Company Mapping</label>
                    <div class="col-md-4">
                        <form:select multiple="multiple"
                                     data-placeholder="---Please Select---"
                                     class="form-control form-control-sm select"
                                     style="width: 100%;" tabindex="7"
                                     path="companyList"
                                     id="companyMappingId"
                                     name="companyMappingId">
                            <form:options items="${companyList}" itemValue="value"
                                          itemLabel="text"/>
                        </form:select>
                    </div>
                </div>--%>
                <div class="form-group">
                    <label class="col-md-2"></label>
                    <%--                <sec:authorize access="hasAuthority('1-ADD')">--%>
                    <input type="submit" class="btn btn-sm btn-primary col-md-2 btnAdd"
                           value="Save" id="btnSave">
                    <%--                </sec:authorize>--%>
                    <%--                <sec:authorize access="hasAuthority('1-EDIT')">--%>
                    <input type="button" value="Update" id="btnUpdate"
                           class="btn btn-sm btn-info btn-group-sm col-md-2 btnUpdate hidden">
                    <%--                </sec:authorize>--%>

                    <%--                <sec:authorize access="hasAuthority('1-EDIT')">--%>
                    <input type="button" value="Delete" id="btnDelete"
                           class="btn btn-sm btn-danger col-md-2 btnDelete hidden">
                    <%--                </sec:authorize>--%>
                </div>

            </form>


            <div class="form-group">
                <legend class="text-uppercase font-size-sm font-weight-bold">User List</legend>

                <div class="table-responsive">
                    <table class="table"
                           id="userListTableId">
                        <thead>
                        <tr class="bg-primary text-white">
                            <th class="">Sl.</th>
                            <th class="">Status</th>
                            <th class=" hidden">id</th>
                            <th class="">Username</th>
                            <th class="">Full Name</th>
                            <th class="">No</th>
                            <th class="">Date</th>
                            <th class="">Role</th>
                            <th class="">Action</th>
                        </tr>
                        <tbody>
                        </tbody>
                    </table>
                </div>

            </div>

        </div>
    </div>
    <!-- /form inputs -->
</div>
</body>
</html>
