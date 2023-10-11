package com.bnw.beta.domain.common.paging;

import com.bnw.beta.domain.admin.dto.EdupostDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class EdupostPageDTO {
    private int total;
    private List<EdupostDTO> edupostList;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int startIndex;


    public EdupostPageDTO(int total, int currentPage, int size, List<EdupostDTO> edupostList){
        this.total=total;
        this.currentPage=currentPage;
        this.edupostList=edupostList;
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
