package pl.bpiatek.peselproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.bpiatek.peselproject.input.InputService;

/**
 * Created by Bartosz Piatek on 03/03/2020
 */
@Slf4j
@Component
class Main implements CommandLineRunner {

  private final InputService inputService;

  Main(InputService inputService) {this.inputService = inputService;}

  @Override
  public void run(String... args) throws Exception {
    inputService.start();
  }
}
