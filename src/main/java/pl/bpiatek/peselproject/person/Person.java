package pl.bpiatek.peselproject.person;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;


import javax.validation.constraints.NotEmpty;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
public class Person {

  private static Long ID = 1L;

  private Long id;
  @NotEmpty(message = "Nie podano miasta")
  private String city;
  @NotEmpty(message = "Nie podano imienia")
  private String name;
  @NotEmpty(message = "Nie podano nazwiska")
  private String lastName;
  @PESEL(message = "podany PESEL jest niepoprawny")
  private String pesel;

  public Person(
      @NotEmpty(message = "Nie podano miasta") String city,
      @NotEmpty(message = "Nie podano imienia") String name,
      @NotEmpty(message = "Nie podano nazwiska") String lastName,
      @PESEL(message = "podany PESEL jest niepoprawny") String pesel
  ) {
    this.id =  ID++;
    this.city = city;
    this.name = name;
    this.lastName = lastName;
    this.pesel = pesel;
  }
}
