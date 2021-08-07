package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());


    public PersonController(PersonRepository personRepository, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.personRepository = personRepository;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.personRepository.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<Person>(
                this.personRepository.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.personRepository.save(person);
        return ResponseEntity.ok().build();
    }
    @ExceptionHandler(value = { IllegalArgumentException.class,com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
