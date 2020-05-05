package com.chibiao.lms.service;

import com.chibiao.lms.domain.Course;
import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.domain.TeacherCourseRel;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

import java.util.List;

/**
 * 教师服务
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
public interface TeacherService {
    /**
     * 分页查询教师信息
     * @param teacher 查询条件 教师名称 教师编号 教师类型
     * @param pageParam 分页参数
     * @return
     */
    PageListRes queryTeachers(Teacher teacher, PageParam pageParam);

    /**
     * 添加教师
     * @param teacher 添加教师
     * @return
     */
    Boolean addTeacher(Teacher teacher);

    /**
     * 删除教师信息
     * @param teacherNo 教师编号
     * @return
     */
    Boolean deleteTeacher(Long teacherNo);

    /**
     * 修改教师信息 根据教师编号
     * @param teacher 教师信息
     * @return
     */
    Boolean updateTeacher(Teacher teacher);

    /**
     * 根据教师编号查询教师信息
     * @param teacherNo
     * @return
     */
    Teacher selectByTeacherNo(Long teacherNo);

    /**
     * 添加老师教授的课程
     * @return
     */
    Boolean addMyCourse(TeacherCourseRel teacherCourseRel);

    /**
     * 查询我教授的课程
     * @param teacherNo 教师编号
     * @return 课程信息
     */
    List<Course> selectMyCourse(Long teacherNo);
}
