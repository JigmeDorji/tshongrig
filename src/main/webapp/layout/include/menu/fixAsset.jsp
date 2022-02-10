<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 9/16/2021
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="panel panel-menu">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#fixedAsset">Fixed Asset Management</a>
        </h4>
    </div>

    <div id="fixedAsset" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <%--                        <sec:authorize access="hasAuthority('12-VIEW')">--%>
                        <a href="<c:url value='/assetSetup' />"> Asset setup</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <%--                        <sec:authorize access="hasAuthority('12-VIEW')">--%>
                        <a href="<c:url value='/assetOpening' />"> Opening</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <%--                        <sec:authorize access="hasAuthority('12-VIEW')">--%>
                        <a href="<c:url value='/assetBuying' />"> Buy</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <%--                        <sec:authorize access="hasAuthority('14-VIEW')">--%>
                        <a href="<c:url value='/fixedAssetSale'/>"> Dispose</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <%--                        <sec:authorize access="hasAuthority('14-VIEW')">--%>
                        <a href="<c:url value='/fixedAssetSchedule' />"> Fixed Assets Schedule</a>
                        <%--                        </sec:authorize>--%>
                    </h4>
                </div>
            </div>
        </div>
    </div>
</div>
