<%--
  Created by IntelliJ IDEA.
  User: Rudraprasad Pradhan
  Date: 31/07/19
  Time: 1:51 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Faliure Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="./images/favicon.ico" type="image/x-icon">
    <meta name="description" content="Visit The DataLeakDetection App Faliure Page">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <style>
        /* Add animation to "page content" */
        .animate-bottom {
            position: relative;
            -webkit-animation-name: animatebottom;
            -webkit-animation-duration: 1s;
            animation-name: animatebottom;
            animation-duration: 1s
        }

        @-webkit-keyframes animatebottom {
            from { bottom:-100px; opacity:0 }
            to { bottom:0px; opacity:1 }
        }

        @keyframes animatebottom {
            from{ bottom:-100px; opacity:0 }
            to{ bottom:0; opacity:1 }
        }

        #myDiv {
            margin-top: 20%;
            display: block;
            text-align: center;
        }
    </style>
</head>
<body style="background-color: darkolivegreen">

<div id="myDiv" class="animate-bottom">
    <h2 style="color: red">Faliure</h2>
    <h3 style="color: azure"><% out.print( request.getAttribute( "response_body" ) ); %></h3>
    <a href="<% out.print( request.getAttribute( "redirloc" ) ); %>" class="btn btn-danger">Back</a>
</div>

</body>
</html>
