<%--
  Created by IntelliJ IDEA.
  User: Bikas Rai
  Date: 28-NoV-16
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<title class="title">Change Password</title>

<body>
<div class="page_title">
    <span class="title">Bhutan Tools of Entertainment</span>
    <span class="subtitle">Change Password</span>
</div>
<div class="col-md-3"></div>
<form id="changePasswordForm" action="" class="form-horizontal globalForm">
    <div class="col-md-12">
        <fieldset>
            <legend>Change Password</legend>
            <div class="form-group">
                <label class="col-md-4 right-label required">User Id </label>

                <div class="col-md-4">
                    <input type="text" class="form-control isEnable" name="userId"
                           readonly="readonly" value="${userId}">
                </div>
            </div>
            <div class="form-group">
                <div class="form-group">
                    <div class="col-md-12">
                        <label class="col-md-4 right-label required">Existing Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control isEnable" name="oldPassword" id="oldPassword"
                                   maxlength="20"
                                   data-rule-required="true">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-12">
                        <label class="col-md-4 right-label required isEnable isDisable">New Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control readonly" id="newPassword" name="newPassword"
                                   maxlength="50" data-rule-required="true">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-12">
                        <label class="col-md-4 right-label  required isEnable">Reconfirm New Password:</label>

                        <div class="col-md-4">
                            <input type="password" class="form-control isDisable" id="confirmPassword"
                                   name="confirmPassword" data-rule-required="true">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-md-12">
                        <div class="col-md-2"></div>
                        <%--<security:authorize access="hasRole('1012-ADD')">--%>
                        <div class="col-md-2">
                            <input type="submit" id="btnSave" value="Submit" class="btn btn-primary btn-block"/>
                        </div>
                            <%--</security:authorize>--%>
                        <div class="col-md-1"></div>
                        <div class="col-md-2">
                            <input type="reset" id="btnCancel" value="Cancel" class="btn btn-primary btn-block"/>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>

    </div>
</form>
</body>
</html>

