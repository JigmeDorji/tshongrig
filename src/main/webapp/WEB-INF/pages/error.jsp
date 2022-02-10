<%--
  Created by IntelliJ IDEA.
  User: bikash.rai
  Date: 05-May-16
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="page_title">
  <span class="title">Error Page</span> <span
        class="subtitle"></span>
</div>

<h1><span class="label label-warning">"${message}"</span></h1>

<c:forEach items="${exception.stackTrace}" var="element" varStatus="status">
  <c:if test="${status.first}">
    <h2><span class="label label-danger">"${exception}"</span></h2>

    <h4><span class="label label-danger">Class Name: <c:out value="${element.getClassName()}"></c:out></span></h4>
    <h4><span class="label label-danger">Method Name: <c:out value="${element.getMethodName()}"></c:out></span></h4>
    <h4><span class="label label-danger">Line Number: <c:out value="${element.getLineNumber()}"></c:out></span></h4>
    <h4><span class="label label-danger">File Name: <c:out value="${element.getFileName()}"></c:out></span></h4>
  </c:if>
  <c:if test="${!status.first}">
    <p>Class Name: <c:out value="${element.getClassName()}"></c:out></p>

    <p>Method Name: <c:out value="${element.getMethodName()}"></c:out></p>

    <p>Line Number: <c:out value="${element.getLineNumber()}"></c:out></p>

    <p>File Name: <c:out value="${element.getFileName()}"></c:out></p>
  </c:if>
  <hr>

</c:forEach>
