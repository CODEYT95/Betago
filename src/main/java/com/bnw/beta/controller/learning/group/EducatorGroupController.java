package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.common.paging.GroupPageDTO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import com.bnw.beta.service.member.MemberService;
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
                               @RequestParam(name = "title", defaultValue = "전체")String game_title,
                               Principal principal, Model model){
        int limit = 6;
        model.addAttribute("addList",1);
        model.addAttribute("title", game_title);
        model.addAttribute("groupAddList", groupService.groupAddList(principal.getName(), game_title, limit, offset));
        System.out.println(groupService.groupAddList(principal.getName(), game_title, limit, offset));
        model.addAttribute("gameTitle", groupService.selectGameTitle(principal.getName()));
        model.addAttribute("totalCnt" , groupService.groupAddListCnt(principal.getName(), game_title));
        return "/learning/group/educator/groupAddList";
    }

    //그룹 등록 가능한 게임 콘텐츠 목록 POST (추가 데이터 불러오기)
    @PostMapping("/addList")
    @ResponseBody
    public List<GroupDTO> groupAddList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "title", defaultValue = "",required = false) String game_title,
                                       Principal principal) {

        int limit = 6;

        return groupService.groupAddList(principal.getName(), game_title, limit, offset);
    }

    //그룹 등록
    @GetMapping("/add")
    public String addGroup(@RequestParam("pay_no") int pay_no, Principal principal, Model model){
        model.addAttribute("member_name",memberService.getMemberInfo(principal.getName()).getMember_name());
        model.addAttribute("gameGroupInfo", groupService.gameGroupInfo(pay_no));
        return "/learning/group/educator/groupAdd";
    }

    //그룹 등록 내용 Insert
    @PostMapping("/addInsert")
    public String addGroupInsert(Principal principal, GroupDTO groupDTO,
                                 @RequestParam("sdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sdate,
                                 @RequestParam("edate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date edate){

        int result = groupService.insertGroup(groupDTO, principal.getName(),sdate, edate);
        return "redirect:/educator/group/addList";
    }

    //학습 그룹 조회
    @GetMapping("/list")
    public String groupList(@RequestParam(name = "groupName", defaultValue = "전체") String group_name, Principal principal, Model model){

        String member_id = principal.getName();

        model.addAttribute("list",1);
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

    //학습 그룹 수정
    @GetMapping("/modify")
    public String groupModify(@RequestParam(name = "group_no") int group_no,Principal principal, Model model){
        model.addAttribute("groupInfo", groupService.selectGroupUpdate(group_no, principal.getName()));
        model.addAttribute("currentCnt", groupService.currentGroupCnt(group_no, groupService.selectGroupUpdate(group_no,principal.getName()).getGame_no(), principal.getName()));
        System.out.println(groupService.selectGroupUpdate(group_no, principal.getName()));
        return "/learning/group/educator/groupModify";
    }

    @PostMapping("/modify")
    public String groupModify(GroupDTO groupDTO){
        groupService.updateGroup(groupDTO);
        return "redirect:/educator/group/list";
    }

    //학습 그룹 삭제
    @PostMapping("/delete")
    @ResponseBody
    public String groupDelete(@RequestParam(name = "group_no") List<Integer> group_no) {
        return groupService.deleteGroup(group_no);
    }


    //그룹 학습자 가입 승인
    @GetMapping("/approveList")
    public String approveStudent(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 @RequestParam(value = "group_no", required = false) Integer group_no,
                                 Model model, Principal principal){

        GroupPageDTO groupPageDTO = groupService.selectGroupApprove(principal.getName(), group_no, page,size);
        model.addAttribute("currentPage", groupPageDTO.getCurrentPage());
        model.addAttribute("listCount", groupPageDTO.getListCount());
        model.addAttribute("GroupPageDTO", groupPageDTO);
        model.addAttribute("group_name", groupService.selectGroupName(principal.getName()));
        model.addAttribute("check",group_no);
        if(group_no != null){
            model.addAttribute("groupInfo",groupService.selectGroupInfo(group_no));
        }
        model.addAttribute("approveList",1);
        return "/learning/group/educator/joinApprove";
    }

    //그룹 학생 목록 업데이트
    @PostMapping("/approveUpdate")
    @ResponseBody
    public String updateGroupMembber(@RequestParam(value = "approve[]", required = false) List<Integer> approveList,
                                     @RequestParam(value = "reject[]", required = false) List<Integer> rejectList,
                                     @RequestParam(value = "group_no", required = false) int group_no){

        return groupService.updateGroupMembber(approveList, rejectList, group_no);
    }
}