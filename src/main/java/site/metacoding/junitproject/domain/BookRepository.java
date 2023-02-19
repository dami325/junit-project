package site.metacoding.junitproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // JpaRepository 상속 시 생략가능
public interface BookRepository extends JpaRepository<Book,Long> {

}
