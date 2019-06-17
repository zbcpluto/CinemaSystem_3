var svList;
var movieId;
var lastIdx;

$(document).ready(function () {
    movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);

    getSchedule();
    getActivities();
});

function getSchedule() {
    getRequest(
        '/schedule/search/audience?movieId=' + movieId,
        function (res) {
            if (res.success) {
            	svList = res.content;
            	if(svList.length) {
            		$('#schedule').css("display", "");
            		repaintSvList(0);
            	}
            	else {
            		$('#schedule-head').text("近期暂无排片");
            	}
            } else {
            	alert(res.message);
            }
        },
        function (error) {
            alert(error);
        }
    );
}

function getActivities() {
	getRequest(
		'/activity/getByMovie/' + movieId,
		function(res) {
			if(res.success) {
				var activities = res.content;
				if(activities.length) {
					$('#activity').css("display", "");
					renderActivities(activities);
	        	}
	        	else {
	        		$('#schedule-head').text("近期暂无活动");
	        	}
			}
			else {
				alert(res.message);
			}
		},
		function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function renderActivities(activities) {
	$("#activity").empty();
    var activitiesDomStr = "";

    activities.forEach(function (activity) {
        activitiesDomStr +=
            "<div class='activity-container'>" +
            "    <div class='activity-card card'>" +
            "       <div class='activity-line'>" +
            "           <span class='title'>"+activity.name+"</span>" +
            "           <span class='gray-text'>"+activity.description+"</span>" +
            "       </div>" +
            "       <div class='activity-line'>" +
            "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
            "       </div>" +
            "    </div>" +
            "    <div class='activity-coupon primary-bg'>" +
            "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
            "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
            "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
            "    </div>" +
            "</div>";
    });
    $("#activity").append(activitiesDomStr);
}

function repaintSvList() {
	var dateContent = "";
	for (var i = 0; i < svList.length; i++) {
		var date = svList[i].date.substring(5, 7) + "月" + svList[i].date.substring(8, 10) + "日";
		dateContent += "<li role='presentation' id='schedule-date" + i +"'><a href='#' onclick='repaintScheduleBody(" + i + ")'>" + date + "</a></li>";
	}
	$('#schedule-date').html(dateContent);
	
    repaintScheduleBody(0);
}

function repaintScheduleBody(idx) {
	$('#schedule-date'+lastIdx).removeClass("active");
	$('#schedule-date'+idx).addClass("active");
    var scheduleItems = svList[idx].scheduleItemList;

    var bodyContent = "";
    scheduleItems.forEach(function (item) {
    	bodyContent += 
    		"<tr>" + 
    		"<td>" + item.startTime.substring(11, 16) + "</td>" +
    		"<td>" + item.endTime.substring(11, 16) + "</td>" +
    		"<td>" + item.hallName + "</td>" +
    		"<td><b>" + item.fare.toFixed(2) + "</b></td>" +
    		"<td><a class='btn btn-primary' href='/user/movieDetail/buy?id="+movieId+"&scheduleId="+item.id+"' role='button'>选座购票</a></td>" +
    		"</tr>";
    });

    $('#schedule-body').html(bodyContent);
    lastIdx = idx;
}