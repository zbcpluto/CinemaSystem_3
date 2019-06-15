var svList;
var movieId;
var lastIdx;

$(document).ready(function () {
    movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);

    getSchedule();

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
                		$('#none-hint').css("display", "");
                	}
                } else {
                    $('#none-hint').css("display", "");
                }
            },
            function (error) {
                alert(error);
            }
        );
    }

});

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