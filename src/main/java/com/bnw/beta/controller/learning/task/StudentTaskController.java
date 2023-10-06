package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentTaskController {

    @Autowired
    private TaskService taskService;

    //전송된 숙제 조회하기
    @GetMapping("/taskList/{member_no}")
    public String selectTaskById(@RequestParam(name = "tasksend_no", defaultValue = "1") int tasksend_no, @PathVariable int member_no, Model model){
        member_no = 99;
        System.out.println("들어옴");
        List<TaskDTO> taskList = taskService.selectTaskById(member_no);
        List<TaskDTO> tasksend = taskService.selectTaskByNo(tasksend_no, member_no);
        System.out.println(taskList);
        model.addAttribute("taskList", taskList);
        model.addAttribute("tasksend", tasksend);
        return "learning/task/student/submitTask";
    }
}
