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
<!-- /page header -->
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

                <div class="form-group row">
                    <label class="col-form-label col-lg-2 required ">Username</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-control-sm" required name="username" id="username">
                    </div>
                    <label class="col-form-label col-lg-2 required ">Full Name</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control form-control-sm" required name="userFullName"
                               id="userFullName">
                    </div>
                </div>


                <div class="form-group row">
                    <label class="col-form-label col-lg-2 required ">Password</label>

                    <div class="col-md-4">
                        <input type="password" name="userPassword" required
                               id="userPassword" class="form-control form-control-sm field">
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
                    <label class="col-form-label col-lg-2 required companyHiddenId ">Company</label>

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
                    </div>
                    <%--                    </div>--%>
                </div>

                <div class="form-group mb-3 row" id="mappingId" hidden>
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
                </div>
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
<!-- /content area -->

<%--<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="index.html" class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Setting</a>
                <span class="breadcrumb-item active">User Creation</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>

        <div class="header-elements d-none">
            <div class="breadcrumb justify-content-center">
                <a href="#" class="breadcrumb-elements-item">
                    <i class="icon-comment-discussion mr-2"></i>
                    Support
                </a>

                <div class="breadcrumb-elements-item dropdown p-0">
                    <a href="#" class="breadcrumb-elements-item dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-gear mr-2"></i>
                        Settings
                    </a>

                    <div class="dropdown-menu dropdown-menu-right">
                        <a href="#" class="dropdown-item"><i class="icon-user-lock"></i> Account security</a>
                        <a href="#" class="dropdown-item"><i class="icon-statistics"></i> Analytics</a>
                        <a href="#" class="dropdown-item"><i class="icon-accessibility"></i> Accessibility</a>
                        <div class="dropdown-divider"></div>
                        <a href="#" class="dropdown-item"><i class="icon-gear"></i> All settings</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /page header -->--%>
<%--<div class="page_title">
    <span class="title">Setting</span>
    <span class="subtitle">Create Users</span>
</div>
<fieldset>
    <legend>User Details</legend>
    <form id="userFormId" action="<c:url value='/user'/>"
          class="form-horizontal globalForm userFormId">
        <input type="hidden" id="statusActive"
               value="${statusActive}">
        <input type="hidden" id="statusInactive"
               value="${statusInactive}">

        <input type="hidden" id="userId" name="userId">
        <input type="hidden" id="loginCompanyId" value="${currentUser.companyId}">

        <div class="form-group">
            <label class="col-form-label col-lg-2 required">Username</label>

            <div class="col-md-4">
                <input type="text" name="username" id="username"
                       class="form-control form-control-sm field" required>
            </div>
            <label class="col-form-label col-lg-2 required">Full
                Name</label>

            <div class="col-md-4">
                <input type="text" name="userFullName"
                       id="userFullName" class="form-control form-control-sm field"
                       required="true">
            </div>
        </div>
        <div class="form-group mb-3 row">
            <label class="col-form-label col-lg-2">Password</label>

            <div class="col-md-4">
                <input type="password" name="userPassword"
                       id="userPassword" class="form-control form-control-sm field">
            </div>
            <label class="col-form-label col-lg-2">Confirm
                Password</label>

            <div class="col-md-4">
                <input type="password" name="txtConfirmPassword"
                       id="txtConfirmPassword"
                       class="form-control form-control-sm field">
            </div>
        </div>
        <div class="form-group mb-3 row">

            <label class="col-form-label col-lg-2 required">Status</label>

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


            <label class="col-form-label col-lg-2">Creation Date</label>

            <div class="col-md-4">
                <input type="text" value="${currentDate}"
                       readonly="readonly" name="createdDate"
                       id="createdDate" class="form-control form-control-sm">
            </div>
        </div>
        <div class="form-group mb-3 row">

            <label class="col-form-label col-lg-2 required">User
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

            <div class="companyHiddenId">
                <label class="col-form-label col-lg-2 required">Company</label>

                <div class="col-md-4">
                    <form:select class="form-control form-control-sm select2"
                                 path="loginCompany"
                                 id="companyId"
                                 name="companyId">
                        <form:options items="${loginCompany}" itemValue="value"
                                      itemLabel="text"/>
                    </form:select>
                </div>
            </div>

        </div>

        <div class="form-group mb-3 row" id="mappingId" hidden>

            <label class="col-form-label col-lg-2 required">Company Mapping</label>

            <div class="col-md-4">
                <form:select multiple="multiple"
                             data-placeholder="---Please Select---"
                             class="form-control form-control-sm select2"
                             style="width: 100%;" tabindex="7"
                             path="companyList"
                             id="companyMappingId"
                             name="companyMappingId">
                    <form:options items="${companyList}" itemValue="value"
                                  itemLabel="text"/>
                </form:select>
            </div>
        </div>
        <div class="col-md-offset-2">
            &lt;%&ndash;                <sec:authorize access="hasAuthority('1-ADD')">&ndash;%&gt;
            <input type="submit" class="btn btn-primary col-12 btnAdd"
                   value="Save" id="btnSave">
            &lt;%&ndash;                </sec:authorize>&ndash;%&gt;
            &lt;%&ndash;                <sec:authorize access="hasAuthority('1-EDIT')">&ndash;%&gt;
            <input type="button" value="Update" id="btnUpdate"
                   class="btn btn-info btn-group-sm col-12 btnUpdate hidden">
            &lt;%&ndash;                </sec:authorize>&ndash;%&gt;

            &lt;%&ndash;                <sec:authorize access="hasAuthority('1-EDIT')">&ndash;%&gt;
            <input type="button" value="Delete" id="btnDelete"
                   class="btn btn-danger col-12 btnDelete hidden">
            &lt;%&ndash;                </sec:authorize>&ndash;%&gt;
        </div>

    </form>
    <fieldset class="v-no-padding">
        <div class="form-group">
            <div class="col-md-12">
                <table class="table card-table table-bordered"
                       id="userListTableId">
                    <thead>
                    <tr>
                        <th class="">Sl No.</th>
                        <th class="">Status</th>
                        <th class=" hidden">id</th>
                        <th class="">Username</th>
                        <th class="">Full Name</th>
                        <th class="">Mobile No</th>
                        <th class="">Creation Date</th>
                        <th class="">Role</th>
                        <th class="">Action</th>
                    </tr>
                    <tbody>
                    </tbody>
                </table>

            </div>
        </div>
    </fieldset>
</fieldset>--%>

</body>
</html>
