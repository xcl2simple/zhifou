package cn.archforce.zhifou.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author 隔壁老李
 * @date 2020/3/31 20:45
 * 发送邮箱验证码
 */
@Component
public class EmailUtil {

    /**
     * Redis工具类，存储邮箱验证码，设置过期时间
     */
    @Autowired
    RedisUtil redisUtil;

    /**
     * springboot 提供的一个发送邮件的简单接口，直接注入即可
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置邮件发送者
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 邮件内容前缀
     */
    private final String PREFIX = "你的验证码为：";

    /**
     * 6位验证码
     */
    private int verificationCode;

    /**
     * 邮件内容后缀
     */
    private final String SUFFIX = "，该验证码5分钟内有效，请尽快验证。";

    /**
     * 邮件主题
     */
    private final String subject = "【知否】验证码";

    /**
     * 获取随机验证码
     */
    private Random random = new Random();

    /**
     * 正则校验邮箱是否合格
     */
    private Pattern pattern = Pattern.compile("\\w+@(\\w+.)+[a-zA-Z]{2,3}");

    /**
     * 生成新的验证码
     */
    private void setVerificationCode(){
        verificationCode = random.nextInt(1000000);
    }

    /**
     * 发送简单文本邮件
     * @param to 邮件接收者邮箱
     */
    public String sendSimpleMail(String to){
        //创建SimpleMailMessage对象
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //邮件发送者
        mailMessage.setFrom(from);
        //邮件接收者
        mailMessage.setTo(to);
        //邮件主题
        mailMessage.setSubject(subject);
        //生成6位验证码
        setVerificationCode();
        //邮件内容
        mailMessage.setText(PREFIX + verificationCode + SUFFIX);
        try {
            //发送邮件
            mailSender.send(mailMessage);
            //将验证码存入redis缓存，并设置5分钟过期时间
            redisUtil.set(to, verificationCode, 300);
            return CodeUtil.getSuccess();
        } catch (MailException e){
            e.printStackTrace();
            redisUtil.del(to);
            return CodeUtil.getServerError();
        }
    }

    /**
     * 获取生成的
     * @return
     */
    public int getVerificationCode(){
        return verificationCode;
    }


    public boolean isFormat(String email){
        return pattern.matcher(email).matches();
    }

}
