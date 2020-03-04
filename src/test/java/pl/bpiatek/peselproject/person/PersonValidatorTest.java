package pl.bpiatek.peselproject.person;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
class PersonValidatorTest {

  private static final String CITY = "Test city";
  private static final String FIRST_NAME = "firstName";
  private static final String LAST_NAME = "lastName";
  private static final String CORRECT_PESEL = "92102496755";
  private static final String INCORRECT_PESEL = "23464323452";

  private static PersonValidator personValidator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    personValidator = new PersonValidator(factory.getValidator());
  }

  @Test
  void shouldAcceptCorrectPesel() {
    // given
    Person person = new Person(CITY, FIRST_NAME, LAST_NAME, CORRECT_PESEL);

    // when
    boolean validate = personValidator.validate(person);

    // then
    assertThat(validate).isTrue();
  }

  @Test
  void shouldNotAcceptIncorrectPesel() {
    // given
    Person person = new Person(CITY, FIRST_NAME, LAST_NAME, INCORRECT_PESEL);

    // when
    boolean validate = personValidator.validate(person);

    // then
    assertThat(validate).isFalse();
  }

  @Test
  void shouldNotAcceptWhenCityIsNotPassed() {
    // given
    Person person = new Person("", FIRST_NAME, LAST_NAME, CORRECT_PESEL);

    // when
    boolean validate = personValidator.validate(person);

    // then
    assertThat(validate).isFalse();
  }

  @Test
  void shouldNotAcceptWhenFirstNameIsNotPassed() {
    // given
    Person person = new Person(CITY, "", LAST_NAME, CORRECT_PESEL);

    // when
    boolean validate = personValidator.validate(person);

    // then
    assertThat(validate).isFalse();
  }

  @Test
  void shouldNotAcceptWhenLastNameIsNotPassed() {
    // given
    Person person = new Person(CITY, FIRST_NAME, "", CORRECT_PESEL);

    // when
    boolean validate = personValidator.validate(person);

    // then
    assertThat(validate).isFalse();
  }
}
