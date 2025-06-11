package cgsimoes23.github.com.repository;

import cgsimoes23.github.com.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
