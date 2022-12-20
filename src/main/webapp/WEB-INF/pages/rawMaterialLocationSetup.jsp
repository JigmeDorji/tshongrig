<%--
  Created by IntelliJ IDEA.
  User: vcass
  Date: 11/30/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAuthority('1-EDIT')" var="hasEditRole"/>
<html>
<title class="title"> Raw Material Location Setup</title>

<body>

<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="index.html" class="breadcrumb-item">
                    <i class="icon-home2 mr-2"></i>
                    Raw Material
                </a>
                <span class="breadcrumb-item active">Location Setup</span>
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
            <form id="rawMaterailLocationSetupForm" action="<c:url value='/rawMaterialLocationSetup'/>" class="globalForm">
<%--            <form id="folktaleCreationFrom" action="<c:url value='/rawMaterialLocationSetup'/>">--%>
                <input type="hidden" id="rawMaterialLocationSetUpId" name="rawMaterialLocationSetUpId">
                <input type="hidden" id="hasEditRole" value="${hasEditRole}">
                <fieldset>
                    <legend class="text-uppercase font-size-sm font-weight-bold">Enter location details</legend>
                    <div class="form-group row">
                        <label class="col-md-2 text-right required">Location ID</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="1" maxlength="50" class="form-control form-control-sm resetfield" name="rawMaterialLocationId"
                                   id="rawMaterialLocationId" required="required"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-2 text-right">Description</label>

                        <div class="col-md-3">
                            <input type="text" tabindex="1" maxlength="50" class="form-control form-control-sm resetfield" name="rawMaterialLocationIdDescription"
                                   id="rawMaterialLocationIdDescription" required="required"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2"></div>
                        <div class="col-md-1">
                            <input type="reset" tabindex="5" class="btn btn-sm btn-primary" value="Reset" id="btnReset">
                        </div>
                        <sec:authorize access="hasAuthority('12-ADD')">
                            <div class="col-md-1">
                                <input type="submit" tabindex="4" class="btn btn-sm btn-primary btn-block" value="Save" id="btnSave">
                            </div>
                        </sec:authorize>


                        <%-- <div class=" col-md-1">
                             <input type="button" tabindex="6" class="btn btn-primary btn-block" value="Delete " id="btnDelete">
                         </div>--%>


                    </div>
                </fieldset>
                <fieldset class="v-no-padding">
                    <legend  class="text-uppercase font-size-sm font-weight-bold">Location Details</legend>
                    <div class="table-responsive">
                        <table class="table"
                               id="locationTable">
                            <thead>
                            <tr class="bg-primary text-white">
                                <th>SL</th>
                                <th hidden>ID</th>
                                <th >Location ID</th>
                                <th>Description</th>
                                <th>Action</th>
                            </tr>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </fieldset>


            </form>

        </div>
    </div>
</div>

</body>
</html>


