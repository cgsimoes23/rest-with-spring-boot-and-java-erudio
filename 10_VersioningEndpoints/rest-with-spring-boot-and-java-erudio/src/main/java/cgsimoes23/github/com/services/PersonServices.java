package cgsimoes23.github.com.services;

import cgsimoes23.github.com.controllers.PersonController;
import cgsimoes23.github.com.data.dto.v1.PersonDTO;
import cgsimoes23.github.com.data.dto.v2.PersonDTOV2;
import cgsimoes23.github.com.exception.ResourceNotFoundException;
import static cgsimoes23.github.com.mapper.ObjectMapper.parseListObjects;
import static cgsimoes23.github.com.mapper.ObjectMapper.parseObject;

import cgsimoes23.github.com.mapper.custom.PersonMapper;
import cgsimoes23.github.com.model.Person;
import cgsimoes23.github.com.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service // This annotation indicates that this class is a service component in the Spring framework.
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonController.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;

    public List<PersonDTO> findAll() {

        logger.info("Finding all people!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(   "Person not found with id: " + id));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one person!");

        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one person V2!");

        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one person!");

        Person entity = repository.findById(person.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        Person entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

}
