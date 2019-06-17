var userId = sessionStorage.getItem('id');
var movieId;
var scheduleId;
var selectedSeats = []
var order = {ticketId: [], couponId: 0};
var activities;
var coupons = [];
var vipCard;
var vipInfo;
var isVIP = false;
var useVIP = true;

var fare;
let allcoupons = [];
var total;
var actualTotal;


$(document).ready(function () {
	movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

    getInfo();

    function getInfo() {
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function(res) {
                if(res.success) {
                	renderSchedule(res.content.scheduleItem, res.content.seats);
                }
                else {
                	alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
        
        getRequest(
    		'/activity/getByMovie/' + movieId,
    		function(res) {
    			if(res.success) {
    				activities = res.content;
    				renderActivities();
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
});

function renderActivities() {
	$(".content-activity").empty();
    var activitiesDomStr = "<h3 class='text-info'>近期活动</h3>";

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
    $(".content-activity").append(activitiesDomStr);
}

function renderSchedule(schedule, seats) {
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + " ~ " + schedule.endTime.substring(11, 16));
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + " ~ " + schedule.endTime.substring(11, 16));
    fare = schedule.fare;
    var seat = "";
    for(var i = 0; i < seats.length; i++) {
        var temp = ""
        for(var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j

            if (seats[i][j] == 0) {
                // 未选
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDomStr =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";

    $('#hall-card').html(hallDomStr);
}

function seatClick(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位";
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    
    $('#seat-detail').html(seatDetailStr);
}

function orderConfirmClick() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");

    lockSeat();  //锁座
    renderOrder();
    
    checkVip();
}

function lockSeat() {
    var temp = [];
    for (let seatLoc of selectedSeats) {
        temp.push({columnIndex: seatLoc[1], rowIndex: seatLoc[0]});
    }
    var form = {
        userId: userId,
        scheduleId: scheduleId,
        seats: temp
    };
    
    postRequest(
        '/ticket/lockSeat',
        form,
        function (res) {
        	if(res.success) {
        		for(let it of res.content){
                    order.ticketId.push(it);
                }
        	}
        	else {
        		alert("选座失败！");
        	}
        },
        function (error) {
            alert(error);
            alert("选座失败！");
        });
}

function renderOrder() {
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of selectedSeats) {
        ticketStr += "<div>" + (ticketInfo[0] + 1) + "排" + (ticketInfo[1] + 1) + "座</div>";
    }
    $('#order-tickets').html(ticketStr);

    total = parseFloat((selectedSeats.length * fare)+"").toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);

    getCoupons();
    
}

function getCoupons() {
	getRequest(
		'/coupon/' + userId + '/get',
        function (res) {
        	if(res.success) {
        		coupons = res.content.filter(function (coupon) {
        			return total >= coupon.targetAmount;
        		})
        		var couponTicketStr = "";
        		if (coupons.length == 0) {
        			actualTotal = total;
			        $('#order-discount').text("优惠金额：¥0");
			        $('#order-actual-total').text("¥" + actualTotal);
			        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
			    } else {
			        for(let coupon of coupons) {
			            couponTicketStr += "<option>" + coupon.name +"：满" + coupon.targetAmount + "减" + coupon.discountAmount + "（剩" + coupon.num + "张）" + "</option>"
			        }
			        $('#order-coupons').html(couponTicketStr);
			        changeCoupon(0);
			    }
        	}
        	else {
        		alert("获取优惠券失败！");
        	}
        },
        function (error) {
            alert(error);
            alert("获取优惠券失败！");
        });
}

function changeCoupon(idx) {
	order.couponId = coupons[idx].id;
    $('#order-discount').text("优惠金额： ¥" + coupons[idx].discountAmount.toFixed(2));
    actualTotal = (total - parseFloat(coupons[idx].discountAmount)).toFixed(2);
    $('#order-actual-total').text("¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
}

function checkVip() {
	getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            vipCard = res.content;
            useVIP = res.success;
            if (vipCard != null) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + vipCard.balance.toFixed(2) + "元</div>");
                checkBalance();
            } 
            else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}

function checkBalance() {
	if(vipCard.balance < actualTotal) {
		$("#buyModal-notice").css("display", "");
		$("#buyModal-confirm").html("立即充值");
		$("#buyModal-confirm").attr("onclick", "chargeClick()");
	}
	else {
		restoreDefault();
	}
}

function restoreDefault() {
	$("#buyModal-notice").css("display", "none");
	$("#buyModal-confirm").html("确认支付");
	$("#buyModal-confirm").attr("onclick", "payConfirmClick()");
}

function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
        checkBalance();
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
        restoreDefault();
    }
}

function chargeClick() {
	$('#buyModal').modal('hide');
	$('#chargeModal').modal('show');
	$('#chargeModal-notice').html("至少充值" + (actualTotal - vipCard.balance) + "才能完成本次购票");
}

function chargeConfirmClick() {
	if(validateChargeForm()) {
		getVipInfo();
		var charge_amount = $('#userBuy-chargeMoney').val();
        postRequest(
            '/vip/charge',
            {vipId: vipCard.id, amount: parseInt(charge_amount)},
            function (res) {
            	if(res.success) {
            		$('#chargeModal').modal('hide');
            		checkVip();
                    setTimeout(function() {
                    	if(charge_amount >= vipInfo.discount_req) {
                        	alert("充值成功，充值满" + vipInfo.discount_req + "元，额外赠送" + vipInfo.discount_res + "元，目前您的余额为" + vipCard.balance + "元");
                        }
                        else {
                        	alert("充值成功，目前您的余额为" + vipCard.balance + "元");
                        }
                    }, 100);
            	}
            	else {
            		alert(res.message);
            	}
            },
            function (error) {
            	alert("充值失败！");
                alert(error);
            });
	}
}

function getVipInfo() {
	getRequest(
		'/vip/getVIPInfo/' + vipCard.serviceId,
		function (res) {
		    if (res.success) {
		    	vipInfo = res.content;
		    } 
		    else {
		        alert(res.content);
		    }
		
		},
		function (error) {
		    alert(error);
		});
}

function validateChargeForm() {
	var isValidate = true;
    if (!$('#userBuy-chargeMoney').val()) {
        isValidate = false;
        $('#userBuy-chargeMoney').parent('.form-group').addClass('has-error');
        $('#userBuy-chargeMoney-error').css("visibility", "visible");
    }
    return isValidate;
}

function payConfirmClick() {
	var couponToget = getCouponsFormAc();
    if(useVIP) {
        postRequest(
            '/ticket/vip/buy',
            {movieId: movieId, ticketId: order.ticketId, couponId: order.couponId, total: actualTotal, couponIdToget: getCouponIdsFormAc()},
            function (res) {
                if(res.success) {
                	repainActivities(couponToget);
                }
                else {
                	$(".content-activity").empty();
                	alert(res.message);
                }
            },
            function (error) {
            	$(".content-activity").empty();
                alert(error);
            }
        );
        postPayRequest();
    }
    else{
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                postRequest(
                    '/ticket/buy',
                    {movieId: movieId, ticketId: order.ticketId, couponId: order.couponId, total: actualTotal, couponIdToget: getCouponIdsFormAc()},
                    function(res) {
                    	if(res.success) {
                    		repainActivities(couponToget);
                    	}
                    	else {
                    		$(".content-activity").empty();
                    		alert(res.message);
                    	}
                    },
                    function(error) {
                    	$(".content-activity").empty();
                        alert(error);
                    }
                );
                postPayRequest();
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

function getCouponsFormAc() {
	var couponToget = [];
	activities.forEach(function (activity) {
		couponToget.push(activity.coupon);
	});
	return couponToget;
}

function getCouponIdsFormAc() {
	var couponIdToget = [];
	activities.forEach(function (activity) {
		couponIdToget.push(activity.coupon.id);
	});
	return couponIdToget;
}

function repainActivities() {
	$(".content-activity").empty();
    var activitiesDomStr = "<h3 class='text-info'>获得的优惠券</h3>";

    activities.forEach(function (activity) {
    	var coupon = activity.coupon
    	activitiesDomStr += 
        	"<div class='col-md-6 coupon-wrapper'>" +
        	"	<div class='coupon'>" +
        	"   	<div class='content'>" +
        	"   		<div class='col-md-8 left'>" +
        	"				<div class='name'>" + coupon.name + "</div>" +
            "				<div class='description'>" + coupon.description + "</div>" +
            "				<div class='price'>" + "满" + coupon.targetAmount + "送" + coupon.discountAmount + "</div>" +
            "			</div>" +
            "  			<div class='col-md-4 middle'>" +
            "				<div>有效日期：</div>" +
            "				<div>" + formatDate(new Date(coupon.startTime)) + " ~ " + formatDate(new Date(coupon.endTime)) + "</div>" +
            "			</div>" +
            "		</div>" +
            "	</div>" +
            "</div>";
    });
    $(".content-activity").append(activitiesDomStr);
}

// TODO:填空
function postPayRequest() {
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    $('#buyModal').modal('hide')
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