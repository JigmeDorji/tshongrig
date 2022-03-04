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
<div class="navbar navbar-expand-lg navbar-dark navbar-static"  style="background:-webkit-linear-gradient(#4085a8 0.8%, #fffdff 100%)">
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
                 alt="asdasd">
            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png'/>" class="d-sm-none" alt="">
        </a>
    </div>

  <%--  <div class="navbar-brand text-center text-lg-left">
        <a href="index.html" class="d-inline-block">
            <img src="<c:url value='/resources/limitless/global_assets/images/logo_trans.png' />" class="d-none d-sm-block" alt="">
            <img src="<c:url value='/resources/limitless/global_assets/images/logo_icon_light.png' />" class="d-sm-none" alt="">
        </a>
    </div>--%>

    <div class="collapse navbar-collapse order-2 order-lg-1" id="navbar-mobile">
       <%-- <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a href="#" class="navbar-nav-link" data-toggle="dropdown">
                    <i class="icon-git-compare"></i>
                    <span class="d-lg-none ml-3">${currentUser.companyName}</span>
&lt;%&ndash;                    <span class="badge badge-warning badge-pill ml-auto ml-lg-0">9</span>&ndash;%&gt;
                </a>
                &lt;%&ndash;this ll used if needed in future&ndash;%&gt;
               &lt;%&ndash; <div class="dropdown-menu dropdown-content wmin-lg-350">
                    <div class="dropdown-content-header">
                        <span class="font-weight-semibold">Git updates</span>
                        <a href="#" class="text-body"><i class="icon-sync"></i></a>
                    </div>

                    <div class="dropdown-content-body dropdown-scrollable">
                        <ul class="media-list">
                            <li class="media">
                                <div class="mr-3">
                                    <a href="#" class="btn bg-transparent border-primary text-primary rounded-pill border-2 btn-icon"><i class="icon-git-pull-request"></i></a>
                                </div>

                                <div class="media-body">
                                    Drop the IE <a href="#">specific hacks</a> for temporal inputs
                                    <div class="text-muted font-size-sm">4 minutes ago</div>
                                </div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <a href="#" class="btn bg-transparent border-warning text-warning rounded-pill border-2 btn-icon"><i class="icon-git-commit"></i></a>
                                </div>

                                <div class="media-body">
                                    Add full font overrides for popovers and tooltips
                                    <div class="text-muted font-size-sm">36 minutes ago</div>
                                </div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <a href="#" class="btn bg-transparent border-info text-info rounded-pill border-2 btn-icon"><i class="icon-git-branch"></i></a>
                                </div>

                                <div class="media-body">
                                    <a href="#">Chris Arney</a> created a new <span class="font-weight-semibold">Design</span> branch
                                    <div class="text-muted font-size-sm">2 hours ago</div>
                                </div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <a href="#" class="btn bg-transparent border-success text-success rounded-pill border-2 btn-icon"><i class="icon-git-merge"></i></a>
                                </div>

                                <div class="media-body">
                                    <a href="#">Eugene Kopyov</a> merged <span class="font-weight-semibold">Master</span> and <span class="font-weight-semibold">Dev</span> branches
                                    <div class="text-muted font-size-sm">Dec 18, 18:36</div>
                                </div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <a href="#" class="btn bg-transparent border-primary text-primary rounded-pill border-2 btn-icon"><i class="icon-git-pull-request"></i></a>
                                </div>

                                <div class="media-body">
                                    Have Carousel ignore keyboard events
                                    <div class="text-muted font-size-sm">Dec 12, 05:46</div>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div class="dropdown-content-footer bg-light">
                        <a href="#" class="text-body mr-auto">All updates</a>
                        <div>
                            <a href="#" class="text-body" data-popup="tooltip" title="Mark all as read"><i class="icon-radio-unchecked"></i></a>
                            <a href="#" class="text-body ml-2" data-popup="tooltip" title="Bug tracker"><i class="icon-bug2"></i></a>
                        </div>
                    </div>
                </div>&ndash;%&gt;
            </li>
        </ul>--%>

        <span class="badge badge-light  my-3 my-lg-0 ml-lg-3 ">${currentUser.companyName}</span><br>

        <%--this ll used if needed in future--%>
        <%--<ul class="navbar-nav ml-lg-auto">
            <li class="nav-item dropdown">
                <a href="#" class="navbar-nav-link" data-toggle="dropdown">
                    <i class="icon-people"></i>
                    <span class="d-lg-none ml-3">Messages</span>
                </a>

                <div class="dropdown-menu dropdown-menu-right dropdown-content wmin-lg-300">
                    <div class="dropdown-content-header">
                        <span class="font-weight-semibold">Users online</span>
                        <a href="#" class="text-body"><i class="icon-search4 font-size-base"></i></a>
                    </div>

                    <div class="dropdown-content-body dropdown-scrollable">
                        <ul class="media-list">
                            <li class="media">
                                <div class="mr-3">
                                    <img src="../../../../global_assets/images/demo/users/face18.jpg" width="36" height="36" class="rounded-circle" alt="">
                                </div>
                                <div class="media-body">
                                    <a href="#" class="media-title font-weight-semibold">Jordana Ansley</a>
                                    <span class="d-block text-muted font-size-sm">Lead web developer</span>
                                </div>
                                <div class="ml-3 align-self-center"><span class="badge badge-mark border-success"></span></div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <img src="../../../../global_assets/images/demo/users/face24.jpg" width="36" height="36" class="rounded-circle" alt="">
                                </div>
                                <div class="media-body">
                                    <a href="#" class="media-title font-weight-semibold">Will Brason</a>
                                    <span class="d-block text-muted font-size-sm">Marketing manager</span>
                                </div>
                                <div class="ml-3 align-self-center"><span class="badge badge-mark border-danger"></span></div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <img src="../../../../global_assets/images/demo/users/face17.jpg" width="36" height="36" class="rounded-circle" alt="">
                                </div>
                                <div class="media-body">
                                    <a href="#" class="media-title font-weight-semibold">Hanna Walden</a>
                                    <span class="d-block text-muted font-size-sm">Project manager</span>
                                </div>
                                <div class="ml-3 align-self-center"><span class="badge badge-mark border-success"></span></div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <img src="../../../../global_assets/images/demo/users/face19.jpg" width="36" height="36" class="rounded-circle" alt="">
                                </div>
                                <div class="media-body">
                                    <a href="#" class="media-title font-weight-semibold">Dori Laperriere</a>
                                    <span class="d-block text-muted font-size-sm">Business developer</span>
                                </div>
                                <div class="ml-3 align-self-center"><span class="badge badge-mark border-warning"></span></div>
                            </li>

                            <li class="media">
                                <div class="mr-3">
                                    <img src="../../../../global_assets/images/demo/users/face16.jpg" width="36" height="36" class="rounded-circle" alt="">
                                </div>
                                <div class="media-body">
                                    <a href="#" class="media-title font-weight-semibold">Vanessa Aurelius</a>
                                    <span class="d-block text-muted font-size-sm">UX expert</span>
                                </div>
                                <div class="ml-3 align-self-center"><span class="badge badge-mark border-secondary"></span></div>
                            </li>
                        </ul>
                    </div>

                    <div class="dropdown-content-footer bg-light">
                        <a href="#" class="text-body mr-auto">All users</a>
                        <a href="#" class="text-body"><i class="icon-gear"></i></a>
                    </div>
                </div>
            </li>
        </ul>--%>
    </div>

    <ul class="navbar-nav flex-row order-1 order-lg-2 flex-1 flex-lg-0 justify-content-end align-items-center">
       <%-- <li class="nav-item nav-item-dropdown-lg dropdown">
            <a href="#" class="navbar-nav-link navbar-nav-link-toggler" data-toggle="dropdown">
                <i class="icon-bubbles4"></i>
                <span class="badge badge-warning badge-pill ml-auto ml-lg-0">2</span>
            </a>

            <div class="dropdown-menu dropdown-menu-right dropdown-content wmin-lg-350">
                <div class="dropdown-content-header">
                    <span class="font-weight-semibold">Messages</span>
                    <a href="#" class="text-body"><i class="icon-compose"></i></a>
                </div>

                <div class="dropdown-content-body dropdown-scrollable">
                    <ul class="media-list">
                        <li class="media">
                            <div class="mr-3 position-relative">
                                <img src="../../../../global_assets/images/demo/users/face10.jpg" width="36" height="36" class="rounded-circle" alt="">
                            </div>

                            <div class="media-body">
                                <div class="media-title">
                                    <a href="#">
                                        <span class="font-weight-semibold">James Alexander</span>
                                        <span class="text-muted float-right font-size-sm">04:58</span>
                                    </a>
                                </div>

                                <span class="text-muted">who knows, maybe that would be the best thing for me...</span>
                            </div>
                        </li>

                        <li class="media">
                            <div class="mr-3 position-relative">
                                <img src="../../../../global_assets/images/demo/users/face3.jpg" width="36" height="36" class="rounded-circle" alt="">
                            </div>

                            <div class="media-body">
                                <div class="media-title">
                                    <a href="#">
                                        <span class="font-weight-semibold">Margo Baker</span>
                                        <span class="text-muted float-right font-size-sm">12:16</span>
                                    </a>
                                </div>

                                <span class="text-muted">That was something he was unable to do because...</span>
                            </div>
                        </li>

                        <li class="media">
                            <div class="mr-3">
                                <img src="../../../../global_assets/images/demo/users/face24.jpg" width="36" height="36" class="rounded-circle" alt="">
                            </div>
                            <div class="media-body">
                                <div class="media-title">
                                    <a href="#">
                                        <span class="font-weight-semibold">Jeremy Victorino</span>
                                        <span class="text-muted float-right font-size-sm">22:48</span>
                                    </a>
                                </div>

                                <span class="text-muted">But that would be extremely strained and suspicious...</span>
                            </div>
                        </li>

                        <li class="media">
                            <div class="mr-3">
                                <img src="../../../../global_assets/images/demo/users/face4.jpg" width="36" height="36" class="rounded-circle" alt="">
                            </div>
                            <div class="media-body">
                                <div class="media-title">
                                    <a href="#">
                                        <span class="font-weight-semibold">Beatrix Diaz</span>
                                        <span class="text-muted float-right font-size-sm">Tue</span>
                                    </a>
                                </div>

                                <span class="text-muted">What a strenuous career it is that I've chosen...</span>
                            </div>
                        </li>

                        <li class="media">
                            <div class="mr-3">
                                <img src="../../../../global_assets/images/demo/users/face25.jpg" width="36" height="36" class="rounded-circle" alt="">
                            </div>
                            <div class="media-body">
                                <div class="media-title">
                                    <a href="#">
                                        <span class="font-weight-semibold">Richard Vango</span>
                                        <span class="text-muted float-right font-size-sm">Mon</span>
                                    </a>
                                </div>

                                <span class="text-muted">Other travelling salesmen live a life of luxury...</span>
                            </div>
                        </li>
                    </ul>
                </div>

                <div class="dropdown-content-footer justify-content-center p-0">
                    <a href="#" class="btn btn-light btn-block border-0 rounded-top-0" data-popup="tooltip" title="Load more"><i class="icon-menu7"></i></a>
                </div>
            </div>
        </li>--%>
           <span class="mr-2 my-3 my-lg-0 ml-lg-3">From</span>
           <span class="badge badge-light  my-3 my-lg-0 ml-lg-3">${currentUser.financialYearFrom}</span>
           <span class="mr-2 my-3 my-lg-0 ml-lg-3">To</span>
           <span class="badge badge-light  my-3 my-lg-0 ml-lg-3">${currentUser.financialYearTo}</span>

        <li class="nav-item nav-item-dropdown-lg dropdown dropdown-user h-100">
            <a href="#" class="navbar-nav-link navbar-nav-link-toggler dropdown-toggle d-inline-flex align-items-center h-100" data-toggle="dropdown">
                <img src="<c:url value='/resources/limitless/global_assets/images/demo/users/user-avatar.jpg' />" class="rounded-pill mr-lg-2" height="34" alt="">
                <span class="d-none d-lg-inline-block">${currentUser.txtUserName}</span>
            </a>

            <div class="dropdown-menu dropdown-menu-right">
                <a href="<c:url value="/changePassword"/>" class="dropdown-item"><i class="icon-cog5"></i> Change Password</a>
                <a href="javascript:$('#logoutForm').submit();" class="dropdown-item"><i class="icon-switch2"></i> Logout</a>
            </div>
        </li>
    </ul>
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
