<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/17/2021
  Time: 12:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="panel panel-menu">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#Construction"> Material</a>
        </h4>
    </div>


    <div id="Construction" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('12-VIEW')">
                            <a href="<c:url value='/locationSetUp' />"> Location Setup</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>


            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('14-VIEW')">
                            <a href="<c:url value='/openingRawMaterialPurchase' />"> Opening</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('14-VIEW')">
                            <a href="<c:url value='/rawMaterialPurchase' />"> Purchase</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('15-VIEW')">
                            <a href="<c:url value='/viewItem' />"> View Item</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('17-VIEW')">
                            <a href="<c:url value='/stockIssue' />"> Issue</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('16-VIEW')">
                            <a href="<c:url value='/issueDetail' />"> Issue Detail</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
        </div>
    </div>
</div>
