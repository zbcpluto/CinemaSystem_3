$(document).ready(function() {
    getPromitions();
    getAllMovies();

    getActivities();

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            if(activity.movieList.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $("#activity-form-btn").click(function () {
       var form = {
           name: $("#activity-name-input").val(),
           description: $("#activity-description-input").val(),
           startTime: $("#activity-start-date-input").val(),
           endTime: $("#activity-end-date-input").val(),
           movieList: [...selectedMovieIds],
           couponForm: {
               description: $("#coupon-name-input").val(),
               name: $("#coupon-description-input").val(),
               targetAmount: $("#coupon-target-input").val(),
               discountAmount: $("#coupon-discount-input").val(),
               startTime: $("#activity-start-date-input").val(),
               endTime: $("#activity-end-date-input").val()
           }
       };
        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                    getPromitions();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }
    
    var selectedUserNames = new Set();
    var selectedUserIds = new Set();
    // 实现筛选金额的点击确认按钮
    $('#filter-money-btn').click(function(){
        $('#filter-user').empty();
        var list=[];
        var consumption = $('#give-coupon-filter-money').val();
        getRequest(
            '/coupon/get/user/'+consumption,
            function (res) {
                list = res.content;
                var selectDomStr = "<option value=\"-1\"> 所有人</option>";
                list.forEach(function(user){
                    selectDomStr+="<option value="+"\""+user.id+"\">"+user.username+"</option>";
                });
                $('#filter-user').append(selectDomStr);
             },
            function(error){
                alert(error);
            }
        );
    });

    $('#filter-user').change(function () {
        var id = $('#filter-user').val();
        var name = $('#filter-user').children('option:selected').text();
        if(id == -1){
            selectedUserIds.clear();
            selectedUserNames.clear();
        } else {
            selectedUserIds.add(id);
            selectedUserNames.add(name);
        }
        renderSelectedUsers();
    });
    //渲染选择的赠送优惠券的用户
    function renderSelectedUsers() {
        $('#selected-users').empty();
        var usersDomStr = "";
        selectedUserNames.forEach(function (userName) {
            usersDomStr += "<span class='label label-primary'>"+userName+"</span>";
        });
        $('#selected-users').append(usersDomStr);
    }

    function getPromitions() {
        var activityList = [];
        getRequest(
            '/activity/get',
            function (res) {
                activityList = res.content;
                console.log(activityList);
                activityList.forEach(function (item) {
                    $('#filter-coupon').append("<option value="+ item.coupon.id +">"+item.coupon.name+"</option>");
                });
                // getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    $('#give-coupon-form-btn').click(function () {
        var couponId = $('#filter-coupon').val();
        var id = [];
        var name = [];
        var users = [];
        for (let i of selectedUserIds){
            id.push(i);
        }
        for (let i of selectedUserNames){
            name.push(i);
        }
        for(let i = 0; i < id.length; i++){
            users.push({id: id[i], name:name[i], level: 1, consumption:null});
        }

        postRequest(
            "/coupon/give/"+couponId,
            users,
            function (res) {
                $('#giveCouponModal').modal('hide');
            },
            function (error) {
                alert(error)
            }
        );
    })
    function getGivenUsers(){

    }

});