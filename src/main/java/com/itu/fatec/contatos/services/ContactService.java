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

        // Validação que já é útil
        if (contact.getCategory() == null || contact.getCategory().getId() == null) { // Adicione .getId() == null para
                                                                                      // validar que o ID da categoria
                                                                                      // existe
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category and Category ID can not be empty");
        }

        // Busca a categoria gerenciada pelo JPA antes de associar
        // Isso é importante para evitar problemas de "detached entity"
        Category category = categoryService.getById(contact.getCategory().getId()); // Busca a categoria persistida

        aux.setName(contact.getName());
        aux.setLastname(contact.getLastname());
        aux.setCell(contact.getCell());
        aux.setPhone(contact.getPhone());
        aux.setEmail(contact.getEmail());
        aux.setDatebirth(contact.getDatebirth());
        aux.setAddress(contact.getAddress());
        // Remova a linha abaixo, pois 'aux.setCategory(category);' já faz isso
        // corretamente.
        // aux.setCategory(contact.getCategory());
        aux.setNote(contact.getNote());
        aux.setCategory(category); // Use o objeto Category buscado, não o que veio da requisição diretamente

        repository.save(aux); // O método save atualizará a entidade 'aux'
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Contato não encontrado");
        }
    }
}
