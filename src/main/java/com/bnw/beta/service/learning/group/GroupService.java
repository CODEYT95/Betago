package com.bnw.beta.service.learning.group;

import com.bnw.beta.domain.common.paging.GroupPageDTO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GroupService {

    //그룹 등록 가능한 게임콘텐츠 Cnt
    int groupAddListCnt(String memeber_id, String game_title);

    //게임 콘텐츠 title 조회
    List<GroupDTO> selectGameTitle(String member_id);

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    List<GroupDTO> groupAddList(String member_id, String game_title, int limit, int offset);

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    GroupDTO gameGroupInfo(int pay_no);

    //학습 그룹 등록(상세)
    int insertGroup(GroupDTO groupDTO, String id, Date sdate, Date edate);

    //학습 그룹 조회 목록 불러오기
    List<GroupDTO> groupListSelect(String member_id, String group_name);

    //학습 그룹 name 조회
    List<GroupDTO> selectGroupName(String member_id);

    //학습 그룹 상세 조회
    List<GroupDTO> selectGroupDetail(int group_no, String group_name);

    //특정 학습 그룹 정보 불러오기
    GroupDTO selectGroupUpdate(int group_no, String member_id);

    //학습 그룹 삭제
    String deleteGroup(List<Integer> group_no);

    //학습 그룹 수정
    int updateGroup(GroupDTO groupDTO);

    //그룹 등록 가능한 인원수 구하기 위한 쿼리
    int currentGroupCnt(int group_no, int game_no, String member_id);

    //그룹 학생 가입 승인 목록
    GroupPageDTO selectGroupApprove(@Param("member_id") String member_id, @Param("group_no") Integer group_no, @Param("page") int page, @Param("size") int size);

    //그룹 학생 가입승인 정보
    GroupDTO selectGroupInfo(int group_no);

    //그룹 학생 목록 업데이트
    String updateGroupMembber(List<Integer> approveList, List<Integer> rejectList, int group_no);

    //////////////////학습자////////////////////

    //학습 그룹 가입 신청 목록
    List<GroupDTO> selectJoinGroup(int member_no, String group_name, String educator_name, int limit, int offset);

    //그룹 가입신청 가능한 목록 갯수
    int joinGroupCount(int member_no, String educator_name, String group_name);

    //그룹명 목록 불러오기
    List<GroupDTO> selectGroupTitle();

    //교육자명 불러오기
    List<GroupDTO> selectEducatorName();

    //그룹 신청 가능 실시간 체크
    String checkJoin(int group_no, int game_no, int member_no);

    //가입신청 내역 목록
    List<GroupDTO> myjoinList(int member_no, String group_name, String educator_name, int limit, int offset);

    //가입신청 내역 목록 갯수
    int myjoinListCount(int member_no, String group_name, String educator_name);

    //가입신청 내역 그룹명 목록
    List<GroupDTO> myjoinListTitle(int member_no);

    //가입신청 교육자 타이틀 목록
    List<GroupDTO> myjoinListEducator(int member_no);

}
