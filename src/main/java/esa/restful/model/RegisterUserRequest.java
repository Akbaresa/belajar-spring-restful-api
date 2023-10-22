package esa.restful.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String name;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
