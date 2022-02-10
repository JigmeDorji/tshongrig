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
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-toggle"></i> <span>Setting</span></a>

                    <ul class="nav nav-group-sub" data-submenu-title="Layouts">
                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/user'/>" class="nav-link"> User Creation</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/financialYearSetup'/>" class="nav-link"> Financial Year Setup</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/companyCreation'/>" class="nav-link"> Company Setup</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasAuthority('2-VIEW')">
                            <li class="nav-item">
                                <a href="<c:url value='/userAccessPermission'/>" class="nav-link"> Access Permission Setup</a></li>
                        </sec:authorize>
                    </ul>
                </li>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-color-sampler"></i> <span>Human Resources</span></a>

                    <ul class="nav nav-group-sub" data-submenu-title="Themes">
                        <li class="nav-item"><a href="<c:url value='/employeeSetup'/>" class="nav-link"> Employee Details</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/employeeAdvance'/>" class="nav-link"> Employee Advance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/salarySheet'/>" class="nav-link"> Salary Sheet</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/salaryRemittance'/>" class="nav-link"> Salary Remittance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/statutoryRemittance'/>" class="nav-link"> Statutory Remittance</a>
                        </li>
                        <li class="nav-item"><a href="<c:url value='/otherRemittance'/>" class="nav-link"> Other Remittance</a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-stack"></i> <span>Accounting</span></a>

                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/ledger' />" class="nav-link"> Ledger</a></li>
                        <li class="nav-item"><a href="<c:url value='/voucherCreation' />" class="nav-link"> Voucher</a></li>
                        <li class="nav-item-divider"></li>
                        <li class="nav-item"><a href="<c:url value='/saleInvoiceGeneration' />" class="nav-link"> Invoice</a></li>
                        <li class="nav-item"><a href="<c:url value='/moneyReceipt' />" class="nav-link"> Money Receipt</a></li>
                    </ul>
                </li>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-bookmark4"></i> <span>BOQ</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/boqSetup' />" class="nav-link"> Upload BOQ</a></li>
                        <li class="nav-item"><a href="<c:url value='/boqDetail' />" class="nav-link"> BOQ Details</a></li>
                        <li class="nav-item-divider"></li>
                        <li class="nav-item"><a href="<c:url value='/raBillGeneration' />" class="nav-link"> Generate RA Bills</a></li>
                        <li class="nav-item"><a href="<c:url value='/raBillDetail' />" class="nav-link"> View RA Bills</a></li>
                    </ul>
                </li>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-package"></i> <span>Fixed Asset Management</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/assetSetup' />" class="nav-link"> Asset setup</a></li>
                        <li class="nav-item"><a href="<c:url value='/assetOpening' />" class="nav-link"> Opening</a></li>
                        <li class="nav-item-divider"></li>
                        <li class="nav-item"><a href="<c:url value='/assetBuying' />" class="nav-link"> Buy</a></li>
                        <li class="nav-item"><a href="<c:url value='/fixedAssetSale' />" class="nav-link"> Dispose</a></li>
                        <li class="nav-item"><a href="<c:url value='/viewAsset' />" class="nav-link"> View</a></li>
                        <li class="nav-item"><a href="<c:url value='/fixedAssetSchedule' />" class="nav-link"> Fixed Assets Schedule</a></li>
                    </ul>
                </li>
                <li class="nav-item nav-item-submenu">
                    <a href="#" class="nav-link"><i class="icon-list3"></i> <span>Inventory</span></a>
                    <ul class="nav nav-group-sub" data-submenu-title="Starter kit">
                        <li class="nav-item"><a href="<c:url value='/locationSetUp' />" class="nav-link"> Location Setup</a></li>
                        <li class="nav-item"><a href="<c:url value='/openingBalanceInventory' />" class="nav-link"> Opening Balance</a></li>
                        <li class="nav-item"><a href="<c:url value='/receivedItem' />" class="nav-link"> Purchase</a></li>
                        <li class="nav-item"><a href="<c:url value='/viewItem' />" class="nav-link"> View Item</a></li>
                        <li class="nav-item"><a href="<c:url value='/saleItem' />" class="nav-link"> Sale</a></li>
                        <li class="nav-item"><a href="<c:url value='/saleDetail' />" class="nav-link"> Sale Detail</a></li>
                        <li class="nav-item"><a href="<c:url value='/barcode' />" class="nav-link"> Barcode</a></li>
                        <li class="nav-item"><a href="<c:url value='/saleRecord' />" class="nav-link"> Daily Sale Report</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /main navigation -->

    </div>
    <!-- /sidebar content -->

