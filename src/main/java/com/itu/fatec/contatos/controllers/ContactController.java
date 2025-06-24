package com.itu.fatec.contatos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itu.fatec.contatos.entities.Contact;
import com.itu.fatec.contatos.services.ContactService;

@CrossOrigin
@RestController
@RequestMapping("contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @GetMapping
    public ResponseEntity<List<Contact>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("favorites")
    public ResponseEntity<List<Contact>> getFavorite(){
        return ResponseEntity.ok(service.getFavorite());
    }

    @PutMapping("{id}/favorite")
    public ResponseEntity<Contact> updateFavorite(@PathVariable Long id){
        service.updateFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Contact> save(@RequestBody Contact contact){
        return ResponseEntity.created(null).body(service.save(contact));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Contact contact){
        service.update(contact, id);
        return ResponseEntity.noContent().build();
    }
    
}
