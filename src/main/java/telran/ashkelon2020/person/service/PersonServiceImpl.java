package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2020.person.dao.PersonRepository;
import telran.ashkelon2020.person.dto.ChildDto;
import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.EmployeeDto;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.model.Child;
import telran.ashkelon2020.person.model.Employee;
import telran.ashkelon2020.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	@Transactional
	
	public boolean addPerson(PersonDto personDto) {
		if(!personRepository.existsById(personDto.getId())) {
			if(personDto instanceof EmployeeDto) {
				Employee person = modelMapper.map(personDto, Employee.class);
				personRepository.save(person);
			} else if(personDto instanceof ChildDto) {
				Child person = modelMapper.map(personDto, Child.class);
				personRepository.save(person);
			} else {
				Person person = modelMapper.map(personDto, Person.class);
				personRepository.save(person);
			}
			return true;
		} else return false;
	}

	@Override
    public PersonDto findPersonById(Integer id) {
        if(!personRepository.existsById(id)) {
            throw new PersonNotFoundException();
        }
        if(personRepository.findById(id).get() instanceof Employee) {
            Employee person = (Employee) personRepository.findById(id).get();
            return modelMapper.map(person, EmployeeDto.class);
        } else if(personRepository.findById(id).get() instanceof Child) {
            Child person = (Child) personRepository.findById(id).get();
            return modelMapper.map(person, ChildDto.class);
        } else {
            Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
            return modelMapper.map(person, PersonDto.class);
        }
    }

	@Override
	@Transactional
	public PersonDto updatePerson(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonDto> getPersonsByName(String name) {
		return personRepository.findPersonsByName(name).map(s -> modelMapper.map(s, PersonDto.class))
				.collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonDto> getPersonsByDate(LocalDate from, LocalDate to) {
		return personRepository.findPersonsByBirthDateBetween(from, to)
				.filter(p -> p.getBirthDate().isAfter(from) && p.getBirthDate().isBefore(to))
				.map(s -> modelMapper.map(s, PersonDto.class)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonDto> findPersonByCity(String city) {
		return personRepository.findByAddressCity(city)
				.map(s -> modelMapper.map(s, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CityPopulationDto> getCityPopulation() {
		return personRepository.getCityPopulation();
	}

	@Override
    @Transactional(readOnly = true)
    public Iterable<EmployeeDto> findEmployeeBySalary(int min, int max) {
        return personRepository.findEmployeeBySalaryBetween(min, max)
                .map(p -> modelMapper.map(p, EmployeeDto.class))
                .collect(Collectors.toList()); 
    }	

	 @Override
	    @Transactional(readOnly = true)
	    public Iterable<ChildDto> getChildren() {
	        return personRepository.findAllChildren()
	                .map(p -> modelMapper.map(p, ChildDto.class))
	                .collect(Collectors.toList());
	    }

}
