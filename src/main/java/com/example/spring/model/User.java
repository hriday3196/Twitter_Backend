package com.example.spring.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name="Books")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

}
