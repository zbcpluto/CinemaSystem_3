$(document).ready(function () {
    getNavi();
    function getNavi(){
        getRequest(
            '/user/home/navi',
            function (res) {
                console.log(res);
                var movieList = res.content||[];
                var carouselDomStr = "";
                //跑马灯导航
                for (let i=0; i < 3 && i < movieList.length; i++){
                    if(i == 0){
                        carouselDomStr+="<div class=\"item active\">\n" +
                            "                        <a href="+ "/user/movieDetail?id="+movieList[i].id+">\n" +
                            "                        <img src=\""+movieList[i].posterUrl+"\" alt=\"target\" style = \"width:30%;height:430px\"  class=\"img-responsive center-block\">\n" +
                            "                        </a>\n" +
                            "                        <div class=\"carousel-caption\">\n" +
                            "                        </div>\n" +
                            "                    </div>";
                    }else{
                        carouselDomStr+="<div class=\"item\">\n" +
                            "                        <a href="+ "/user/movieDetail?id="+movieList[i].id+">\n" +
                            "                        <img src=\""+movieList[i].posterUrl+"\" alt=\"target\" style = \"width:30%;height:430px\"  class=\"img-responsive center-block\">\n" +
                            "                        </a>\n" +
                            "                        <div class=\"carousel-caption\">\n" +
                            "                        </div>\n" +
                            "                    </div>";
                    }
                }
                $('.carousel-inner').append(carouselDomStr);
                //下面的导航栏
            },
            function (error) {
                alert("error");
            }
        )
    }
});