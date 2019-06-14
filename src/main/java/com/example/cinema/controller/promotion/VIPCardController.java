spackage com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPInfoForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.FilteredEndpoint;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId, @RequestParam int vipServiceId){
        return vipService.addVIPCard(userId, vipServiceId);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @PostMapping("/publish")
    public ResponseVO publishVipcard(@RequestBody VIPInfoForm vipInfoForm){
        return vipService.publishVipcard(vipInfoForm);
    }

    @GetMapping("{userId}/get")
    public ResponseVO getChargeHistory(@PathVariable int userId){
        return vipService.getChargeHistory(userId);
    }


    @PostMapping("/update")
    public ResponseVO updateVipcard(@RequestBody VIPInfoForm vipInfoForm){
        return vipService.updateVipcard(vipInfoForm);
    }
    
}
