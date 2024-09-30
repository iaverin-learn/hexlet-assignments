package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody ContactCreateDTO contactDto) {
        var contact = new Contact();
        contact.setLastName(contactDto.getLastName());
        contact.setFirstName(contactDto.getFirstName());
        contact.setPhone(contactDto.getPhone());
        contact = contactRepository.save(contact);
        
        var createdContactDto = new ContactDTO();
        createdContactDto.setId(contact.getId());
        createdContactDto.setFirstName(contact.getFirstName());
        createdContactDto.setLastName(contact.getLastName());
        createdContactDto.setPhone(contact.getPhone());
        createdContactDto.setCreatedAt(contact.getCreatedAt());
        createdContactDto.setUpdatedAt(contact.getUpdatedAt());
        
        return createdContactDto;
    }
    
    // END
}
