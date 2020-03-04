package pl.bpiatek.peselproject.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.bpiatek.peselproject.person.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
@Service
public class FileService {

  private File file;
  private Gson gson;

  public FileService(@Value("${databasefile.path}") String path) {
    this.gson = new GsonBuilder().setPrettyPrinting().create();
    file = new File(path);
  }

  public Person saveJsonToFile(Person person) {
    Person savedPerson = person;
    List<Person> people = readPersonListFromFile();

    if(people.isEmpty()) {
      people.add(person);
    }

    Person replacedPerson = replaceEntryInList(people, person);
    if(replacedPerson == null) {
      people.add(person);
    } else {
      savedPerson = replacedPerson;
    }

    try (Writer writer = new FileWriter(file) ){
      gson.toJson(people, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return savedPerson;
  }

  public List<Person> readPersonListFromFile() {
    Type type = new TypeToken<ArrayList<Person>>() {}.getType();
    List<Person> persons = gson.fromJson(readFileAsJsonString(), type);

    return persons == null ? new ArrayList<>() : persons;
  }

  private String readFileAsJsonString() {
    try {
      return new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Person replaceEntryInList(List<Person> people, Person person) {
    for(int i = 0; i < people.size(); i++) {
      if(isPeselEqual(people.get(i), person)) {
        Person element = new Person(
            people.get(i).getId(),
            person.getCity(),
            person.getName(),
            person.getLastName(),
            person.getPesel()
        );
        people.set(i, element);
        return element;
      }
    }

    return null;
  }

  private boolean isPeselEqual(Person personFromList, Person person) {
    return personFromList.getPesel().equals(person.getPesel());
  }
}
