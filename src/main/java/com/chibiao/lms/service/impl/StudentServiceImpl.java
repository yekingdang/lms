package com.chibiao.lms.service.impl;

import com.chibiao.lms.constant.StudentConst;
import com.chibiao.lms.domain.*;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.ClazzMapper;
import com.chibiao.lms.mapper.DepartmentMapper;
import com.chibiao.lms.mapper.SpecialtyMapper;
import com.chibiao.lms.mapper.StudentMapper;
import com.chibiao.lms.param.EmailParam;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.StudentService;
import com.chibiao.lms.template.SendEmailTemplate;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/11
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SpecialtyMapper specialtyMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public PageListRes queryStudents(Student student, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Student> students = studentMapper.queryStudents(student);
        return PageListResUtil.buildSuccessPageListRes(page, students);
    }

    @Override
    public Boolean addStudent(Student student) {
        // 校验
        checkClazz(student);
        // 设置初始密码
        student.setStudentPassword(StudentConst.INIT_STUDENT_PASSWORD);
        Student s = studentMapper.selectByStudentId(student.getStudentId());
        if (s != null) {
            throw new BusinessException(BusinessErrorCode.STUDENT_NO_EXIST);
        }
        studentMapper.insert(student);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteStudent(Long studentId) {
        studentMapper.deleteByStudentId(studentId);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateStudent(Student student) {
        checkClazz(student);
        studentMapper.updateByStudentId(student);
        return Boolean.TRUE;
    }

    @Override
    public Boolean resetStudentPassword(Long studentId) {
        studentMapper.resetStudentPassword(studentId,StudentConst.INIT_STUDENT_PASSWORD);
        return Boolean.TRUE;
    }

    @Override
    public void addStudentList(List<StudentData> list) {
        studentMapper.addStudentList(list);
    }

    @Override
    public Student selectByStudentId(Long studentId) {
        return studentMapper.selectByStudentId(studentId);
    }

    @Override
    public Boolean sendReminderMail(Long leaveRecordId) {
        //1. 查询出请假信息
        //2. 查询出办理人
        //3. 发送邮件
        EmailParam emailParam = new EmailParam();
        String sentText = SendEmailTemplate.reminderMail(emailParam);
        SimpleMailMessage message = new SimpleMailMessage();
        //标题
        message.setSubject("请假申请催办");
        message.setText(sentText);
        //发件人
        message.setFrom("1992999264@qq.com");
        //收件人 办理人邮箱
        message.setTo("");
        javaMailSender.send(message);
        return null;
    }

    @Override
    public Boolean updateStudentPassword(Student student, String oldPassword, String newPassword) {
        Student student1 = studentMapper.selectByStudentId(student.getStudentId());
        if (!student1.getStudentPassword().equals(oldPassword)){
            throw new BusinessException(BusinessErrorCode.PASSWORD_FALSE);
        }else {
            studentMapper.updateStudentPassword(student.getStudentId(),newPassword);
        }
        return Boolean.TRUE;
    }

    private void checkClazz(Student student) {
        // 查询班级是否存在
        Clazz clazz = clazzMapper.selectByClazzNo(student.getClazz().getClazzNo());
        if (clazz == null) {
            throw new BusinessException(BusinessErrorCode.CLAZZ_NOT_EXIST);
        }
        // 查询专业是否存在
        Specialty specialty = specialtyMapper.selectBySpecialtyNo(clazz.getSpecialty().getSpecialtyNo());
        if (specialty == null) {
            throw new BusinessException(BusinessErrorCode.SPECIALTY_NOT_EXIST);
        }
        if (!clazz.getDepartment().getDeptNo().equals(specialty.getDepartment().getDeptNo())) {
            throw new BusinessException(BusinessErrorCode.SPECIALTY_NOT_IN_DEPARTMENT);
        }
        Department department = departmentMapper.selectByDeptNo(clazz.getDepartment().getDeptNo());
        if (department == null) {
            throw new BusinessException(BusinessErrorCode.DEPARTMENT_NOT_EXIST);
        }
    }
}
