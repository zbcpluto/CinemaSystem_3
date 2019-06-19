var vipRefStra;
var nonVipRefStra;
var state; //会员：1， 非会员： 0
var isAdding; //发布：true，修改：false
var inputRow = 1;

$(document).ready(function () {
	
	$('.vipRef-body').empty();
	getVipRefStra();
	$('.nonVipRef-body').empty();
	getNonVipRefStra();

});

function getVipRefStra() {
	getRequest(
        '/refund/get/vip',
        function(res) {
        	if(res.success) {
        		vipRefStra = res.content;
        		if(vipRefStra != null) {
        			renderVipRefStra();
        		}
        	}
        	else {
        		alert(res.message);
        	}
        },
        function(error) {
            alert(error);
        });
}

function getNonVipRefStra() {
	getRequest(
        '/refund/get/nonVip',
        function(res) {
        	if(res.success) {
        		nonVipRefStra = res.content;
        		if(nonVipRefStra != null) {
        			renderNonVipRefStra();
        		}
        	}
        	else {
        		alert(res.message);
        	}
        },
        function(error) {
            alert(error);
        });
}

/**
 * 页面展示会员退票策略
 * @param list
 */
function renderVipRefStra() {
	$(".vip-notice").text("会员的退票策略");
	$("#vip-add-button").css("display", "none");
	$(".vipRef-body").empty();
	
    var vipRefDomStr = "";
    var free = vipRefStra.freeTime.split(":");
    var free_hour = parseInt(free[0]);
    var free_min = parseInt(free[1]);
    var prohibit = vipRefStra.falseTime.split(":");
    var prohibit_hour = parseInt(prohibit[0]);
    var prohibit_min = parseInt(prohibit[1]);
    
    vipRefDomStr +=
    	"<div class='refund-string'>" +
    	"	<div class='refund-name'>" + vipRefStra.name+ "</div>" +
    	"	<div class='refund-user-type'>用户类型：<span class='refund-red-text'>会员</span></div>" +
    	"	<div class='refund-free-time'>电影开场前" +
    	"		<span class='refund-red-text'>" + ((free_hour != 0) ? free_hour+"小时":"") + free_min + "分钟前可免费退票" + "</span>" +
    	"	</div>" +
    	"	<div class='refund-false-time'>距离电影开场" +
    	"		<span class='refund-red-text'>" + ((prohibit_hour != 0) ? prohibit_hour+"小时":"") + prohibit_min + "分钟内不可退票" + "</span>" +
    	"	</div>" +
    	"</div>" +
    	"<div class='refund-permit'>" +
    	"	<table class='table table-striped' style='text-align: center;'>" +
    	"		<thead>" +
    	"			<tr>" +
    	"				<th style='text-align: center;'>距离电影开场前（小时 : 分钟）</th>" +
    	"				<th style='text-align: center;'>退票折算系数</th>" +
    	"			</tr>" +
    	"		</thead>" +
    	"		<tbody>" + getPermitDomStr(vipRefStra.startTime, vipRefStra.endTime, vipRefStra.penalty) +
    	"		</tbody>" +
    	"	</table>" +
    	"</div>" +
    	"<div class='refund-button'>" +
    	"	<button type='button' class='btn btn-primary refund-btn-update' onclick='updateRefStra(1)'>修改</button>" +
    	"	<button type='button' class='btn btn-danger refund-btn-delete' onclick='deleteRefStra(1)'>删除</button>" +
    	"</div>";
   
    $('.vipRef-body').append(vipRefDomStr);
}

/**
 * 页面展示非会员退票策略
 * @param list
 */
