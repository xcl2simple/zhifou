package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.config.MyConfiguration;
import cn.archforce.zhifou.dao.AnswerDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.model.entity.Answer;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.AnswerService;
import cn.archforce.zhifou.utils.TextUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * @author 隔壁老李
 * @date 2020/4/8 18:59
 * @roject zhifou
 */
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public boolean addAnswer(Answer answer) {
        String txtPath = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearAndMonth = year + (month / 10 > 0 ? "" + month : "0" + month);
        String fileName = calendar.getTimeInMillis() + MyConfiguration.SUFFIX_TEXT;
        try {
            txtPath = TextUtil.saveToLocal(answer.getContent(), myConfiguration.getTextRoot() + yearAndMonth, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        answer.setContent(txtPath);
        answer.setCreateTime(new Date());
        answer.setLikeNum(0);
        if (answerDao.addAnswer(answer) != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<Answer> getAnswerList(Long questionId, Integer sort, Integer startIndex, Integer num) {
        String orderBy;
        if (sort == 1){
            orderBy = "like_num DESC";
        }else {
            orderBy = "create_time DESC";
        }
        //设置页码，数据量和排序方式
        PageHelper.startPage(startIndex, num, orderBy);
        List<Answer> answers = answerDao.selectAnswerByQuestionId(questionId);
        if (getUserInfo(answers)){
            return answers;
        }
        return new ArrayList<>();
    }

    /**
     * 获取回答者的信息
     * @param answers 回答的列表
     */
    private boolean getUserInfo(List<Answer> answers){
        if (answers != null){
            try {
                Iterator<Answer> iterator = answers.iterator();
                Answer answer;
                User user;
                while (iterator.hasNext()){
                    answer = iterator.next();
                    user = userMapper.getUserById(answer.getUserId());
                    answer.setAuthor(user);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
