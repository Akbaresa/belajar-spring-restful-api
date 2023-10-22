package esa.restful.service;

import esa.restful.entity.User;
import esa.restful.model.RegisterUserRequest;
import esa.restful.repository.UserRepository;
import esa.restful.security.BCrypt;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    public void register(RegisterUserRequest request){
        validationService.validate(request);

        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword() , BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }



}
