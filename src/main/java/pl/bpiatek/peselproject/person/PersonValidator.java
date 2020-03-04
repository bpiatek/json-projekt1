package pl.bpiatek.peselproject.person;

import org.springframework.stereotype.Component;

import java.util.Set;

import javax.validation.*;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
@Component
public class PersonValidator {

  private final Validator validator;

  public PersonValidator(Validator validator) {this.validator = validator;}

  public boolean validate(Person person) {
    Set<ConstraintViolation<Person>> personViolations = validator.validate(person);
    if (!personViolations.isEmpty()) {
      System.out.println("Coś poszło nie tak:");
      personViolations.forEach(p -> System.out.println(p.getMessage()));
      return false;
    } else {
      System.out.println("Osoba została poprawnie zwalidowana.");
      return true;
    }
  }


}
