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
    <title>Data Leakage Detection</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="./images/favicon.ico" type="image/x-icon">
    <meta name="description" content="Visit The Data Leak Detection App Logon Home Page">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <style>
        /* Center the loader */
        #loader {
            position: absolute;
            left: 50%;
            top: 50%;
            z-index: 1;
            width: 150px;
            height: 150px;
            margin: -75px 0 0 -75px;
            border: 16px solid #f3f3f3;
            border-radius: 50%;
            border-top: 16px solid  #ffe135;
            width: 120px;
            height: 120px;
            -webkit-animation: spin 2s linear infinite;
            animation: spin 2s linear infinite;
            display: none;
        }

        @-webkit-keyframes spin {
            0% { -webkit-transform: rotate(0deg); }
            100% { -webkit-transform: rotate(360deg); }
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body style="background-color: darkolivegreen">

<form style="margin-top: 15%; margin-left: 20%" action="./MainServlet" method="get" class="container form-horizontal">
    <br/>
    <br/>
    <h2 style="color: azure">DATA LEAKAGE DETECTION</h2>
    <br/>
    <br/>

    <input name="req_origin" type="hidden" value="" style="display:none;"/>

    <input name="usrid" type="hidden" value="<% out.print( request.getParameter( "uid" ) ); %>" style="display:none;"/>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button onclick="setOrigin('setdata')" type="submit" class="btn btn-warning">Set Data</button>
            <button onclick="setOrigin('getdata')" type="submit" class="btn btn-warning" style="margin-left: 2%">Get Data</button>
            <a href="<% out.print( request.getAttribute( "redirloc" ) ); %>" class="btn btn-warning" style="margin-left: 2%">Logout</a>
        </div>
    </div>

</form>

<div id="loader"></div>

<script>
    function setOrigin (val){ (document.getElementsByName("req_origin"))[0].value = val; }
</script>

</body>
</html>