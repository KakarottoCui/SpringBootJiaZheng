package com.singulee.carschool.service;

import com.singulee.carschool.pojo.*;
import com.singulee.carschool.pojo.AdviceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 
 * Date: 2024/01/19
 * Description:
 * Version: V1.0
 */
@Service
public class AdviceService {

    @Resource
    AdviceMapper adviceMapper;

    @Resource
    StudentMapper studentMapper;

    @Resource
    TeacherMapper teacherMapper;



    public Map<String,Object> addAdvice(Advice advice, HttpServletRequest request){
        advice.setCreatedate(new Date());
        advice.setValid(1);
        adviceMapper.insert(advice);
        return getRes(200,"success");
    }

    public Map<String,Object> updateAdvice(Advice advice,HttpServletRequest request){
        if(advice.getState() !=null && advice.getState()==2){
            advice.setAnsdate(new Date());
        }
        adviceMapper.update(advice);
        return getRes(200,"success");
    }

    public Map<String,Object> findAdvice(Advice advice){
        List<Advice> adviceList = adviceMapper.queryAll(advice);
        for(Advice ad : adviceList){
            if(ad.getSid()!=null){
                Student student = studentMapper.selectById(ad.getSid());
                if (student != null) {
                    ad.setStudent(student);
                }
            }
            if(ad.getTid()!=null){
                Teacher teacher = teacherMapper.selectByTeaId(ad.getTid());
                if (teacher != null) {
                    ad.setTeacher(teacher);
                }
            }
        }
        return getRes(200,adviceList);
    }

    public Map<String,Object> getRes(Integer code, Object data){
        Map<String,Object> res = new HashMap<>();
        res.put("code",code);
        res.put("data",data);
        res.put("msg","操作成功");
        return  res;
    }
}
