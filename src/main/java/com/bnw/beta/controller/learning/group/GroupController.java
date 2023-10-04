package com.bnw.beta.controller.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.service.learning.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/educator/group")
@RequiredArgsConstructor
public class  GroupController{

    @Autowired
    private GroupService groupService;

    //그룹 등록 가능한 게임 콘텐츠 목록
    @GetMapping("/addList")
    public String groupAddList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                               @RequestParam(name = "title", defaultValue = "")String game_title, Model model){

        int limit = 6;


        if(game_title.equals("null") || game_title.equals("")){
            game_title="전체";
        }
        model.addAttribute("title", game_title);
        model.addAttribute("groupAddList", groupService.groupAddList(game_title, limit, offset));
        model.addAttribute("gameTitle", groupService.selectGameTitle());
        model.addAttribute("totalCnt" , groupService.groupAddListCnt(game_title));
        return "/educator/group/groupAddList";
    }

    //그룹 등록 가능한 게임 콘텐츠 목록(추가 데이터 불러오기)
    @PostMapping("/addList")
    @ResponseBody
    public List<GroupDTO> groupAddList(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "title", defaultValue = "") String game_title) {


        int limit = 6;

        return groupService.groupAddList(game_title, limit, offset);
    }

    @GetMapping("/add")
    public String addGroup(@RequestParam("game_no") int game_no, Model model){

        model.addAttribute("gameGroupInfo", groupService.gameGroupInfo(game_no));
        return "/educator/group/groupAdd";
    }

    @PostMapping("/addInsert")
    public String addGroupInsert(
            @RequestParam("game_no") int game_no,
            @RequestParam("groupName") String groupName,
            @RequestParam("count") int count,
            @RequestParam("sdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sdate,
            @RequestParam("edate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date edate){
        String id = "baduk";

        System.out.println(sdate);
        int result = groupService.insertGroup(game_no,id, groupName, count, sdate, edate);
        return "/educator/group/groupAddList";
    }

    @GetMapping("/list")
    public String grouplist(@RequestParam(name = "groupName", defaultValue = "") String group_name, Model model){

        System.out.println("그룹네임 초기값 확인"+group_name);

        String member_id = "baduk";

        model.addAttribute("groupName", groupService.selectGroupName(member_id));
        model.addAttribute("groupList", groupService.groupListSelect(member_id, group_name));
        return "/educator/group/groupList";
    }
}

