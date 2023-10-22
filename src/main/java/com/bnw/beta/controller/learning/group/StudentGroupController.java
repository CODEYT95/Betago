package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.connector.Request;
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
                            @RequestParam(name = "educator_name", defaultValue = "") String educator_name,
                            Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        int limit = 6;

        System.out.println(groupService.selectJoinGroup(member_no, educator_name, group_name,limit,offset));
        model.addAttribute("joinList", 1);
        model.addAttribute("groupTitle", group_name);
        model.addAttribute("educatorTitle", educator_name);
        model.addAttribute("title", groupService.selectGroupTitle());
        model.addAttribute("educator", groupService.selectEducatorName());
        model.addAttribute("totalCnt", groupService.joinGroupCount(member_no, educator_name, group_name));
        model.addAttribute("groupJoinList", groupService.selectJoinGroup(member_no, group_name, educator_name, limit, offset));
        return "/learning/group/student/groupJoin";
    }

    //학습그룹 가입신청 목록(추가 목록)
    @PostMapping("joinList")
    @ResponseBody
    public List<GroupDTO> groupJoinAdd(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "group_name", defaultValue = "") String group_name,
                                       @RequestParam(name = "educator_name", defaultValue = "") String educator_name,
                                       Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        int limit = 6;

        return groupService.selectJoinGroup(member_no, educator_name, group_name,limit,offset);
    }

    //그룹 가능 실시간 체크
    @PostMapping("join")
    @ResponseBody
    public String join(@RequestParam(name = "group_no") int group_no,
                       @RequestParam(name = "game_no") int game_no, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");
            return groupService.checkJoin(group_no, game_no, member_no);
    }

    //가입신청 내역
    @GetMapping("myJoinList")
    public String myJoinList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                             @RequestParam(name = "group_name", defaultValue = "") String group_name,
                             @RequestParam(name = "educator_name", defaultValue = "") String educator_name,
                             Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        System.out.println(member_no);
        System.out.println(groupService.myjoinListTitle(member_no));

        int limit = 6;

        model.addAttribute("myJoinList", 1);
        model.addAttribute("groupTitle", group_name);
        model.addAttribute("educatorTitle", educator_name);
        model.addAttribute("title", groupService.myjoinListTitle(member_no));
        model.addAttribute("educator", groupService.myjoinListEducator(member_no));//나중에 title이랑 합치기 중복됨
        model.addAttribute("totalCnt", groupService.myjoinListCount(member_no, group_name, educator_name));
        model.addAttribute("groupJoinList", groupService.myjoinList(member_no, group_name, educator_name, limit, offset));

        System.out.println(groupService.myjoinList(member_no, group_name, educator_name, limit, offset));
        System.out.println(groupService.myjoinListTitle(member_no));

        return "/learning/group/student/myGroupList";
    }



    //가입신청 내역
    @PostMapping("myJoinList")
    @ResponseBody
    public List<GroupDTO> myJoinListAdd(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "group_name", defaultValue = "") String group_name,
                                       @RequestParam(name = "educator_name", defaultValue = "") String educator_name,
                                       Model model, HttpSession session){

        int member_no = (int) session.getAttribute("member_no");

        int limit = 6;

        return groupService.myjoinList(member_no, group_name, educator_name, limit, offset);
    }
}
