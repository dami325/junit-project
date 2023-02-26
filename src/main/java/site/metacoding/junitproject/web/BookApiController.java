package site.metacoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookRespDto;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has 관계

    /**
     * final이 붙었을땐 컨트롤러 호출시 초기화가 돼야함
     */
    private final BookService bookService;

    // 1. 책등록
    // key = value&key = value
    // { "key" : value, "key" : value }
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);

        // 원래 AOP 처리하는게 좋음!!
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println("=========================");
            System.out.println(errorMap.toString());
            System.out.println("=========================");

            return new ResponseEntity<>(CMRespDto.builder()
                    .code(-1)
                    .msg(errorMap.toString())
                    .body(bookRespDto)
                    .build(),HttpStatus.BAD_REQUEST); // 400 = 요청이 잘못됨
        }


        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookRespDto)
                .build(),HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책목록보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책한건보기
    public ResponseEntity<?> getBookOne() {
        return null;
    }

    // 4. 책삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책수정하기
    public ResponseEntity<?> updateBook() {
        return null;
    }

}
