package cgsimoes23.github.com.repository;

import cgsimoes23.github.com.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
