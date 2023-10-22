package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/educator")
public class EducatorTaskController {
    @Autowired
    private TaskService taskService;

    //숙제 생성 폼 보여주기 (페이징)
    @GetMapping("/createTaskForm")
    public String saveTaskForm(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "3") int size,
                               Authentication authentication, Model model) {

        String member_id = authentication.getName();
        TaskPageDTO taskPageDTO = taskService.createTaskList(member_id, page, size);
        model.addAttribute("createTask", 1);
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

    //숙제 제목으로 조회하기
    @GetMapping("/sendTask")
    public String selectTaskDetailByTitle(@RequestParam(defaultValue = "") String task_title,
                                          @RequestParam(defaultValue = "") String group_name,
                                          @RequestParam(defaultValue = "") Integer group_no,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "5") int size,
                                          Authentication authentication, Model model){
        if(task_title.equals("전체")){
            task_title="";
        }else {
            model.addAttribute("task_title", task_title);
        }
        String member_id = authentication.getName();
        List<String> taskTitle = taskService.selectTaskTitle(member_id);
        TaskPageDTO taskPageDTO = taskService.selectTaskByTitle(task_title, member_id,page,size);
        List<GroupDTO> groupName = taskService.selectGroupName(member_id);
        List<GroupDTO> groupDetail = taskService.selectGroupByName(group_name, member_id, group_no);

        model.addAttribute("sendTask",1);
        model.addAttribute("check",group_name);
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("groupName", groupName);
        model.addAttribute("groupDetail", groupDetail);
        model.addAttribute("taskPageDTO", taskPageDTO);
        model.addAttribute("currentPage", taskPageDTO.getCurrentPage());
        model.addAttribute("listCount", taskPageDTO.getListCount());
        model.addAttribute("taskPageDTO", taskPageDTO);

        return "learning/task/educator/sendTask";
    }

    //숙제 전송
    @PostMapping("/sendToMember")
    public String sendTask(@RequestParam("task_no[]") List<Integer> task_no,
                           @RequestParam("member_no[]") List<Integer> member_no,
                           @RequestParam ("group_no") Integer group_no, Authentication authentication){

        String member_id = authentication.getName();
        String result = taskService.sendTask(task_no, member_no, group_no, member_id);
        if(result.equals("success")){
            return "redirect:/educator/sendTask";
        }else {
            return "/error";
        }
    }

    //전송한 숙제 조회하기
    @GetMapping("/evalTask")
    public String selectSendTask(Authentication authentication, Model model, @RequestParam(defaultValue = "") String task_title){
        String member_id = authentication.getName();
        List<String> taskTitle = taskService.selectTaskTitle(member_id);
        List<TaskDTO> taskList = taskService.selectSendTask(member_id, task_title);

        model.addAttribute("evalTask",1);
        model.addAttribute("taskTitle", taskTitle);
        model.addAttribute("taskList", taskList);

        return "learning/task/educator/evalTask";
    }

    //제출된 숙제 조회
    @PostMapping("/member")
    @ResponseBody
    public List<TaskDTO> evalTaskList(Authentication authentication, @RequestParam Integer task_no) {
        String member_id = authentication.getName();
        List<TaskDTO> evalList = taskService.evalTaskList(member_id, task_no);
        return evalList;
    }

    //숙제 제출 상세 조회하기
    @GetMapping("/evalDetail")
    @ResponseBody
    public TaskDTO evalDetail(@RequestParam Integer tasksubmit_no){
        TaskDTO evalDetail = taskService.evalTaskDetail(tasksubmit_no);
        return evalDetail;
    }

    @PostMapping("/eval")
    public String insertEval(@RequestParam String tasksubmit_comment, @RequestParam String tasksubmit_eval,
                             @RequestParam Integer group_no, @RequestParam Integer member_no,
                             @RequestParam String member_level, @RequestParam Integer tasksend_no){

        int result = taskService.insertEvaluation(tasksubmit_comment, tasksubmit_eval, group_no, member_no, member_level, tasksend_no);
        if(result > 0){
            return  "redirect:/educator/evalTask";
        }
        return "/error";
    }

}