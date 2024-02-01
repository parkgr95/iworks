//package com.example.iworks.domain.board.repository;
//
//import com.example.iworks.domain.board.domain.Board;
//import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
//import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
//import com.example.iworks.global.model.entity.Code;
//import com.example.iworks.global.model.entity.CodeGroup;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional
//@SpringBootTest
//class BoardRepositoryTest {
//
//    @Autowired BoardRepository boardRepository;
//    @Autowired CodeRepository codeRepository;
//    @Autowired CodeGroupRepository codeGroupRepository;
//    private final PageRequest pageRequest = PageRequest.of(0, 10);
//
//    @Test
//    @Rollback(value = false)
//    public void 공지자유게시판조회() throws Exception {
//        //given
//        CodeGroup cg1 = new CodeGroup();
////        cg1.setCodeGroupName("공지자유게시판");
//        codeGroupRepository.save(cg1);
//
//        Code c1 = new Code();
//        c1.setCodeName("공지");
//        c1.setCodeCodeGroup(cg1);
//        codeRepository.save(c1);
//
//        Board b1 = new Board();
//        b1.setBoardCategoryCode(c1);
//        b1.setBoardOwnerId(1);
//        b1.setBoardCreatorId(1);
//        boardRepository.save(b1);
//
//        // when
//        List<Board> allByBoardCategoryCode = boardRepository.findAllByBoardCategoryCode(pageRequest, c1);
//
//        // then
//        Assertions.assertThat(allByBoardCategoryCode.get(0).getBoardCategoryCode()).isEqualTo(c1);
//    }
//
//    @Test
//    public void 부서팀게시판조회() throws Exception {
//        //given
//        CodeGroup cg1 = new CodeGroup();
////        cg1.setCodeGroupName("공지자유게시판");
//        codeGroupRepository.save(cg1);
//
//        Code c1 = new Code();
//        c1.setCodeName("공지");
//        c1.setCodeCodeGroup(cg1);
//        codeRepository.save(c1);
//
//        Board b1 = new Board();
//        b1.setBoardCategoryCode(c1);
//        b1.setBoardOwnerId(1);
//        b1.setBoardCreatorId(1);
//        boardRepository.save(b1);
//
//        //when
//        List<Board> allByBoardCategoryCodeAndBoardOwnerId = boardRepository.findAllByBoardCategoryCodeAndBoardOwnerId(pageRequest, c1, 1);
//
//        //then
//        Assertions.assertThat(allByBoardCategoryCodeAndBoardOwnerId.get(0).getBoardCategoryCode()).isEqualTo(c1);
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 카테고리키워드별검색() throws Exception {
//        //given
//        CodeGroup cg1 = new CodeGroup();
////        cg1.setCodeGroupName("공지자유게시판");
//        codeGroupRepository.save(cg1);
//
//        Code c1 = new Code();
//        c1.setCodeName("공지");
//        c1.setCodeCodeGroup(cg1);
//        codeRepository.save(c1);
//
//        Board b1 = new Board();
//        b1.setBoardCategoryCode(c1);
//        b1.setBoardTitle("ssafy");
//        b1.setBoardOwnerId(1);
//        b1.setBoardCreatorId(1);
//        boardRepository.save(b1);
//
//        Board b2 = new Board();
//        b2.setBoardCategoryCode(c1);
//        b2.setBoardContent("ssafy");
//        b2.setBoardOwnerId(1);
//        b2.setBoardCreatorId(2);
//        boardRepository.save(b2);
//
//        Board b3 = new Board();
//        b3.setBoardCategoryCode(c1);
//        b3.setBoardOwnerId(1);
//        b3.setBoardCreatorId(3);
//        boardRepository.save(b3);
//
//        Board b4 = new Board();
//        b4.setBoardCategoryCode(c1);
//        b4.setBoardTitle("ssafy");
//        b4.setBoardContent("ssafy");
//        b4.setBoardOwnerId(1);
//        b4.setBoardCreatorId(4);
//        boardRepository.save(b4);
//
//        BoardSearchRequestDto s1 = new BoardSearchRequestDto();
//        s1.setBoardTitle("ssafy");
//
//        BoardSearchRequestDto s2 = new BoardSearchRequestDto();
//        s2.setBoardContent("ssafy");
//
//        BoardSearchRequestDto s3 = new BoardSearchRequestDto();
//        s3.setBoardCreatorId(3);
//
//        //when
//        List<BoardGetResponseDto> t1 = boardRepository.findAllByKeyword(pageRequest, s1);
//        List<BoardGetResponseDto> t2 = boardRepository.findAllByKeyword(pageRequest, s2);
//        List<BoardGetResponseDto> t3 = boardRepository.findAllByKeyword(pageRequest, s3);
//        List<BoardGetResponseDto> t4 = boardRepository.findAllByKeywords(pageRequest, "ssafy");
//
//        //then
//        for (int i = 0; i < t1.size(); i++) {
//            System.out.println("t1 = " + t1.get(i).getBoardCreatorId());
//            System.out.println("t1 = " + t1.get(i).getBoardContent());
//            System.out.println("t1 = " + t1.get(i).getBoardTitle());
//        }
//        for (int i = 0; i < t2.size(); i++) {
//            System.out.println("t2 = " + t2.get(i).getBoardCreatorId());
//            System.out.println("t2 = " + t2.get(i).getBoardContent());
//            System.out.println("t2 = " + t2.get(i).getBoardTitle());
//        }
//        for (int i = 0; i < t3.size(); i++) {
//            System.out.println("t3 = " + t3.get(i).getBoardCreatorId());
//            System.out.println("t3 = " + t3.get(i).getBoardContent());
//            System.out.println("t3 = " + t3.get(i).getBoardTitle());
//        }
//        for (int i = 0; i < t4.size(); i++) {
//            System.out.println("t4 = " + t4.get(i).getBoardCreatorId());
//            System.out.println("t4 = " + t4.get(i).getBoardContent());
//            System.out.println("t4 = " + t4.get(i).getBoardTitle());
//        }
//    }
//}