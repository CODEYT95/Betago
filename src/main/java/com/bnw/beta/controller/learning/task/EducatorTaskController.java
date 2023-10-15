package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/educator")
public class EducatorTaskController {
    @Autowired
    private TaskService taskService;

    //숙제 생성 폼 보여주기 (페이징)
    @GetMapping("/createTaskForm")
    public String saveTaskForm(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                        Authentication authentication, Model model) {

        String member_id = authentication.getName();
        TaskPageDTO taskPageDTO = taskService.sendTaskList(member_id, page, size);
        model.addAttribute("currentPage", taskPageDTO.getCurrentPage());
        model.addAttribute("listCount", taskPageDTO.getListCount());
        model.addAttribute("taskPageDTO", taskPageDTO);

        return "learning/task/educator/createTask";
    }

    //숙제 생성하기
    @PostMapping("/createTask")
    public String saveTask(@RequestParam String task_title, @RequestParam String task_content,
                                            @RequestParam String task_chapter, @RequestParam String year,
                                            @RequestParam String month, @RequestParam String day, Model model,
                                            Authentication authentication) {

        String member_id = authentication.getName();
        String task_deadline = year + "-" + month + "-" + day;

        int result = taskService.createTask(member_id, task_title, task_content, task_chapter, task_deadline);

        if (result == 1) {
            return "redirect:/educator/createTaskForm";
        } else {
            model.addAttribute("errorMessage", "다시 시도해주세요.");
        }

        return "learning/task/educator/createTask";
    }

    //숙제 제목 조회하기
    @GetMapping("/sendTask")
    public String selectTaskDetailByTitle(Authentication authentication, Model model, @RequestParam(defaultValue = "") String task_title){
        String member_id = authentication.getName();
        List<String> taskTitle = taskService.selectTaskTitle(member_id);
        List<TaskDTO> taskDetail = taskService.selectTaskByTitle(task_title, member_id);

        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("taskDetail", taskDetail);
        return "learning/task/educator/sendTask";
    }

    //숙제 제목으로 상세 조회
    @PostMapping("/selectTask")

    //제출된 숙제 조회하기
    @GetMapping("/evalTask")
    public String evalTask(){
        return "learning/task/educator/evalTask";
    }

}
