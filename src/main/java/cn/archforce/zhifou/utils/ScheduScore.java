package cn.archforce.zhifou.utils;

import cn.archforce.zhifou.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 隔壁老李
 * @date 2020/5/14 20:16
 * 月末定时清除分数
 */
@Slf4j
@Component
public class ScheduScore {

    @Autowired
    UserMapper userMapper;

    /**
     * 定时每个月最后一天最后一秒清除分数
     */
    @Scheduled(cron = "59 59 23 L * ?")
    private void clearScore(){
        if (userMapper.clearScore() == 0){
            log.info("清除积分失败");
        }else {
            log.info("月末最后一秒清除积分");
        }
    }

}
