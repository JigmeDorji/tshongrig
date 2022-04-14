<%--
  Created by IntelliJ IDEA.
  User: Bcass Sawa
  Date: 5/3/2019
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


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
                    <div class="form-group row">
                        <div class="col-md-12">
                            <table
                                    class="table table-bordered"
                                    id="companyCreationGrid">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th hidden></th>
                                    <th width="20%">Company Name</th>
                                    <th width="10%">Cont. Person</th>
                                    <th width="8%">Cont. No</th>
                                    <th width="15%">Email</th>
                                    <th width="7%">Bus. Type</th>
                                    <th width="7%">Trail Expiry Date</th>
                                    <th width="10%">Status</th>
                                    <th width="3%">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </fieldset>

                <div class="modal fade" id="companyDetailModal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <fieldset>
                                    <legend class="text-uppercase font-size-sm font-weight-bold">Company Detail</legend>
                                    <input type="hidden" name="companyId" id="companyId" class="field"/>

                                    <div class="form-group row">
                                        <c:if test="${currentUser.userRoleTypeId==0}">
                                            <div class="form-group form-group-float col-md-6">
                                                <label class="required">Company Name</label>
                                                <input type="text" tabindex="1"
                                                       class="form-control form-control-sm field" autofocus
                                                       name="companyName" id="companyName" required="true"/>
                                            </div>
                                        </c:if>

                                        <c:if test="${currentUser.userRoleTypeId!=0}">
                                            <div class="form-group form-group-float col-md-6">
                                                <label class="required">Company Name</label>
                                                <input type="text" tabindex="1"
                                                       class="form-control form-control-sm field" autofocus
                                                       name="companyName" readonly id="companyName" required="true"/>
                                            </div>
                                        </c:if>


                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Mailing Address </label>
                                            <input type="text" tabindex="2"
                                                   class="form-control form-control-sm field"
                                                   name="mailingAddress"
                                                   id="mailingAddress" required="true"/>
                                        </div>

                                    </div>

                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Mobile No. </label>
                                            <input type="text" tabindex="3"
                                                   class="form-control form-control-sm field numeric"
                                                   name="mobileNo"
                                                   id="mobileNo" required="true"/>
                                        </div>
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="">Email </label>
                                            <input type="text" tabindex="4"
                                                   class="form-control form-control-sm field" name="email"
                                                   id="email"/>
                                        </div>
                                    </div>


                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="">Website</label>
                                            <input type="text" tabindex="5"
                                                   class="form-control form-control-sm field"
                                                   name="website"
                                                   id="website"/>
                                        </div>
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Financial Year Start</label>

                                            <input type="text" tabindex="6"
                                                   class="form-control form-control-sm formatDate field"
                                                   name="fnYrStart" placeholder="DD.MM.YYYY"
                                                   required="true"
                                                   id="fnYrStart"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Business Type</label>
                                            <c:if test="${currentUser.userRoleTypeId==0}">
                                                <form:select class="form-control form-control-sm field"
                                                             name="businessType"
                                                             path="businessTypeList"
                                                             id="businessType" tabindex="8">
                                                    <form:option value="">---Please Select---</form:option>
                                                    <form:options items="${businessTypeList}" itemValue="valueInteger"
                                                                  itemLabel="text"/>
                                                </form:select>
                                            </c:if>
                                            <c:if test="${currentUser.userRoleTypeId!=0}">
                                                <form:select class="form-control form-control-sm field "
                                                             name="businessType" disabled="true"
                                                             path="businessTypeList"
                                                             id="businessType" tabindex="8">
                                                    <form:option value="">---Please Select---</form:option>
                                                    <form:options items="${businessTypeList}" itemValue="valueInteger"
                                                                  itemLabel="text"/>
                                                </form:select>
                                            </c:if>


                                        </div>

                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">PF Percentage(%)</label>
                                            <input type="text" tabindex="6"
                                                   class="form-control form-control-sm  field numeric"
                                                   name="pfPercentage"
                                                   required="true"
                                                   id="pfPercentage"/>
                                        </div>
                                    </div>


                                    <div class="form-group row">
                                        <c:if test="${currentUser.userRoleTypeId==0}">
                                            <div class="form-group form-group-float col-md-6">
                                                <label class="required">Approval Status</label>
                                                <select class="form-control form-control-sm field"
                                                        name="status" required
                                                        id="businessType" tabindex="8">
                                                    <option value="">-- Please Select --</option>
                                                    <option value="A">Approved</option>
                                                    <option value="N">Not Approved</option>
                                                </select>
                                            </div>
                                        </c:if>


                                        <c:if test="${currentUser.userRoleTypeId!=0}">
                                            <div class="form-group form-group-float col-md-6">
                                                <label class="required">Approval Status</label>
                                                <select class="form-control form-control-sm field"
                                                        name="status" readonly=""
                                                        tabindex="8">
                                                    <option value="A">Approved</option>
                                                </select>
                                            </div>
                                        </c:if>


                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Remarks</label>
                                            <textarea type="text" tabindex="6"
                                                      class="form-control form-control-sm field"
                                                      name="remarks"
                                                      id="remarks"></textarea>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="required">Concat Person </label>
                                            <input type="text" tabindex="3"
                                                   class="form-control form-control-sm field numeric"
                                                   name="contactPerson"
                                                   id="contactPerson" required="true"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-md-9"></div>
                                        <div class="col-md-1">
                                            <input type="reset" tabindex="10" class="btn btn-danger"
                                                   value="Reset" id="reRestBtn">
                                        </div>
                                        <div class="col-md-1 ml-1">
                                            <input type="submit" tabindex="9" class="btn btn-primary"
                                                   value="Save" id="saveBtn">
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>


            </form>
        </div>
    </div>
</div>


</body>
</html>


