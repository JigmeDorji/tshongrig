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
            <a data-toggle="collapse" data-parent="#accordion" href="#Accounting">Accounting </a>
        </h4>
    </div>


    <div id="Accounting" class="panel-collapse collapse">
        <div class="panel-body">
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('3-VIEW')">
                            <a href="<c:url value='/ledger' />"> &nbsp;Ledger</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('6-VIEW')">
                            <a href="<c:url value='/voucherCreation' />"> Voucher</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>
            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('5-VIEW')">
                            <a href="<c:url value='/saleInvoiceGeneration' />">Invoice</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <sec:authorize access="hasAuthority('4-VIEW')">
                            <a href="<c:url value='/moneyReceipt' />"> Money Receipt</a>
                        </sec:authorize>
                    </h4>
                </div>
            </div>

            <div class="panel panel-menu-level-2">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#reports-accordion-level-2" href="#0402">
                            Auto Voucher</a>
                    </h4>
                </div>
                <div id="0402" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <sec:authorize access="hasAuthority('4-VIEW')">
                                <li><a href="<c:url value='/payment' />"> Payment</a>
                                </li>
                            </sec:authorize>

                            <sec:authorize access="hasAuthority('4-VIEW')">
                                <li><a href="<c:url value='/cashDepositWithdrawal' />">Cash
                                    Deposit / Withdrawal</a></li>
                            </sec:authorize>

                            <sec:authorize access="hasAuthority('4-VIEW')">
                                <li><a href="<c:url value='/receipt' />">Receipt</a>
                                </li>
                            </sec:authorize>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>