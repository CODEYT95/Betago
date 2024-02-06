package com.bnw.beta.service.learning.group;

import com.bnw.beta.domain.common.paging.GroupPageDTO;
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
    public int groupAddListCnt(String memeber_id, String game_title){

        if (game_title.equals("전체")){
            game_title="";
        }
        return groupDAO.groupAddListCnt(memeber_id, game_title);
    }

    //게임 콘텐츠 title 조회
    @Override
    public List<GroupDTO> selectGameTitle(String member_id){return groupDAO.selectGameTitle(member_id);}

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    @Override
    public List<GroupDTO> groupAddList(String member_id, String game_title, int limit, int offset) {

        GroupDTO groupDTO = new GroupDTO();
        if (game_title.equals("전체")){
            game_title="";
        }
        groupDTO.setMember_id(member_id);
        groupDTO.setGame_title(game_title);
        groupDTO.setLIMIT(limit);
        groupDTO.setOFFSET(limit*offset);
        System.out.println(groupDTO);
        List<GroupDTO> grouplist = groupDAO.groupAddList(groupDTO);
        return grouplist;
    }

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    @Override
    public GroupDTO gameGroupInfo(int pay_no) {
        return groupDAO.gameGroupInfo(pay_no);
    }

    //학습 그룹 등록(상세)
    @Override
    public int insertGroup(GroupDTO groupDTO, String id, Date sdate, Date edate){

        groupDTO.setMember_id(id);
        java.sql.Date sqlStartDate = new java.sql.Date(sdate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(edate.getTime());

        groupDTO.setGroup_startdate(sqlStartDate);
        groupDTO.setGroup_enddate(sqlEndDate);
        System.out.println("insert"+groupDTO);
        return groupDAO.insertGroup(groupDTO);
    }


    //학습 그룹 조회 목록 불러오기
    @Override
    public List<GroupDTO> groupListSelect(String member_id, String group_name){

        GroupDTO groupDTO = new GroupDTO();
        if (group_name.equals("전체")){
            group_name="";
        }
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_id(member_id);

        return groupDAO.groupListSelect(groupDTO);
    }


    //학습 그룹 name 조회
    @Override
    public List<GroupDTO> selectGroupName(String member_id){return groupDAO.selectGroupName(member_id);}

    //학습 그룹 상세 조회
    @Override
    public List<GroupDTO> selectGroupDetail(int group_no, String group_name) {

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_no(group_no);
        groupDTO.setGroup_name("");
        return groupDAO.selectGroupDetail(groupDTO);
    }

    //특정 학습 그룹 정보 불러오기
    @Override
    public GroupDTO selectGroupUpdate(int group_no, String member_id){

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_no(group_no);
        groupDTO.setMember_id(member_id);

        return groupDAO.selectGroupUpdate(groupDTO);
    }

    //학습 그룹 삭제
    @Override
    public String deleteGroup(List<Integer> group_no){

        int result = groupDAO.deleteGroup(group_no);
        if(result == 0){

            return "fail";

        }else{

            return "success";

        }
    }

    //그룹 등록 가능한 인원수 구하기 위한 쿼리
    public int currentGroupCnt(int group_no, int game_no, String member_id){

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_no(group_no);
        groupDTO.setGame_no(game_no);
        groupDTO.setMember_id(member_id);

        return groupDAO.currentGroupCnt(groupDTO);
    }

    //학습 그룹 수정
    @Override
    public int updateGroup(GroupDTO groupDTO){return groupDAO.updateGroup(groupDTO);}

    //그룹 학생 가입 승인 목록
    @Override
    public GroupPageDTO selectGroupApprove(String member_id, Integer group_no, int pageNum, int size){
        if(pageNum <= 0){
            pageNum = 1;
        }
        int offset = (pageNum - 1) * size;

        List<GroupDTO> groupList = groupDAO.selectGroupApprove(member_id, group_no, offset, size);
        int listCount = groupDAO.selectGroupApproveCount(member_id, group_no);

        GroupPageDTO groupPageDTO = new GroupPageDTO(listCount, pageNum, size, groupList);
        groupPageDTO.setListCount(listCount);

        return groupPageDTO;
    }

    //그룹 학생 가입승인 정보
    @Override
    public GroupDTO selectGroupInfo(int group_no){return groupDAO.selectGroupInfo(group_no);};

    @Override
    //그룹 학생 목록 업데이트
    public String updateGroupMembber(List<Integer> approveList, List<Integer> rejectList, int group_no){

        String state;

        if(approveList != null){
            state = "승인";
            groupDAO.updateGroupMembber(state,approveList);
            groupDAO.updateGroupJoin(approveList.size(), group_no);
        }

        if(rejectList != null){
            state = "거부";
            groupDAO.updateGroupMembber(state,rejectList);
        }
        return "update";
    }

    ////////////////////////////////////////////////////////////////////////

    //학습 그룹 가입 신청 목록
    @Override
    public List<GroupDTO> selectJoinGroup(int member_no, String group_name, String educator_name, int limit, int offset){

        if (group_name.equals("전체")){
            group_name="";
        }

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setMember_no(member_no);
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_name(educator_name);
        groupDTO.setLIMIT(limit);
        groupDTO.setOFFSET(offset*limit);

        return groupDAO.selectJoinGroup(groupDTO);}

    //그룹 가입신청 가능한 목록 갯수
    @Override
    public int joinGroupCount(int member_no, String educator_name, String group_name){

        if (group_name.equals("전체")){
            group_name="";
        }

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setMember_no(member_no);
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_name(educator_name);

        return groupDAO.joinGroupCount(groupDTO);}

    //그룹명 목록 불러오기
    @Override
    public List<GroupDTO> selectGroupTitle(){return groupDAO.selectGroupTitle();}

    //교육자명 불러오기
    @Override
    public List<GroupDTO> selectEducatorName(){return groupDAO.selectEducatorName();}

    //그룹 신청 가능 실시간 체크
    @Override
    public String checkJoin(int group_no, int game_no, int member_no) {

        int result = groupDAO.checkJoin(group_no);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_no(group_no);
        groupDTO.setMember_no(member_no);
        groupDTO.setGame_no(game_no);

        System.out.println("test"+groupDAO.checkJoin2(groupDTO));

        if(groupDAO.checkJoin2(groupDTO) > 0){
            return "already";
        }else{
            if (result > 0) {
                groupDAO.insertGroupJoin(groupDTO);
                return "applyable";
            } else {
                return "unapplyable";
            }
        }

    }

    //가입신청 내역 목록
    @Override
    public List<GroupDTO> myjoinList(int member_no, String group_name, String educator_name, int limit, int offset){

        if (group_name.equals("전체")){
            group_name="";
        }

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setMember_no(member_no);
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_name(educator_name);
        groupDTO.setLIMIT(limit);
        groupDTO.setOFFSET(offset*limit);

        return groupDAO.myjoinList(groupDTO);
    }

    //가입신청 내역 목록 갯수
    @Override
    public int myjoinListCount(int member_no, String group_name, String educator_name){

        if (group_name.equals("전체")){
            group_name="";
        }

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setMember_no(member_no);
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_name(educator_name);

        return groupDAO.myjoinListCount(groupDTO);
    }

    //가입신청 내역 그룹명 목록
    @Override
    public List<GroupDTO> myjoinListTitle(int member_no){
        return groupDAO.myjoinListTitle(member_no);
    }

    //가입신청 교육자 타이틀 목록
    @Override
    public List<GroupDTO> myjoinListEducator(int member_no){
        return groupDAO.myjoinListEducator(member_no);
    }
}
