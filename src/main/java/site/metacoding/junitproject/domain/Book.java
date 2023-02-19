package site.metacoding.junitproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Id
    private Long id;

    @Column(length = 50, nullable = false) // 50, not null
    private String title;
    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

}