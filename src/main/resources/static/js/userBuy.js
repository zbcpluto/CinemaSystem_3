var userId = sessionStorage.getItem('id');
var ticketList;
var amountList = [];
var index;
var amount;
var isVip;
var refundRatio;
var refundAmount;
var withdrawnCouponIds;

$(document).ready(function () {
	
    getTicketList();

});

function getTicketList() {
    getRequest(
        '/ticket/get/' + userId,
        function (res) {
        	ticketList = res.content;
            renderTicketList();
        },
        function (error) {
            alert(error);
        });
}

// TODO:填空
function renderTicketList() {
	$('.ticket-on-list').empty();
    var ticketDomStr = '';
    
    if(ticketList.length == 0) {
    	ticketDomStr += "<div class='sign'>您还未在nju影院买过电影票，快去电影界面逛逛吧</div>";
    }
    else {
    	var idx = 0;
    	ticketList.forEach(function (ticket) {
        	ticketDomStr +=
                "<li class='ticket-item card'>" +
                "	<img class='ticket-img' src='" + (ticket.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
                "	<div class='ticket-info'>" +
                "		<div class='ticket-title primary-text'>" + ticket.movieName + " " + ticket.seats.length + "张" + "</div>" +
                "		<div class='ticket-date'>" + getDate(ticket.startTime, ticket.endTime) + "</div>" +
                "		<div class='ticket-location'>" + ticket.hallName + " -- " + getSeats(ticket.seats) + "</div>" +
                "		<div><hr/></div>" +
                "		<div class='ticket-coupon'>优惠券：" + ticket.couponDes + "</div>" +
                "		<div class='ticket-price'>" +
                "       	<div>总价：" + getPriceDes(idx) + "</div>" +
                "			<div id='ticket-label" + idx + "' class='label label-" + (ticket.state==1 ? "primary'>已完成" : (!ticket.state ? "danger'>未完成" : "default'>已失效")) + "</div>" +
                "		</div>" +
                "		<div class='ticket-time'>" +
                "			<div>购票时间：" + formatDateTime(new Date(ticket.time)) + "</div>";
        	if(ticket.state == 0) {
        		var remainTime = getRemainTime(idx);
        		if(remainTime < 0) {
        			postRequest(
    					"/ticket/cancel",
    					ticketList[idx].idList,
    					function (res) {
    						$("#ticket-label"+idx).empty();
    						$("#ticket-label"+idx).attr("class", "label label-default");
    					},
    		            function (error) {
    		                alert(error);
    		            });
        		}
        		else {
        			ticketDomStr +=
            			"<div class='buy-notice'>距离失效还有：" + getformRemainTime(remainTime) + "</div>" +
            			"<button type='button' class='btn btn-primary' onclick='repay(" + idx + ")'>付款</button>";
        		}
        	}
        	else if(ticket.state == 1) {
        		if(isPreBegin(idx)) {
        			ticketDomStr += "<button type='button' class='btn btn-danger' onclick='refund(" + idx + ")'><i class='icon-remove-sign'></i> 退票 </button>";
        		}
        	}
            ticketDomStr += "</div></div></li>";
            idx++;
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

function getPriceDes(idx) {
	var ticket = ticketList[idx];
	var total = parseFloat(ticket.singleFare * ticket.seats.length).toFixed(2);
	var res = "" + total;
	var couponDes = ticket.couponDes;
	if(couponDes != "无") {
		var i = ticket.couponDes.lastIndexOf("减");
		if(i != -1) {
			var discount = parseFloat(couponDes.substr(i+1, couponDes.length-1)).toFixed(2);
			total = parseFloat(total-discount).toFixed(2);
			res += " - " + discount + " = " + total;
		}
	}
	amountList[idx] = total;
	return res;
}

function getRemainTime(idx) {
	var ticket = ticketList[idx];
	var begin = new Date(ticket.time);
	var end = new Date();
	
	var span = 15*60*1000 - (end.getTime() - begin.getTime());
	return span;
}

function getformRemainTime(span) {
	var minute = Math.floor(span / (60 * 1000));
	var second = Math.floor((span % (60 * 1000)) / 1000);
	return minute + "分" + second + "秒"; 
}

function repay(idx) {
	if(isPreBegin(idx)) {
		amount = amountList[idx];
		$("#pay-amount").html("金额：" + amount);
		$("#buyModal").modal('show');
		index = idx;
	}
	else {
		alert("电影已开始，无法重新购买，正在为您刷新界面");
		ticketList = null;
		getTicketList();
	}
}

function refund(idx) {
	if(isPreBegin(idx)) {
		index = idx;
		checkVip(idx); //嵌套做法
	}
	else {
		alert("电影已开始，无法退票，正在为您刷新界面");
		ticketList = null;
		getTicketList();
	}
}

function isPreBegin(idx) {
	var ticket = ticketList[idx];
	var begin = new Date(ticket.startTime);
	var now = new Date();
	return now < begin;
}

function payConfirmClick() {
	if (validateForm()) {
        if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
        	var ticket = ticketList[index];
            postRequest(
                '/ticket/buy',
                {movieId: ticket.movieId, ticketId: ticket.idList, couponId: 0, total: amount, couponIdToget: []},
                function(res) {
                	if(res.success) {
                		alert("付款成功");
                		ticketList = null;
                		getTicketList();
                	}
                	else {
                		alert(res.message);
                	}
                },
                function(error) {
                    alert(error);
                }
            );
            $('#buyModal').modal('hide')
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}

// 检查该用户是否为会员
function checkVip(idx) {
	getRequest(
        '/vip/' + userId + '/get',
        function (res) {
            isVip = res.success;
            getRefundRatio(idx);
        },
        function (error) {
            alert(error);
        });
}

// 获得退票手续费的折算比
function getRefundRatio(idx) {
	var ticket = ticketList[idx];
	postRequest(
        '/refund/get/ratio',
        {isVip: (isVip ? 1:0), startTime: ticket.startTime},
        function (res) {
        	if(res.success) {
        		refundRatio = res.content;
            	$("#refund-ratio").html(refundRatio*100 + "%");
            	amount = amountList[idx];
            	var serviceFee = parseFloat(amount * refundRatio).toFixed(2);
            	refundAmount = parseFloat(amount - serviceFee).toFixed(2);
            	$("#refund-amount").html(amount + " - " + serviceFee + " = "  + refundAmount + "元");
            	getWithDrawCoupons(idx);
            	$("#refundModal").modal('show');
        	}
        	else {
        		alert(res.message);
        	}
        },
        function (error) {
            alert(error);
        });
}

// 获得退票收回的优惠券
function getWithDrawCoupons(idx) {
	var ticket = ticketList[idx];
	getRequest(
		'/activity/getByMovie/' + ticket.movieId,
		function(res) {
			if(res.success) {
				var activities = res.content;
				if(activities.length == 0) {
					withdrawnCouponIds = [];
				}
				else {
					var couponIdList = [];
					var coupons = [];
					for(let i = 0; i < activities.length; i++) {
						couponIdList.push(activities[i].coupon.id);
						coupons.push(activities[i].coupon);
					}
					withdrawnCouponIds = couponIdList;
					renderCoupons(coupons);
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

function renderCoupons(coupons) {
	$("#coupon-withdraw").empty();
	var couponDomStr = "";
	coupons.forEach(function(coupon) {
		couponDomStr += 
			"<div>" + coupon.name +"：满" + coupon.targetAmount + "减" + coupon.discountAmount + "</div>";
	});
	$("#coupon-withdraw").append(couponDomStr);
}

function refundConfirmClick() {
	var ticket = ticketList[index];
	$("#refundModal").modal('hide');
	postRequest(
		"/ticket/refund",
		{amount: refundAmount, ticketIds: ticket.idList, couponIds: withdrawnCouponIds, isVip: (isVip ? 1:0), userId: userId},
		function(res) {
			if(res.success) {
				alert("退票成功！");
				ticketList = null;
				getTicketList();
			} 
			else {
				alert(res.message);
			}
		},
		function (error) {
            alert(error);
        });
}