package com.bnw.beta.domain.learning.dao;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupDAO {

    //그룹 등록 가능한 게임콘텐츠 Cnt
    int groupAddListCnt(@Param("member_id") String memeber_id, @Param("game_title") String game_title);

    //게임 콘텐츠 title 조회
    List<GroupDTO> selectGameTitle(String member_id);

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    List<GroupDTO> groupAddList(GroupDTO groupDTO);

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    GroupDTO gameGroupInfo(int pay_no);

    //학습 그룹 등록(상세)
    int insertGroup(GroupDTO groupDTO);

    //학습 그룹 조회 목록 불러오기
    List<GroupDTO> groupListSelect(GroupDTO groupDTO);

    //학습 그룹 name 조회
    List<GroupDTO> selectGroupName(String member_id);

    //학습 그룹 상세 조회
    List<GroupDTO> selectGroupDetail(GroupDTO groupDTO);

    //특정 학습 그룹 정보 불러오기
    GroupDTO selectGroupUpdate(GroupDTO groupDTO);

    //학습 그룹 삭제
    int deleteGroup(List<Integer> group_no);

    //학습 그룹 수정
    int updateGroup(GroupDTO groupDTO);

    //그룹 등록 가능한 인원수 구하기 위한 쿼리
    int currentGroupCnt(GroupDTO groupDTO);

    //그룹 학생 가입 승인 목록
    List<GroupDTO> selectGroupApprove(@Param("member_id") String member_id, @Param("group_no") Integer group_no, @Param("page") int page, @Param("size") int size);

    //그룹 학생 가입 승인 목록 Count
    int selectGroupApproveCount(@Param("member_id") String member_id, @Param("group_no") Integer group_no);

    //그룹 학생 가입승인 정보
    GroupDTO selectGroupInfo(int group_no);

    //그룹 학생 목록 업데이트
    int updateGroupMembber(@Param("state") String state, @Param("list") List<Integer> updateList);

    //////////////////학습자////////////////////

    //학습 그룹 가입 신청 목록
    List<GroupDTO> selectJoinGroup(GroupDTO groupDTO);

    //그룹 가입신청 가능한 목록 갯수
    int joinGroupCount(GroupDTO groupDTO);

    //그룹명 목록 불러오기
    List<GroupDTO> selectGroupTitle();

    //교육자명 불러오기
    List<GroupDTO> selectEducatorName();

    //그룹 신청 가능 실시간 체크
    int checkJoin(int group_no);

    //그룹 신청 가능 실시간 체크2
    int checkJoin2(GroupDTO groupDTO);

    //학생 그룹 가입신청 Insert
    int insertGroupJoin(GroupDTO groupDTO);

    //학생 그룹 현재인원 Update
    int updateGroupJoin(@Param("num")int num, @Param("group_no") int group_no);

    //가입신청 내역 목록
    List<GroupDTO> myjoinList(GroupDTO groupDTO);

    //가입신청 내역 목록 갯수
    int myjoinListCount(GroupDTO groupDTO);

    //가입신청 내역 그룹명 목록
    List<GroupDTO> myjoinListTitle(int member_no);

    //가입신청 교육자 타이틀 목록
    List<GroupDTO> myjoinListEducator(int member_no);
}
