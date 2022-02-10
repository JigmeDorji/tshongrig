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
            <a data-toggle="collapse" data-parent="#accordion" href="#Inventory">Inventory </a>
        </h4>
    </div>


    <div id="Inventory" class="panel-collapse collapse">
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
                        <%--                        <sec:authorize access="hasAuthority('14-VIEW')">--%>
                        <a href="<c:url value='/openingBalanceInventory' />"> Opening Balance</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('14-VIEW')">
                            <a href="<c:url value='/receivedItem' />"> Purchase</a>
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
                        <sec:authorize access="hasAuthority('16-VIEW')">
                            <a href="<c:url value='/saleItem' />"> Sale</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('16-VIEW')">
                            <a href="<c:url value='/saleDetail' />"> Sale Detail</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>

<%--            <div class="panel panel-menu-level-2">--%>
<%--                <div class="panel-heading">--%>
<%--                    <h4 class="panel-title">--%>
<%--                        <sec:authorize access="hasAuthority('18-VIEW')">--%>
<%--                            <a href="<c:url value='/returnItem' />"> Return/Exchange</a>--%>
<%--                        </sec:authorize>--%>
<%--                    </h4>--%>
<%--                </div>--%>
<%--            </div>--%>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">

                        <sec:authorize access="hasAuthority('19-VIEW')">
                            <a href="<c:url value='/barcode' />"> Barcode</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('17-VIEW')">
                            <a href="<c:url value='/saleRecord' />"> Daily Sale Report</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>


        </div>
    </div>
</div>