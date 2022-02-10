<%--
  Created by IntelliJ IDEA.
  User: Bikash Rai
  Date: 28-NoV-16
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url value="/logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="get" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<nav id="navHeader" class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <div class="form-group">
            <div class="col-md-2">
                <img style="height:50px;padding:0px 0px 0px 0px;" src="<c:url value='/resources/images/logobcs.png'/>">
            </div>

            <div class="col-md-10">
                <label style="color: #4995E0!important;margin-left: 50px ;margin-top:18px">(No company selected)</label>
            </div>
        </div>

        <div class=""></div>
    </div>

    <ul class="nav navbar-top-links navbar-right">
        <li>
            <div class="login-detail">
                <label style="color: #4995E0!important;">Welcome::</label>
                <i style="color: #4995E0!important;" class="fa fa-user-md fa-fw"></i>
                <label style="color: #4995E0!important;"> (${currentUser.loginId})</label>
            </div>
        </li>
        <li><a href="javascript:$('#logoutForm').submit();" style="color: #4995E0!important;"><i
                class="fa fa-power-off"></i> Logout</a>
        </li>
    </ul>
    <br/>
</nav>
