package telran.ashkelon2020.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import telran.ashkelon2020.person.dto.ChildDto;
import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.model.Child;
import telran.ashkelon2020.person.model.Employee;
import telran.ashkelon2020.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{
	
	//@Query(value ="select * from persons where name =?1",nativeQuery = true)
	@Query("select p from Person p where p.name =?1")
	Stream<Person> findPersonsByName(String name);
	
	//Stream<Person> findPersonsByName(String name);
	
	Stream<Person> findPersonsByBirthDateBetween(LocalDate from, LocalDate to);
	@Query("select p from Person p where p.address.city=:city")
	Stream<Person> findByAddressCity(@Param("city") String city);
	//Stream<Person> findByAddressCity(String city);
	
	@Query("select new telran.ashkelon2020.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCityPopulation();
	
	@Query("select p from Person p where p.salary >=?1 and p.salary <=?2")
	Stream<Employee> findEmployeeBySalaryBetween(int min, int max);
	
	
	@Query("select p from Person p where p.kindergarten is not null")
	Stream<Child> findAllChildren();
}
