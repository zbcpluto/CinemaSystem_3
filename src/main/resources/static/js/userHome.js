$(document).ready(function () {
    getNavi();
    getMovieLikeNum();
    function getNavi(){
        getRequest(
            '/user/home/navi',
            function (res) {
                var movieList = res.content||[];
                var carouselDomStr = "";
                //跑马灯导航
                for (let i=0; i < 3 && i < movieList.length; i++){
                    if(i == 0){
                        carouselDomStr+=
                            "                   <div class=\"item active\">\n" +
                            "                        <a href="+ "/user/movieDetail?id="+movieList[i].id+">\n" +
                            "                        <img src=\""+movieList[i].posterUrl+"\" alt=\"target\" style = \"width:30%;height:430px\"  class=\"img-responsive center-block\">\n" +
                            "                        </a>\n" +
                            "                        <div class=\"carousel-caption\">\n" +
                            "                        </div>\n" +
                            "                    </div>";
                    }else{
                        carouselDomStr+=
                            "                   <div class=\"item\">\n" +
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
                var downNaviDomStr = "";
                for(let i=3; i < 7 && i<movieList.length; i++){
                    downNaviDomStr+=
                        "                    <li class=\"span4\" style=\"width: 300px;border:none;outline:none;margin-top: 20px\">\n" +
                        "                        <a href="+ "/user/movieDetail?id="+movieList[i].id+" \"style=\"margin: 1px 0 0 35px\">\n" +
                        "                            <img src=\""+movieList[i].posterUrl+"\" style=\"height:330px;width:230px\" alt=\"\">\n" +
                        "                            <h4 class=\"text-center\">"+movieList[i].name+"</h4>\n" +
                        "                        </a>\n" +
                        "                    </li>"
                }
                $('#downNavigate').append(downNaviDomStr);
            },
            function (error) {
                alert("error");
            }
        )
    }
    
    function getMovieLikeNum(){
        getRequest(
           '/user/home/like',
            function (res) {
                var movieList = res.content;
                console.log(movieList)
                var movieLikeDomStr = "";
                movieList.forEach(function(item) {
                    movieLikeDomStr +=
                        "                <div class=\"statistic-item\">\n" +
                        "                    <span><a href="+"/user/movieDetail?id="+item.id+">"+item.name+"</a></span>\n" +
                        "                    <span class=\"error-text\">"+item.likeNum+"</span>\n" +
                        "                </div>";
                });
                $('.top-expectation-list').append(movieLikeDomStr);
            },
            function (error) {
                alert(error);
            }
        )
    }
});