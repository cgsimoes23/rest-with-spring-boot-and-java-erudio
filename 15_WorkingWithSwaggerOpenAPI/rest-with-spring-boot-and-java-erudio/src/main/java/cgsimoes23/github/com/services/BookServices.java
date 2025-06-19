package cgsimoes23.github.com.services;

import cgsimoes23.github.com.controllers.BookController;
import cgsimoes23.github.com.data.dto.BookDTO;
import cgsimoes23.github.com.exception.RequiredObjectIsNullException;
import cgsimoes23.github.com.exception.ResourceNotFoundException;
import cgsimoes23.github.com.model.Book;
import cgsimoes23.github.com.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cgsimoes23.github.com.mapper.ObjectMapper.parseListObjects;
import static cgsimoes23.github.com.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service // This annotation indicates that this class is a service component in the Spring framework.
public class BookServices {

    private Logger logger = LoggerFactory.getLogger(BookController.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll() {

        logger.info("Finding all books!");

        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(   "Book not found with id: " + id));
        var dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");

        var entity = parseObject(book, Book.class);

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        Book entity = repository.findById(book.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto =  parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");
        Book entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }


    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

    }

}
