package com.bnw.beta.service.learning.Task;

import com.bnw.beta.domain.common.paging.TaskPageDTO;
import com.bnw.beta.domain.learning.dao.TaskDAO;
import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.domain.learning.dto.TaskSendDTO;
import com.bnw.beta.domain.learning.dto.TaskSubmitDTO;
import lombok.RequiredArgsConstructor;
import okhttp3.internal.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate;

        try {
            endDate = sdf.parse(task_deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

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
        TaskSendDTO taskSendDTO = new TaskSendDTO();

        for (Integer taskNo : task_no) {
            for (Integer memberNo : member_no) {
                taskSendDTO.setTask_no(taskNo);
                taskSendDTO.setMember_no(memberNo);
                taskSendDTO.setMember_id(member_id);
                taskSendDTO.setGroup_no(group_no);
                taskDAO.sendTask(taskSendDTO);
            }
        }
        return "success";
    }

    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    @Override
    public List<TaskDTO> selectTaskById(Integer member_no) {
        return taskDAO.selectTaskById(member_no);
    }

    //숙제 번호로 정보 조회
    @Override
    public TaskSendDTO selectTaskByNo(Integer tasksend_no, Integer member_no) {
        TaskSendDTO taskSendDTO = new TaskSendDTO();
        taskSendDTO.setMember_no(member_no);
        taskSendDTO.setTasksend_no(tasksend_no);

        return taskDAO.selectTaskByNo(taskSendDTO);
    }

    //숙제 작성하기
    @Override
    public int wirteTask(int tasksend_no, int task_no, String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add, String member_id) {

        TaskSubmitDTO taskSubmitDTO = new TaskSubmitDTO();
        taskSubmitDTO.setTasksend_no(tasksend_no);
        taskSubmitDTO.setTask_no(task_no);
        taskSubmitDTO.setMember_id(member_id);
        taskSubmitDTO.setTasksubmit_chapter(tasksubmit_chapter);
        taskSubmitDTO.setTasksubmit_content(tasksubmit_content);
        taskSubmitDTO.setTasksubmit_add(tasksubmit_add);

        int savedTaskSubmit = taskDAO.wirteTask(taskSubmitDTO);
        int result = taskDAO.saveTask(tasksend_no);

        return (savedTaskSubmit == 1 && result == 1) ? 1 : 0;
    }

    //작성한 숙제 조회
    @Override
    public TaskSubmitDTO modifyTask(int tasksend_no, int member_no) {
        TaskSubmitDTO taskSubmitDTO = new TaskSubmitDTO();
        taskSubmitDTO.setTasksend_no(tasksend_no);
        taskSubmitDTO.setMember_no(member_no);

        return taskDAO.modifyTask(taskSubmitDTO);
    }

    //숙제 수정
    @Override
    public int ModifySubmitTask(int tasksend_no, String tasksubmit_chapter, String tasksubmit_content, String tasksubmit_add) {

        TaskSubmitDTO taskSubmitDTO = new TaskSubmitDTO();
        taskSubmitDTO.setTasksend_no(tasksend_no);
        taskSubmitDTO.setTasksubmit_chapter(tasksubmit_chapter);
        taskSubmitDTO.setTasksubmit_content(tasksubmit_content);
        taskSubmitDTO.setTasksubmit_add(tasksubmit_add);

        return taskDAO.ModifySubmitTask(taskSubmitDTO);
    }

    //숙제 전송
    public int submitTask(List<Integer> tasksend_no){
        return taskDAO.submitTask(tasksend_no);
    }

    //제출 숙제 조회
    @Override
    public List<TaskSubmitDTO> selectSubmitTask(Integer member_no) {
        return taskDAO.selectSubmitTask(member_no);
    }

    //평가 완료된 숙제 조회

    @Override
    public TaskSubmitDTO selectSubmitTaskByNo(int tasksend_no, int member_no) {

        TaskSubmitDTO taskSubmitDTO = new TaskSubmitDTO();
        taskSubmitDTO.setTasksend_no(tasksend_no);
        taskSubmitDTO.setMember_no(member_no);

        return taskDAO.selectSubmitTaskByNo(taskSubmitDTO);
    }
}
