package com.sonpen.board.controller;

import com.sonpen.board.domain.BoardVO;
import com.sonpen.board.domain.FileVO;
import com.sonpen.board.service.BoardService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by 1109806 on 2019-03-18.
 */
@Controller
public class BoardController {

    @Autowired
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
    private String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
        BoardVO board = new BoardVO();
        FileVO fileVO = new FileVO();

        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setWriter(request.getParameter("writer"));

        if(files.isEmpty()) { // 업로드할 파일이 없을시
            boardService.boardInsertService(board);
        } else {
            String sourceFileName = files.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            File destinationFile;
            String destinationFileName;
            String fileUrl = "C:\\workspace\\IdeaProjects\\bbs_springboot\\src\\main\\webapp\\WEB-INF\\uploadFiles\\";

            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                destinationFile = new File(fileUrl, destinationFileName);
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);

            boardService.boardInsertService(board);     // 게시글 insert

            fileVO.setBno(board.getBno());
            fileVO.setFileName(destinationFileName);
            fileVO.setFileOriName(sourceFileName);
            fileVO.setFileUrl(fileUrl);

            boardService.fileInsertService(fileVO);     // 파일 insert
        }

        return "redirect:/list";
    }

    @RequestMapping("/update/{bno}") // 게시글 수정폼 호출
    private String boardUpdateForm(@PathVariable int bno, Model model) throws Exception{
        model.addAttribute("detail", boardService.boardDetailService(bno));
        return "update";
    }
    @RequestMapping("/updateProc")
    private String boardUpdateProc(HttpServletRequest request) throws Exception{

        BoardVO board = new BoardVO();
        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setBno(Integer.parseInt(request.getParameter("bno")));

        boardService.boardUpdateService(board);
        return "redirect:/detail/" + request.getParameter("bno");
    }

    @RequestMapping("/delete/{bno}")
    private String boardDelete(@PathVariable int bno) throws Exception {
        boardService.boardDeleteService(bno);
        return "redirect:/list";
    }
}
