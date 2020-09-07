package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.List;

import telran.ashkelon2020.person.dto.PersonDto;

public interface PersonService {
	
	boolean addPerson(PersonDto personDto);
	PersonDto findPersonById(Integer id);
	PersonDto updatePerson(Integer id, String name);
	PersonDto deletePerson(Integer id);
	List<PersonDto> getPersonsByName(String name);
	List<PersonDto> getPersonsByDate(LocalDate from, LocalDate to);
}
