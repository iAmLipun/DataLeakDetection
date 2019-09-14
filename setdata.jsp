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
    <title>Set Data Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="./images/favicon.ico" type="image/x-icon">
    <meta name="description" content="Visit The Data Leak Detection App Set Data Page">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $("button#submit").click(function(){
                    $("div#sdat_form").css("display","none");
                    $("div#loader").css("display","block");
                    var dat = {
                        "req_origin" : $("input#req_origin").val(),
                        "uid" : $("input#uid").val(),
                        "usub" : $("input#usub").val(),
                        "ufilename" : $("input#ufilename").val(),
                        "useckey" : $("input#useckey").val()
                    };
                    $.post("MainServlet", dat, function(result){
                        $("input#usub").val("");
                        $("input#ufilename").val("");
                        $("input#useckey").val("");
                        $("div#loader").css("display","none");
                        $("div#sdat_form").css("display","block");
                        alert(result);
                    });
            });
        });
    </script>
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

<div id="sdat_form"  style="margin-top: 8%"  class="container">
    <br/>
    <br/>
    <h2 style="color: azure">SET DATA PAGE</h2>
    <br/>
    <br/>

    <input id="req_origin" type="hidden" value="usrdata" style="display:none;"/>

    <input id="uid" type="hidden" value="<% out.print( request.getParameter( "usrid" ) ); %>" style="display:none;"/>

    <div class="form-group">
        <label for="usub" style="color: azure">Subject : </label>
        <input type="text" class="form-control" id="usub" placeholder="Enter subject ... ">
    </div>

    <div class="form-group">
        <label for="ufilename" style="color: azure">Filename : </label>
        <input type="text" class="form-control" id="ufilename" placeholder="Enter filename ... ">
    </div>

    <div class="form-group">
        <label for="useckey" style="color: azure">Secretkey : </label>
        <input type="password" maxlength="6" class="form-control" id="useckey" placeholder="Enter secretkey ... ">
    </div>

    <button id="submit" type="submit" class="btn btn-warning">Submit</button>

    <form method="get" action="./MainServlet" style="margin-top: 2%">
        <input name="req_origin" type="hidden" value="back" style="display:none;"/>

        <input name="uid" type="hidden" value="<% out.print( request.getParameter( "usrid" ) ); %>" style="display:none;"/>

        <button type="submit" class="btn btn-warning">Back</button>
    </form>
</div>

<div id="loader"></div>

</body>
</html>
