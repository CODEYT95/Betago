package com.bnw.beta.service.learning.Task;

import com.bnw.beta.domain.learning.dao.TaskDAO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.domain.learning.dto.TaskSendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskDAO taskDAO;

    /*교육자 부분--------------------------------------------------------*/
    //숙제 생성
    @Override
    public int createTask(TaskDTO taskDTO) {
        return taskDAO.createTask(taskDTO);
    }

    //숙제 전송 목록
    @Override
    public List<TaskDTO> sendTaskList(String member_id, int pageNum, int size) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum-1) * size;
        return taskDAO.sendTaskList(member_id, offset, size);
    }

    //숙제 전송 갯수
    @Override
    public int sendTaskListCount(String member_id) {
        return taskDAO.sendTaskListCount(member_id);
    }

    /*학습자 부분--------------------------------------------------------*/
    //전송된 숙제 조회
    @Override
    public List<TaskDTO> selectTaskById(int member_no) {
        return taskDAO.selectTaskById(member_no);
    }

    //숙제 번호로 정보 조회
    @Override
    public List<TaskDTO> selectTaskByNo(int tasksend_no) {
        return taskDAO.selectTaskByNo(tasksend_no);
    }
}
