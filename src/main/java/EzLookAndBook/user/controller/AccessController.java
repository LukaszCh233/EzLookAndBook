package EzLookAndBook.user.controller;

import EzLookAndBook.user.dto.AdminDTO;
import EzLookAndBook.user.dto.ClientDTO;
import EzLookAndBook.user.dto.OwnerDTO;
import EzLookAndBook.user.entity.Admin;
import EzLookAndBook.user.entity.Client;
import EzLookAndBook.user.entity.LoginRequest;
import EzLookAndBook.user.entity.OwnerRegister;
import EzLookAndBook.user.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access")
public class AccessController {
    private final AccountService accountService;

    public AccessController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/owner-register")
    public ResponseEntity<OwnerDTO> registerOwner(@Valid @RequestBody OwnerRegister ownerRegister) {
        OwnerDTO createOwnerDTO = accountService.createOwner(ownerRegister);

        return ResponseEntity.ok(createOwnerDTO);
    }

    @PostMapping("/client-register")
    public ResponseEntity<ClientDTO> registerClient(@Valid @RequestBody Client client) {
        ClientDTO createClientDTO = accountService.createClient(client);

        return ResponseEntity.ok(createClientDTO);
    }

    @PostMapping("/owner-login")
    ResponseEntity<String> loginOwner(@Valid @RequestBody LoginRequest owner) {
        String jwtToken = accountService.ownerAuthorization(owner);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/client-login")
    ResponseEntity<String> loginClient(@Valid @RequestBody LoginRequest client) {
        String jwtToken = accountService.clientAuthorization(client);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/admin-register")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody Admin admin) {
        AdminDTO createAdminDTO = accountService.createAdmin(admin);

        return ResponseEntity.ok(createAdminDTO);
    }

    @PostMapping("/admin-login")
    ResponseEntity<String> loginAdmin(@Valid @RequestBody LoginRequest admin) {
        String jwtToken = accountService.adminAuthorization(admin);

        return ResponseEntity.ok(jwtToken);
    }
}
