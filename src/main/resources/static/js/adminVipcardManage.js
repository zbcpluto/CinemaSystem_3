var isChanging = false;
var vipInfoList;
var vipID;

$(document).ready(function() {

    getVipInfo();

    function getVipInfo() {
        getRequest(
            '/vip/getVIPInfo',
            function (res) {
                vipInfoList = res.content;
                renderVipInfo();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function renderVipInfo() {
        $(".content-vipInfo").empty();
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
                "       </div>" +
                "       <div class='vipInfo-line'>" +
                "           <span>优惠：充  " +
                "           <span class='primary-text'>" + vipInfo.discount_req + "</span>" +
                "           <span> 送 </span>" +
                "           <span class='primary-text'>" + vipInfo.discount_res + "</span>" +
                "           </span>" +
                "           <span class='change-btn'>" +
    			"				<button type='button' class='btn btn-primary' onclick='changeVipInfo(" + idx + ")' data-backdrop='static' data-toggle='modal' data-target='#vipInfoModal'> 修改 </button>" +
    			"			</span>" +
                "       </div>" +
                "    </div>" +
                "</li>";
        	
        	idx++;
        });
        $(".content-vipInfo").append(vipInfoDomStr);
    }

    $("#vipInfo-form-btn").click(function () {
		if(!isChanging) {
			postRequest(
					'/vip/publish',
					getForm(),
					function (res) {
						if(res.success) {
							getVipInfo();
							$("#vipInfoModal").modal('hide');
						} else {
							alert(res.message);
						}
					 },
					 function (error) {
					     alert(JSON.stringify(error));
					 }
		        );
		}
		else {
			postRequest(
					'/vip/update',
					getForm(),
					function (res) {
						if(res.success) {
							getVipInfo();
							$("#vipInfoModal").modal('hide');
						} else {
							alert(res.message);
						}
					 },
					 function (error) {
					     alert(JSON.stringify(error));
					 }
		        );
		}
        
		isChanging = false;
     });
    
    function getForm() {
    	var form = {
    			id: vipID,
    		    name: $("#vipInfo-name-input").val(),
    		    price: $("#vipInfo-price-input").val(),
    		    discount_req: $("#vipInfo-discount_req-input").val(),
    		    discount_res: $("#vipInfo-discount_res-input").val(),
    		};
    	return form;
    }
    	
});

function changeVipInfo(idx) {
	isChanging = true;
	var vipInfo = vipInfoList[idx];
	vipID = vipInfo.id;
	$('#vipInfo-name-input').attr('value', vipInfo.name);
    $('#vipInfo-price-input').attr('value', vipInfo.price);
    $('#vipInfo-discount_req-input').attr('value', vipInfo.discount_req);
    $('#vipInfo-discount_res-input').attr('value', vipInfo.discount_res);
}