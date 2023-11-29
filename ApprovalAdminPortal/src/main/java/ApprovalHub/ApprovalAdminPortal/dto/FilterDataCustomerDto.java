package ApprovalHub.ApprovalAdminPortal.dto;

import ApprovalHub.ApprovalAdminPortal.models.StatusRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDataCustomerDto {

    private String idNumber;
    private String name;
    private LocalDate birthDate;
    private LocalDate dateForm;
    private LocalDate dateTo;
    private StatusRegister status;
    private String referenceNumber;
}
