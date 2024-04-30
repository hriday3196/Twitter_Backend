package com.example.spring.model;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name="Comments")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentID;

    @Column
    private String commentBody;

    @Column
    private int postID;

    @Column
    private int userID;
}
