package com.b2b.spring.boot.demo.DTO;

public record ModificaUser(Long id, String nome, String email, IndirizzoRecord dettaglio) {
}
