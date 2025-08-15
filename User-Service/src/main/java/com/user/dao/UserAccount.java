package com.user.dao;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SB_Bank")
@Data
public class UserAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long userId;

  @Column
  private String userName;

  @Column
  private double balance;

}
