package com.bnw.beta.domain.learning.dao;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.domain.learning.dto.TaskSendDTO;
import com.bnw.beta.domain.learning.dto.TaskSubmitDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskDAO {

    /*교육자 부분--------------------------------------------------------*/
    //숙제 생성
    int createTask(TaskDTO taskDTO);

    //생성한 숙제 조회
    List<TaskDTO> createTaskList(@Param("member_id") String member_id, @Param("page") int page, @Param("size") int size);
    int sendTaskListCount(String member_id);

    //숙제 조회하기
    List<String> selectTaskTitle(String member_id);
    List<TaskDTO> selectTaskByTitle(@Param("task_title") String task_title, @Param("member_id") String member_id, @Param("page") int page, @Param("size") int size);
    int countTasksByTitle(TaskDTO taskDTO);

    //그룹 조회하기
    List<GroupDTO> selectGroupName(String member_id);
    List<GroupDTO> selectGroupByName(GroupDTO groupDTO);

    //숙제 전송하기
    int sendTask(TaskSendDTO taskSendDTO);

    //전송한 숙제 조회하기


    //제출된 숙제 조회하기


    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    List<TaskDTO> selectTaskById(int member_no);

    //숙제 번호로 정보 조회
    TaskSendDTO selectTaskByNo(TaskSendDTO taskSendDTO);

    //숙제 작성
    int wirteTask(TaskSubmitDTO taskSubmitDTO);
    int saveTask(int tasksend_no);

    //숙제 수정
    TaskSubmitDTO modifyTask(TaskSubmitDTO taskSubmitDTO);
    int ModifySubmitTask(TaskSubmitDTO taskSubmitDTO);

    //숙제 제출
    int submitTask(List<Integer> tasksend_no);

    //제출 숙제 조회
    List<TaskSubmitDTO> selectSubmitTask(int member_no);

    //평가 완료된 숙제 조회
    TaskSubmitDTO selectSubmitTaskByNo(TaskSubmitDTO taskSubmitDTO);
}
