package ru.etulnev.web.dto;

import java.util.UUID;

public class AccountDto {
  private UUID id;
  private String typeCode;
  private double balance;
  private UUID userId;

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "счёт пользователя: {" +
      "id=" + id +
      ", typeCode='" + typeCode + '\'' +
      ", balance=" + balance +
      ", userId=" + userId +
      '}';
  }
}
