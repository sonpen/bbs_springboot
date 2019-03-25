package com.sonpen.board.service;

import com.sonpen.board.domain.BoardVO;
import com.sonpen.board.domain.FileVO;
import com.sonpen.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 1109806 on 2019-03-18.
 */
@Service
public class BoardService {
    @Resource(name="com.sonpen.board.mapper.BoardMapper")
    BoardMapper boardMapper;

    public List<BoardVO> boardListService() throws Exception {
        return boardMapper.boardList();
    }

    public BoardVO boardDetailService(int bno) throws Exception {
        return boardMapper.boardDetail(bno);
    }

    public int boardInsertService(BoardVO boardVO) throws Exception{
        return boardMapper.boardInsert(boardVO);
    }

    public int boardUpdateService(BoardVO boardVO) throws Exception{
        return boardMapper.boardUpdate(boardVO);
    }

    public int boardDeleteService(int bno) throws Exception{
        return boardMapper.boardDelete(bno);
    }

    public int fileInsertService(FileVO fileVO) throws Exception{
        return boardMapper.fileInsert(fileVO);
    }

    public FileVO fileDetailService(int bno) throws Exception{
        return boardMapper.fileDetail(bno);
    }
}
