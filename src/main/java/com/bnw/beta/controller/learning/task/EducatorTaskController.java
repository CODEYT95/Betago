package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/educator")
public class EducatorTaskController {
    @Autowired
    private TaskService taskService;

    //숙제 생성 폼 보여주기 (페이징)
    @GetMapping("/saveTaskForm")
    public String saveTaskForm(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                    @RequestParam(value = "member_id", defaultValue = "dumy") String member_id,
                                                     Model model){

        int listCount = taskService.sendTaskListCount(member_id);
        int totalPages = (int) Math.ceil((double) listCount / size);
        page = Math.min(Math.max(1, page), totalPages);
        List<TaskDTO> taskList = taskService.sendTaskList("dumy", page, size);
        TaskPageDTO taskPageDTO = new TaskPageDTO(listCount, page, size, taskList);

        model.addAttribute("currentPage", page);
        model.addAttribute("listCount", listCount);
        model.addAttribute("taskPageDTO", taskPageDTO);

        return "learning/task/educator/saveTask";
    }

    //숙제 생성하기
    @PostMapping("/saveTask")
    public String saveTask(String member_id, @RequestParam String task_title,
                           @RequestParam String task_content, @RequestParam String task_chapter,
                           @RequestParam String year, @RequestParam String month,
                           @RequestParam String day, Model model){
        member_id = "dumy";

        String task_deadline = year+"-"+month+"-"+day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date endDate = sdf.parse(task_deadline);

            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setMember_id(member_id);
            taskDTO.setTask_title(task_title);
            taskDTO.setTask_content(task_content);
            taskDTO.setTask_chapter(task_chapter);
            taskDTO.setTask_deadline(endDate);

            int result = taskService.createTask(taskDTO);
            if (result == 1) {
                return "redirect:learning/task/educator/saveTaskForm";
            } else {
                model.addAttribute("errorMessage", "다시 시도해주세요.");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "learning/task/educator/saveTask";
    }

}
