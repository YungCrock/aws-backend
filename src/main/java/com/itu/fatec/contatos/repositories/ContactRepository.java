package com.itu.fatec.contatos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.fatec.contatos.entities.Contact;

public interface ContactRepository extends JpaRepository <Contact, Long> {
    List<Contact> findByFavoriteTrue();
}
