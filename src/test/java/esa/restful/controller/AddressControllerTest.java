package esa.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import esa.restful.entity.Contact;
import esa.restful.entity.User;
import esa.restful.model.CreateAddressRequest;
import esa.restful.model.WebResponse;
import esa.restful.repository.AddressRepository;
import esa.restful.repository.ContactRepository;
import esa.restful.repository.UserRepository;
import esa.restful.security.BCrypt;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){

        userRepository.deleteAll();
        contactRepository.deleteAll();

        User user = new User();
        user.setUsername("esa");
        user.setName("Maulana");
        user.setToken("token");
        user.setPassword(BCrypt.hashpw("esa123" , BCrypt.gensalt()));
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("esa");
        contact.setUser(user);
        contact.setFirstame("Maulana");
        contact.setLastName("Akbar");
        contact.setEmail("esa@example.com");
        contact.setPhone("0858");
        contactRepository.save(contact);


    }

    @Test
    void testBadRequest() throws  Exception{

        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
               post("/api/contacts/test/addresses")
                       .accept(MediaType.APPLICATION_JSON)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(request))
                       .header("X-API-TOKEN" , "token")
        ).andExpectAll(
             status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response =objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }
}