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
<div class="navbar navbar-expand-lg navbar-light navbar-static"
     style="background:-webkit-linear-gradient(#4085a8 0.8%, #fffdff 100%)">
    <div class="d-flex flex-1 d-lg-none">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-mobile">
            <i class="icon-paragraph-justify3"></i>
        </button>
        <button class="navbar-toggler sidebar-mobile-main-toggle" type="button">
            <i class="icon-transmission"></i>
        </button>
    </div>

    <div class="navbar-brand text-center text-lg-left">
        <a href="/" class="d-inline-block">
            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png'/>"
                 style="width: 90px; height: 26px; margin-top: -10px"
                 class="d-none d-sm-block"
                 alt="logo">
            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png'/>" class="d-sm-none"
                 alt="">
        </a>
    </div>

    <div class="collapse navbar-collapse order-2 order-lg-1" id="navbar-mobile">
        <ul class="navbar-nav">
            <span class="badge badge-light my-1 text-left">${currentUser.companyName}</span>
        </ul>
        <ul class="navbar-nav ml-lg-auto">
            <li class="nav-item dropdown row align-content-center">
                <span class="badge badge-light  ml-2">From: ${currentUser.financialYearFrom}</span> &nbsp;
                <span class="badge badge-light">To: ${currentUser.financialYearTo}</span>
            </li>
        </ul>
    </div>

    <ul class="navbar-nav flex-row order-1 order-lg-2 flex-1 flex-lg-0 justify-content-end align-items-center">
        <li class="nav-item nav-item-dropdown-lg dropdown dropdown-user h-100">
            <a href="#"
               class="navbar-nav-link navbar-nav-link-toggler dropdown-toggle d-inline-flex align-items-center h-100"
               data-toggle="dropdown">
                <img src="<c:url value='/resources/limitless/global_assets/images/demo/users/user-avatar.jpg' />"
                     class="rounded-pill mr-lg-2" height="34" alt="">
                <span class="d-none d-lg-inline-block">${currentUser.txtUserName}</span>
            </a>

            <div class="dropdown-menu dropdown-menu-right">
                <a href="<c:url value="/changePassword"/>" class="dropdown-item"><i class="icon-cog5"></i> Change
                    Password</a>
                <a href="javascript:$('#logoutForm').submit();" class="dropdown-item"><i class="icon-switch2"></i>
                    Logout</a>
            </div>
        </li>
    </ul>
</div>