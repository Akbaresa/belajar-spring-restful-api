package esa.restful.service;

import esa.restful.entity.Contact;
import esa.restful.entity.User;
import esa.restful.model.ContactResponse;
import esa.restful.model.CreateContactRequest;
import esa.restful.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    public ContactResponse create(User  user , CreateContactRequest request){
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstame(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);

        contactRepository.save(contact);
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstame())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    private ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstame())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }
    @Transactional(readOnly = true)
    public ContactResponse get(User user , String id){
        Contact contact = contactRepository.findFirstByUserAndId(user , id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND , "contact not found"
                ));
        return toContactResponse(contact);
    };
}
