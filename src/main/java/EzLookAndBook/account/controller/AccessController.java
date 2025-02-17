package EzLookAndBook.account.controller;

import EzLookAndBook.account.admin.Admin;
import EzLookAndBook.account.admin.AdminDTO;
import EzLookAndBook.account.admin.AdminService;
import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientDTO;
import EzLookAndBook.account.client.ClientService;
import EzLookAndBook.account.input.LoginRequest;
import EzLookAndBook.account.owner.OwnerDTO;
import EzLookAndBook.account.owner.OwnerRegister;
import EzLookAndBook.account.owner.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access")
public class AccessController {
    private final ClientService clientService;
    private final AdminService adminService;
    private final OwnerService ownerService;

    public AccessController(ClientService clientService, AdminService adminService, OwnerService ownerService) {
        this.clientService = clientService;
        this.adminService = adminService;
        this.ownerService = ownerService;
    }

    @PostMapping("/client-register")
    public ResponseEntity<ClientDTO> registerClient(@Valid @RequestBody Client client) {
        ClientDTO createClientDTO = clientService.createClient(client);

        return ResponseEntity.ok(createClientDTO);
    }

    @PostMapping("/client-login")
    ResponseEntity<String> loginClient(@Valid @RequestBody LoginRequest client) {
        String jwtToken = clientService.clientAuthorization(client);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/owner-register")
    public ResponseEntity<OwnerDTO> registerOwner(@Valid @RequestBody OwnerRegister ownerRegister) {
        OwnerDTO createOwnerDTO = ownerService.createOwner(ownerRegister);

        return ResponseEntity.ok(createOwnerDTO);
    }

    @PostMapping("/owner-login")
    ResponseEntity<String> loginOwner(@Valid @RequestBody LoginRequest owner) {
        String jwtToken = ownerService.ownerAuthorization(owner);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/admin-register")
    public ResponseEntity<AdminDTO> registerAdmin(@Valid @RequestBody Admin admin) {
        AdminDTO createAdminDTO = adminService.createAdmin(admin);

        return ResponseEntity.ok(createAdminDTO);
    }

    @PostMapping("/admin-login")
    ResponseEntity<String> loginAdmin(@Valid @RequestBody LoginRequest admin) {
        String jwtToken = adminService.adminAuthorization(admin);

        return ResponseEntity.ok(jwtToken);
    }
}
