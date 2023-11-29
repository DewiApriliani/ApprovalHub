package ApprovalHub.ApprovalAdminPortal.services;

import ApprovalHub.ApprovalAdminPortal.models.OrderingCustomer;
import ApprovalHub.ApprovalAdminPortal.repositories.OrderingCustomerRepository;
import ApprovalHub.ApprovalAdminPortal.dto.ActionPortalDto;

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

                if (beneficiaryCustomerService.findDetailByIdOrderingCus(oc.getId()) == null){
                    log.error("Data Beneficiary not available");
                    return 0;
                }

                BeneficiaryCustomer bc = beneficiaryCustomerService.findDetailByIdOrderingCus(oc.getId());
                bc.setStatus(customer.getStatusRegister());

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
                beneficiaryCustomerRepository.save(bc);
                approvedEmailNotifService.statusRegisterNotification(statusRegister);
                log.info("success {}", customer.getStatusRegister());
                return 1;
            }

            // existing
            if (existingCustomerService.findByIdcust(customer.getIdOrdering()) != null) {
                ExistingCustomer ec = existingCustomerService.findByIdcust(customer.getIdOrdering());

                if (existingBeneficiaryService.findByIdExistingCust(ec.getId()) == null){
                    log.error("Data Beneficiary not available");
                    return 0;
                }
                ExistingBeneficiary eb = existingBeneficiaryService.findByIdExistingCust(ec.getId());
                eb.setStatus(customer.getStatusRegister());

                if (customer.getStatusRegister() == StatusRegister.VERIFIED) {
                    // if data exist return data already exist
                    if (ec.getActivationCode() != null){
                        return 2;
                    }
                    activationCodeService.insertExistingCustomer(existingCustomerService.findByIdcust(customer.getIdOrdering()));
                }

                statusRegister.setEmail(ec.getEmail());
                statusRegister.setLongName(ec.getLongname());
                statusRegister.setStatusRegister(customer.getStatusRegister().toString());
                statusRegister.setRegistrationDate(ec.getCreateDate().format(formatter));

                ec.setStatus(customer.getStatusRegister());
                ec.setNotes(customer.getMessage());
                existingCustomerService.save(ec);
                existingBeneficiaryService.save(eb);
                approvedEmailNotifService.statusRegisterNotification(statusRegister);
                log.info("success {}", customer.getStatusRegister());
                return 1;
            }
            return 0;

        } catch (Exception e) {
            log.error("error in action portal, {}", e.getMessage());
            throw new CustomException("500");
        }
    }

    public OrderingCustomer findByNoResidenceCard(String no_residence_card) {
        return orderingCustomerRepository.findByIdNumber1(no_residence_card);
    }

    public boolean cekNoResidenceCard(String noResidenceCard) {
        boolean result = orderingCustomerRepository.existsByIdNumber1(noResidenceCard);
        return result == true ? true : false;
    }

    public boolean cekEmailOrderingCustomer(String email) {
        boolean result = orderingCustomerRepository.existsByEmail(email);
        return result == true ? true : false;
    }

    public OrderingCustomer findByEmail(String email) {
        return orderingCustomerRepository.findByEmail(email);
    }

    public String getReferenceNumOrderingCustomer(OrderingCustomer orderingCustomer) {
        Integer num = orderingCustomer.getId();
        String result;

        if (num >= 0 && num < 10) {
            result = "NEWTKY00000" + num;
        } else if (num >= 10 && num < 100) {
            result = "NEWTKY0000" + num;
        } else if (num >= 100 && num < 1000) {
            result = "NEWTKY000" + num;
        } else if (num >= 1000 && num < 10000) {
            result = "NEWTKY00" + num;
        } else {
            result = "NEWTKY0" + num;
        }

        OrderingCustomer byId = findById(num);
        byId.setReferenceNumber(result);
        orderingCustomerRepository.save(byId);

        return result;
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

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }
}
