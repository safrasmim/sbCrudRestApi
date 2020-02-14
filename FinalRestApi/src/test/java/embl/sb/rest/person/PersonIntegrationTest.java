package embl.sb.rest.person;

import embl.sb.rest.person.beans.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

//@EnableAutoConfiguration
public class PersonIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/persons",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetPersonById() {
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/1", Person.class);
        System.out.println(person.getFirstName());
        assertNotNull(person);
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        person.setFirstName("Jon");
        person.setLastName("Doe");
        person.setAge(25);
        person.setFavouriteColor("Doe");
        person.setHobby(null);

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/persons", person, Person.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdatePerson() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        person.setFirstName("Jon");
        person.setLastName("Doe");
        restTemplate.put(getRootUrl() + "/persons/" + id, person);
        Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        assertNotNull(updatedPerson);
    }

    @Test
    public void testDeletePerson() {
        int id = 2;
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        assertNotNull(person);
        restTemplate.delete(getRootUrl() + "/persons/" + id);
        try {
            person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
