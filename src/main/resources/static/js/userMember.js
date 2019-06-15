$(document).ready(function () {
    getVIP();
    getCoupon();
});

var userId = sessionStorage.getItem('id');
var isBuyState = true;
var vipCard;
var serviceId;
var vipInfoList;
var vipInfo;

function getVIP() {
    getRequest(
        '/vip/' + userId + '/get',
        function (res) {
            if (res.success) {
                // 是会员
                $("#member-card").css("visibility", "visible");
                $("#member-card").css("display", "");
                $("#nonmember-card").css("display", "none");

                vipCard = res.content;
                $("#member-id").text(res.content.id);  //会员卡号
                $("#member-joinDate").text(res.content.joinDate.substring(0, 10));  //入会日期
                renderVipDiscount(res.content.serviceId);  //优惠策略
                $("#member-balance").text("¥" + res.content.balance.toFixed(2));  //余额
            } else {
                // 非会员
                $("#member-card").css("display", "none");
                $("#nonmember-card").css("display", "");
                getVipInfo();
            }
        },
        function (error) {
            alert(error);
        });
}

function renderVipDiscount(serviceId) {
	getRequest(
		'/vip/getVIPInfo/' + serviceId,
		function (res) {
		    if (res.success) {
		    	vipInfo = res.content;
		        $("#member-buy-price").text(res.content.price);
		        $("#member-buy-discount_req").text(res.content.discount_req);
		        $("#member-buy-discount_res").text(res.content.discount_res);
		    } else {
		        alert(res.content);
		    }
		
		},
		function (error) {
		    alert(error);
		});
}

function getVipInfo() {
    getRequest(
        '/vip/getVIPInfo',
        function (res) {
            vipInfoList = res.content;
            renderVipInfo(vipInfoList);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function renderVipInfo(vipInfoList) {
    $("#vipInfo-list").empty();
    var vipInfoDomStr = "";

    var idx = 0;
    vipInfoList.forEach(function (vipInfo) {
    	vipInfoDomStr +=
            "<li class='vipInfo-container'>" +
            "    <div class='vipInfo-card card'>" +
            "       <div class='vipInfo-line'>" +
            "           <span class='title'>" + vipInfo.name + "</span>" +
            "       </div>" +
            "       <div class='vipInfo-line'>" +
            "           <span>价格：" + 
            "           <span class='primary-text'>" + vipInfo.price + "</span>" +
            "			</span>" +
            "       </div>" +
            "       <div class='vipInfo-line'>" +
            "           <span>优惠：充  " +
            "           <span class='primary-text'>" + vipInfo.discount_req + "</span>" +
            "           <span> 送 </span>" +
            "           <span class='primary-text'>" + vipInfo.discount_res + "</span>" +
            "           </span>" +
            "           <span class='change-btn'>" +
			"				<button type='button' class='btn btn-primary' onclick='buyClick(" + idx + ")' '> 购买 </button>" +
			"			</span>" +
            "       </div>" +
            "    </div>" +
            "</li>";
    	
    	idx++;
    });
    $("#vipInfo-list").append(vipInfoDomStr);
}

function buyClick(idx) {
	vipInfo = vipInfoList[idx];
	renderBuyForm(vipInfo);
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
    serviceId = vipInfo.id;
}

function renderBuyForm(vipInfo) {
	$('#myModalLabel').text("购买"+vipInfo.name);
	$('#payInfo-price').text(vipInfo.price);
	clearForm();
}

function chargeClick() {
	renderChargeForm();
    $('#buyModal').modal('show')
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function renderChargeForm(vipInfo) {
	$('#myModalLabel').text("充值");
	$('#payInfo').css("display", "none");
	clearForm();
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
            if (isBuyState) {
                postRequest(
                    '/vip/add?userId=' + userId + '&' + 'serviceId=' + serviceId,
                    null,
                    function (res) {
                        $('#buyModal').modal('hide');
                        alert("购买会员卡成功");
                        getVIP();
                    },
                    function (error) {
                        alert(error);
                    });
            } else {
            	var charge_amount = $('#userMember-amount').val();
                postRequest(
                    '/vip/charge',
                    {vipId: vipCard.id, amount: parseInt(charge_amount)},
                    function (res) {
                        $('#buyModal').modal('hide');
                        getBalance();
                        setTimeout(function() {
                        	if(charge_amount >= vipInfo.discount_req) {
                            	alert("充值成功，充值满" + vipInfo.discount_req + "元，额外赠送" + vipInfo.discount_res + "元，目前您的余额为" + vipCard.balance + "元");
                            }
                            else {
                            	alert("充值成功，目前您的余额为" + vipCard.balance + "元");
                            }
                        }, 100);
                        
                    },
                    function (error) {
                        alert(error);
                    });
            }
        } else {
            alert("银行卡号或密码错误");
        }
    }
}

function getBalance() {
    getRequest(
        '/vip/' + userId + '/get',
        function (res) {
        	vipCard = res.content;
        	$("#member-balance").text("¥" + vipCard.balance.toFixed(2));  //余额
        },
        function (error) {
            alert(error);
        });
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function chargeHistory() {
	getRequest(
		'/vip/chargeRecord/' + userId,
		function (res) {
		    if (res.success) {
		    	recordList = res.content;
		    	renderRecordList(recordList);
		    } else {
		        alert(res.content);
		    }
		
		},
		function (error) {
		    alert(error);
		});
	setTimeout(function() {
		$("#recordModal").modal('show');
    }, 100);
	
}

function renderRecordList(recordList) {
	var joinDate = new Date(vipCard.joinDate);
	$("#record-joinDate").text("购买日期：" + formatDateTime(joinDate));
	$("#record-list").empty();
    var recordDomStr = "";
    recordList.forEach(function (record) {
    	recordDomStr +=
            "<li class='record-container'>" +
            "    <div class='record-card card'>" +
            "       <div class='record-line'>" +
            "           <span>" + formatDateTime(new Date(record.chargeTime)) + "</span>" +
            "           <span class='primary-text'> + " + record.chargeAmount;
    	if(record.discountAmount > 0) {
    		recordDomStr +=
    			"       <span class='default-text'> + " + record.discountAmount + "</span>";
    	}
    	recordDomStr +=   
    		"           </span>" +
            "       </div>" +
            "    </div>" +
            "</li>";
    });
    $("#record-list").append(recordDomStr);
}

function getCoupon() {
    getRequest(
        '/coupon/' + userId + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '送' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

/*function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
} */