package esa.restful.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.ManagedArray;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateContactRequest {

    @NotBlank
    @Size(max =100)
    private String firstName;

    @Size(max =100)
    private String lastName;

    @Size(max =100)
    @Email
    private String email;

    @Size(max =100)
    private String phone;
}
