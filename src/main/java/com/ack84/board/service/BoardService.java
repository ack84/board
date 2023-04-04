package com.ack84.board.service;

import com.ack84.board.repository.Board;
import com.ack84.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board){

        boardRepository.save(board);

    }
}
