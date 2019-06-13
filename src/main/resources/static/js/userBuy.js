$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
    	$('.ticket-on-list').empty();
        var ticketDomStr = '';
        
        if(list.length == 0) {
        	ticketDomStr += "<div class='sign'>您还未在nju影院买过电影票，快去电影界面逛逛吧</div>";
        }
        else {
        	list.forEach(function (ticket) {
        		alert(ticket.posterUrl);
            	ticketDomStr +=
                    "<li class='ticket-item card'>" +
                    "<img class='ticket-img' src='" + (ticket.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
                    "<div class='ticket-info'>" +
                    "<div class='ticket-title'>" +
                    "<span class='primary-text'>" + ticket.movieName + " " + ticket.seats.length + "张</span>" +
                    "</div>" +
                    "<div class='ticket-date'>" + getDate(ticket.startTime, ticket.endTime) + "</div>" +
                    "<div class='ticket-location'>" + ticket.hallName + " -- " + getSeats(ticket.seats) + "</div>" +
                    "<div class='ticket-footer'>" +
                    "<span class='ticket-price'>总价：" + ticket.seats.length * ticket.singlePrice + "</span>" +
                    "<span class='label label-" + (ticket.state==1 ? "primary'>已完成" : (!ticket.state ? "danger'>未完成" : "default'>已失效")) + "</span>";
            	if(ticket.state == 1) {
            		ticketDomStr +=
            			"<span class='refund'>" +
            			"<button type='button' class='btn btn-danger' data-backdrop='static' data-toggle='modal' data-target='#movieModal'><i class='icon-remove-sign'></i> 退票 </button>" +
            			"</span>";
            	}
                ticketDomStr += "</div></div></li>";
            });
        }
        $('.ticket-on-list').append(ticketDomStr);
    }
    
    function getDate(time1, time2) {
    	var date1 = new Date(time1);
    	var res = formatDate(date1);
    	res += " " + formatTime(date1);
    	
    	var date2 = new Date(time2);
    	res += " ~ " + formatTime(date2);
    	return res;
	}
    
    function getSeats(list) {
    	var res = "";
    	list.forEach(function (seat) {
    		res += " ";
    		res += (seat.rowIndex+1) + "排"
    		res += (seat.columnIndex+1) + "座";
    	});
    	return res;
    }

});