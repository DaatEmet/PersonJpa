package telran.ashkelon2020.person.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee extends Person {
	String company;
	Integer salary;
	
	public Employee(Integer id, String name, LocalDate birthDate, Address address, String company, Integer salary) {
		super(id, name, birthDate, address);
		this.company = company;
		this.salary = salary;
	}
	
	
}
