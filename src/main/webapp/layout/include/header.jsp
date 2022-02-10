<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 10/22/2020
  Time: 3:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>

<sec:authentication var="auth" property="principal"/>
<c:url value="/logout" var="logoutUrl"/>

<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" id="financialYearFrom" value="${currentUser.financialYearFrom}"/>
    <input type="hidden" id="financialYearTo" value="${currentUser.financialYearTo}"/>
</form>
<!-- Main navbar -->
<div class="navbar navbar-expand-lg navbar-dark bg-indigo navbar-static shadow-none" style="background:-webkit-linear-gradient(#4085a8 0.8%, #fffdff 100%)">
    <div class="d-flex flex-1 d-lg-none">
        <button type="button" class="navbar-toggler sidebar-mobile-main-toggle">
            <i class="icon-transmission"></i>
        </button>
        <button data-target="#navbar_search" type="button" class="navbar-toggler" data-toggle="collapse">
            <i class="icon-"></i>
        </button>
    </div>

    <div class="navbar-brand text-center text-lg-left">
        <a href="/" class="d-inline-block">
            <img src="<c:url value='/resources/limitless/global_assets/images/logobcs.png'/>" class="d-none d-sm-block" alt="asdasd">
            <img src="<c:url value='/resources/limitless/global_assets/images/logobcs.png'/>" class="d-sm-none" alt="">
        </a>
    </div>


    <div class="collapse navbar-collapse order-2 order-lg-1" id="navbar_search">
        <div class="navbar-search d-flex align-items-center py-3 py-lg-0">
            <div class="form-group-feedback form-group-feedback-left flex-grow-1">
                <span><strong>${currentUser.companyName}</strong></span>
            </div>
        </div>
    </div>

    <div class="order-1 order-lg-2 d-flex flex-1 flex-lg-0 justify-content-end align-items-center">
        <span class="mr-2">From</span>
        <span class="badge badge-light d-none d-lg-inline-block mr-3">${currentUser.financialYearFrom}</span>
        <span class="mr-2">To</span>
        <span class="badge badge-light d-none d-lg-inline-block mr-3">${currentUser.financialYearTo}</span>


        <ul class="navbar-nav flex-row align-items-center h-100">

            <li class="nav-item nav-item-dropdown-lg dropdown dropdown-user h-100">
                <a href="#" class="navbar-nav-link navbar-nav-link-toggler dropdown-toggle d-inline-flex align-items-center h-100" data-toggle="dropdown" aria-expanded="false">
<%--                    <i class="icon-user" class="rounded-pill mr-lg-2" height="34"></i>--%>
                    <img src="../../resources/limitless/global_assets/images/demo/users/user-avatar.jpg" class="rounded-pill mr-lg-2" height="34" alt="">
                    <span class="d-none d-lg-inline-block">${currentUser.txtUserName}</span>
                </a>

                <div class="dropdown-menu dropdown-menu-right">
                    <a href="<c:url value="/changePassword"/>" class="dropdown-item"><i class="icon-cog5"></i> Change Password</a>
                    <a href="javascript:$('#logoutForm').submit();" class="dropdown-item"><i class="icon-switch2"></i> Logout</a>
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- /main navbar -->


<%--<nav class="topnav navbar navbar-light">
    <button type="button" class="navbar-toggler text-muted mt-2 p-0 mr-3 collapseSidebar">
        <i class="fe fe-menu navbar-toggler-icon"></i>
    </button>
    &lt;%&ndash;    <form class="form-inline mr-auto searchform text-muted">&ndash;%&gt;
    <h6>
        <small>
            <label style="color: #808080!important;font-size: 10px"> From:</label>
            <label style="color: #808080!important;font-size: 10px">(${currentUser.financialYearFrom})</label>
            <label style="color: #808080!important;font-size: 10px">To:</label>
            <label style="color: #808080!important;font-size: 10px">(${currentUser.financialYearTo})</label></small>
    </h6>
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link text-muted my-2" href="./#" data-toggle="modal" data-target=".modal-shortcut">
                &lt;%&ndash;                <label style="color: #fff!important;"><strong></strong></label>&ndash;%&gt;
                <h6><label
                        style="color: #808080!important;"><strong>${currentUser.companyName}:
                    [${currentUser.loginId}]</strong></label>
                </h6>
            </a>
        </li>
        <li class="nav-item nav-notif">
            <a class="nav-link text-muted my-2" href="./#" data-toggle="modal" data-target=".modal-shortcut">
                <span class="fe fe-grid fe-16"></span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-muted my-2" href="#" id="modeSwitcher" data-mode="light">
                <i class="fe fe-sun fe-16"></i>
            </a>
        </li>
    </ul>
</nav>--%>


<script>
    $('.currentPeriod').on('click', function () {
        window.location.href = spms.getUrl() + 'financialYearSetup'
    })
</script>