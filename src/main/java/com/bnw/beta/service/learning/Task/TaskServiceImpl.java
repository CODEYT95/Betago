package com.bnw.beta.service.learning.Task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dao.TaskDAO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskDAO taskDAO;

    /*교육자 부분--------------------------------------------------------*/
    //전송한 숙제 목록
    public TaskPageDTO createTaskList(String member_id, int pageNum, int size) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum - 1) * size;
        List<TaskDTO> taskList = taskDAO.createTaskList(member_id, offset, size);
        int listCount = taskDAO.sendTaskListCount(member_id);

        TaskPageDTO taskPageDTO = new TaskPageDTO(listCount, pageNum, size, taskList);
        taskPageDTO.setListCount(listCount);

        return taskPageDTO;
    }

    //숙제 생성
    @Override
    public int createTask(String member_id, String task_title, String task_content, String task_chapter, String task_deadline) {

        java.sql.Date endDate = java.sql.Date.valueOf(task_deadline);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setMember_id(member_id);
        taskDTO.setTask_title(task_title);
        taskDTO.setTask_content(task_content);
        taskDTO.setTask_chapter(task_chapter);
        taskDTO.setTask_deadline(endDate);

        return taskDAO.createTask(taskDTO);
    }

    //숙제 조회
    @Override
    public List<String> selectTaskTitle(String member_id) {
        return taskDAO.selectTaskTitle(member_id);
    }
    @Override
    public TaskPageDTO selectTaskByTitle(String task_title, String member_id, int pageNum, int size) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum - 1) * size;

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTask_title(task_title);
        taskDTO.setMember_id(member_id);

        List<TaskDTO> taskList = taskDAO.selectTaskByTitle(task_title, member_id, offset, size);
        int listCount = taskDAO.countTasksByTitle(taskDTO);

        TaskPageDTO taskPageDTO = new TaskPageDTO(listCount, pageNum, size, taskList);
        taskPageDTO.setListCount(listCount);

        return taskPageDTO;
    }

    //그룹 조회
    @Override
    public List<GroupDTO>selectGroupName(String member_id) {
        return taskDAO.selectGroupName(member_id);
    }
    @Override
    public List<GroupDTO> selectGroupByName(String group_name, String member_id, Integer group_no) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_name(group_name);
        groupDTO.setMember_id(member_id);
        if(group_no != null){
            groupDTO.setGroup_no(group_no);
        }
        return taskDAO.selectGroupByName(groupDTO);
    }

    //숙제 전송
    @Override
    public String sendTask(List<Integer> task_no,  List<Integer> member_no,  Integer group_no, String member_id) {
        TaskDTO taskDTO = new TaskDTO();

        for (Integer taskNo : task_no) {
            for (Integer memberNo : member_no) {
                taskDTO.setTask_no(taskNo);
                taskDTO.setMember_no(memberNo);
                taskDTO.setMember_id(member_id);
                taskDTO.setGroup_no(group_no);
                taskDAO.sendTask(taskDTO);
            }
        }
        return "success";
    }

    //전송한 숙제 조회
    @Override
    public List<TaskDTO> selectSendTask(String member_id, String task_title) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setMember_id(member_id);
        taskDTO.setTask_title(task_title);

        return taskDAO.selectSendTask(taskDTO);
    }

    //제출된 숙제 조회하기
    @Override
    public List<TaskDTO> evalTaskList(String member_id, Integer task_no) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setMember_id(member_id);
        taskDTO.setTask_no(task_no);

        return taskDAO.evalTaskList(taskDTO);
    }

    //제출된 숙제 상세 조회하기
    @Override
    public TaskDTO evalTaskDetail( Integer tasksubmiut_no) {
        return taskDAO.evalTaskDetail(tasksubmiut_no);
    }

    //숙제 평가하기
    @Override
    public int insertEvaluation(String tasksubmit_comment,  String tasksubmit_eval,
                                Integer group_no, Integer member_no, String member_level, Integer tasksend_no) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTasksend_no(tasksend_no);
        taskDTO.setTasksubmit_comment(tasksubmit_comment);
        taskDTO.setTasksubmit_eval(tasksubmit_eval);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroup_no(group_no);
        groupDTO.setMember_no(member_no);
        groupDTO.setMember_level(member_level);


        int insertResult = taskDAO.insertEvaluation(taskDTO);
        System.out.println("임플1"+insertResult);
        if(insertResult > 0){
            System.out.println("그룹디티오"+groupDTO);
            int updateResult = taskDAO.updateMemberLevel(groupDTO);
            System.out.println("임플2"+updateResult);
            if(updateResult > 0){
                return 1;
            }
        }
        return 0;
    }

    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    @Override
    public List<TaskDTO> selectTaskById(Integer member_no) {
        return taskDAO.selectTaskById(member_no);
    }

    //숙제 번호로 정보 조회
    @Override
    public TaskDTO selectTaskByNo(Integer tasksend_no) {
        return taskDAO.selectTaskByNo(tasksend_no);
    }

    //숙제 작성하기
    @Override
    public int wirteTask(Integer tasksend_no, Integer task_no, String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add, String member_id) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTasksend_no(tasksend_no);
        taskDTO.setTask_no(task_no);
        taskDTO.setMember_id(member_id);
        taskDTO.setTasksubmit_chapter(tasksubmit_chapter);
        taskDTO.setTasksubmit_content(tasksubmit_content);
        taskDTO.setTasksubmit_add(tasksubmit_add);

        int savedTaskSubmit = taskDAO.wirteTask(taskDTO);
        int result = taskDAO.saveTask(tasksend_no);

        return (savedTaskSubmit == 1 && result == 1) ? 1 : 0;
    }

    //작성한 숙제 조회
    @Override
    public TaskDTO modifyTask(Integer tasksend_no) {
        return taskDAO.modifyTask(tasksend_no);
    }

    //숙제 수정
    @Override
    public int ModifySubmitTask(Integer tasksend_no, String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTasksend_no(tasksend_no);
        taskDTO.setTasksubmit_chapter(tasksubmit_chapter);
        taskDTO.setTasksubmit_content(tasksubmit_content);
        taskDTO.setTasksubmit_add(tasksubmit_add);

        return taskDAO.ModifySubmitTask(taskDTO);
    }

    //숙제 전송
    public int submitTask(List<Integer> tasksend_no){
        return taskDAO.submitTask(tasksend_no);
    }

    //제출 숙제 조회
    @Override
    public List<TaskDTO> selectSubmitTask(Integer member_no, int limit, int offset) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setMember_no(member_no);
        taskDTO.setLIMIT(limit);
        taskDTO.setOFFSET(limit*offset);
        return taskDAO.selectSubmitTask(taskDTO);
    }

    //제출 숙제 갯수
    @Override
    public int submitTaskCount(Integer member_no) {
        return taskDAO.submitTaskCount(member_no);
    }

    //평가 완료된 숙제 조회
    @Override
    public TaskDTO selectSubmitTaskByNo(Integer tasksend_no) {
        return taskDAO.selectSubmitTaskByNo(tasksend_no);
    }
}