package ack.board.controller;

import ack.board.dto.BoardDto;
import ack.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){
        boardService.save(boardDto);
        return "index";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList",boardDtoList);
        return "list";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable  Long id, Model model){
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
        return "detail";

    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDto);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
        return "detail";
    }
}
