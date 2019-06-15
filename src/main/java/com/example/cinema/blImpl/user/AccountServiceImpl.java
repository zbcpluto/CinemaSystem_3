package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.bl.user.AccountServiceForBl;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService, AccountServiceForBl {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public ResponseVO managerAssign(UserForm userForm) {
        try{
            User user = new User(userForm.getUsername(), userForm.getPassword(), userForm.getLevel());
            accountMapper.managerAssign(user);
            return ResponseVO.buildSuccess("经理分配职责成功");
        }catch(Exception e){

            return ResponseVO.buildFailure("经理分配职责失败");
        }
    }

    @Override
    public ResponseVO updateTicketConsumption(int userId,double ticketConsumption){
        try {
            accountMapper.updateTicketConsumption(userId,ticketConsumption);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("更新消费数额失败");
        }
    }

    @Override
    public ResponseVO getLevelByUserName(String username) {
        try{
            int level = accountMapper.getLevelByUserName(username);
            return ResponseVO.buildSuccess(level);
        }catch (Exception e){
            return ResponseVO.buildFailure("获取用户等级失败");
        }
    }
}
