package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import jakarta.servlet.http.HttpSession;
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
    @GetMapping("joinList")
    public String groupJoin(@RequestParam(name = "offset", defaultValue = "0") int offset,
                            @RequestParam(name = "group_name", defaultValue = "") String group_name,
                            Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        int limit = 1;

        model.addAttribute("title", groupService.selectGroupTitle());
        model.addAttribute("educator", groupService.selectEducatorName());
        model.addAttribute("totalCnt", groupService.joinGroupCount(member_no, group_name));
        model.addAttribute("groupJoinList", groupService.selectJoinGroup(member_no,group_name,limit,offset));
        return "/learning/group/student/groupJoin";
    }

    //학습그룹 가입신청 목록(추가 목록)
    @PostMapping("joinList")
    @ResponseBody
    public List<GroupDTO> groupJoinAdd(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "group_name", defaultValue = "") String group_name,
                                       Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        int limit = 1;

        return groupService.selectJoinGroup(member_no, group_name,limit,offset);
    }

    //그룹 가능 실시간 체크
    @PostMapping("join")
    @ResponseBody
    public String join(@RequestParam(name = "group_no") int group_no,
                       @RequestParam(name = "game_no") int game_no, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        if(groupService.checkJoin(group_no).equals("applyable")){
            groupService.insertGroupJoin(member_no, group_no, game_no);
            groupService.updateGroupJoin(group_no);
            return "applyable";

        }else{
            System.out.println("실패");
            return "unapplyable";
        }

    }
}
