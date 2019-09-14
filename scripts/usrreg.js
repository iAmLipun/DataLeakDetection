/* 
        @Author : Rudraprasad Pradhan
        @CreatedON : 21st JUL, 2019, 5:45:55 PM
        @FILEName :  usrreg.js
 */

$(document).ready(function(){

       function fieldValidationVisStat(obj, flag){
           obj.parent().parent().removeClass("has-success has-error has-feedback");
           obj.parent().children("div.msg").remove();
           obj.parent().children("span.glyphicon").remove();
           var span = document.createElement("span");
           var div = document.createElement("div");
           div.className = "col-lg-12 msg";
           if (flag){
               span.className = "glyphicon glyphicon-ok form-control-feedback"; div.innerText = "Proper value !!"; div.style.color = "lightgreen"; div.style.textAlign = "center";
               obj.parent().parent().addClass("has-success has-feedback");
               (obj.parent()).append(span);
               (obj.parent()).append(div);
           }
           else{
               span.className = "glyphicon glyphicon-remove form-control-feedback"; div.innerText = "Invalid value !!"; div.style.color = "red"; div.style.textAlign = "center";
               obj.parent().parent().addClass("has-error has-feedback");
               (obj.parent()).append(span);
               (obj.parent()).append(div);
           }
       }
    
       $("form#reg_form").on('submit', function(e) {
           e.preventDefault();
           var cnt = 0;
           if ((/^(([A-Z,a-z]+\s[A-Z,a-z]+)|([A-Z,a-z]+))$/g).test($("input#uname").val())) {
               fieldValidationVisStat($("input#uname"), true);
               cnt++;
           } else {
               fieldValidationVisStat($("input#uname"), false);
           }
           if ((/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g).test($("input#ueml").val())) {
               fieldValidationVisStat($("input#ueml"), true);
               cnt++;
           } else {
               fieldValidationVisStat($("input#ueml"), false);
           }
           if ((/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/g).test($("input#pwd").val())) {
               fieldValidationVisStat($("input#pwd"), true);
               cnt++;
           } else {
               fieldValidationVisStat($("input#pwd"), false);
           }
           if (($("input#pwd").val() === $("input#pwd2").val()) && ($("input#pwd2").val() !== "")) {
               fieldValidationVisStat($("input#pwd2"), true);
               cnt++;
           } else {
               fieldValidationVisStat($("input#pwd2"), false);
           }
           if (cnt === 4) {
               $(this).css("display","none");
               $("div#loader").css("display","block");
               $(this).unbind('submit').submit();
           }
       });

});
