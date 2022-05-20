package com.mybank.accounts;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mybank.customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceConfig accountConf;

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {
        return accountService.findByCustomerId(customer.getCustomerId()).orElse(null);
    }

    @GetMapping("/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountConf.getMsg(), accountConf.getBuildVersion(), accountConf.getMailDetails(), accountConf.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

}
