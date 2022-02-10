<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/21/2022
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">RA Bill Detail</title>
<body>
<div class="page_title">
    <span class="title">BOQ</span>
    <span class="subtitle">RA Bill Detail</span>
</div>

<fieldset class="v-no-padding">
    <legend>RA Bill List</legend>
    <div class="form-group">
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" Id="raBillDetailGrid">
                <thead>
                <tr>
                    <th width="5%">Srl</th>
                    <th hidden></th>
                    <th hidden></th>
                    <th width="8%">RA Serial No</th>
                    <th width="10%">Work Order No</th>
                    <th width="20%">RA Bill No</th>
                    <th width="20%">Bill Date</th>
                    <th width="8%">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</fieldset>
</body>
</html>

