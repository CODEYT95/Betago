package com.bnw.beta.service.learning.group;

import com.bnw.beta.domain.learning.dto.GroupDTO;

import java.util.Date;
import java.util.List;

public interface GroupService {

    //그룹 등록 가능한 게임콘텐츠 Cnt
    int groupAddListCnt(String game_title);

    //게임 콘텐츠 title 조회
    List<GroupDTO> selectGameTitle();

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    List<GroupDTO> groupAddList(String game_title, int limit, int offset);

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    List<GroupDTO> gameGroupInfo(int game_no);

    //학습 그룹 등록(상세)
    int insertGroup(int game_no, String id, String groupName, int count, Date sdate, Date edate);

    //학습 그룹 조회 목록 불러오기
    List<GroupDTO> groupListSelect(String member_id, String group_name);

    //학습 그룹 name 조회
    List<GroupDTO> selectGroupName(String member_id);

    //학습 그룹 상세 조회
    List<GroupDTO> selectGroupDetail(int group_no, String group_name);

    //학습 그룹 삭제
    String deleteGroup(List<Integer> group_no);

    //////////////////학습자////////////////////

    //학습 그룹 가입 신청 목록
    List<GroupDTO> selectJoinGroup(String group_name,int limit, int offset);

    //그룹 가입신청 가능한 목록 갯수
    int joinGroupCount();
}
