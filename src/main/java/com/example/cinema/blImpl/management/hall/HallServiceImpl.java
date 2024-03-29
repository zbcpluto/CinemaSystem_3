package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    @Override
    public ResponseVO addHall(HallVO hall){
        try{
            Hall temp = new Hall();
            temp.setColumn(hall.getColumn());
            temp.setRow(hall.getRow());
            temp.setName(hall.getName()+"号厅");
            System.out.println(hall.getColumn());
            return ResponseVO.buildSuccess(hallMapper.addHall(temp));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateHall(HallVO hallVO) {
        try{
            Hall hall = new Hall();
            hall.setId(hallVO.getId());
            hall.setName(hallVO.getName()+"号厅");
            hall.setRow(hallVO.getRow());
            hall.setColumn(hallVO.getColumn());
            hallMapper.updateHall(hall);
            return ResponseVO.buildSuccess();
        }catch(Exception e){
            return ResponseVO.buildFailure("error");
        }
    }
}
