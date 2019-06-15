package com.example.cinema.blImpl.management.refund;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.data.management.RefundMapper;
import com.example.cinema.po.RefundStrategy;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AzureXH on 2019/6/8
 */
@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private RefundMapper refundMapper;
    @Override
    public ResponseVO addRefundStrategy(RefundForm refundForm) {
        try{
            List<RefundStrategy> refundStrategies = convert2List(refundForm);
            refundMapper.insertRefundStrategy(refundStrategies);
            return ResponseVO.buildSuccess();
        }catch (NullPointerException e){
            return ResponseVO.buildFailure("新建退票策略有误");
        }
    }

    /**
     * 将退票策略根据针对的用户类型进行排序返回给页面
     * @return
     */
    @Override
    public ResponseVO getAllRefundStrategy() {
        try{
            List<RefundStrategy> refundStrategyList = refundMapper.selectAllRefundStrategy();
            List<RefundForm> refundFormList = refundPOList2refundVOList(refundStrategyList);
            for (int i=0; i<refundFormList.size(); i++){
                for (int j=i+1; j<refundFormList.size(); j++){
                    RefundForm r1 = refundFormList.get(i);
                    RefundForm r2 = refundFormList.get(j);
                    if (r1.getIsVip()<r2.getIsVip()){
                        refundFormList.set(i, r2 );
                        refundFormList.set(j, r1);
                    }
                }
            }
            return ResponseVO.buildSuccess(refundFormList);
        }catch(IndexOutOfBoundsException e){
            //数据为空只要返回空值就好了
            return  ResponseVO.buildSuccess();
        }
    }

    @Override
    public ResponseVO deleteRefundStrategyByName(String name) {
        try{
            return ResponseVO.buildSuccess(refundMapper.deleteRefundStrategyByName(name));
        }catch(Exception e){
            return ResponseVO.buildFailure("删除退票策略失败");
        }
    }

    @Override
    public ResponseVO updateRefundStrategy(RefundForm refundForm) {
        try{
            refundMapper.deleteRefundStrategyByName(refundForm.getName());
            refundMapper.insertRefundStrategy(convert2List(refundForm));
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure("更新退票策略失败");
        }
    }

    /**
     * 将数据库传上来的POList转为给页面层的VOList
     * @param refundStrategyList
     * @return
     */
    private List<RefundForm> refundPOList2refundVOList(List<RefundStrategy> refundStrategyList){
        //列表声明
        List<RefundForm> resultList = new ArrayList<RefundForm>();
        List<String> startTimeList = new ArrayList<String>();
        List<String> endTimeList = new ArrayList<String>();
        List<Double> penaltyList = new ArrayList<Double>();

        //添加策略
        for(int i=0;i<refundStrategyList.size()-1;i++){
            RefundStrategy r1 = refundStrategyList.get(i);
            RefundStrategy r2 = refundStrategyList.get(i+1);

            startTimeList.add(r1.getStartTime());
            endTimeList.add(r1.getEndTime());
            penaltyList.add(r1.getPenalty());
            //前后两个策略是不同的
            if(!r1.getName().equals(r2.getName())){
                //前后策略不同 可以向结果List中添加新的RefundForm
                //获取参数
                String name = r1.getName();
                int isVip = r1.getIsVip();
                String falseTime = r1.getFalseTime();

                RefundForm refundForm = new RefundForm(name,isVip,falseTime,startTimeList,endTimeList,penaltyList);
                resultList.add(refundForm);

                //清除列表 重新添加
                startTimeList = new ArrayList<String>();
                endTimeList = new ArrayList<String>();
                penaltyList = new ArrayList<Double>();

            }
        }
        //对末尾策略的处理
        //无论末尾策略是否和上一个策略名称相同 操作都是一样的
        int listSize = refundStrategyList.size();
        RefundStrategy r1 = refundStrategyList.get(listSize-1);
        //获取参数
        String name = r1.getName();
        int isVip = r1.getIsVip();
        String falseTime = r1.getFalseTime();
        startTimeList.add(r1.getStartTime());
        endTimeList.add(r1.getEndTime());
        penaltyList.add(r1.getPenalty());

        RefundForm refundForm = new RefundForm(name,isVip,falseTime,startTimeList,endTimeList,penaltyList);
        resultList.add(refundForm);

        return resultList;
    }

    /**
     * 将页面层的单个VO转化为给数据库的POList
     * @param refundForm
     * @return
     */
    private List<RefundStrategy> convert2List(RefundForm refundForm){
        List<RefundStrategy> refundStrategies = new ArrayList<RefundStrategy>();
        int len = refundForm.getStartTime().size();
        String name = refundForm.getName();
        String falseTime = refundForm.getFalseTime();
        int isVip = refundForm.getIsVip();
        for (int i=0;i<len;i++){
            String startTime = refundForm.getStartTime().get(i);
            String endTime = refundForm.getEndTime().get(i);
            double penalty = refundForm.getPenalty().get(i);
            refundStrategies.add(new RefundStrategy(name,isVip,falseTime,startTime,endTime,penalty));
        }
        return refundStrategies;
    }
}
