package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import com.bnw.beta.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/educator/group")
@RequiredArgsConstructor
public class EducatorGroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;

    //그룹 등록 가능한 게임 콘텐츠 목록 GET
    @GetMapping("/addList")
    public String groupAddList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                               @RequestParam(name = "title", defaultValue = "전체")String game_title, Model model){

        int limit = 6;

        model.addAttribute("title", game_title);
        model.addAttribute("groupAddList", groupService.groupAddList(game_title, limit, offset));
        model.addAttribute("gameTitle", groupService.selectGameTitle());
        model.addAttribute("totalCnt" , groupService.groupAddListCnt(game_title));
        return "/learning/group/educator/groupAddList";
    }

    //그룹 등록 가능한 게임 콘텐츠 목록 POST (추가 데이터 불러오기)
    @PostMapping("/addList")
    @ResponseBody
    public List<GroupDTO> groupAddList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "title", defaultValue = "") String game_title) {

        int limit = 6;

        return groupService.groupAddList(game_title, limit, offset);
    }

    //그룹 등록
    @GetMapping("/add")
    public String addGroup(@RequestParam("game_no") int game_no, Principal principal, Model model){
        model.addAttribute("member_name",memberService.getMemberInfo(principal.getName()).getMember_name());
        model.addAttribute("gameGroupInfo", groupService.gameGroupInfo(game_no));
        return "/learning/group/educator/groupAdd";
    }

    //그룹 등록 내용 Insert
    @PostMapping("/addInsert")
    public String addGroupInsert(Principal principal,
                                 @RequestParam("game_no") int game_no,
                                 @RequestParam("groupName") String groupName,
                                 @RequestParam("count") int count,
                                 @RequestParam("sdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sdate,
                                 @RequestParam("edate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date edate){


        int result = groupService.insertGroup(game_no, principal.getName(), groupName, count, sdate, edate);
        return "/learning/group/educator/groupAddList";
    }

    //학습 그룹 조회
    @GetMapping("/list")
    public String groupList(@RequestParam(name = "groupName", defaultValue = "전체") String group_name, Model model){

        String member_id = "baduk";

        model.addAttribute("group_name", group_name);
        model.addAttribute("groupName", groupService.selectGroupName(member_id));
        model.addAttribute("groupList", groupService.groupListSelect(member_id, group_name));
        return "/learning/group/educator/groupList";
    }

    //학습 그룹 상세 조회
    @GetMapping("/listDetail")
    @ResponseBody
    public List<GroupDTO> selectGroupDetail(@RequestParam(name = "group_no") int group_no,
                                            @RequestParam(name = "group_name",defaultValue = "") String group_name){


        return groupService.selectGroupDetail(group_no,group_name);
    }


    //학습 그룹 삭제
    @PostMapping("delete")
    @ResponseBody
    public String groupDelete(@RequestParam(name = "group_no") List<Integer> group_no) {
        return groupService.deleteGroup(group_no);
    }

}

