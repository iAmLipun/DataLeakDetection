/*
        @Author : Rudraprasad Pradhan
        @CreatedON : 21st JUL, 2019, 5:45:55 PM
        @FILEName :  usrlogin.js
 */

$(document).ready(function(){


    function fieldValidationVisStat(obj, flag){
        obj.parent().removeClass("has-success has-error has-feedback");
        obj.parent().children("div.msg").remove();
        var div = document.createElement("div");
        div.className = "msg";
        if (flag){
            div.innerText = "Proper value !!"; div.style.color = "lightgreen"; div.style.textAlign = "center";
            obj.parent().addClass("has-success has-feedback");
            (obj.parent()).append(div);
        }
        else{
            div.innerText = "Invalid value !!"; div.style.color = "red"; div.style.textAlign = "center";
            obj.parent().addClass("has-error has-feedback");
            (obj.parent()).append(div);
        }
    }

    $("form#log_form").on('submit', function(e) {
        e.preventDefault();
        var cnt = 0;
        if ((/^USR[0-9]+$/g).test($("input#uid").val())) {
            fieldValidationVisStat($("input#uid"), true);
            cnt++;
        } else {
            fieldValidationVisStat($("input#uid"), false);
        }
        if ((/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/g).test($("input#pass").val())) {
            fieldValidationVisStat($("input#pass"), true);
            cnt++;
        } else {
            fieldValidationVisStat($("input#pass"), false);
        }
        if (cnt === 2){
            $(this).parent().css("display","none");
            $("div#loader").css("display","block");
            $(this).unbind('submit').submit();
        }
    });

});