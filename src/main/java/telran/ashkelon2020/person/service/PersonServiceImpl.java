package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.person.dao.PersonRepository;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public boolean addPerson(PersonDto personDto) {
		Person person = personRepository.findById(personDto.getId()).orElse(null);
		if(person == null) {
			person = modelMapper.map(personDto, Person.class);
			personRepository.save(person);
			return true;
		} else {
			throw new PersonExistsException(person.getId());
		}
		
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePerson(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		if(name != null) person.setName(name);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional
	@Override
	public List<PersonDto> getPersonsByName(String name) {
		return personRepository.findPersonsByName(name)
				.map(s-> modelMapper.map(s, PersonDto.class))
				.collect(Collectors.toList());

	}
	
	@Transactional
	@Override
	public List<PersonDto> getPersonsByDate(LocalDate from, LocalDate to) {
		return personRepository.findPersonsByBirthDateBetween(from, to)
				.filter(p->p.getBirthDate().isAfter(from) && p.getBirthDate().isBefore(to))
				.map(s-> modelMapper.map(s, PersonDto.class))
				.collect(Collectors.toList());
	}

}
