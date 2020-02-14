package embl.sb.rest.person.controller;

import embl.sb.rest.person.PersonService;
import embl.sb.rest.person.beans.Person;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/embl/sb/rest/persons")
public class PersonAPI {

    private static final Logger logger = LoggerFactory.getLogger(PersonAPI.class);

    @Autowired
    private final PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId) {
        Optional<Person> personOptional = personService.findById(personId);
        if (!personOptional.isPresent()) {
            logger.error("PersonId " + personId + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(personOptional.get());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable(value = "id") Long personId, @Valid @RequestBody Person personEntity) {
        Optional<Person> personOptional = personService.findById(personId);
        if (!personOptional.isPresent()) {
            logger.error("PersonId " + personId + " is not existed");
            ResponseEntity.badRequest().build();
        }

        Person person = personOptional.get();
        if (!StringUtils.isEmpty(personEntity.getFirstName())) person.setFirstName(personEntity.getFirstName());
        if (!StringUtils.isEmpty(personEntity.getLastName())) person.setLastName(personEntity.getLastName());
        person.setAge(personEntity.getAge());
        person.setFavouriteColor(personEntity.getFavouriteColor());
        person.setHobby(personEntity.getHobby());

        return ResponseEntity.accepted().body(personService.save(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        personService.deleteById(id);

        return ResponseEntity.accepted().build();
    }

//    @PostMapping("/persons")
//    public Person createPerson(@Valid @RequestBody Person person) {
//        return personService.save(person);
//    }
//
//    @PutMapping("/persons/{id}")
//    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId,
//                                                   @Valid @RequestBody Person personEntity) throws ResourceNotFoundException {
//        Person person = personService.findById(personId)
//                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));
//
//        person.setLastName(personEntity.getLastName());
//        person.setFirstName(personEntity.getFirstName());
//        person.setAge(personEntity.getAge());
//        person.setFavouriteColor(personEntity.getFavouriteColor());
//        person.setHobby(personEntity.getHobby());
//        final Person updatedPerson = personService.save(person);
//        return ResponseEntity.ok(updatedPerson);
//    }
//
//    @DeleteMapping("/persons/{id}")
//    public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId)
//           throws ResourceNotFoundException {
//        Person person = personService.findById(personId)
//            .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));
//
//        personService.deleteById(personId);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
}
