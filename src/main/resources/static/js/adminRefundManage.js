$(document).ready(function () {

    getRefundStrategy();

    var strategyNum = 0;

    function getRefundStrategy() {
    	$('.refunds-box').empty();
    	strategyNum = 0;
        getRequest(
            '/refund/manage',
            function (res) {
                renderRefundList(res.content);
            },
            function (error) {
                alert(error);
            }
        )
    }

    /**
     * 页面展示退票策略
     * @param list
     */
    function renderRefundList(list) {
        var refundDomStr = "";
        if (list.length>=2) {
        	$('#refund-add-btn').attr('disabled',true);
        }
        list.forEach(function(refund) {
            var prohibit = refund.falseTime.split(':');
            var prohibit_hour = parseInt(prohibit[0],10);
            var prohibit_min = parseInt(prohibit[1],10);
            strategyNum++;
            refundDomStr+=
            "            <div class=\"refund-container card\" >\n" +
            "                <div class=\"refund-string\" >\n" +
            "                    <span class=\"refund-name\" >"+refund.name+"</span>\n" +
            "                    <span class=\"refund-user-type\">用户类型：\n" +
            "                        <span class=\"refund-red-text\">"+(refund.isVip ? "会员":"非会员")+"</span></span>\n" +
            "                    <div class=\"refund-prohibit-time\">\n" +
            "                        距离电影开场前\n" +
            "                        <span class=\"refund-red-text\">"+((prohibit_hour !=0) ?prohibit_hour+"小时":"")+prohibit_min+"分钟内"+"</span>\n" +
            "                        不可退票\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"refund-permit\">\n" +
            "                    <table class=\"table table-striped\" style=\"text-align: center\">\n" +
            "                        <thead>\n" +
            "                            <tr>\n" +
            "                                <th style=\"text-align: center\">距离电影开场前（小时）</th>\n" +
            "                                <th style=\"text-align: center\">退票折算系数</th>\n" +
            "                            </tr>\n" +
            "                        </thead>\n" +
            "                        <tbody>\n" +
                getPermitDomStr(refund.startTime,refund.endTime,refund.penalty) +
            "                        </tbody>\n" +
            "                    </table>\n" +
            "                </div>\n" +
            "                <div class=\"button-container\">\n" +
            "                    <button type=\"button\" class=\"btn btn-primary refund-btn-update\" data-target=\"#activityModal2\">修改</button>\n" +
            "                    <button type=\"button\" class=\"btn btn-danger refund-btn-delete\">删除</button>\n" +
            "                </div>\n" +
            "            </div>";
        });
        $('.refunds-box').append(refundDomStr);
    }

    /**
     * 为了实现删除按钮的动态绑定需要这么写
     */
    $('.refunds-box').on('click','.refund-btn-delete',function(){
        var btn = $(this);
        var name = btn.parent().parent().first().find('.refund-name').text();
        getRequest(
            '/refund/delete/'+name,
            function(res){
                btn.parent().parent().remove();
                strategyNum--;
                if(strategyNum<2){
                    $('#refund-add-btn').attr('disabled',false);
                }
            },
            function(error){
                alert(error)
            }
        );
    });

    /**
     * 实现修改按钮
     */
    $('.refunds-box').on('click','.refund-btn-update',function() {
        $('#activityModal2').modal('show');
        var btn = $(this);
        var name = btn.parent().parent().first().find('.refund-name').text();
        $("#refund-name-input2").attr("placeholder",name);
            $('#refund-form-btn2').click(function (){
                var formdata = getformdata(name);
                postRequest(
                    '/refund/update',
                    formdata,
                    function () {
                        getRefundStrategy();
                        for (let i=1;i<inputRow2;i++){
                            $('#refund-time-start2-'+i).val("");
                            $('#refund-time-end2-'+i).val("");
                            $('#refund-penalty2-'+i).val("");
                        }
                        inputRow2 = 1;
                        $('#activityModal2').modal('hide');
                    },
                    function (error) {
                      alert("error");
                    }
                )})
    });
    
    function getformdata(name) {
        return{name:name,
             isVip:$('#is-vip2').val(),
             falseTime:$('#false-time2').val(),
             startTime:getRefundStart2(),
             endTime:getRefundEnd2(),
             penalty:getRefundPenalty2()};
     }
    
    /**
     * 退票策略中的表格展示
     * @param startTime
     * @param endTime
     * @param penalty
     * @returns {string}
     */
    function getPermitDomStr(startTime,endTime,penalty){
        var permitDomStr="";
        for(let i=0;i<startTime.length;i++){
            permitDomStr+=
                "                        <tr>\n" +
                "                            <td>\n" +
                startTime[i]+
                "                                <span>至</span>\n" +
                endTime[i]+
                "                            </tDomStr(startd>\n" +
                "                            <td>\n"+
                penalty[i] +
                "                            </td>\n" +
                "                        </tr>\n";

        }
        return permitDomStr;
    }

    /**
     * 新增退票策略时获取表单信息
     * @returns {{name: *|jQuery, isVip: *|jQuery, falseTime: *|jQuery, startTime, endTime, penalty}}
     */
    function getRefundForm(){
        return{
            name:$('#refund-name-input').val(),
            isVip:$('#is-vip').val(),
            falseTime:$('#false-time').val(),
            startTime:getRefundStart(),
            endTime:getRefundEnd(),
            penalty:getRefundPenalty()
        };
    }

    function getRefundStart(){
        var startTimeList=[];
        for(let i=1; i<=inputRow;i++){
            startTimeList.push($('#refund-time-start-'+i).val());
        }
        return startTimeList;
    }
    
    function getRefundEnd(){
        var endTimeList=[];
        for(let i=1; i<=inputRow;i++){
            endTimeList.push($('#refund-time-end-'+i).val());
        }
        return endTimeList;
    }
    
    function getRefundPenalty(){
        var penaltyList=[];
        for(let i=1; i<=inputRow;i++){
            penaltyList.push($('#refund-penalty-'+i).val());
        }
        return penaltyList;
    }
    
    function getRefundStart2(){
        var startTimeList=[];
        for(let i=1; i<=inputRow2;i++){
            startTimeList.push($('#refund-time-start2-'+i).val());
        }
        return startTimeList;
    }
    
    function getRefundEnd2(){
        var endTimeList=[];
        for(let i=1; i<=inputRow2;i++){
            endTimeList.push($('#refund-time-end2-'+i).val());
        }
        return endTimeList;
    }
    
    function getRefundPenalty2(){
        var penaltyList=[];
        for(let i=1; i<=inputRow2;i++){
            penaltyList.push($('#refund-penalty2-'+i).val());
        }
        return penaltyList;
    }

    /**
     * 触发新增退票策略的确定按钮
     */
    $('#refund-form-btn').click(function () {
        var formData = getRefundForm();
        postRequest(
            '/refund/add',
            formData,
            function(res){
                getRefundStrategy();
                $('#activityModal').modal('hide');
                $('#refund-name-input').val("");
                $('#false-time').val("");
                for(let i=1 ;i<=inputRow;i++){
                    $('#refund-time-start-'+i).val("");
                    $('#refund-time-end-'+i).val("");
                    $('#refund-penalty-'+i).val("");
                }
                inputRow = 1;
            },
            function (error) {
                alert('error');
            });
    });
    
    /**
     * 当前表单
     * @type {number}
     */
    var inputRow = 1;
    $('#input-plus').click(function() {
    	inputRow = 1;
        if(inputRow < 5){
            inputRow++;
            html =
                '<div class="form-group">\n' +
                    '<label class="col-sm-2 control-label" for="refund-time-start-' +inputRow+  '"><span class="error-text">*</span>电影开场</label>\n' +
                    '<div class="col-sm-4">\n' +
                        '<input class="form-control" type="time" id="refund-time-start-' +inputRow+  '" step="300">\n' +
                    '</div>\n' +
                    '<label class="col-sm-1 control-label" for="refund-time-end-' +inputRow+  '">至</label>\n' +
                    '<div class="col-sm-5">\n' +
                        '<input class="form-control" type="time" id="refund-time-end-' +inputRow+  '" step="300">\n' +
                    '</div>\n' +
                '</div>\n' +
                '<div class="form-group">\n' +
                    '<label  class="col-sm-2 control-label" for="refund-penalty-' +inputRow+  '"><span class="error-text">*</span>折算系数</label>\n' +
                    '<div class="col-sm-10">\n' +
                        '<input type="text" class="form-control" id="refund-penalty-' +inputRow+  '" placeholder="请输入退票手续费的折算系数">\n' +
                    '</div>\n' +
                '</div>\n';
            $('#input-plus').parent().parent().before(html);
        }else{
            alert('不可增加，时间区间划分过多！');
        }
    });
    
    $('#input-minus').click(function() {
        if(inputRow>1){
            $('#input-minus').parent().parent().prev().remove();
            $('#input-minus').parent().parent().prev().remove();
            inputRow--;
        }else{
            alert('不可删减，时间区间划分过少！');
        }
    });
    
    var inputRow2 = 1;
    $('#input-plus2').click(function(){
        inputRow2 = 1;
        if(inputRow2<5){
            inputRow2++;
            html =
                '<div class="form-group">\n' +
                '<label class="col-sm-2 control-label" for="refund-time-start2-' +inputRow2+  '"><span class="error-text">*</span>电影开场</label>\n' +
                '<div class="col-sm-4">\n' +
                '<input class="form-control" type="time" id="refund-time-start2-' +inputRow2+  '" step="300">\n' +
                '</div>\n' +
                '<label class="col-sm-1 control-label" for="refund-time-end2-' +inputRow2+  '">至</label>\n' +
                '<div class="col-sm-5">\n' +
                '<input class="form-control" type="time" id="refund-time-end2-' +inputRow2+  '" step="300">\n' +
                '</div>\n' +
                '</div>\n' +
                '<div class="form-group">\n' +
                '<label  class="col-sm-2 control-label" for="refund-penalty2-' +inputRow2+  '"><span class="error-text">*</span>折算系数</label>\n' +
                '<div class="col-sm-10">\n' +
                '<input type="text" class="form-control" id="refund-penalty2-' +inputRow2+  '" placeholder="请输入退票手续费的折算系数">\n' +
                '</div>\n' +
                '</div>\n';
            $('#input-plus2').parent().parent().before(html);
        }else{
            alert('不可增加，时间区间划分过多！');
        }
    });
    
    $('#input-minus2').click(function(){
        if(inputRow2>1){
            $('#input-minus2').parent().parent().prev().remove();
            $('#input-minus2').parent().parent().prev().remove();
            inputRow2--;
        }else{
            alert('不可删减，时间区间划分过少！');
        }
    });
    
});
