package no.ntnu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NordriveApplication {
  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    SpringApplication.run(NordriveApplication.class, args);
  }
}
