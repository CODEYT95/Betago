package com.bnw.beta.service.learning.Task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.domain.learning.dto.TaskSendDTO;
import com.bnw.beta.domain.learning.dto.TaskSubmitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskService {
    /*교육자 부분--------------------------------------------------------*/
    //숙제 생성
    int createTask(String member_id, String task_title, String task_content, String task_chapter, String task_deadline);

    //전송한 숙제 조회
    TaskPageDTO sendTaskList(@Param("member_id") String member_id, @Param("page") int page, @Param("size") int size);

    //숙제 조회하기
    List<String> selectTaskTitle(String member_id);
    List<TaskDTO> selectTaskByTitle(String task_title, String member_id);

    //그룹 조회하기
    List<String> selectGroupName(String member_id);
    List<GroupDTO> selectGroupByName(String group_name, String member_id);

    //숙제 전송하기
    String sendTask(List<Integer> task_no,  List<Integer> member_no, Integer group_no, String member_id);


    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    List<TaskDTO> selectTaskById(Integer member_no);

    //숙제 번호로 정보 조회
    TaskSendDTO selectTaskByNo(Integer tasksend_no, Integer member_no);

    //숙제 작성
    int wirteTask(int tasksend_no, int task_no,  String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add, String member_id);

    //작성한 숙제 조회
    TaskSubmitDTO modifyTask(int tasksend_no, int member_no);

    //숙제 수정
    int ModifySubmitTask(int tasksubmit_no, String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add);

    //숙제 전송
    int submitTask(List<Integer> tasksend_no);

    //제출 숙제 조회
    List<TaskSubmitDTO> selectSubmitTask(Integer member_no);

    //평가 완료된 숙제 조회
    TaskSubmitDTO selectSubmitTaskByNo(int tasksend_no, int member_no);
}

