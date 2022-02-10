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
            <a data-toggle="collapse" data-parent="#accordion" href="#HumanResources">Human Resources </a>
        </h4>
    </div>


    <div id="HumanResources" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/employeeSetup'/>"> Employee Details</a>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/employeeAdvance' />"> Employee Advance</a>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/salarySheet' />"> Salary Sheet</a></h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/salaryRemittance' />"> Salary Remittance</a>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/statutoryRemittance' />"> Statutory Remittance</a></h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="<c:url value='/otherRemittance' />"> Other Remittance</a></div>
            </div>
        </div>
    </div>
</div>
