package com.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.Board;
import com.web.repository.BoardRepository;
import com.web.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

	private final BoardService boardService;

	private BoardRepository boardRepository;

	public BoardRestController(BoardService boardService) {

		this.boardService = boardService;

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBoards(@PageableDefault Pageable pageable) {

		Page<Board> boards = boardRepository.findAll(pageable);
		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(),
				boards.getTotalElements());
		PagedResources<Board> resources = new PagedResources<>(boards.getContent(), pageMetadata);
		resources.add(linkTo(methodOn(BoardRestController.class).getBoards(pageable)).withSelfRel());
		return ResponseEntity.ok(resources);
	}

	@PostMapping() // 등록
	public ResponseEntity<?> postBoard(@RequestBody Board board) {

		board.setCreatedDateNow();

		boardService.save(board);

		return new ResponseEntity<Board>(boardService.save(board), HttpStatus.OK);
	}

	@PutMapping("/{idx}") // 수정
	public ResponseEntity<?> putBoard(@PathVariable("idx") Long idx, @RequestBody Board board) {

		Board persistBoard = boardService.findBoardByIdx(idx);

		persistBoard.update(board);

		boardService.save(persistBoard);

		return new ResponseEntity<>("{}", HttpStatus.OK);

	}

	@DeleteMapping("/{idx}") // 삭제
	public ResponseEntity<?> deleteBoard(@PathVariable("idx") Long idx) {

		boardService.deleteById(idx);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
