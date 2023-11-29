package ApprovalHub.ApprovalAdminPortal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserMobileDto {

    public interface RegisterMobile {}
    public interface Login{}

    @Size(groups = {RegisterMobile.class, Login.class}, max = 20, message = "username length max must {max} character")
    @NotBlank(groups = {RegisterMobile.class, Login.class}, message = "username can not blank")
    private String username;

    @Email(groups = {RegisterMobile.class}, message = "email must valid format")
    @NotBlank(groups = {RegisterMobile.class}, message = "email can not blank")
    private String email;

    @NotBlank(groups = {RegisterMobile.class, Login.class}, message = "password can not blank")
    private String password;

    private Set<String> role;

    private String activationCode;
}
