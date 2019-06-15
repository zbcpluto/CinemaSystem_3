$(document).ready(function () {

    $("#login-btn").click(function () {
        var formData = getLoginForm();
        if(validateLoginForm(formData)) {
            postRequest(
                '/logadmin',
                formData,
                function () {
                    alert("管理员账号分配成功")
                },
                function () {
                    alert("fail");
                }
            )
        }
    });
    $("#login-btn-2").click(function(){
       var formData = getLoginForm2();
        if(validateLoginForm(formData)) {
            postRequest(
                '/logadmin',
                formData,
                function () {
                    alert("售票员账号分配成功")
                },
                function () {
                    alert("fail");
                }
            )
        }
    });
    function getLoginForm(){
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val(),
            level:3
        };
    }
    function getLoginForm2(){
        return {
            username: $('#index-name-2').val(),
            password: $('#index-password-2').val(),
            level:2
        };
    }
    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }
});