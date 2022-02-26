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

<!-- Page content -->
<div class="page-content">

    <!-- Main content -->
    <div class="content-wrapper">
        <!-- Inner content -->
        <div class="content-inner">
            <div class="d-md-flex flex-row-reverse">
                <div class="signin-right">
                    <div class="card col-md-10">
                        <div class="card-body p-4">
                            <form action="<c:url value='/auth'/>" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="">
                                    <h1 class="signin-title-primary">Welcome back!</h1>
                                    <h3 class="signin-title-secondary">Sign in to continue.</h3>

                                    <div class="form-group">
                                        <form:select required="true" path="companyList" cssStyle="margin-bottom: 4%"
                                                     class="form-control form-select field select2-validationSelect2-container"
                                                     id="companyId"
                                                     name="companyId">
                                            <form:option
                                                    value="">---Select Company---</form:option>
                                            <form:options items="${companyList}"
                                                          itemValue="value"
                                                          itemLabel="text"/>
                                        </form:select>
                                    </div><!-- form-group -->
                                    <div class="form-group">
                                        <input type="text" id="inputEmail" name="username" class="form-control"
                                               placeholder="Username">
                                    </div><!-- form-group -->
                                    <div class="form-group mg-b-50">
                                        <input type="password" id="inputPassword" name="password" class="form-control"
                                               placeholder="Enter your password">
                                    </div><!-- form-group -->
                                    <button type="submit" class="btn btn-primary btn-block btn-signin">Sign In</button>
                                    <%--                        <p class="mg-b-0">Don't have an account? <a href="page-signup2.html">Sign Up</a></p>--%>
                                    <c:if test="${not empty error}">
                                        <div class="form-group">
                                            <div class="alert alert-danger border-0 alert-dismissible">
                                                    <%--                                        <button type="button" class="close" data-dismiss="alert"><span>×</span></button>--%>
                                                <span class="font-weight-semibold"></span> <spring:message
                                                    code="${error}"/>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </form>
                        </div>

                    </div>

                </div><!-- signin-right -->
                <div class="signin-left">
                    <div class="signin-box">
                        <%--                        <h2 class="slim-logo"><a href="index.html">slim<span>.</span></a></h2>--%>
                        <img src="<c:url value='/resources/images/logobcs.png'/>" class="img-fluid pb-2" alt="logo">
                            <p style="font-style: italic;font-family:Helvetica">"We care for your Business"</p>
                        <p>Our first product, <strong>Tshong Rig ERP system</strong> is here to help you manage your
                            business efficiently at ease. It has all the features required in the daily operation of
                            business.</p>

                        <p>Browse our site and explorer Tshong Rig.</p>

                        <p><a href="" class="btn btn-outline-secondary pd-x-25">Explore Tshong Rig Packages Offer </a>
                        </p>
                    </div>
                </div><!-- signin-left -->
            </div>
            <!-- Content area -->

            <!-- /content area -->
            <!-- Footer -->
            <div class="navbar navbar-expand-lg navbar-light border-bottom-0 border-top">
                <div class="text-center d-lg-none w-100">
                    <button type="button" class="navbar-toggler dropdown-toggle" data-toggle="collapse"
                            data-target="#navbar-footer">
                        <i class="icon-unfold mr-2"></i>
                        Footer
                    </button>
                </div>

                <div class="navbar-collapse collapse" id="navbar-footer">
						<span class="navbar-text">
							&copy; 2018 - 2022. <a href="tshongrig.com">Tshong Rig</a>
						</span>

                    <ul class="navbar-nav ml-lg-auto">
                        <li class="nav-item"><a href="https://www.tshongrig.com/contact-2/" class="navbar-nav-link"
                                                target="_blank"><i class="icon-lifebuoy mr-2"></i> Support</a></li>
                        <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/"
                                                class="navbar-nav-link" target="_blank"><i
                                class="icon-file-text2 mr-2"></i> Docs</a></li>
                        <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/"
                                                class="navbar-nav-link font-weight-semibold" target="_blank"><span
                                class="text-pink"><i
                                class="icon-cart2 mr-2"></i>  Purchase Tshong Rig Packages</span></a></li>
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
