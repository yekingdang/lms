package com.chibiao.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.channels.Pipe;

/**
 * 控制页面跳转的控制器
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/department")
    public String department(){
        return "department";
    }
    @RequestMapping("/specialty")
    public String specialty(){
        return "specialty";
    }
    @RequestMapping("/clazz")
    public String clazz(){
        return "clazz";
    }
    @RequestMapping("/course")
    public String course(){
        return "course";
    }
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    @RequestMapping("/student")
    public String student(){
        return "student";
    }
    @RequestMapping("/teacher")
    public String teacher(){
        return "teacher";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/myCourse")
    public String myCourse(){
        return "myCourse";
    }
    @RequestMapping("/myClazz")
    public String myClazz(){
        return "myClazz";
    }
    @RequestMapping("/myClazzCourseTime")
    public String myClazzCourseTime(){
        return "myClazzCourseTime";
    }
    @RequestMapping("/clazzCourseTime")
    public String clazzCourseTime(){
        return "clazzCourseTime";
    }
    @RequestMapping("/leaveRecord")
    public String leaveRecord(){
        return "leaveRecord";
    }
    @RequestMapping("/workFlowManage")
    public String workFlowManage(){
        return "workFlowManage";
    }
    @RequestMapping("/taskManage")
    public String taskManager(){
        return "taskManage";
    }
    @RequestMapping("/historyTask")
    public String historyTask(){
        return "historyTask";
    }
    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }

}
