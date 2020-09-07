package telran.ashkelon2020.person.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.person.dto.ChildDto;
import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.EmployeeDto;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@PutMapping("/{id}/{name}")
	public PersonDto updatePerson(@PathVariable Integer id, @PathVariable String name) {
		return personService.updatePerson(id, name);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}

	@PostMapping("/{name}")
	public List<PersonDto> getPersonsByName(@PathVariable String name) {
		return personService.getPersonsByName(name);
	}

	@PostMapping("/period")
	public List<PersonDto> getPersonsByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate from,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate to) {
		return personService.getPersonsByDate(from, to);
	}

	@GetMapping("/city/{city}")
	public Iterable<PersonDto> getPersonsByCity(@PathVariable String city) {
		return personService.findPersonByCity(city);
	}

	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personService.getCityPopulation();
	}

	@GetMapping("/salary/{min}/{max}")
	public Iterable<EmployeeDto> findEmployeeBySalary(@PathVariable int min, @PathVariable int max) {
		return personService.findEmployeeBySalary(min, max);
	}

	@GetMapping("/children")
	public Iterable<ChildDto> findAllChildren() {
		return personService.getChildren();
	}
}
