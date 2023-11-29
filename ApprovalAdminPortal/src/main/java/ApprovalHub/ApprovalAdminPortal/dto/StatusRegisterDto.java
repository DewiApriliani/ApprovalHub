package ApprovalHub.ApprovalAdminPortal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRegisterDto {
    private String email;
    private String longName;
    private String registrationDate;
    private String statusRegister;
    private String message;
}
