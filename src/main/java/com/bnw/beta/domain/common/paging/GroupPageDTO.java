package com.bnw.beta.domain.common.paging;

import com.bnw.beta.domain.learning.dto.GroupDTO;
import com.bnw.beta.domain.learning.dto.TaskDTO;
import lombok.Data;

import java.util.List;

@Data
public class GroupPageDTO {

    private int total;
    private List<GroupDTO> groupList;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int startIndex;
    private int listCount;


    public GroupPageDTO(int total, int currentPage, int size, List<GroupDTO> groupList){
        this.total=total;
        this.currentPage=currentPage;
        this.groupList=groupList;
        if(total==0) {
            totalPages=0;
            startPage=0;
            endPage=0;
        }else {

            totalPages=total/size;
            if(total%size>0) {
                totalPages++;
            }

            int modVal = currentPage%5;
            startPage=currentPage/5*5+1;
            if(modVal==0)  startPage=startPage-5;

            endPage=startPage+4;
            if(endPage>totalPages) endPage=totalPages;

            startIndex = (currentPage - 1) * size;
        }
    }
}
