$(document).ready(function () {
    getNavi();
    function getNavi(){
        getRequest(
            '/user/home/navi',
            function (res) {
                console.log(res);
            },
            function (error) {
                alert("error");
            }
        )
    }
});