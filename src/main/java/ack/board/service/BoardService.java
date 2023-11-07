package ack.board.service;

import ack.board.dto.BoardDto;
import ack.board.entity.BoardEntity;
import ack.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    public void save(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
        boardRepository.save(boardEntity);
    }

    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }
        return boardDtoList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);

    }

    public BoardDto findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDto.toBoardDTO(boardEntity);
        } else {
            return null;
        }

    }

    public BoardDto update(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDto> paging(Pageable pageable) {
        //page는 0부터 시작함
        int page = pageable.getPageNumber() -1 ;
        int pageLimit = 3; //한페이지에 보여지는 게시글 수 
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.Direction.DESC,"id"));

        Page<BoardDto> boardDtos = boardEntities.map(board -> new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDtos;

    }
}