function renderNonVipRefStra() {
	$(".nonVip-notice").text("非会员的退票策略");
	$("#nonVip-add-button").css("display", "none");
	$(".nonVipRef-body").empty();


    var nonVipRefDomStr = "";
    var free = nonVipRefStra.freeTime.split(":");
    var free_hour = parseInt(free[0]);
    var free_min = parseInt(free[1]);
    var prohibit = nonVipRefStra.falseTime.split(":");
    var prohibit_hour = parseInt(prohibit[0]);
    var prohibit_min = parseInt(prohibit[1]);
    
    nonVipRefDomStr +=
    	"<div class='refund-string'>" +
    	"	<div class='refund-name'>" + nonVipRefStra.name+ "</div>" +
    	"	<div class='refund-user-type'>用户类型：<span class='refund-red-text'>非会员</span></div>" +
    	"	<div class='refund-free-time'>电影开场前" +
    	"		<span class='refund-red-text'>" + ((free_hour != 0) ? free_hour+"小时":"") + free_min + "分钟前可免费退票" + "</span>" +
    	"	</div>" +
    	"	<div class='refund-false-time'>距离电影开场" +
    	"		<span class='refund-red-text'>" + ((prohibit_hour != 0) ? prohibit_hour+"小时":"") + prohibit_min + "分钟内不可退票" + "</span>" +
    	"	</div>" +
    	"</div>" +
    	"<div class='refund-permit'>" +
    	"	<table class='table table-striped' style='text-align: center;'>" +
    	"		<thead>" +
    	"			<tr>" +
    	"				<th style='text-align: center;'>距离电影开场（小时 : 分钟）</th>" +
    	"				<th style='text-align: center;'>退票折算系数</th>" +
    	"			</tr>" +
    	"		</thead>" +
    	"		<tbody>" + getPermitDomStr(nonVipRefStra.startTime, nonVipRefStra.endTime, nonVipRefStra.penalty) +
    	"		</tbody>" +
    	"	</table>" +
    	"</div>" +
    	"<div class='refund-button'>" +
    	"	<button type='button' class='btn btn-primary refund-btn-update' onclick='updateRefStra(0)'>修改</button>" +
    	"	<button type='button' class='btn btn-danger refund-btn-delete' onclick='deleteRefStra(0)'>删除</button>" +
    	"</div>";
   
    $('.nonVipRef-body').append(nonVipRefDomStr);
}

/**
 * 退票策略中的表格展示
 */
function getPermitDomStr(startTime, endTime, penalty) {
    var permitDomStr = "";
    for(let i = 0; i < startTime.length; i++){
        permitDomStr +=
            "<tr>" +
            "	<td>" + startTime[i] + "至" + endTime[i] + "</td>" +
            "	<td>" + penalty[i] + "</td>" +
            "</tr>";
    }
    return permitDomStr;
}

/**
 * 发布
 */
function addRefStra(para) {
	state = para;
	isAdding = true;
	$("#refModal").modal("show");
}

/**
 * 修改
 */
function updateRefStra(para) {
	state = para;
	isAdding = false;
	preFilling();
	$("#refModal").modal("show");
}

/**
 * 删除
 */
function deleteRefStra(para) {
	state = para;
	$("#deleteModal").modal("show");
}

function deConfirmClick() {
	$("#deleteModal").modal("hide");
	deleteDetail();
	if(state == 1) {
		$(".vip-notice").text("暂无会员的退票策略");
		$("#vip-add-button").css("display", "");
	}
	else {
		$(".nonVip-notice").text("暂无非会员的退票策略");
		$("#nonVip-add-button").css("display", "");
	}
	refresh();
}

function confirmClick() {
	$("#refModal").modal("hide");
	if(isAdding) {
		addDetail();
		alert("添加成功！");
	}
	else {
		deleteDetail();
		addDetail();
		alert("修改成功！");
	}
	refresh();
}

function addDetail() {
	var formData = getRefundForm();
	postRequest(
        '/refund/add',
        formData,
        function(res) {
        	if(!res.success) {
        		alert(res.message);
        	}
        },
        function(error) {
        	alert(error);
        });
}

function deleteDetail() {
    getRequest(
        '/refund/delete/'+state,
        function(res){
        	if(!res.success) {
        		alert(res.message);
        	}
        },
        function(error){
            alert(error)
        }
    );
}

