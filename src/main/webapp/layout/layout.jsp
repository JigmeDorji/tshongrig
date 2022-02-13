<%--
  Created by IntelliJ IDEA.
  User: bikash rai
  Date: 21-Mar-16
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page session="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title><sitemesh:write property="title"/></title>
    <link href="<c:url value='/resources/images/logobcs.png'/>" rel="icon">
    <jsp:include page="include/css.jsp"/>

</head>

<body>
<jsp:include page="include/header.jsp"/>
<!-- Page content -->
<div class="page-content">
    <jsp:include page="include/menu.jsp"/>
    <!-- Main content -->
    <div class="content-wrapper">
        <!-- Inner content -->
        <div class="content-inner">
            <sitemesh:write property="body"/>
        </div>
        <!-- /inner content -->


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
							&copy; 2018 - 2022. <a href="tshongrig.com">Tshong Rig Private Limitted</a>
						</span>

                <ul class="navbar-nav ml-lg-auto">
                    <li class="nav-item"><a href="https://www.tshongrig.com/contact-2/" class="navbar-nav-link"
                                            target="_blank"><i class="icon-lifebuoy mr-2"></i> Support</a></li>
                    <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/" class="navbar-nav-link"
                                            target="_blank"><i class="icon-file-text2 mr-2"></i> Docs</a></li>
                    <li class="nav-item"><a href="http://www.tshongrig.com/price-packages-2/"
                                            class="navbar-nav-link font-weight-semibold" target="_blank"><span
                            class="text-success"><i class="icon-cart2 mr-2"></i> Purchase Tshong Rig Packages</span></a></li>
                </ul>
            </div>
        </div>
        <!-- /footer -->
    </div>

</div>
<!-- /page content -->


<%--<div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div><div class="d3-tip" style="position: absolute; top: 0px; display: none; pointer-events: none; box-sizing: border-box;"></div></body>--%>
<%--<body class="vertical  light">
<div class="wrapper">
    &lt;%&ndash;Load Header Files&ndash;%&gt;
        <jsp:include page="include/menu.jsp"/>
        <jsp:include page="include/header.jsp"/>
        <main role="main" class="main-content">

        </main>
</div>
<jsp:include page="include/js.jsp"/>
</body>--%>
</body>
<jsp:include page="include/js.jsp"/>
</html>
