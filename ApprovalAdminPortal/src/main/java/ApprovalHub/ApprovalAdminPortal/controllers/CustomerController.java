package ApprovalHub.ApprovalAdminPortal.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import ApprovalHub.ApprovalAdminPortal.models.OrderingCustomer;
import ApprovalHub.ApprovalAdminPortal.services.OrderingCustomerService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class CustomerController {

    @Autowired
    private OrderingCustomerService orderingCustomerService;

    @GetMapping("/portal/listOrderingCustomer")
    @PreAuthorize("hasRole('OPERATOR') or hasRole('ADMIN') or hasRole('INQUIRY')")
    public List<OrderingCustomer> findAll() { return orderingCustomerService.findAll();}
}
