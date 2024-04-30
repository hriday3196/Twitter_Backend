package com.example.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name="Posts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postID;

    @Column
    private String postBody;

    @Column
    private LocalDate date; // yyyy-MM-dd format

    @Column
    private int userID;
}
