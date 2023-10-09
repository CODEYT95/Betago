package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student/group")
public class StudentGroupController {
    @Autowired
    GroupService groupService;

    //학습그룹 가입신청 목록
    @GetMapping("join")
    public String groupJoin(@RequestParam(name = "offset", defaultValue = "0") int offset,
                            @RequestParam(name = "group_name", defaultValue = "") String group_name,
                            Model model){

        int limit = 1;

        model.addAttribute("totalCnt", groupService.joinGroupCount());
        model.addAttribute("groupJoinList", groupService.selectJoinGroup(group_name,limit,offset));
        return "/learning/group/student/groupJoin";
    }

    //학습그룹 가입신청 목록(추가 목록)

    @PostMapping("join")
    @ResponseBody
    public List<GroupDTO> groupJoinAdd(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "group_name", defaultValue = "") String group_name,
                                       Model model){

        int limit = 1;

        return groupService.selectJoinGroup(group_name,limit,offset);
    }
}
