package com.itu.fatec.contatos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.itu.fatec.contatos.entities.Category;
import com.itu.fatec.contatos.entities.Contact;
import com.itu.fatec.contatos.repositories.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private CategoryService categoryService;

    public List<Contact> getAll() {
        return repository.findAll();
    }

    public List<Contact> getFavorite() {
        return repository.findByFavoriteTrue();
    }

    public Contact updateFavorite(@PathVariable Long id) {
        Contact contact = repository.findById(id).orElseThrow();
        contact.setFavorite(!contact.isFavorite());
        return repository.save(contact);
    }

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public void update(Contact contact, Long id) {
        Contact aux = repository.getReferenceById(id);

        if (contact.getCategory() == null || contact.getCategory().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category and Category ID can not be empty");
        }

        Category category = categoryService.getById(contact.getCategory().getId());

        aux.setName(contact.getName());
        aux.setLastname(contact.getLastname());
        aux.setCell(contact.getCell());
        aux.setPhone(contact.getPhone());
        aux.setEmail(contact.getEmail());
        aux.setDatebirth(contact.getDatebirth());
        aux.setAddress(contact.getAddress());
        aux.setNote(contact.getNote());
        aux.setCategory(category);

        repository.save(aux);
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Contato não encontrado");
        }
    }
}
