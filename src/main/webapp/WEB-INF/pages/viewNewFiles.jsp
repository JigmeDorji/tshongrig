<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/4/2019
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title class="title"> View New Files </title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> File</a>
                <span class="breadcrumb-item active">New Files</span>
            </div>
            <a href="." class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->

<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">

            <div class="container">

                <table id="" class="w-100 text-center border" border="1">
                    <th>Date</th>
                    <th>File Name</th>
                    <th>Action</th>
                    <tbody id="FileList">

                    <c:if test="${files.size()>0}">
                        <c:forEach items="${files}" var="file">

                            <tr>
                                <td><fmt:formatDate type="date" value="${file.createdDate}" pattern="dd/MM/yy"/></td>
                                <td>${file.fileName}</td>
                                <td>
                                    <button class="btn btn-info btn-sm onViewBtn" data-id="${file.id}">View</button>

                                    <button class="onDeleteBtn btn btn-danger btn-sm" id="deleteBtn"  accesskey="${file.id}">Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <c:if test="${files.size()==0}">
                        <tr id="noFileRow">
                            <td colspan="3">No file added</td>
                        </tr>
                    </c:if>


                    </tbody>
                </table>


                <div class="modal fade" id="onViewModal" role="dialog">
                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>







                            <h4 class="card-title text-center" id="fileName">

                            </h4>
                            <div class="modal-body">



                                <img id="previewImage" class="img-fluid" src="" alt="Preview Image">

                                <div class="embed-responsive embed-responsive-16by9" id="pdfViewFrame">

                                    <iframe id="iFrameContainer" class="embed-responsive-item"></iframe>
                                </div>

                            </div>
                            <hr>

                            <div class="modal-footer d-flex justify-content-center m-2">

                                    <div class="mx-2">
                                        <input type="button" tabindex="9" class="btn btn-sm btn-primary"  value="Mark as Completed" id="onMarkedAsCompletedBtn">
                                    </div>
                                    <div class="mx-2">
                                        <input type="button" tabindex="9" class="btn btn-sm btn-info" value="Print"  id="onPrintBtn">
                                    </div>
                                    <div class="mx-2">
                                        <input type="submit" tabindex="9" class="onDeleteBtn btn btn-sm btn-danger" value="Delete" id="onDeleteBtn">
                                    </div>

                            </div>
                        </div>
                    </div>
                </div>


                <%--                <div class="modal fade" id="onViewModal" role="dialog">--%>
                <%--                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">--%>
                <%--                        <!-- Modal content-->--%>
                <%--                        <div class="modal-content">--%>
                <%--                            <div class="modal-header">--%>
                <%--                                <button type="button" class="close" data-dismiss="modal">&times;</button>--%>
                <%--                                &lt;%&ndash;<h4 class="modal-title">Edit Ledger Details</h4>&ndash;%&gt;--%>
                <%--                            </div>--%>
                <%--                            <div class="modal-body">--%>
                <%--                                <form id="ledgerEditForm" class="form-horizontal ledgerEditForm" method="POST">--%>
                <%--                                    <input type="hidden" id="editLedgerId" name="ledgerId">--%>
                <%--                                    &lt;%&ndash;<legend class="text-uppercase font-size-sm font-weight-bold">Ledger Details</legend>&ndash;%&gt;--%>
                <%--                                    <div class="embed-responsive embed-responsive-1by1">--%>
                <%--                                        <iframe id="iFrameContainer" class="embed-responsive-item"></iframe>--%>
                <%--                                    </div>--%>
                <%--                                    <div class="form-group row">--%>
                <%--                                        <div class="col-md-4"></div>--%>
                <%--                                        <div class="col-md-2">--%>
                <%--                                            <input type="submit" tabindex="9" class="btn btn-sm btn-primary btn-block"--%>
                <%--                                                   value="Update" id="editBtnSave">--%>
                <%--                                        </div>--%>
                <%--                                    </div>--%>
                <%--                                </form>--%>
                <%--                            </div>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>

            </div>


        </div>
    </div>
</div>
</body>
</html>


