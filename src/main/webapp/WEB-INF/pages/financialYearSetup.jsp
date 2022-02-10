<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 5/5/2020
  Time: 10:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Financial Year Setup</title>
</head>
<body>
<!-- Page header -->
<div class="page-header page-header-light">
    <div class="breadcrumb-line breadcrumb-line-light header-elements-lg-inline">
        <div class="d-flex">
            <div class="breadcrumb">
                <a href="index.html" class="breadcrumb-item"><i class="icon-home2 mr-2"></i> Setting</a>
                <span class="breadcrumb-item active">Financial Year Setup</span>
            </div>
            <a href="#" class="header-elements-toggle text-body d-lg-none"><i class="icon-more"></i></a>
        </div>
    </div>
</div>
<!-- /page header -->

<div class="content">
    <!-- Form inputs -->
    <div class="card">
        <div class="card-body">
            <form id="financialYearForm" action="<c:url value='/financialYearSetup'/> "
                  class="globalForm">
                <div class="card-body">
                        <legend class="text-uppercase font-size-sm font-weight-bold">Financial Year</legend>
                        <div class="table-responsive">
                            <table id="financialYearGrid" class="table">
                                <thead>
                                <tr class="bg-primary text-white">
                                    <th>SL</th>
                                    <th hidden></th>
                                    <th>From</th>
                                    <th>To</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%--<div class="wrapper">--%>
<!-- Content Wrapper. Contains page content -->
<div class="">
    <!-- Content Header (Page header) -->
    <div class="page_title">
        <span class="title">Setting</span>
        <span class="subtitle">Financial Year Setup</span>
    </div>

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="card card-info">

                <!-- /.card-header -->
                <!-- form start -->

            </div>
        </div>
        <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
    <%--</div>--%>
</div>
</body>
</html>





