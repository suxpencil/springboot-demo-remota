package com.b2b.spring.boot.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="UTENTE")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DETTAGLIO")
    private String dettaglio;

}
