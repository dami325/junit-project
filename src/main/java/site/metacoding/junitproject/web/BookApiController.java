package site.metacoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookListRespDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println("=========================");
            System.out.println(errorMap.toString());
            System.out.println("=========================");

            throw new RuntimeException(errorMap.toString());
        }

        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookRespDto)
                .build(), HttpStatus.CREATED); // 201 = insert
    }

    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBookV2(@RequestBody BookSaveReqDto bookSaveReqDto) {
        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);

        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookRespDto)
                .build(), HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookListRespDto = bookService.책목록보기();
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 목록보기 성공")
                .body(bookListRespDto)
                .build(), HttpStatus.OK); // 200 = OK
    }

    // 3. 책한건보기
    @GetMapping("api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.책한건보기(id);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 한건보기 성공")
                .body(bookRespDto)
                .build(), HttpStatus.OK); // 200 = OK
    }

    // 4. 책삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 삭제하기 성공")
                .body(null)
                .build(), HttpStatus.OK); // 200 = OK
    }

    // 5. 책수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        // 원래 AOP 처리하는게 좋음!!
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookRespDto bookRespDto = bookService.책수정하기(id, bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("글 수정하기 성공")
                .body(bookRespDto)
                .build(), HttpStatus.OK);
    }

}
