package com.bnw.beta.domain.learning.dao;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupDAO {

    //그룹 등록 가능한 게임콘텐츠 Cnt
    int groupAddListCnt(String game_title);

    //게임 콘텐츠 title 조회
    List<GroupDTO> selectGameTitle();

    //그룹 등록 가능한 게임콘텐츠 조회(무한스크롤)
    List<GroupDTO> groupAddList(GroupDTO groupDTO);

    //그룹 등록을 위한 게임 콘텐츠 정보 불러오기
    List<GroupDTO> gameGroupInfo(int game_no);

    //학습 그룹 등록(상세)
    int insertGroup(GroupDTO groupDTO);

    //학습 그룹 조회 목록 불러오기
    List<GroupDTO> groupListSelect(GroupDTO groupDTO);

    //학습 그룹 name 조회
    List<GroupDTO> selectGroupName(String member_id);

    //학습 그룹 삭제
    int deleteGroup(List<Integer> group_no);
}
