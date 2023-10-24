package esa.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import esa.restful.entity.Contact;
import esa.restful.entity.User;
import esa.restful.model.ContactResponse;
import esa.restful.model.CreateContactRequest;
import esa.restful.model.UpdateContactRequest;
import esa.restful.model.WebResponse;
import esa.restful.repository.ContactRepository;
import esa.restful.repository.UserRepository;
import esa.restful.security.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("esa");
        user.setName("Maulana");
        user.setToken("token");
        user.setPassword(BCrypt.hashpw("esa123" , BCrypt.gensalt()));
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest()throws Exception{
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createContactSuccess()throws Exception{
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Maulana");
        request.setLastName("Akbar");
        request.setPhone("0858");
        request.setEmail("esa@example.com");


        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("Maulana" , response.getData().getFirstName());
            assertEquals("Akbar" , response.getData().getLastName());
            assertEquals("esa@example.com" , response.getData().getEmail());
            assertEquals("0858" , response.getData().getPhone());

            assertTrue(contactRepository.existsById(response.getData().getId()));

        });
    }

    @Test
    void getContactNotFound()throws Exception{

        mockMvc.perform(
                get("/api/contacts/1212")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getContactSuccess()throws Exception{
        User user = userRepository.findById("esa").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstame("Maulana");
        contact.setLastName("Akbar");
        contact.setEmail("esa@example.com");
        contact.setPhone("0858");
        contactRepository.save(contact);


        mockMvc.perform(
                get("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals(contact.getId() , response.getData().getId());
            assertEquals(contact.getFirstame() , response.getData().getFirstName());
            assertEquals(contact.getLastName() , response.getData().getLastName());
            assertEquals(contact.getEmail() , response.getData().getEmail());
            assertEquals(contact.getPhone() , response.getData().getPhone());
        });
    }

    @Test
    void updateContactBadRequest()throws Exception{
        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateContactSuccess()throws Exception{
        User user = userRepository.findById("esa").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstame("Maulana");
        contact.setLastName("Akbar");
        contact.setEmail("esa@example.com");
        contact.setPhone("0858");
        contactRepository.save(contact);

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Esa");
        request.setLastName("Putra");
        request.setPhone("0859");
        request.setEmail("esa@example.com");


        mockMvc.perform(
                put("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContactResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getFirstName() , response.getData().getFirstName());
            assertEquals(request.getLastName() , response.getData().getLastName());
            assertEquals(request.getEmail() , response.getData().getEmail());
            assertEquals(request.getPhone() , response.getData().getPhone());

            assertTrue(contactRepository.existsById(response.getData().getId()));

        });
    }


    @Test
    void deleteContactNotFound()throws Exception{

        mockMvc.perform(
                delete("/api/contacts/1212")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }


    @Test
    void deleteContactSuccess()throws Exception{
        User user = userRepository.findById("esa").orElseThrow();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        contact.setFirstame("Maulana");
        contact.setLastName("Akbar");
        contact.setEmail("esa@example.com");
        contact.setPhone("0858");
        contactRepository.save(contact);

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Esa");
        request.setLastName("Putra");
        request.setPhone("0859");
        request.setEmail("esa@example.com");


        mockMvc.perform(
                delete("/api/contacts/" + contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals("ok" , response.getData());
        });
    }

    @Test
    void searchNotFound()throws Exception{

        mockMvc.perform(
                get("/api/contacts" )
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals(0 , response.getData().size());
            assertEquals(0 , response.getPaging().getTotalPage());
            assertEquals(0 , response.getPaging().getCurrentPage());
            assertEquals(10 , response.getPaging().getSize());
        });
    }

    @Test
    void searchUsingName()throws Exception{
        User user = userRepository.findById("esa").orElseThrow();

        for (int i = 0 ; i< 100 ;  i++){
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID().toString());
            contact.setUser(user);
            contact.setFirstame("Maulana " + i);
            contact.setLastName("Akbar");
            contact.setEmail("esa@example.com");
            contact.setPhone("0858");
            contactRepository.save(contact);
        }
        mockMvc.perform(
                get("/api/contacts" )
                        .queryParam("nama" , "Akbar")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            log.info("size cuy  " ,response.getPaging().getTotalPage().toString());

            assertEquals(10 , response.getData().size());
            assertEquals(10 , response.getPaging().getTotalPage());
            assertEquals(0 , response.getPaging().getCurrentPage());
            assertEquals(10 , response.getPaging().getSize());
        });
    }

    @Test
    void searchSuccess()throws Exception{
        User user = userRepository.findById("esa").orElseThrow();

        for (int i = 0 ; i< 100 ;  i++){
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID().toString());
            contact.setUser(user);
            contact.setFirstame("Maulana " + i);
            contact.setLastName("Akbar");
            contact.setEmail("esa@example.com");
            contact.setPhone("0858");
            contactRepository.save(contact);
        }
        mockMvc.perform(
                get("/api/contacts" )
                        .queryParam("nama" , "Akbar")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());


            assertEquals(10 , response.getData().size());
            assertEquals(10 , response.getPaging().getTotalPage());
            assertEquals(0 , response.getPaging().getCurrentPage());
            assertEquals(10 , response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts" )
                        .queryParam("email" , "@example.com")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());


            assertEquals(10 , response.getData().size());
            assertEquals(10 , response.getPaging().getTotalPage());
            assertEquals(0 , response.getPaging().getCurrentPage());
            assertEquals(10 , response.getPaging().getSize());
        });

        mockMvc.perform(
                get("/api/contacts" )
                        .queryParam("phone " ,"85")
                        .queryParam("page" , "1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN" , "token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            assertEquals(0 , response.getData().size());
            assertEquals(10 , response.getPaging().getTotalPage());
            assertEquals(1000 , response.getPaging().getCurrentPage());
            assertEquals(10 , response.getPaging().getSize());
        });
    }
}