package pl.bpiatek.peselproject.input;

import org.springframework.stereotype.Service;
import pl.bpiatek.peselproject.file.FileService;
import pl.bpiatek.peselproject.person.Person;
import pl.bpiatek.peselproject.person.PersonValidator;

import java.util.*;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
@Service
public class InputService {

  private final Scanner scanner;
  private final PersonValidator personValidator;
  private final FileService fileService;

  public InputService(
      Scanner scanner,
      PersonValidator personValidator,
      FileService fileService
  ) { this.scanner = scanner;
    this.personValidator = personValidator;
    this.fileService = fileService;
  }

  public void start() {
    boolean again = true;

    do {
      System.out.println("Podaj nazwe miasta: ");
      String city = scanner.nextLine();
      System.out.println("Podaj imię, nazwisko i PESEL (spacja pomiędzy każdym):");
      String details = scanner.nextLine();

      Person personFromInput = createPersonFromInput(city, details);
      if (personFromInput == null) {
        System.out.println("Nie udało się stworzyć osoby");
      } else {
        System.out.println("Osoba dodana do pliku : " + personFromInput);
      }

      boolean goodAnswer = false;
      do {
        System.out.println("Czy chcesz dodac jeszcze jedna osobe? (T, N)");
        String answer = scanner.nextLine().toUpperCase();
        if("T".equals(answer)) {
          again = true;
          goodAnswer = false;
        } else if("N".equals(answer)) {
          goodAnswer = false;
          again = false;
        } else {
          System.out.println("Mozliwe odpowiedzi to T lub N");
          goodAnswer = true;
        }
      } while(goodAnswer);
    } while (again);
  }

  private Person createPersonFromInput(String city, String details) {
    List<String> divided = new ArrayList<>(Arrays.asList(details.split(" ")));
    if(divided.size() != 3) {
      System.out.println("Prawdopodobnie nie wszystkie pole zostaly wprowadzone");
      return null;
    }
    Person person = new Person(city, divided.get(0), divided.get(1), divided.get(2));

    if (personValidator.validate(person)) {
      return fileService.saveJsonToFile(person);
    } else {
      return null;
    }
  }
}
