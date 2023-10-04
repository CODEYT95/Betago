package com.bnw.beta.domain.learning.dao;

import com.bnw.beta.domain.learning.dto.TaskDTO;
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

    //전송한 숙제 조회
    List<TaskDTO> sendTaskList(@Param("member_id") String member_id, @Param("page") int page, @Param("size") int size);
    int sendTaskListCount(String member_id);

    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    List<TaskDTO> selectTaskById(int member_no);
}
