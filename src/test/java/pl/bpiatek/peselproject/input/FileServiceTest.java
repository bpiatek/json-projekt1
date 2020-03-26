package pl.bpiatek.peselproject.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.bpiatek.peselproject.file.FileService;
import pl.bpiatek.peselproject.person.Person;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
class FileServiceTest {

  private static String path =  "src/test/resources/database.json";
  private static FileService fileService;

  @BeforeAll
  static void setUp() throws IOException, ClassNotFoundException {
    fileService = new FileService(path);
  }

  @AfterEach
  void cleanUp() {
    fileService.getFile().delete();
    System.out.println("Plik po tescie zostal usuniety!");
  }

  @Test
  void shouldCorrectlySavePersonToFileAndReadFromIt() {
    // given
    Person person = new Person("Poznan", "Name", "LastName", "87080704392");

    // when
    fileService.saveJsonToFile(person);

    // then
    assertThat(fileService.readPersonListFromFile().get(0).getName()).isEqualTo("Name");
  }

  @Test
  void shouldReplaceEntryIfPeselIsTheSame() {
    // given
    Person person1 = new Person("Poznan", "Name", "LastName", "87080704392");
    Person person2 = new Person("Poznan2", "Name2", "LastNam2", "78070866518");
    fileService.saveJsonToFile(person1);
    fileService.saveJsonToFile(person2);

    // when
    fileService.saveJsonToFile(new Person("Poznan2", "Changed", "LastNam2", "78070866518"));

    // then
    List<Person> people = fileService.readPersonListFromFile();
    Person changed = people.stream().filter(x -> x.getPesel().equals("78070866518")).findFirst().get();

    assertThat(changed.getName()).isEqualTo("Changed");
  }

}
