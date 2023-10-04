package com.bnw.beta.service.learning.group;

import com.bnw.beta.domain.learning.dao.GroupDAO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;


    //그룹 등록 가능한 게임콘텐츠 Cnt
    @Override
    public int groupAddListCnt(String game_title){

        if (game_title.equals("전체")){
            game_title="";
        }
        return groupDAO.groupAddListCnt(game_title);
    }

    //게임 콘텐츠 title 조회
    @Override
    public List<GroupDTO> selectGameTitle(){return groupDAO.selectGameTitle();}

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    @Override
    public List<GroupDTO> groupAddList(String game_title, int limit, int offset) {

        GroupDTO groupDTO = new GroupDTO();
        if (game_title.equals("전체")){
            game_title="";
        }
        groupDTO.setGame_title(game_title);
        groupDTO.setLIMIT(limit);
        groupDTO.setOFFSET(limit*offset);

        List<GroupDTO> grouplist = groupDAO.groupAddList(groupDTO);
        return grouplist;
    }

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    @Override
    public List<GroupDTO> gameGroupInfo(int game_no) {
        return groupDAO.gameGroupInfo(game_no);
    }

    //학습 그룹 등록(상세)
    @Override
    public int insertGroup(int game_no, String id, String groupName, int count, Date sdate, Date edate){

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGame_no(game_no);
        groupDTO.setMember_id(id);
        groupDTO.setGroup_name(groupName);
        groupDTO.setGroup_cnt(count);
        java.sql.Date sqlStartDate = new java.sql.Date(sdate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(edate.getTime());

        groupDTO.setGroup_startdate(sqlStartDate);
        groupDTO.setGroup_enddate(sqlEndDate);

        return groupDAO.insertGroup(groupDTO);
    }

    @Override
    //학습 그룹 조회 목록 불러오기
    public List<GroupDTO> groupListSelect(String member_id, String group_name){

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_id(member_id);

        return groupDAO.groupListSelect(groupDTO);
    }

    @Override
    //학습 그룹 name 조회
    public List<GroupDTO> selectGroupName(String member_id){return groupDAO.selectGroupName(member_id);}
}
