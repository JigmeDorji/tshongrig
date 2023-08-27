<%--
  Created by IntelliJ IDEA.
  User: bikash.rai
  Date: 04-May-16
  Time: 1:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>TSHONGRIG -LOGIN</title>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <jsp:include page="include/css.jsp"/>
</head>
<body>
<sitemesh:write property="body"/>


<jsp:include page="include/js.jsp"/>


<%--<style>--%>

<%--    .wrapper {--%>
<%--        width: 200px;--%>
<%--        height: 60px;--%>
<%--        position: relative;--%>
<%--        z-index: 1;--%>

<%--    }--%>

<%--    .circle {--%>
<%--        width: 30px;--%>
<%--        height: 30px;--%>
<%--        position: absolute;--%>
<%--        border-radius: 50%;--%>
<%--        background-color:saddlebrown;--%>
<%--        left: 0%;--%>
<%--        transform-origin: 50%;--%>
<%--        animation: circle7124 .5s alternate infinite ease;--%>
<%--    }--%>

<%--    @keyframes circle7124 {--%>
<%--        0% {--%>
<%--            top: 60px;--%>
<%--            height: 5px;--%>
<%--            border-radius: 50px 50px 25px 25px;--%>
<%--            transform: scaleX(1.7);--%>
<%--        }--%>

<%--        40% {--%>
<%--            height: 20px;--%>
<%--            border-radius: 50%;--%>
<%--            transform: scaleX(1);--%>
<%--        }--%>

<%--        100% {--%>
<%--            top: 0%;--%>
<%--        }--%>
<%--    }--%>

<%--    .circle:nth-child(2) {--%>
<%--        left: 100%;--%>
<%--        animation-delay: .2s;--%>
<%--    }--%>

<%--    .circle:nth-child(3) {--%>
<%--        left: 200%;--%>
<%--        right: 0%;--%>
<%--        animation-delay: .3s;--%>
<%--    }--%>

<%--    .shadow {--%>
<%--        width: 20px;--%>
<%--        height: 4px;--%>
<%--        border-radius: 50%;--%>
<%--        background-color: rgba(0,0,0,0.9);--%>
<%--        position: absolute;--%>
<%--        top: 62px;--%>
<%--        transform-origin: 50%;--%>
<%--        z-index: -1;--%>
<%--        left: 0%;--%>
<%--        filter: blur(1px);--%>
<%--        animation: shadow046 .5s alternate infinite ease;--%>
<%--    }--%>

<%--    @keyframes shadow046 {--%>
<%--        0% {--%>
<%--            transform: scaleX(1.5);--%>
<%--        }--%>

<%--        40% {--%>
<%--            transform: scaleX(1);--%>
<%--            opacity: .7;--%>
<%--        }--%>

<%--        100% {--%>
<%--            transform: scaleX(.2);--%>
<%--            opacity: .4;--%>
<%--        }--%>
<%--    }--%>

<%--    .shadow:nth-child(4) {--%>
<%--        left: 100%;--%>
<%--        animation-delay: .2s--%>
<%--    }--%>

<%--    .shadow:nth-child(5) {--%>
<%--        left: 200%;--%>
<%--        right: 15%;--%>
<%--        animation-delay: .3s;--%>
<%--    }--%>




<%--</style>--%>


<style>
    .wrapper {
        width: 200px;
        height: 60px;
        position: relative;
        z-index: 1;

    }

    .circle {
        width: 20px;
        height: 20px;
        position: absolute;
        border-radius: 50%;
        background-color:saddlebrown;
        left: 15%;
        transform-origin: 50%;
        animation: circle7124 .5s alternate infinite ease;
    }

    @keyframes circle7124 {
        0% {
            top: 60px;
            height: 5px;
            border-radius: 50px 50px 25px 25px;
            transform: scaleX(1.7);
        }

        40% {
            height: 20px;
            border-radius: 50%;
            transform: scaleX(1);
        }

        100% {
            top: 0%;
        }
    }

    .circle:nth-child(2) {
        left: 45%;
        animation-delay: .2s;
    }

    .circle:nth-child(3) {
        left: auto;
        right: 15%;
        animation-delay: .3s;
    }

    .shadow {
        width: 20px;
        height: 4px;
        border-radius: 50%;
        background-color: rgba(0,0,0,0.9);
        position: absolute;
        top: 62px;
        transform-origin: 50%;
        z-index: -1;
        left: 15%;
        filter: blur(1px);
        animation: shadow046 .5s alternate infinite ease;
    }

    @keyframes shadow046 {
        0% {
            transform: scaleX(1.5);
        }

        40% {
            transform: scaleX(1);
            opacity: .7;
        }

        100% {
            transform: scaleX(.2);
            opacity: .4;
        }
    }

    .shadow:nth-child(4) {
        left: 45%;
        animation-delay: .2s
    }

    .shadow:nth-child(5) {
        left: auto;
        right: 15%;
        animation-delay: .3s;
    }




</style>


</body>

</html>
