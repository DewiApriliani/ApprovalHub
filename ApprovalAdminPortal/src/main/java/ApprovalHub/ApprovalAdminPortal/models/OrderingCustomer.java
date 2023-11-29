package ApprovalHub.ApprovalAdminPortal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ordering_customer")
@Data
public class OrderingCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "id_type")
    private String idType1;

    @Column(name = "id_number", length = 25)
    private String idNumber2;

    @Column(name = "customer_type")
    private String customer_type;

    @Column(name = "long_name", length = 100)
    private String long_name;

    @Column(name = "address", length = 50)
    private String address1;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "birthdate", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Column(name = "postcode", nullable = false, length = 25)
    private String postcode;

    @Column( name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 25)
    private String phone_number;

    @Column(name = "mobile", nullable = false, length = 25)
    private String mobile;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "company_address", nullable = false, length = 50)
    private String company_address;

    @Column(name = "company_phone", length = 25)
    private String company_phone;

    @Column(name = "status_register")
    private StatusRegister statusRegister;

    @Column(name = "message")
    private String message;

}
