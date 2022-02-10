<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/13/2022
  Time: 10:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<title class="title">BOQ Detail</title>
<body>
<div class="page_title">
    <span class="title">Accounting</span>
    <span class="subtitle">BOQ Detail</span>
</div>

<fieldset class="v-no-padding">
    <legend>BOQ List</legend>
    <div class="form-group">
        <div class="col-md-12">
            <table class="table table-bordered table-striped editable-grid" Id="boqDetailGrid">
                <thead>
                <tr>
                    <th width="5%">Srl</th>
                    <th hidden></th>
                    <th width="15%">Work Order No</th>
                    <th width="15%">Name Of Work</th>
                    <th width="15%">Employing Agency</th>
                    <th width="8%">Work Order Date</th>
                    <th width="8%">Work Start Date</th>
                    <th width="8%">Completion Date</th>
                    <th width="5%">Action</th>
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

