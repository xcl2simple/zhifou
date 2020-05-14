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

    @Autowired
    RedisUtil redisUtil;

    /**
     * 定时每个月第一天凌晨清除分数
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    private void clearScore(){
        //清除缓存
        redisUtil.removeZSet();
        if (userMapper.clearScore() == 0){
            log.info("清除积分失败");
        }else {
            log.info("月初清除积分");
        }
    }

}
