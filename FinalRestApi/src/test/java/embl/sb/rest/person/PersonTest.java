package embl.sb.rest.person;

import embl.sb.rest.person.beans.Person;
import embl.sb.rest.person.controller.PersonAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @InjectMocks
    PersonAPI personAPI;

    @Mock
    PersonService personService;

    @Test
    public void testCreatePerson() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(personService.save(any(Person.class))).thenReturn(any(Person.class));
        Person person = new Person("Jhon", "Seana", 30, "RED", null);
        ResponseEntity responseEntity = personAPI.create(person);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getStatusCode().name()).isEqualTo("CREATED");
    }

    @Test
    public void testFindAll() {
        // given
        Person person1 = new Person("Jhon", "Seana", 30, "RED", null);
        Person person2 = new Person("Jhon", "Doe", 32, "BLACK", null);

        personAPI.create(person1);
        personAPI.create(person2);

        List<Person> personList = Arrays.asList(person1, person2);
        when(personService.findAll()).thenReturn(personList);

        // when
        List<Person> result = personAPI.getAllPersons();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getFirstName())
                .isEqualTo(person1.getFirstName());

        assertThat(result.get(1).getFirstName())
                .isEqualTo(person2.getFirstName());
    }
}
