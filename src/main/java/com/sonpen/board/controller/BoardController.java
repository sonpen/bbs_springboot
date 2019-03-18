package com.sonpen.board.controller;

import com.sonpen.board.domain.BoardVO;
import com.sonpen.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 1109806 on 2019-03-18.
 */
@Controller
public class BoardController {

    @Resource(name="com.sonpen.board.service.BoardService")
    BoardService boardService;

    @RequestMapping("/list")
    private String boardList(Model model) throws Exception {
        model.addAttribute("list", boardService.boardListService());
        return "list"; // jsp
    }

    @RequestMapping("/detail/{bno}")
    private String boardDetail(@PathVariable int bno, Model model) throws Exception{
        model.addAttribute("detail", boardService.boardDetailService(bno));
        return "detail";
    }

    @RequestMapping("/insert") // 게시글 작성폼 호출
    private String boardInsertForm() {
        return "insert";
    }
    @RequestMapping("/insertProc")
    private int boardInsertProd(HttpServletRequest request) throws Exception{
        BoardVO board = (BoardVO)request.getParameterMap();
        return boardService.boardInsertService(board);
    }

    @RequestMapping("/update/{bno}") // 게시글 수정폼 호출
    private String boardUpdateForm(@PathVariable int bno, Model model) throws Exception{
        model.addAttribute("detail", boardService.boardDetailService(bno));
        return "update";
    }
    @RequestMapping("/updateProc")
    private int boardUpdateProc(HttpServletRequest request) throws Exception{
        BoardVO board = (BoardVO)request.getParameterMap();
        return boardService.boardUpdateService(board);
    }

    @RequestMapping("/delete/{bno}")
    private String boardDelete(@PathVariable int bno) throws Exception {
        boardService.boardDeleteService(bno);
        return "redirect:/list";
    }
}
