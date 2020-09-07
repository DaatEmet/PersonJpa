package telran.ashkelon2020.person.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2020.person.model.Address;

@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("child")
public class ChildDto extends PersonDto {
	String kindergarten;

	public ChildDto(Integer id, String name, LocalDate birthDate, Address address, String kindergarten) {
		super(id, name, birthDate, address);
		this.kindergarten = kindergarten;
	}
	
}
