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
    <title>BCS-Registration</title>
</head>
<body class="light">

<!-- Page content -->
<div class="page-content">

    <!-- Main content -->
    <div class="content-wrapper">
        <!-- Inner content -->
        <div class="content-inner">
            <div class="d-md-flex flex-row-reverse">
                <div class="signin-right col-md-8">
                    <div class="card col-md-10">
                        <div class="card-body p-3">
                            <div class="responseMsg"></div>
                            <form id="registrationForm" action="<c:url value='/registration/'/>"
                                  class="form-horizontal registrationForm">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="">
                                    <h1 class="signin-title-primary">Enter your business firm detail here</h1>
                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="form-group-float-label animate required">Company Name</label>
                                            <input type="text" class="form-control field" id="companyName"
                                                   name="companyName" required placeholder="Company Name">
                                        </div>

                                        <div class="form-group form-group-float col-md-6">
                                            <label class="form-group-float-label animate required">Business Type</label>
                                            <form:select class="form-control resetfield field" name="businessType"
                                                         placeholder="Business Type"
                                                         path="businessTypeList"
                                                         id="businessType" tabindex="8" required="true">
                                                <form:option value="">-- Select Business Type --</form:option>
                                                <form:options items="${businessTypeList}" itemValue="valueInteger"
                                                              itemLabel="text"/>
                                            </form:select>
                                        </div>

                                    </div>
                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="form-group-float-label animate required">Contact
                                                Person</label>
                                            <input type="text" name="contactPerson" id="contactPerson"
                                                   class="form-control field" placeholder="Contact Person">

                                        </div>
                                        <div class="form-group form-group-float col-md-6">

                                            <label class="form-group-float-label anim required">Contact No.</label>
                                            <input type="text" name="mobileNo" class="form-control field"
                                                   placeholder="Contact No.">

                                        </div>

                                    </div>
                                    <div class="form-group row">
                                        <div class="form-group form-group-float col-md-6">
                                            <label class="form-group-float-label animate required">
                                                Address</label>
                                            <textarea type="text" class="form-control field" name="mailingAddress"
                                                      id="mailingAddress" required
                                                      placeholder="Address"></textarea>
                                        </div>

                                        <div class="form-group form-group-float col-md-6">
                                            <label class="form-group-float-label animate required">Email ID</label>
                                            <input type="text" name="email" id="email" required
                                                   class="form-control field" placeholder="Email ID">
                                        </div>
                                    </div>


                                    <button type="submit" class="btn btn-primary btn-block btn-signin mt-4"
                                            id="btnSubmit">Submit
                                    </button>
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
                <div class="signin-left col-md-4 text-center">
                    <div class="signin-box">
                        <%--                        <h2 class="slim-logo"><a href="index.html">slim<span>.</span></a></h2>--%>
                        <img  src="<c:url value='/resources/limitless/global_assets/images/logo.png'/>" class="img-fluid pb-2" alt="logo">
                        <h1>Start a 30-day free trial</h1>
                        <h5>Subscribe Tshong Rig ERP system today to power your business.</h5>
                        <p class="pt-5"><a href="<c:url value='/login'/>" class="btn btn-outline-secondary pd-x-25"><i
                                class="icon-arrow-left32"></i> Go back</a>
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
