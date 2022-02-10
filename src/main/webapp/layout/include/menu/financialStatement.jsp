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
            <a data-toggle="collapse" data-parent="#accordion" href="#Reports">Financial Statement </a>
        </h4>
    </div>

    <div id="Reports" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('7-VIEW')">
                            <a href="<c:url value='/accProfitAndLossReport' />"> Income &
                                Expenditure</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('8-VIEW')">
                            <a href="<c:url value='/accBalanceSheetReport' />"> Financial Position</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('9-VIEW')">
                            <a href="<c:url value='/accTrialBalance' />"> Trial Balance</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('10-VIEW')">
                            <a href="<c:url value='/accCashFlow' />"> Cash Flow</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
        </div>
    </div>
</div>