package com.bnw.beta.service.subscribe.subContents;

import com.bnw.beta.domain.subscribe.dao.subContentsDAO;
import com.bnw.beta.domain.subscribe.dto.subContentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubContentsServiceImpl implements SubContentsService {

    private final subContentsDAO subContentsDAO;

    @Override
    public List<subContentsDTO> selectContents(subContentsDTO subContentsDTO) {
        System.out.println("임플"+subContentsDTO);
        return subContentsDAO.selectContents(subContentsDTO);
    }

    @Override
    public int contentsCnt(subContentsDTO subContentsDTO){
        return subContentsDAO.contentsCnt(subContentsDTO);
    }

    @Override
    public Integer deleteContents(int game_no) {

        return subContentsDAO.deleteContents(game_no);
    }
}
