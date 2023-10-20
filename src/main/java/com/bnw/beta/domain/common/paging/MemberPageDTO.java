package com.bnw.beta.domain.common.paging;

import com.bnw.beta.domain.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberPageDTO {
    private int total;
    private List<MemberDTO> memberList;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int startIndex;
    private int listCount;
    private Date startDate;
    private Date endDate;
    private String searchType;
    private String searchType2;
    private String searchType3;
    private String keyword = "";

    public MemberPageDTO(int total, int currentPage, int size, List<MemberDTO> memberList){
        this.total=total;
        this.currentPage=currentPage;
        this.memberList=memberList;
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
