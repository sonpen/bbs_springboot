package com.sonpen.board.mapper;

import com.sonpen.board.domain.BoardVO;
import com.sonpen.board.domain.FileVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 1109806 on 2019-03-14.
 */
@Repository("com.sonpen.board.mapper.BoardMapper")
public interface BoardMapper {

    // 게시글 개수
    int boardCount() throws Exception;

    // 게시글 목록
    public List<BoardVO> boardList() throws Exception;

    // 게시글 상세
    public BoardVO boardDetail(int bno) throws Exception;

    // 게시글 작성
    public int boardInsert(BoardVO boardVO) throws Exception;

    // 게시글 수정
    public int boardUpdate(BoardVO boardVO) throws Exception;

    // 게시글 삭제
    public int boardDelete(int bno) throws Exception;

    // 파일저장
    public int fileInsert(FileVO file) throws Exception;

    // 파일상세
    public FileVO fileDetail(int bno) throws Exception;
}
