package ApprovalHub.ApprovalAdminPortal.dto;


import ApprovalHub.ApprovalAdminPortal.models.StatusRegister;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActionPortalDto {

    public interface Action{}

    @NotNull(groups = {Action.class}, message = "id ordering can not null")
    private String idOrdering;

    @NotNull(groups = {Action.class}, message = "status register can not null")
    private StatusRegister statusRegister;

    @NotBlank(groups = {Action.class}, message = "message can not blank")
    private String message;

}
