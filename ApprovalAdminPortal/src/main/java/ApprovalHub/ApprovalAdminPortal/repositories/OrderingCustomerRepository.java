package ApprovalHub.ApprovalAdminPortal.repositories;

import ApprovalHub.ApprovalAdminPortal.models.OrderingCustomer;
import ApprovalHub.ApprovalAdminPortal.models.StatusRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderingCustomerRepository extends JpaRepository<OrderingCustomer, Integer> {

    OrderingCustomer findByIdNumber1(String idNumber);

    Boolean existsByIdNumber1(String idNumber);

    OrderingCustomer findByEmail(String email);

    Boolean existsByEmail(String email);

    OrderingCustomer findByIdOrdering(String idOrdering);

    Boolean existsByIdOrdering(String idOrdering);

    @Query(value = "SELECT * FROM ordering_customer WHERE create_token = :token AND id_ordering = :idOrdering", nativeQuery = true)
    OrderingCustomer findByCreateToken(String idOrdering,String token);

    //    Page<OrderingCustomer> findAll(Pageable pageable);

    @Query("SELECT oc FROM OrderingCustomer oc WHERE (:idNumber1 is null OR oc.idNumber1 LIKE %:idNumber1%) AND (:name is null OR oc.name LIKE %:name%) AND (:birthdate is null OR oc.birthdate = :birthdate) AND (:dateFrom is null OR oc.registrationDate >= :dateFrom) AND (:dateTo is null OR oc.registrationDate <= :dateTo) AND (:statusRegister is null OR oc.statusRegister = :statusRegister) AND (:referenceNum is null OR oc.referenceNumber LIKE %:referenceNum%)")
    List<OrderingCustomer> filterDataOrdering(
            @Param("idNumber1") String idNumber,
            @Param("name") String name,
            @Param("birthdate") LocalDate birthDate,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("statusRegister") StatusRegister status,
            @Param("referenceNum") String referenceNumber
    );
}