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
            <fieldset>
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
                            <input type="text" class="form-control form-control-sm" required name="username"
                                   id="username">
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
            </fieldset>
        </div>
    </div>
    <!-- /form inputs -->
</div>

<!-- Main content -->
<div class="content-wrapper">

    <!-- Inner content -->
    <div class="content-inner">

        <!-- Page header -->
        <div class="page-header page-header-light">
            <div class="page-header-content header-elements-lg-inline">
                <div class="page-title d-flex">
                    <h4><i class="icon-arrow-left52 mr-2"></i> <span class="font-weight-semibold">Forms</span> - Basic
                        Inputs</h4>
                    <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
                </div>

                <div class="header-elements d-none">
                    <div class="d-flex justify-content-center">
                        <a href="#" class="btn btn-link btn-float text-body"><i
                                class="icon-bars-alt text-primary"></i><span>Statistics</span></a>
                        <a href="#" class="btn btn-link btn-float text-body"><i
                                class="icon-calculator text-primary"></i> <span>Invoices</span></a>
                        <a href="#" class="btn btn-link btn-float text-body"><i class="icon-calendar5 text-primary"></i>
                            <span>Schedule</span></a>
                    </div>
                </div>
            </div>

            <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
                <div class="d-flex">
                    <div class="breadcrumb">
                        <a href="index.html" class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Home</a>
                        <a href="form_inputs.html" class="breadcrumb-item">Forms</a>
                        <span class="breadcrumb-item active">Basic inputs</span>
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
        <!-- /page header -->


        <!-- Content area -->
        <div class="content">

            <!-- Form inputs -->
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">Basic form inputs</h5>
                </div>

                <div class="card-body">
                    <p class="mb-4">Examples of standard form controls supported in an example form layout. Individual
                        form controls automatically receive some global styling. All textual <code>&lt;input></code>,
                        <code>&lt;textarea></code>, and <code>&lt;select></code> elements with
                        <code>.form-control</code> are set to <code>width: 100%;</code> by default. Wrap labels and
                        controls in <code>.form-group</code> for optimum spacing. Labels in horizontal form require
                        <code>.col-form-label</code> class.</p>

                    <form action="#">
                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Basic inputs</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Default text input</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Password</label>
                                <div class="col-lg-10">
                                    <input type="password" class="form-control">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Input with placeholder</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" placeholder="Enter your username...">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Read only field</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" readonly value="read only">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Disabled field</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" disabled value="disabled">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Predefined value</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" value="https://">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Disabled autocomplete</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" placeholder="Autocomplete is off"
                                           autocomplete="off">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Maximum value</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" maxlength="4"
                                           placeholder="Maximum 4 characters">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2 cursor-pointer" for="clickable-label">Focus on
                                    label click</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" id="clickable-label"
                                           placeholder="Field focus on label click">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Static text</label>
                                <div class="col-lg-10">
                                    <div class="form-control-plaintext">This is a static text</div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Textarea</label>
                                <div class="col-lg-10">
                                    <textarea rows="3" cols="3" class="form-control"
                                              placeholder="Default textarea"></textarea>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Basic selects</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Single select</label>
                                <div class="col-lg-10">
                                    <select class="form-control">
                                        <option value="opt1">Default select box</option>
                                        <option value="opt2">Option 2</option>
                                        <option value="opt3">Option 3</option>
                                        <option value="opt4">Option 4</option>
                                        <option value="opt5">Option 5</option>
                                        <option value="opt6">Option 6</option>
                                        <option value="opt7">Option 7</option>
                                        <option value="opt8">Option 8</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Custom CSS select</label>
                                <div class="col-lg-10">
                                    <select class="custom-select">
                                        <option value="opt1">Styled select box</option>
                                        <option value="opt2">Option 2</option>
                                        <option value="opt3">Option 3</option>
                                        <option value="opt4">Option 4</option>
                                        <option value="opt5">Option 5</option>
                                        <option value="opt6">Option 6</option>
                                        <option value="opt7">Option 7</option>
                                        <option value="opt8">Option 8</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Multiple select</label>
                                <div class="col-lg-10">
                                    <select multiple="multiple" class="form-control">
                                        <option selected>Amsterdam</option>
                                        <option selected>Atlanta</option>
                                        <option>Baltimore</option>
                                        <option>Boston</option>
                                        <option>Buenos Aires</option>
                                        <option>Calgary</option>
                                        <option selected>Chicago</option>
                                        <option>Denver</option>
                                        <option>Dubai</option>
                                        <option>Frankfurt</option>
                                        <option>Hong Kong</option>
                                        <option>Honolulu</option>
                                        <option>Houston</option>
                                        <option>Kuala Lumpur</option>
                                        <option>London</option>
                                        <option>Los Angeles</option>
                                        <option>Melbourne</option>
                                        <option>Mexico City</option>
                                        <option>Miami</option>
                                        <option>Minneapolis</option>
                                    </select>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Basic file inputs</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Default file input</label>
                                <div class="col-lg-10">
                                    <input type="file" class="form-control h-auto">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Custom CSS file input</label>
                                <div class="col-lg-10">
                                    <div class="custom-file">
                                        <input type="file" class="custom-file-input" id="customFile">
                                        <label class="custom-file-label" for="customFile">Choose file</label>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Form helpers</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Text form helpers</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="form-text text-muted">Left aligned helper</span>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="form-text text-muted text-center">Centered helper</span>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="form-text text-muted text-right">Right aligned helper</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Full width badge helpers</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="badge d-block badge-primary form-text text-left">Left aligned badge</span>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="badge d-block badge-danger form-text">Centered badge</span>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <span class="badge d-block badge-info form-text text-right">Right aligned badge</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Block badge helpers</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <div class="d-block form-text">
                                                <span class="badge badge-primary">Left aligned badge</span>
                                            </div>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <div class="d-block form-text text-center">
                                                <span class="badge badge-danger">Centered badge</span>
                                            </div>
                                        </div>

                                        <div class="col-lg-4">
                                            <input type="text" class="form-control">
                                            <div class="d-block form-text text-right">
                                                <span class="badge badge-info text-right">Right aligned badge</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Inline text helper</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control">
                                        </div>
                                        <div class="col-lg-6 mt-1 mt-lg-0 align-self-center">
                                            <span class="text-muted">Inline text helper</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Inline badge helper</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control">
                                        </div>
                                        <div class="col-lg-6 mt-1 mt-lg-0 align-self-center">
                                            <span class="badge badge-teal">Inline badge helper</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Input icons</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Input with icons</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <div class="form-group form-group-feedback form-group-feedback-left">
                                                <input type="text" class="form-control form-control-lg"
                                                       placeholder="Left icon, input large">
                                                <div class="form-control-feedback form-control-feedback-lg">
                                                    <i class="icon-make-group"></i>
                                                </div>
                                            </div>

                                            <div class="form-group form-group-feedback form-group-feedback-left">
                                                <input type="text" class="form-control"
                                                       placeholder="Left icon, input default">
                                                <div class="form-control-feedback">
                                                    <i class="icon-droplets"></i>
                                                </div>
                                            </div>

                                            <div class="form-group form-group-feedback form-group-feedback-left">
                                                <input type="text" class="form-control form-control-sm"
                                                       placeholder="Left icon, input small">
                                                <div class="form-control-feedback form-control-feedback-sm">
                                                    <i class="icon-pin-alt"></i>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-6">
                                            <div class="form-group form-group-feedback form-group-feedback-right">
                                                <input type="text" class="form-control form-control-lg"
                                                       placeholder="Right icon, input large">
                                                <div class="form-control-feedback form-control-feedback-lg">
                                                    <i class="icon-make-group"></i>
                                                </div>
                                            </div>

                                            <div class="form-group form-group-feedback form-group-feedback-right">
                                                <input type="text" class="form-control"
                                                       placeholder="Right icon, input default">
                                                <div class="form-control-feedback">
                                                    <i class="icon-droplets"></i>
                                                </div>
                                            </div>

                                            <div class="form-group form-group-feedback form-group-feedback-right">
                                                <input type="text" class="form-control form-control-sm"
                                                       placeholder="Right icon, input small">
                                                <div class="form-control-feedback form-control-feedback-sm">
                                                    <i class="icon-pin-alt"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Input with spinners</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <div class="form-group form-group-feedback form-group-feedback-left">
                                                <input type="text" class="form-control" placeholder="Left spinner">
                                                <div class="form-control-feedback">
                                                    <i class="icon-spinner2 spinner"></i>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-6">
                                            <div class="form-group form-group-feedback form-group-feedback-right">
                                                <input type="text" class="form-control" placeholder="Right spinner">
                                                <div class="form-control-feedback">
                                                    <i class="icon-spinner2 spinner"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold border-bottom">Validation
                                states
                            </legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2 font-weight-semibold text-success">Valid
                                    state</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control is-valid" placeholder="Valid state" required>
                                    <span class="valid-feedback">Valid state helper</span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2 font-weight-semibold text-danger">Invalid
                                    state</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control is-invalid" placeholder="Invalid state"
                                           reduired>
                                    <span class="invalid-feedback">Invalid state helper</span>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Text options</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Light text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control font-weight-light"
                                           placeholder="Input with light text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Semibold text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control font-weight-semibold"
                                           placeholder="Input with semibold text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Bold text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control font-weight-bold"
                                           placeholder="Input with bold text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Capitalized text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control text-capitalize"
                                           placeholder="Input with capitalized text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Centered text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control text-center"
                                           placeholder="Input with centered text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Right aligned text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control text-right"
                                           placeholder="Input with right aligned text">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Uppercase text</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control text-uppercase"
                                           placeholder="Input with uppercase text">
                                    <span class="form-text text-muted">Other text options work as well</span>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Field options</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Tooltip on focus</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" data-popup="tooltip" data-trigger="focus"
                                           title="Tooltip on focus" placeholder="Click on input">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Tooltip on hover</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" data-popup="tooltip"
                                           title="Tooltip on hover" placeholder="Hover on input">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Rounded input</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control rounded-pill" placeholder="Rounded input">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Roundless input</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control rounded-0" placeholder="Roundless input">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Fixed input sizing</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-3">
                                            <input type="text" class="form-control" placeholder=".col-3">
                                        </div>

                                        <div class="col-4">
                                            <input type="text" class="form-control" placeholder=".col-4">
                                        </div>

                                        <div class="col-5">
                                            <input type="text" class="form-control" placeholder=".col-5">
                                        </div>
                                    </div>
                                    <span class="form-text text-muted">Available in 12 columns sizes since it's based on 12 columns grid</span>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Sizing options</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-form-label-lg col-lg-2">Large size</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control form-control-lg"
                                           placeholder=".col-form-label-lg .form-control-lg">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Default size</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" placeholder=".col-form-label .form-control">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-form-label-sm col-lg-2">Small size</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control form-control-sm"
                                           placeholder=".col-form-label-sm .form-control-sm">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Inputs only</label>
                                <div class="col-lg-10">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <div class="form-group">
                                                <input class="form-control form-control-lg" type="text"
                                                       placeholder="Large input height">
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control" type="text"
                                                       placeholder="Default input height">
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control form-control-sm" type="text"
                                                       placeholder="Small input height">
                                            </div>
                                        </div>

                                        <div class="col-lg-6">
                                            <div class="form-group">
                                                <select class="form-control form-control-lg">
                                                    <option value="opt1">Large select height</option>
                                                    <option value="opt2">Option 2</option>
                                                    <option value="opt3">Option 3</option>
                                                    <option value="opt4">Option 4</option>
                                                    <option value="opt5">Option 5</option>
                                                    <option value="opt6">Option 6</option>
                                                    <option value="opt7">Option 7</option>
                                                    <option value="opt8">Option 8</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <select class="form-control">
                                                    <option value="opt1">Default select height</option>
                                                    <option value="opt2">Option 2</option>
                                                    <option value="opt3">Option 3</option>
                                                    <option value="opt4">Option 4</option>
                                                    <option value="opt5">Option 5</option>
                                                    <option value="opt6">Option 6</option>
                                                    <option value="opt7">Option 7</option>
                                                    <option value="opt8">Option 8</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <select class="form-control form-control-sm">
                                                    <option value="opt1">Small select height</option>
                                                    <option value="opt2">Option 2</option>
                                                    <option value="opt3">Option 3</option>
                                                    <option value="opt4">Option 4</option>
                                                    <option value="opt5">Option 5</option>
                                                    <option value="opt6">Option 6</option>
                                                    <option value="opt7">Option 7</option>
                                                    <option value="opt8">Option 8</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="mb-3">
                            <legend class="text-uppercase font-size-sm font-weight-bold">Type options</legend>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Datetime</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="datetime-local" name="datetime-local">
                                    <span class="form-text text-muted">Using <code>input type="datetime-local"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Date</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="date" name="date">
                                    <span class="form-text text-muted">Using <code>input type="date"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Month</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="month" name="month">
                                    <span class="form-text text-muted">Using <code>input type="month"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Time</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="time" name="time">
                                    <span class="form-text text-muted">Using <code>input type="time"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Week</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="week" name="week">
                                    <span class="form-text text-muted">Using <code>input type="week"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Number</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="number" name="number">
                                    <span class="form-text text-muted">Using <code>input type="number"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Email</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="email" name="email">
                                    <span class="form-text text-muted">Using <code>input type="email"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">URL</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="url" name="url">
                                    <span class="form-text text-muted">Using <code>input type="url"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Search</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="search" name="search">
                                    <span class="form-text text-muted">Using <code>input type="search"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Tel</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="tel" name="tel">
                                    <span class="form-text text-muted">Using <code>input type="tel"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Color</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="color" name="color">
                                    <span class="form-text text-muted">Using <code>input type="color"</code></span>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-form-label col-lg-2">Range</label>
                                <div class="col-lg-10">
                                    <input class="form-control" type="range" name="range" min="0" max="10">
                                    <span class="form-text text-muted">Using <code>input type="range"</code></span>
                                </div>
                            </div>
                        </fieldset>

                        <div class="text-right">
                            <button type="submit" class="btn btn-primary">Submit <i class="icon-paperplane ml-2"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- /form inputs -->

        </div>
        <!-- /content area -->


        <!-- Footer -->
        <div class="navbar navbar-expand-lg navbar-light border-bottom-0 border-top">
            <div class="text-center d-lg-none w-100">
                <button type="button" class="navbar-toggler dropdown-toggle" data-toggle="collapse"
                        data-target="#navbar-footer">
                    <i class="icon-unfold mr-2"></i>
                    Footer
                </button>
            </div>

            <div class="navbar-collapse collapse" id="navbar-footer">
						<span class="navbar-text">
							&copy; 2015 - 2018. <a href="#">Limitless Web App Kit</a> by <a
                                href="https://themeforest.net/user/Kopyov" target="_blank">Eugene Kopyov</a>
						</span>

                <ul class="navbar-nav ml-lg-auto">
                    <li class="nav-item"><a href="https://kopyov.ticksy.com/" class="navbar-nav-link" target="_blank"><i
                            class="icon-lifebuoy mr-2"></i> Support</a></li>
                    <li class="nav-item"><a href="https://demo.interface.club/limitless/docs/" class="navbar-nav-link"
                                            target="_blank"><i class="icon-file-text2 mr-2"></i> Docs</a></li>
                    <li class="nav-item"><a
                            href="https://themeforest.net/item/limitless-responsive-web-application-kit/13080328?ref=kopyov"
                            class="navbar-nav-link font-weight-semibold"><span class="text-pink"><i
                            class="icon-cart2 mr-2"></i> Purchase</span></a></li>
                </ul>
            </div>
        </div>
        <!-- /footer -->

    </div>
    <!-- /inner content -->

</div>
<!-- /main content -->
</body>
</html>
