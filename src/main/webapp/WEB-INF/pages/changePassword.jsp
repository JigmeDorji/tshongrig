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
<html>
<title class="title">Change Password</title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <span class="breadcrumb-item active">Password Reset Detail</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- Content area -->
<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="changePasswordForm" action="" class="form-horizontal globalForm">

                <fieldset>
                    <legend>Change Password</legend>
                    <div class="form-group row row">
                        <label class="col-md-3 right-label required">User Id </label>

                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-sm isEnable" name="userId"
                                   readonly="readonly" value="${userId}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 right-label required">Existing Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control form-control-sm isEnable"
                                   name="oldPassword"
                                   id="oldPassword"
                                   maxlength="20"
                                   data-rule-required="true">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3 right-label required isEnable isDisable">New
                            Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control form-control-sm readonly"
                                   id="newPassword"
                                   name="newPassword"
                                   maxlength="50" data-rule-required="true">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3 right-label  required isEnable">Reconfirm New
                            Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control form-control-sm isDisable"
                                   id="confirmPassword"
                                   name="confirmPassword" data-rule-required="true">
                        </div>
                    </div>


                    <div class="form-group row">
                        <div class="col-md-3"></div>
                        <%--<security:authorize access="hasRole('1012-ADD')">--%>
                        <div class="col-md-2">
                            <input type="submit" id="btnSave" value="Submit"
                                   class="btn btn-primary btn-sm"/>
                            <input type="reset" id="btnCancel" value="Cancel"
                                   class="btn btn-primary btn-sm"/>
                        </div>
                        <%--</security:authorize>--%>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>

