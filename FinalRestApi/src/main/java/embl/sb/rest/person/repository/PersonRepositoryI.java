package embl.sb.rest.person.repository;

import embl.sb.rest.person.beans.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepositoryI extends JpaRepository<Person, Long> {

}