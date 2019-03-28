package com.sonpen.board.mapper;

import com.sonpen.board.domain.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 1109806 on 2019-03-28.
 */
@Repository("com.sonpen.board.mapper.CommentMapper")
public interface CommentMapper {
    // 댓글 개수
    public int commentCount() throws Exception;

    // 댓글 목록
    public List<CommentVO> commentList(int bno) throws Exception;

    // 댓글 작성
    public int commentInsert(CommentVO comment) throws Exception;

    // 댓글 수정
    public int commentUpdate(CommentVO comment) throws Exception;

    // 댓글 삭제
    public int commentDelete(int cno) throws Exception;
}
