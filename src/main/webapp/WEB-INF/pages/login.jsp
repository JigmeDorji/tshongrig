<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="decorator" content="/layout/login-layout.jsp"/>
    <%--    <link rel="icon" href="favicon.ico">--%>
    <title>BCS-LOGIN</title>
</head>
<body class="light">
<%--<!-- Main navbar -->
<div class="navbar navbar-expand-lg navbar-dark bg-indigo navbar-static">
    <div class="navbar-brand ml-2 ml-lg-0">
        <a href="index.html" class="d-inline-block">
            <img src="../../../../global_assets/images/logo_light.png" alt="">
        </a>
    </div>

    <div class="d-flex justify-content-end align-items-center ml-auto">
        <ul class="navbar-nav flex-row">
            <li class="nav-item">
                <a href="#" class="navbar-nav-link">
                    <i class="icon-lifebuoy"></i>
                    <span class="d-none d-lg-inline-block ml-2">Support</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="navbar-nav-link">
                    <i class="icon-user-plus"></i>
                    <span class="d-none d-lg-inline-block ml-2">Register</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="#" class="navbar-nav-link">
                    <i class="icon-user-lock"></i>
                    <span class="d-none d-lg-inline-block ml-2">Login</span>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- /main navbar -->--%>


<!-- Page content -->
<div class="page-content">

    <!-- Main content -->
    <div class="content-wrapper">

        <!-- Inner content -->
        <div class="content-inner">

            <!-- Content area -->
            <div class="content d-flex justify-content-center align-items-center">

                <!-- Login card -->
                <form  action="<c:url value='/auth'/>" method="post" class="login-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="card mb-0">
                        <div class="card-body">
                            <div class="text-center mb-3 col-md-12">
                                <img src="<c:url value='/resources/images/logobcs.png'/>" class="img-fluid" alt="logo">
                                <h5 class="mb-0">Login to your account</h5>
                                <span class="d-block text-muted">Your credentials</span>
                            </div>

                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <form:select required="true"  path="companyList" cssStyle="margin-bottom: 4%"
                                             class="form-control form-select field select2-validationSelect2-container"
                                             id="companyId"
                                             name="companyId" >
                                    <form:option
                                            value="">---Select Company---</form:option>
                                    <form:options items="${companyList}"
                                                  itemValue="value"
                                                  itemLabel="text"/>
                                </form:select>
                                <div class="form-control-feedback">
                                    <i class="icon-user text-muted"></i>
                                </div>
                            </div>
                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="text" id="inputEmail" name="username" class="form-control" placeholder="Username">
                                <div class="form-control-feedback">
                                    <i class="icon-user text-muted"></i>
                                </div>
                            </div>

                            <div class="form-group form-group-feedback form-group-feedback-left">
                                <input type="password" id="inputPassword" name="password"  class="form-control" placeholder="Password">
                                <div class="form-control-feedback">
                                    <i class="icon-lock2 text-muted"></i>
                                </div>
                            </div>

                            <div class="form-group d-flex align-items-center">
                                <label class="custom-control custom-checkbox">
                                    <input type="checkbox" name="remember" class="custom-control-input" checked>
                                    <span class="custom-control-label">Remember</span>
                                </label>

                                <a href="login_password_recover.html" class="ml-auto">Forgot password?</a>
                            </div>

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                            </div>

                            <c:if test="${not empty error}">
                                <div class="form-group">
                                    <div class="alert alert-danger border-0 alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert"><span>×</span></button>
                                        <span class="font-weight-semibold"></span> <spring:message code="${error}"/>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </form>
                <!-- /login card -->
            </div>
            <!-- /content area -->
            <!-- Footer -->
            <div class="navbar navbar-expand-lg navbar-light border-bottom-0 border-top">
                <div class="text-center d-lg-none w-100">
                    <button type="button" class="navbar-toggler dropdown-toggle" data-toggle="collapse" data-target="#navbar-footer">
                        <i class="icon-unfold mr-2"></i>
                        Footer
                    </button>
                </div>

                <div class="navbar-collapse collapse" id="navbar-footer">
						<span class="navbar-text">
							&copy; 2018 - 2022. <a href="tshongrig.com">Tshong Rig Private Limitted</a>
						</span>

                    <ul class="navbar-nav ml-lg-auto">
                        <li class="nav-item"><a href="https://www.tshongrig.com/contact-2/" class="navbar-nav-link" target="_blank"><i class="icon-lifebuoy mr-2"></i> Support</a></li>
                        <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/" class="navbar-nav-link" target="_blank"><i class="icon-file-text2 mr-2"></i> Docs</a></li>
                        <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/" class="navbar-nav-link font-weight-semibold" target="_blank"><span class="text-pink"><i class="icon-cart2 mr-2"></i> Purchase</span></a></li>
                    </ul>
                </div>
            </div>
            <!-- /footer -->

        </div>
        <!-- /inner content -->

    </div>
    <!-- /main content -->

</div>
<!-- /page content -->
<%--<div class="wrapper vh-100">
    <div class="row align-items-center h-100">
        <form  action="<c:url value='/auth'/>" method="post" class="col-lg-3 col-md-4 col-10 mx-auto text-center">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <img id="profile-img"  width="45%"
                 height="auto"  src="<c:url value='/resources/images/logobcs.png'/>"/>
&lt;%&ndash;            <label >We care for your business</label>&ndash;%&gt;
            <h5 class="h5 mb-5">We care for your business</h5>
            <h1 class="h6 mb-3">Sign in</h1>
            <div class="form-group">
                <form:select required="true"  path="companyList" cssStyle="margin-bottom: 4%"
                             class="form-control form-select field select2-validationSelect2-container"
                             id="companyId"
                             name="companyId" >
                    <form:option
                            value="">---Select Company---</form:option>
                    <form:options items="${companyList}"
                                  itemValue="value"
                                  itemLabel="text"/>
                </form:select>
            </div>
            <div class="form-group">
                <label for="inputEmail" class="sr-only">User Name</label>
                <input type="text" id="inputEmail" name="username" class="form-control form-control-lg" placeholder="User Name" required="" autofocus="">
            </div>
            <div class="form-group">
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="password"  class="form-control form-control-lg" placeholder="Password" required="">
            </div>
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="remember-me"> Remember me </label>
            </div>
            <button type="submit" class="btn btn-lg btn-primary btn-block btn-signin">LOGIN</button>
            <p class="mt-5 mb-3 text-muted">© 2021</p>

            <div>
                <c:if test="${not empty error}">
                    <label style="color: red"><spring:message code="${error}"/></label>
                </c:if>
            </div>
        </form>
    </div>
</div>--%>
<script>
    window.dataLayer = window.dataLayer || [];

    function gtag() {
        dataLayer.push(arguments);
    }

    gtag('js', new Date());
    gtag('config', 'UA-56159088-1');
</script>
</body>
</html>
