<%--
  Created by IntelliJ IDEA.
  User: jigmePc
  Date: 5/4/2019
  Time: 11:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title"> File Upload Screen </title>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="." class="breadcrumb-item"><i class="icon-home2 mr-2"></i> File</a>
                <span class="breadcrumb-item active">Upload Screen</span>
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
            <form>
                <div class="card-body">
                    <legend class="text-uppercase font-size-sm font-weight-bold">File Upload Panel</legend>
                    <div class="table-responsive">
                        <div class="form-group-float">
                            <div id="preview" class="">Upload Image</div>
                            <style>
                                #preview {
                                    display: flex;
                                    justify-content: center;
                                    align-items: center;
                                    height: 200px;
                                    border: 1px solid #ccc;
                                    margin-top: 10px;
                                }

                                #preview {
                                    max-width: 100%;
                                    max-height: 100%;
                                }

                                #preview img {
                                    max-width: 100%;
                                    max-height: 100%;
                                }
                            </style>
                            <input class="fileName form-control">
                            File: Max Size : 10 MB !
                            <input type="file"
                                   accept="application/pdf, image/jpeg, image/png, image/gif"

                                   class="form-control field" id="file"
                                   name="profilePicture">
                        </div>
                        <p class="card-text" id="fileSize"></p>
                        <div class="p-1">
                            <input type="button" value="Add" class="btn btn-primary btn-sm" id="addFileToListBtn">

                            <input type="button" value="Select New" id="selectNew" class="btn btn-info btn-sm">
                        </div>


                    </div>
                </div>

            </form>


        <div class="container">

            <table id="allTeamTableList" class="w-100 text-center">
                <th>SI No.</th>
                <th>File Name</th>
                <th>Action</th>
                <tbody id="FileList">
                <tr id="noFileRow">
                    <td colspan="3">No file added</td>
                </tr>

                </tbody>
            </table>

            <div class="p-1 text-center">
                <input type="button" value="Upload" class="btn btn-primary btn-sm" id="uploadBtn">

                <input type="button" value="Cancel" id="cancelBtn" class="btn btn-warning btn-sm">
            </div>

        </div>




        </div>
    </div>
</div>
</body>
</html>


