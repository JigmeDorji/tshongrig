<%--
  Created by IntelliJ IDEA.
  User: Bcass Sawa
  Date: 5/3/2019
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">Company Setup</title>

<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Setting</a>
                <span class="breadcrumb-item active">Company Setup</span>
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
            <form id="companyCreationForm" action="<c:url value='/companyCreation'/>"
                  class="form-horizontal companyCreationForm">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Company Detail</legend>
                    <input type="hidden" name="companyId" id="companyId"/>

                    <div class="form-group row">
                        <label class="col-md-2  required">Company Name</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="1" class="form-control form-control-sm resetfield" autofocus
                                   name="companyName" id="companyName" required="true"/>
                        </div>
                        <label class="col-md-2  required">Mailing Address </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="2" class="form-control form-control-sm resetfield"
                                   name="mailingAddress"
                                   id="mailingAddress" required="true"/>
                        </div>
                    </div>



                    <div class="form-group row">
                        <label class="col-md-2  required">Mobile No. </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="3" class="form-control form-control-sm resetfield numeric" name="mobileNo"
                                   id="mobileNo" required="true"/>
                        </div>
                        <label class="col-md-2 ">Email </label>

                        <div class="col-md-3">
                            <input type="text" tabindex="4" class="form-control form-control-sm resetfield" name="email"
                                   id="email"/>
                        </div>
                    </div>



                    <div class="form-group row">
                        <label class="col-md-2 ">Website</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="5" class="form-control form-control-sm resetfield" name="website"
                                   id="website"/>
                        </div>
                        <label class="col-md-2   required">Financial Year Start</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="6" class="form-control form-control-sm formatDate resetfield"
                                   name="fnYrStart" placeholder="DD.MM.YYYY"
                                   required="true"
                                   id="fnYrStart"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-2  required">Business Type</label>

                        <div class="col-md-3">
                            <form:select class="form-control form-control-sm select2 resetfield" name="businessType"
                                         path="businessTypeList"
                                         id="businessType" tabindex="8">
                                <form:option value="">---Please Select---</form:option>
                                <form:options items="${businessTypeList}" itemValue="valueInteger"
                                              itemLabel="text"/>
                            </form:select>
                        </div>
                        <label class="col-md-2  required">PF Percentage(%)</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="6" class="form-control form-control-sm  resetfield numeric"
                                   name="pfPercentage"
                                   required="true"
                                   id="pfPercentage"/>
                        </div>

                    </div>


                    <div class="form-group row">
                        <div class="col-md-2"></div>

                        <div class="col-md-2">
                            <input type="submit" tabindex="9" class="btn btn-primary btn-block resetfield"
                                   value="Save" id="saveBtn">
                        </div>
                        <div class="col-md-2">
                            <input type="reset" tabindex="10" class="btn btn-primary btn-block"
                                   value="Reset" id="reRestBtn">
                        </div>

                    </div>
                    <fieldset>
                        <div class="form-group row">
                            <div class="col-md-12">
                                <table
                                        class="table table-bordered table-striped editable-grid"
                                        id="companyCreationGrid">
                                    <thead>
                                    <tr class="bg-primary text-white">
                                        <th hidden></th>
                                        <th width="20%">Company Name</th>
                                        <th width="30%">Mailing Address</th>
                                        <th width="10%">Mobile Number</th>
                                        <th width="15%">Email</th>
                                        <th width="15%">Website</th>
                                        <th width="10%">Financial Year Start</th>
                                        <%--                                                    <th width="10%">Book Year Start</th>--%>
                                        <th hidden></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </fieldset>
                </fieldset>

            </form>
        </div>
    </div>
</div>


</body>
</html>


