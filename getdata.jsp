<%--
  Created by IntelliJ IDEA.
  User: Rudraprasad Pradhan
  Date: 31/07/19
  Time: 1:51 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Get Data Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="./images/favicon.ico" type="image/x-icon">
    <meta name="description" content="Visit The Data Leak Detection App Get Data Page">
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
</head>
<body style="background-color: darkolivegreen">

<div style="margin-top: 5%"  class="container">
    <br/>
    <br/>
    <h2 style="color: azure">GET DATA PAGE</h2>
    <br/>
    <br/>

    <table class="table table-bordered">
        <thead>
        <tr style="background-color: #ffe135">
            <th>USRID</th>
            <th>SUBJECT</th>
            <th>FILENAME</th>
            <th>SECKEY</th>
        </tr>
        </thead>
        <tbody>
            <%  ArrayList <String []> lst = (ArrayList <String []>)(request.getAttribute( "list" ));
                for (int i = 0 ; i < lst.size() ; i++) {
                if ( !(((lst.get(i))[3]).equals( "123456" )) ) { %>
                    <tr style="background-color: darkred">
                <% } else { %>
                    <tr>
                <% } %>
                <td style="color: azure"><% out.print(lst.get(i)[0]); %></td>
                <td style="color: azure"><% out.print(lst.get(i)[1]); %></td>
                <td style="color: azure"><% out.print(lst.get(i)[2]); %></td>
                <td style="color: azure"><% out.print(lst.get(i)[3]); %></td>
             </tr>
            <% } %>
        </tbody>
    </table>
    <br/>
    <h5 style="color: azure">Red highlighted rows signify data leak.</h5>
    <br/>
    <form method="get" action="./MainServlet" style="margin-top: 2%">
        <input name="req_origin" type="hidden" value="back" style="display:none;"/>

        <input name="uid" type="hidden" value="<% out.print( request.getParameter( "usrid" ) ); %>" style="display:none;"/>

        <button type="submit" class="btn btn-warning">Back</button>
    </form>
</div>

</body>
</html>