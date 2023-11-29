package ApprovalHub.ApprovalAdminPortal.services;

import ApprovalHub.ApprovalAdminPortal.models.OrderingCustomer;
import ApprovalHub.ApprovalAdminPortal.models.StatusRegister;
import ApprovalHub.ApprovalAdminPortal.repositories.OrderingCustomerRepository;
import ApprovalHub.ApprovalAdminPortal.dto.ActionPortalDto;
import ApprovalHub.ApprovalAdminPortal.dto.*;
import ApprovalHub.ApprovalAdminPortal.dto.StatusRegisterDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OrderingCustomerService {

    @Autowired
    private OrderingCustomerRepository orderingCustomerRepository;


    public List<OrderingCustomer> findAll() {
        return orderingCustomerRepository.findAll();
    }

    public OrderingCustomer findById(Integer id) {
        return orderingCustomerRepository.findById(id).get();
    }

    public OrderingCustomer findByIdOrdering(String idOrdering) {
        return orderingCustomerRepository.findByIdOrdering(idOrdering);
    }

    public Integer actionPortal(ActionPortalDto customer) throws CustomException {
        try {

            StatusRegisterDto statusRegister = new StatusRegisterDto();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d LLLL yyyy");

            // new
            if (orderingCustomerRepository.findByIdOrdering(customer.getIdOrdering()) != null) {
                OrderingCustomer oc = orderingCustomerRepository.findByIdOrdering(customer.getIdOrdering());

                if (customer.getStatusRegister() == StatusRegister.VERIFIED) {
                    // if data exist return data already exist
                    if (oc.getActivationCode() != null){
                        return 2;
                    }
                    activationCodeService.insertNewCustomer(findByIdOrdering(customer.getIdOrdering()));
                }

                statusRegister.setEmail(oc.getEmail());
                statusRegister.setLongName(oc.getLong_name());
                statusRegister.setStatusRegister(customer.getStatusRegister().toString());
                statusRegister.setRegistrationDate(oc.getRegistrationDate().format(formatter));

                oc.setStatusRegister(customer.getStatusRegister());
                oc.setMessage(customer.getMessage());
                orderingCustomerRepository.save(oc);
                log.info("success {}", customer.getStatusRegister());
                return 1;
            }

            return 0;

        } catch (Exception e) {
            log.error("error in action portal, {}", e.getMessage());
            throw new CustomException("500");
        }
    }

    public OrderingCustomer findByEmail(String email) {
        return orderingCustomerRepository.findByEmail(email);
    }

    public List<OrderingCustomer> filterByRegistryDate(FilterDataCustomerDto filterDataCustomerDto) {

        if (filterDataCustomerDto.getIdNumber() == null && filterDataCustomerDto.getName() == null && filterDataCustomerDto.getBirthDate() == null
                && filterDataCustomerDto.getDateForm() == null && filterDataCustomerDto.getDateTo() == null && filterDataCustomerDto.getStatus() == null
                && filterDataCustomerDto.getReferenceNumber() == null) {
            filterDataCustomerDto.setDateForm(LocalDate.now());
            filterDataCustomerDto.setDateTo(LocalDate.now().plusDays(1));
        }

        return orderingCustomerRepository.filterDataOrdering(
                filterDataCustomerDto.getIdNumber(),
                filterDataCustomerDto.getName(),
                filterDataCustomerDto.getBirthDate(),
                filterDataCustomerDto.getDateForm(),
                filterDataCustomerDto.getDateTo(),
                filterDataCustomerDto.getStatus(),
                filterDataCustomerDto.getReferenceNumber());
    }

}
