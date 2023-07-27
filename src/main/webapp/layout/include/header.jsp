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

    <div class="custom-nav-brand navbar-brand">

        <style>

            @media (min-width: 1030px ) {
                .companyName {
                    font-size: 120%;
                }
            }

            @media (min-width: 630px) {
                #span-FY {
                    display: none;
                }
            }

            @media (max-width: 630px) {
                .companyName {
                    font-size: 80%;
                }

                .custom-fY li span {
                    font-size: 60%;
                }

                .custom-badge-fY {
                    display: none;
                }
            }


            @media (max-width: 500px) {
                .companyName {
                    font-size: 60%;
                }

                .custom-fY li span {
                    font-size: 50%;
                }
            }

            @media (max-width: 399px) {
                .companyName {
                    font-size: 50%;
                }

                .custom-fY li span {
                    font-size: 30%;
                }
            }

        </style>
        <a href="<c:url value='/redirectHome'/>" class="d-inline-block">
            <%--            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png'/>"--%>
            <img src="<c:url value='/resources/limitless/global_assets/images/logo.png'/>"
                 style="padding-left:10%;width: 180px; height: 50px; margin-top: -10px"
            <%--                 style="padding-left:10%;width: 10%; height: 10%; margin-top: -10px"--%>
                 class="d-none d-sm-block"
                 alt="logo">

            <%--            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png'/>" class="d-sm-none"--%>
            <%--                 alt="">--%>
        </a>
    </div>
    <c:if test="${currentUser.companyName.length() >=40}">
        <div class="collapse navbar-collapse   navbar-collapse  order-2 order-lg-1  d-flex justify-content-sm-around text-center "
             id="navbar-mobile">
            <div>
                <ul class="navbar-nav ">
                <span class="badge  badge-light badge-pill my-1 companyName"
                >${currentUser.companyName}</span>

                </ul>
            </div>
            <ul class="navbar-nav custom-fY">
                <li class="nav-item dropdown row align-content-center ">

                    <span class="custom-badge-fY badge badge-light badge-pill ">From: ${currentUser.financialYearFrom}</span>
                    <span class="custom-badge-fY ">To:</span>&nbsp;
                    <span class="custom-badge-fY  badge badge-light badge-pill"> ${currentUser.financialYearTo}</span>

                    <span id="span-FY">From: ${currentUser.financialYearFrom} To: ${currentUser.financialYearTo}</span>
                </li>
            </ul>
        </div>

    </c:if>
    <c:if test="${currentUser.companyName.length() <=40}">
        <div class="collapse navbar-collapse   navbar-collapse  order-2 order-lg-1  d-flex justify-content-around"
             id="navbar-mobile">
            <div>
                <ul class="navbar-nav ">
                <span class="badge  badge-light badge-pill my-1 companyName"
                >${currentUser.companyName}</span>

                </ul>
            </div>
            <ul class="navbar-nav custom-fY">
                <li class="nav-item dropdown row align-content-center ">

                    <span class="custom-badge-fY badge badge-light badge-pill ">From: ${currentUser.financialYearFrom}</span>
                    <span class="custom-badge-fY ">To:</span>&nbsp;
                    <span class="custom-badge-fY  badge badge-light badge-pill"> ${currentUser.financialYearTo}</span>

                    <span id="span-FY">From: ${currentUser.financialYearFrom} To: ${currentUser.financialYearTo}</span>
                </li>
            </ul>
        </div>

    </c:if>

    <ul class="navbar-nav flex-row order-1 order-lg-2 flex-1 flex-lg-0 justify-content-end align-items-center">

        <li class="nav-item nav-item-dropdown-lg dropdown dropdown-user h-100">
            <a href="#"
               class="navbar-nav-link navbar-nav-link-toggler dropdown-toggle d-inline-flex align-items-center h-100"
               data-toggle="dropdown">
                <img src="<c:url value='/resources/limitless/global_assets/images/demo/users/user-avatar.jpg' />"
                     class="rounded-pill mr-lg-2" height="34" alt="">
                <%--                <span class="d-none d-md-none d-lg-inline-block">${currentUser.txtUserName}</span>--%>
                <span class="d-none d-lg-none d-xl-inline-block">${currentUser.txtUserName}</span>
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


<%--<div class="wrapper">--%>
<%--    <div class="circle"></div>--%>
<%--    <div class="circle"></div>--%>
<%--    <div class="circle"></div>--%>
<%--    <div class="shadow"></div>--%>
<%--    <div class="shadow"></div>--%>
<%--    <div class="shadow"></div>--%>
<%--</div>--%>


<%--<style>--%>
<%--    .wrapper {--%>
<%--        width: 500px;--%>
<%--        height: 60px;--%>
<%--        position: relative;--%>
<%--        z-index: 1;--%>
<%--    }--%>

<%--    .circle {--%>
<%--        width: 20px;--%>
<%--        height: 20px;--%>
<%--        position: absolute;--%>
<%--        border-radius: 50%;--%>
<%--        background-color: #fff;--%>
<%--        left: 15%;--%>
<%--        transform-origin: 50%;--%>
<%--        animation: circle7124 .5s alternate infinite ease;--%>
<%--    }--%>

<%--    @keyframes circle7124 {--%>
<%--        0% {--%>
<%--            top: 60px;--%>
<%--            height: 5px;--%>
<%--            border-radius: 50px 50px 25px 25px;--%>
<%--            transform: scaleX(1.7);--%>
<%--        }--%>

<%--        40% {--%>
<%--            height: 20px;--%>
<%--            border-radius: 50%;--%>
<%--            transform: scaleX(1);--%>
<%--        }--%>

<%--        100% {--%>
<%--            top: 0%;--%>
<%--        }--%>
<%--    }--%>

<%--    .circle:nth-child(2) {--%>
<%--        left: 45%;--%>
<%--        animation-delay: .2s;--%>
<%--    }--%>

<%--    .circle:nth-child(3) {--%>
<%--        left: auto;--%>
<%--        right: 15%;--%>
<%--        animation-delay: .3s;--%>
<%--    }--%>

<%--    .shadow {--%>
<%--        width: 20px;--%>
<%--        height: 4px;--%>
<%--        border-radius: 50%;--%>
<%--        background-color: rgba(0,0,0,0.9);--%>
<%--        position: absolute;--%>
<%--        top: 62px;--%>
<%--        transform-origin: 50%;--%>
<%--        z-index: -1;--%>
<%--        left: 15%;--%>
<%--        filter: blur(1px);--%>
<%--        animation: shadow046 .5s alternate infinite ease;--%>
<%--    }--%>

<%--    @keyframes shadow046 {--%>
<%--        0% {--%>
<%--            transform: scaleX(1.5);--%>
<%--        }--%>

<%--        40% {--%>
<%--            transform: scaleX(1);--%>
<%--            opacity: .7;--%>
<%--        }--%>

<%--        100% {--%>
<%--            transform: scaleX(.2);--%>
<%--            opacity: .4;--%>
<%--        }--%>
<%--    }--%>

<%--    .shadow:nth-child(4) {--%>
<%--        left: 45%;--%>
<%--        animation-delay: .2s--%>
<%--    }--%>

<%--    .shadow:nth-child(5) {--%>
<%--        left: auto;--%>
<%--        right: 15%;--%>
<%--        animation-delay: .3s;--%>
<%--    }--%>

<%--</style>--%>