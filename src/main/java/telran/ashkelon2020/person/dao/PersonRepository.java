package telran.ashkelon2020.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2020.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{
	
	@Transactional
	Stream<Person> findPersonsByName(String name);
	
	@Transactional
	Stream<Person> findPersonsByBirthDateBetween(LocalDate from, LocalDate to);
}
