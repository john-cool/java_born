import service.WelcomeService;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    WelcomeService welcomeService = new WelcomeService();
    welcomeService.greetings();
  }
}