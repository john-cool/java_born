package ru.etulnev.web;

import ru.etulnev.web.view.WelcomeView;

import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    WelcomeView welcomeView = new WelcomeView();
    welcomeView.greetings();
  }
}