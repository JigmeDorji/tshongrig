<%--
  Created by IntelliJ IDEA.
  User: Bikash Rai
  Date: 7/24/2021
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="panel panel-menu">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#Setting">Setting </a>
        </h4>
    </div>

    <div id="Setting" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <a href="<c:url value='/user'/>"> User Creation</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <a href="<c:url value='/financialYearSetup' />"> Financial Year Setup</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <a href="<c:url value='/companyCreation' />"> Company Setup</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('2-VIEW')">
                            <a href="<c:url value='/userAccessPermission' />"> Access Permission Setup</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
        </div>
    </div>
</div>