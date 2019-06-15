$(document).ready(function () {

    $("#login-btn").click(function () {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }
        var level = 1;
        getRequest(
          '/get/level/'+formData.username,
          function (resp) {
              level = resp.content;
              console.log(level);
              setTimeout(s,1000);
              postRequest(
                  '/login',
                  formData,
                  function (res) {
                      if (res.success) {
                          sessionStorage.setItem('username', formData.username);
                          sessionStorage.setItem('id', res.content.id);
                          console.log(formData.username);
                          if (level==4) {
                              sessionStorage.setItem('role', 'manager');
                              window.location.href = "/manager/account"
                          }
                          else if (level==3) {
                              sessionStorage.setItem('role', 'admin');
                              window.location.href = "/admin/movie/manage"
                          }
                          else if(level==2){
                              sessionStorage.setItem('role','seller');
                              window.location.href = "/seller/movie"
                          } else{
                              sessionStorage.setItem('role', 'user');
                              window.location.href = "/user/home"
                          }
                      } else {
                          alert(res.message);
                      }
                  },
                  function (error) {
                      alert(error);
                  });
          },
          function () {

          }
        );

    });

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
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
    function s(){
        console.log("1s");
    }

});