</div>
<!-- /main sidebar -->




<%--<aside class="sidebar-left border-right bg-white shadow" id="leftSidebar" data-simplebar>
    <a href="#" class="btn collapseSidebar toggle-btn d-lg-none text-muted ml-2 mt-3" data-toggle="toggle">
        <i class="fe fe-x"><span class="sr-only"></span></i>
    </a>
    <nav class="vertnav navbar navbar-light">
        <!-- nav bar -->
        <div class="w-100 mb-4 d-flex">
            <img id="profile-img" width="45%"
                 height="auto" src="<c:url value='/resources/images/logobcs.png'/>"/>
        </div>
        <ul class="navbar-nav flex-fill w-100 mb-2">
            <li class="nav-item dropdown">
                <a href="/" data-toggle="collapse" aria-expanded="false" class="nav-link">
                    <i class="fe fe-home fe-16"></i>
                    <span class="ml-3 item-text">HOME</span><span class="sr-only">(current)</span>
                </a>
            </li>
        </ul>
        <ul class="navbar-nav flex-fill w-100 mb-2">
            <li class="nav-item dropdown">
                <a href="#ui-elements" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-settings fe-16"></i>
                    <span class="ml-3 item-text">Setting</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="ui-elements">
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('1-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/user'/>"><span class="ml-1 item-text">Financial Year Setup</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/financialYearSetup' />"><span
                                    class="ml-1 item-text">Financial Year Setup</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('20-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/companyCreation' />"><span
                                    class="ml-1 item-text">Company Setup</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('2-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/userAccessPermission' />"><span
                                    class="ml-1 item-text">Access Permission Setup</span></a>
                        </sec:authorize>
                    </li>
                </ul>
            </li>

            <li class="nav-item dropdown">
                <a href="#forms" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-users fe-16"></i>
                    <span class="ml-3 item-text">Human Resources</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="forms">
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/employeeSetup'/>"><span class="ml-1 item-text">Employee Details</span></a>
                        <a href="./form_elements.html"></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/employeeAdvance' />"><span class="ml-1 item-text">Employee Advance</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/salarySheet' />"><span class="ml-1 item-text">Salary Sheet</span></a></h4>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/salaryRemittance' />"><span
                                class="ml-1 item-text">Salary Remittance</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/statutoryRemittance' />"><span
                                class="ml-1 item-text">Statutory Remittance</span></a></h4>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/otherRemittance' />"><span class="ml-1 item-text">Other Remittance</span></a>
                    </li>
                </ul>
            </li>
            <li class="nav-item dropdown">
                <a href="#tables" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-grid fe-16"></i>
                    <span class="ml-3 item-text">Accounting</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="tables">
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('3-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/ledger' />"><span class="ml-1 item-text">Ledger</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('6-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/voucherCreation' />"><span
                                    class="ml-1 item-text">Voucher</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('5-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/saleInvoiceGeneration' />"><span
                                    class="ml-1 item-text">Invoice</span></a>
                        </sec:authorize>
                    </li>
                    <li class="nav-item">
                        <sec:authorize access="hasAuthority('4-VIEW')">
                            <a class="nav-link pl-3" href="<c:url value='/moneyReceipt' />"><span
                                    class="ml-1 item-text">Money Receipt</span></a>
                        </sec:authorize>
                    </li>
                </ul>
            </li>
            <li class="nav-item dropdown">
                <a href="#charts" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-pie-chart fe-16"></i>
                    <span class="ml-3 item-text">Fixed Asset Management</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="charts">
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/assetSetup' />"><span class="ml-1 item-text">Asset setup</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/assetOpening' />"><span class="ml-1 item-text">Opening</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/assetBuying' />"><span
                                class="ml-1 item-text">Buy</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/fixedAssetSale' />"><span class="ml-1 item-text">Dispose</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link pl-3" href="<c:url value='/fixedAssetSchedule' />"><span
                                class="ml-1 item-text">Fixed Assets Schedule</span></a>
                    </li>
                </ul>
            </li>
        </ul>
        <ul class="navbar-nav flex-fill w-100 mb-2">
            <li class="nav-item dropdown">
                <a href="#contact" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-book fe-16"></i>
                    <span class="ml-3 item-text">Inventory</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="contact">
                    <sec:authorize access="hasAuthority('12-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/locationSetUp' />"><span class="ml-1">Location Setup</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('12-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/openingBalanceInventory' />"><span class="ml-1">Opening Balance</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('14-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/receivedItem' />"><span
                                class="ml-1">Purchase</span></a>
                    </sec:authorize>

                    <sec:authorize access="hasAuthority('15-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/viewItem' />"><span class="ml-1">View Item</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('12-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/saleItem' />"><span class="ml-1">Sale</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('16-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/saleDetail' />"><span
                                class="ml-1">Sale Detail</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('19-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/barcode' />"><span class="ml-1">Barcode</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('17-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/saleRecord' />"><span class="ml-1">Daily Sale Report</span></a>
                    </sec:authorize>
                </ul>
            </li>
            <li class="nav-item dropdown">
                <a href="#profile" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-user fe-16"></i>
                    <span class="ml-3 item-text">Material</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="profile">
                    <sec:authorize access="hasAuthority('12-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/locationSetUp' />"><span class="ml-1"> Location Setup</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('14-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/openingRawMaterialPurchase' />"><span
                                class="ml-1">Opening</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('12-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/rawMaterialPurchase' />"><span class="ml-1">Purchase</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('15-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/viewItem' />"><span class="ml-1">View Item</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('17-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/stockIssue' />"><span
                                class="ml-1">Issue</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('17-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/issueDetail' />"><span
                                class="ml-1">Issue Detail</span></a>
                    </sec:authorize>
                </ul>
            </li>
            <li class="nav-item dropdown">
                <a href="#fileman" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                    <i class="fe fe-folder fe-16"></i>
                    <span class="ml-3 item-text">Financial Statement</span>
                </a>
                <ul class="collapse list-unstyled pl-4 w-100" id="fileman">
                    <sec:authorize access="hasAuthority('7-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/accProfitAndLossReport' />"><span class="ml-1">Income &
                            Expenditure</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('8-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/accBalanceSheetReport' />"><span class="ml-1">Financial Position</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('9-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/accTrialBalance' />"><span class="ml-1">Trial Balance</span></a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('7-VIEW')">
                        <a class="nav-link pl-3" href="<c:url value='/accCashFlow' />"><span
                                class="ml-1">Cash Flow</span></a>
                    </sec:authorize>
                </ul>
            </li>
        </ul>
    </nav>
</aside>
<main role="main" class="main-content">
    <div class="modal fade modal-shortcut modal-slide" tabindex="-1" role="dialog" aria-labelledby="defaultModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="defaultModalLabel">Shortcuts</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body px-5">
                    <div class="row align-items-center">
                        <div class="col-6 text-center">
                            <div class="squircle bg-success justify-content-center">
                                <i class="fe fe-cpu fe-32 align-self-center text-white"></i>

                            </div>
                            <p><a class="dropdown-item" href="<c:url value="/changePassword"/>">Change Password</a></p>
                        </div>
                        <div class="col-6 text-center">
                            <div class="squircle bg-primary justify-content-center">
                                <i class="fe fe-log-out fe-32 align-self-center text-white"></i>
                            </div>
                            <p><a class="dropdown-item" href="javascript:$('#logoutForm').submit();">Logout</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>--%>
<!-- main -->
