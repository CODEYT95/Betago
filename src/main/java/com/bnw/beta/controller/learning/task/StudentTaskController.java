package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentTaskController {

    @Autowired
    private TaskService taskService;

    //전송된 숙제 조회하기
    @GetMapping("/taskList")
    public String selectTaskById(Model model, HttpSession session){
        Integer member_no = (Integer) session.getAttribute("member_no");
        if (member_no != null) {
            List<TaskDTO> taskList = taskService.selectTaskById(member_no);
            model.addAttribute("member_name", session.getAttribute("member_name"));
            model.addAttribute("taskList", taskList);
        }
        return "learning/task/student/taskList";
    }

    //모달창 숙제 정보 불러오기
    @PostMapping("/taskDetail")
    @ResponseBody
    public TaskDTO taskDetail(@RequestParam int tasksend_no){
        TaskDTO taskDetail = taskService.selectTaskByNo(tasksend_no);
        return taskDetail;
    }

    //숙제 저장하기 (미작성 -> 작성중)
    @PostMapping("/saveTask")
    public String saveTask(Authentication authentication, @RequestParam int tasksend_no, @RequestParam int task_no,
                           @RequestParam String tasksubmit_chapter, @RequestParam String tasksubmit_content,
                           @RequestParam String tasksubmit_add){

        String member_id = authentication.getName();
        int result = taskService.wirteTask(tasksend_no, task_no, tasksubmit_chapter, tasksubmit_content, tasksubmit_add, member_id);
        if(result > 0){
            return "redirect:/student/taskList";
        }
        return "redirect:/student/taskDetail/" + tasksend_no;
    }

    //숙제 수정페이지
    @PostMapping("/modify")
    @ResponseBody
    public TaskDTO taskModify(int tasksend_no){
        TaskDTO taskSubmit = taskService.modifyTask(tasksend_no);
        return taskSubmit;
    }

    //숙제 수정하기
    @PostMapping("/taskModify")
    public String taskModify(@RequestParam int tasksend_no, @RequestParam String tasksubmit_chapter,
                             @RequestParam String tasksubmit_content, @RequestParam String tasksubmit_add,
                             @RequestParam String task_state){

        int result = taskService.ModifySubmitTask(tasksend_no,tasksubmit_chapter,tasksubmit_content,tasksubmit_add);
        if(task_state.equals("작성중") && result > 0) {
            return "redirect:/student/taskList";

        } else if(task_state.equals("제출완료") && result > 0) {
            return "redirect:/student/submitTaskList";
        }
        return "error";
    }

    //숙제 제출하기
    @PostMapping("/submitTask")
    public String submitTask(@RequestParam("tasksend_no[]") List<Integer> tasksend_no){
        int result = taskService.submitTask(tasksend_no);
        if(result > 0){
            return "redirect:/student/submitTaskList";
        }
        return "redirect:/student/taskList";
    }

    //제출 숙제 조회
    @GetMapping("/submitTaskList")
    public String selectSubmitTask(Model model, HttpSession session, @RequestParam(name = "offset", defaultValue = "0") int offset){
        Integer member_no = (Integer) session.getAttribute("member_no");
        if (member_no != null) {
            int limit = 1;
            List<TaskDTO> submitList =  taskService.selectSubmitTask(member_no, limit, offset);
            int totalCount = taskService.submitTaskCount(member_no);

            model.addAttribute("submitTaskList", 1);
            model.addAttribute("member_name", session.getAttribute("member_name"));
            model.addAttribute("submitList", submitList);
            model.addAttribute("totalCount", totalCount);
        }
        return "learning/task/student/submitTaskList";
    }

    //무한 스크롤
    @PostMapping("/submitList")
    @ResponseBody
    public List<TaskDTO> submitList(@RequestParam(name = "offset", defaultValue = "0") int offset, HttpSession session){
        Integer member_no = (Integer) session.getAttribute("member_no");
        int limit = 1;
        List<TaskDTO> submitTask = taskService.selectSubmitTask(member_no, limit, offset);
        return submitTask;
    }

    //평가 완료된 숙제 조회
    @PostMapping("/viewEval")
    @ResponseBody
    public TaskDTO selectSubmitTaskByNo(@RequestParam int tasksend_no){
        TaskDTO taskEval = taskService.selectSubmitTaskByNo(tasksend_no);
        return taskEval;
    }
}