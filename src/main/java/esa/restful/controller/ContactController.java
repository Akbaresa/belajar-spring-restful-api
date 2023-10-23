package esa.restful.controller;

import esa.restful.entity.User;
import esa.restful.model.*;
import esa.restful.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user , @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder()
                .data(contactResponse)
                .build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user ,@PathVariable("contactId") String id){
        ContactResponse contactResponse = contactService.get(user, id);
        return WebResponse.<ContactResponse>builder()
                .data(contactResponse)
                .build();
    }

    @PutMapping(
            path = "/api/contacts/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> update(User user ,
                                               @RequestBody UpdateContactRequest request,
                                               @PathVariable("contactId") String contactId){
        request.setId(contactId);
        ContactResponse contactResponse = contactService.update(user, request);
        return WebResponse.<ContactResponse>builder()
                .data(contactResponse)
                .build();
    }

    @DeleteMapping(
            path = "/api/contacts/{contactId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user ,@PathVariable("contactId") String id){
        contactService.delete(user, id);

        return WebResponse.<String>builder()
                .data("ok")
                .build();
    }

    @GetMapping(
            path = "/api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContactResponse>> search( User user ,
                                                      @RequestParam(value = "name" , required = false) String name ,
                                                      @RequestParam(value = "email" , required = false) String email ,
                                                      @RequestParam(value = "phone" , required = false) String phone ,
                                                      @RequestParam(value = "page" , required = false , defaultValue = "0") Integer page ,
                                                      @RequestParam(value = "size" , required = false , defaultValue = "10") Integer size
    ){
        SearchContactRequest request = SearchContactRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .email(email)
                .phone(phone)
                .build();

        Page<ContactResponse> contactResponse = contactService.search(user, request);
        return WebResponse.<List<ContactResponse>>builder()
                .data(contactResponse.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contactResponse.getNumber())
                        .totalPage(contactResponse.getTotalPages())
                        .size(contactResponse.getSize())
                        .build())
                .build();
    }
}
