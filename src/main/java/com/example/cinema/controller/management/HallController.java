package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "/hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "/hall/add", method = RequestMethod.POST)
    public ResponseVO addHall(@RequestBody HallVO hallinfo){return hallService.addHall(hallinfo);}

    @RequestMapping(value = "/hall/update", method = RequestMethod.POST)
    public ResponseVO updateHall(@RequestBody HallVO hallVO){return hallService.updateHall(hallVO);}
}
