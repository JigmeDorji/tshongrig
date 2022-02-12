<%--
  Created by IntelliJ IDEA.
  User: Bikash Rai
  Date: 28/11/2016
  Time: 10:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Main sidebar -->
<div class="sidebar sidebar-light sidebar-main sidebar-expand-lg">
    <!-- Sidebar content -->
    <div class="sidebar-content">

        <!-- Header -->
        <div class="sidebar-section sidebar-header bg-indigo" style="background-color: #4085a8 !important;">
            <div class="sidebar-section-body d-flex align-items-center justify-content-center pb-0">
                <h6 class="sidebar-resize-hide flex-1 mb-0 text-white"><strong>Dashboard</strong></h6>
                <div>
                    <button type="button"
                            class="btn btn-outline-light text-body border-transparent btn-icon rounded-pill btn-sm sidebar-control sidebar-main-resize d-none d-lg-inline-flex">
                        <i class="icon-transmission"></i>
                    </button>

                    <button type="button"
                            class="btn btn-outline-light text-body border-transparent btn-icon rounded-pill btn-sm sidebar-mobile-main-toggle d-lg-none">
                        <i class="icon-cross2"></i>
                    </button>
                </div>
            </div>
        </div>
        <!-- /header -->


        <!-- Main navigation -->
        <div class="sidebar-section">
            <ul class="nav nav-sidebar nav-sidebar-bordered" data-nav-type="accordion">
                <!-- Main -->
                <%--Setting--%>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class=" icon-gear"></i> <span>Setting</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Layouts">
                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/user'/>" class="nav-link"> User Creation</a>
                            </li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/financialYearSetup'/>" class="nav-link"> Financial Year
                                    Setup</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/companyCreation'/>" class="nav-link"> Company Setup</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('2-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/userAccessPermission'/>" class="nav-link"> Access Permission
                                    Setup</a></li>
                        </sec:authorize>
                    </ul>
                </li>

                <%--Human Resource--%>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-users4"></i> <span>Human Resources</span></a>

                    <ul class="nav nav-group-sub" data-submenu-title="Themes">
                        <li class="nav-item"><a href="<c:url value='/employeeSetup'/>" class="nav-link"> Employee
                            Details</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/employeeAdvance'/>" class="nav-link"> Employee
                            Advance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/salarySheet'/>" class="nav-link"> Salary Sheet</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/salaryRemittance'/>" class="nav-link"> Salary
                            Remittance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/statutoryRemittance'/>" class="nav-link"> Statutory
                            Remittance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/otherRemittance'/>" class="nav-link"> Other
                            Remittance</a>
                        </li>
                    </ul>
                </li>

                <%--Accounting--%>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-stack"></i> <span>Accounting</span></a>

                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/ledger' />" class="nav-link"> Ledger</a></li>
                        <li class="nav-item"><a href="<c:url value='/voucherCreation' />" class="nav-link"> Voucher</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/saleInvoiceGeneration' />" class="nav-link">
                            Invoice</a></li>
                        <li class="nav-item"><a href="<c:url value='/moneyReceipt' />" class="nav-link"> Money
                            Receipt</a></li>
                        <li class="nav-item nav-item-submenu">
                            <a href="#" class="nav-link">Voucher</a>
                            <ul class="nav nav-group-sub">
                                <li class="nav-item">
                                    <a href="<c:url value='/payment' />" class="nav-link">Payment</a>
                                </li>
                                <li class="nav-item"><a href="<c:url value='/cashDepositWithdrawal' />"
                                                        class="nav-link">Cash
                                    Deposit / Withdrawal</a></li>
                                <li class="nav-item"><a href="<c:url value='/receipt' />" class="nav-link">Receipt</a>
                                </li>
                                <li class="nav-item"><a href="<c:url value='/bankTransfer' />" class="nav-link">Bank
                                    Transfer</a></li>
                                <li class="nav-item"><a href="<c:url value='/adjustment' />"
                                                        class="nav-link">Adjustment</a></li>
                                <li class="nav-item"><a href="<c:url value='/payable' />" class="nav-link">Payable
                                    Booking</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <%--Fixed Asset--%>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-package"></i> <span>Fixed Asset Management</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/assetSetup' />" class="nav-link"> Asset setup</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/assetOpening' />" class="nav-link"> Opening</a>
                        </li>

                        <li class="nav-item"><a href="<c:url value='/assetBuying' />" class="nav-link"> Buy</a></li>
                        <li class="nav-item"><a href="<c:url value='/fixedAssetSale' />" class="nav-link"> Dispose</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/viewAsset' />" class="nav-link"> View</a></li>
                        <li class="nav-item"><a href="<c:url value='/fixedAssetSchedule' />" class="nav-link"> Fixed
                            Assets Schedule</a></li>
                    </ul>
                </li>

                <c:if test="${currentUser.businessType ==1}">
                    <%--Inventory--%>
                    <li class="nav-item nav-item-submenu">
                        <a href="#" class="nav-link"><i class="icon-list3"></i> <span>Inventory</span></a>
                        <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                            <li class="nav-item"><a href="<c:url value='/locationSetUp' />" class="nav-link"> Location
                                Setup</a></li>
                            <li class="nav-item"><a href="<c:url value='/openingBalanceInventory' />" class="nav-link">
                                Opening Balance</a></li>
                            <li class="nav-item"><a href="<c:url value='/receivedItem' />" class="nav-link">
                                Purchase</a>
                            </li>
                            <li class="nav-item"><a href="<c:url value='/viewItem' />" class="nav-link"> View Item</a>
                            </li>
                            <li class="nav-item"><a href="<c:url value='/saleItem' />" class="nav-link"> Sale</a></li>
                            <li class="nav-item"><a href="<c:url value='/saleDetail' />" class="nav-link"> Sale
                                Detail</a>
                            </li>
                            <li class="nav-item"><a href="<c:url value='/barcode' />" class="nav-link"> Barcode</a></li>
                            <li class="nav-item"><a href="<c:url value='/saleRecord' />" class="nav-link"> Daily Sale
                                Report</a></li>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${currentUser.businessType ==4}">
                    <%--Material--%>
                    <li class="nav-item nav-item-submenu">
                        <a href="#" class="nav-link"><i class="icon icon-hammer-wrench"></i> <span>Material</span></a>
                        <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                            <li class="nav-item">
                                <sec:authorize access="hasAuthority('12-VIEW')">
                                    <a href="<c:url value='/locationSetUp' />" class="nav-link"> Location Setup</a>
                                </sec:authorize>
                            </li>
                            <li class="nav-item">

                                <sec:authorize access="hasAuthority('14-VIEW')">
                                    <a class="nav-link" href="<c:url value='/openingRawMaterialPurchase' />">
                                        Opening</a>
                                </sec:authorize>
                            </li>
                            <li class="nav-item">
                                <sec:authorize access="hasAuthority('14-VIEW')">
                                    <a class="nav-link" href="<c:url value='/rawMaterialPurchase' />"> Purchase</a>
                                </sec:authorize>
                            </li>
                            <li class="nav-item">
                                <sec:authorize access="hasAuthority('15-VIEW')">
                                    <a class="nav-link" href="<c:url value='/viewItem' />"> View Item</a>
                                </sec:authorize>
                            </li>
                            <li class="nav-item">
                                <sec:authorize access="hasAuthority('17-VIEW')">
                                    <a class="nav-link" href="<c:url value='/stockIssue' />"> Issue</a>
                                </sec:authorize>
                            </li>
                            <li class="nav-item">
                                <sec:authorize access="hasAuthority('16-VIEW')">
                                    <a class="nav-link" href="<c:url value='/issueDetail' />"> Issue Detail</a>
                                </sec:authorize>
                            </li>
                        </ul>
                    </li>

                    <%--BOQ--%>
                    <li class="nav-item nav-item-submenu">
                        <a href="#" class="nav-link"><i class="icon-bookmark4"></i> <span>BOQ</span></a>
                        <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                            <li class="nav-item"><a href="<c:url value='/boqSetup' />" class="nav-link"> Upload BOQ</a>
                            </li>
                            <li class="nav-item"><a href="<c:url value='/boqDetail' />" class="nav-link"> BOQ
                                Details</a>
                            </li>
                            <li class="nav-item"><a href="<c:url value='/raBillGeneration' />" class="nav-link">
                                Generate RA
                                Bills</a></li>
                            <li class="nav-item"><a href="<c:url value='/raBillDetail' />" class="nav-link"> View RA
                                Bills</a></li>
                        </ul>
                    </li>
                </c:if>

                <%--Financial Statement--%>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon icon-cash3"></i> <span>Financial Statement</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item">
                            <sec:authorize access="hasAuthority('7-VIEW')">
                                <a class="nav-link" href="<c:url value='/accProfitAndLossReport' />"> Income &
                                    Expenditure</a>
                            </sec:authorize>
                        </li>
                        <li class="nav-item">

                            <sec:authorize access="hasAuthority('8-VIEW')">
                                <a class="nav-link" href="<c:url value='/accBalanceSheetReport' />"> Financial
                                    Position</a>
                            </sec:authorize>
                        </li>
                        <li class="nav-item">
                            <sec:authorize access="hasAuthority('9-VIEW')">
                                <a class="nav-link" href="<c:url value='/accTrialBalance' />"> Trial Balance</a>
                            </sec:authorize>
                        </li>
                        <li class="nav-item">
                            <sec:authorize access="hasAuthority('10-VIEW')">
                                <a class="nav-link" href="<c:url value='/accCashFlow' />"> Cash Flow</a>
                            </sec:authorize>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /main navigation -->

    </div>
    <!-- /sidebar content -->

</div>
<!-- /main sidebar -->


