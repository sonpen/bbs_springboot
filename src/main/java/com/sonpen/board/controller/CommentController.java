package com.sonpen.board.controller;

import com.sonpen.board.domain.CommentVO;
import com.sonpen.board.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 1109806 on 2019-03-28.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource(name="com.sonpen.board.service.CommentService")
    CommentService commentService;

    @RequestMapping("/list") // 댓글 리스트
    @ResponseBody
    private List<CommentVO> commentServiceList(@RequestParam int bno) throws Exception{
        return commentService.commentListSerivce(bno);
    }

    @RequestMapping("/insert") // 댓글 작성
    @ResponseBody
    private int commentServiceInsert(@RequestParam int bno, @RequestParam String content) throws Exception{
        CommentVO commentVO = new CommentVO();
        commentVO.setBno(bno);
        commentVO.setContent(content);
        // 로그인 기능을 구현했거나 따로 댓글 작성자를 입력받는 폼이 있다면 입력 받아온 값으로 사용하되나, 구현 안되었으니까..일단 test로
        commentVO.setWriter("test");

        return commentService.commentInsertService(commentVO);
    }

    @RequestMapping("/update") // 댓글 수정
    @ResponseBody
    private int commentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{
        CommentVO commentVO = new CommentVO();
        commentVO.setCno(cno);
        commentVO.setContent(content);

        return commentService.commentUpdateService(commentVO);
    }

    @RequestMapping("/delete/{cno}") // 댓글 삭제
    @ResponseBody
    private int commentServiceDelete(@PathVariable int cno) throws Exception{
        return commentService.commentDeleteService(cno);
    }
}