function preFilling() {
	if(state == 1) {
		inputRow = vipRefStra.startTime.length;
		$('#refund-name-input').val(vipRefStra.name);
	    $('#free-time').val(vipRefStra.freeTime);
	    $('#false-time').val(vipRefStra.falseTime);
	    for(let i = 0; i < inputRow; i++) {
	    	$('#time-group'+(i+1)).css("display", "");
			$('#penalty-group'+(i+1)).css("display", "");
	    	$('#refund-time-start-'+(i+1)).val(vipRefStra.startTime[i]);
	        $('#refund-time-end-'+(i+1)).val(vipRefStra.endTime[i]);
	        $('#refund-penalty-'+(i+1)).val(vipRefStra.penalty[i]);
	    }
	}
	else {
		inputRow = nonVipRefStra.startTime.length;
		$('#refund-name-input').val(nonVipRefStra.name);
	    $('#free-time').val(nonVipRefStra.freeTime);
	    $('#false-time').val(nonVipRefStra.falseTime);
	    for(let i = 0; i < inputRow; i++) {
	    	$('#time-group'+(i+1)).css("display", "");
			$('#penalty-group'+(i+1)).css("display", "");
	    	$('#refund-time-start-'+(i+1)).val(nonVipRefStra.startTime[i]);
	        $('#refund-time-end-'+(i+1)).val(nonVipRefStra.endTime[i]);
	        $('#refund-penalty-'+(i+1)).val(nonVipRefStra.penalty[i]);
	    }
	}
}

function refresh() {
	if(state == 1) {
		$('.vipRef-body').empty();
		vipRefStra = null;
		setTimeout(function() {
			getVipRefStra();
		}, 100);
	}
	else {
		$('.nonVipRef-body').empty();
		nonVipRefStra = null;
		setTimeout(function() {
			getNonVipRefStra();
		}, 100);
	}
	initialModal();
}

function initialModal() {
	$('#refundModal').modal('hide');
    $('#refund-name-input').val("");
    $('#free-time').val("");
    $('#false-time').val("");
    for(let i = 1; i <= inputRow; i++) {
        $('#refund-time-start-'+i).val("");
        $('#refund-time-end-'+i).val("");
        $('#refund-penalty-'+i).val("");
    }
    for(let i = 2; i <= inputRow; i++) {
    	$('#time-group'+i).css("display", "none");
    	$('#penalty-group'+i).css("display", "none");
    }
    inputRow = 1;
}

function getRefundForm() {
    return {
        name: $('#refund-name-input').val(),
        isVip: state,
        freeTime: $('#free-time').val(),
        falseTime: $('#false-time').val(),
        startTime: getRefundStart(),
        endTime: getRefundEnd(),
        penalty: getRefundPenalty(),
    };
}

function getRefundStart() {
    var startTimeList = [];
    for(let i = 1; i <= inputRow; i++) {
        startTimeList.push($('#refund-time-start-'+i).val());
    }
    return startTimeList;
}

function getRefundEnd() {
    var endTimeList = [];
    for(let i = 1; i <= inputRow; i++) {
        endTimeList.push($('#refund-time-end-'+i).val());
    }
    return endTimeList;
}

function getRefundPenalty() {
    var penaltyList = [];
    for(let i = 1; i <= inputRow; i++) {
        penaltyList.push($('#refund-penalty-'+i).val());
    }
    return penaltyList;
}

/**
 * 增加一行输入
 */
function inputPlus() {
	if(inputRow == 5) {
		alert('不可增加，时间区间划分过多！');
	}
	else {
		inputRow++;
		$('#time-group'+inputRow).css("display", "");
		$('#penalty-group'+inputRow).css("display", "");
	}
}

/**
 * 删减一行输入
 */
function inputMinus() {
	if(inputRow == 1) {
		alert('不可删减，时间区间划分过少！');
	}
	else {
		$('#time-group'+inputRow).css("display", "none");
		$('#penalty-group'+inputRow).css("display", "none");
		inputRow--;
	}
}
