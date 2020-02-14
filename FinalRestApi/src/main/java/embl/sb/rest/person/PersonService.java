package embl.sb.rest.person;

import embl.sb.rest.person.beans.Person;
import embl.sb.rest.person.repository.PersonRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepositoryI personRepositoryI;


    public List<Person> findAll() {
        return personRepositoryI.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepositoryI.findById(id);
    }

    public Person save(Person person) {
        return personRepositoryI.save(person);
    }

    public void deleteById(Long id) {
        personRepositoryI.deleteById(id);
    }
}
