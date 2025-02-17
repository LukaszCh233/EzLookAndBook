package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.account.client.ClientDTO;
import EzLookAndBook.account.client.ClientService;
import EzLookAndBook.serviceProvider.booking.BookingDTO;
import EzLookAndBook.serviceProvider.booking.BookingRequest;
import EzLookAndBook.serviceProvider.booking.BookingService;
import EzLookAndBook.serviceProvider.serviceOpinion.OpinionRequest;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientPanelController {
    private final ServiceProviderService serviceProviderService;
    private final ClientService clientService;
    private final ServiceOpinionService serviceOpinionService;
    private final BookingService bookingService;

    public ClientPanelController(ServiceProviderService serviceProviderService, ClientService clientService,
                                 ServiceOpinionService serviceOpinionService, BookingService bookingService) {
        this.serviceProviderService = serviceProviderService;
        this.clientService = clientService;
        this.serviceOpinionService = serviceOpinionService;
        this.bookingService = bookingService;
    }

    @PutMapping("/userCity/{city}")
    public ResponseEntity<String> setUserCityForClient(@PathVariable String city, Principal principal) {
        serviceProviderService.setCityForClient(city, principal);
        return ResponseEntity.ok(city);
    }

    @GetMapping("/client")
    public ResponseEntity<ClientDTO> displayClientInfo(Principal principal) {
        ClientDTO client = clientService.getClientInfo(principal);

        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/serviceOpinion/{opinionId}")
    public ResponseEntity<?> deleteUserOpinion(@PathVariable Long opinionId, Principal principal) {
        serviceOpinionService.deleteOpinionByIdForClient(opinionId, principal);

        return ResponseEntity.ok("Opinion has been deleted");
    }

    @PostMapping("/serviceOpinion/{serviceProviderId}")
    public ResponseEntity<String> addServiceOpinion(@PathVariable Long serviceProviderId,
                                                    @RequestBody @Valid OpinionRequest opinionRequest,
                                                    Principal principal) {
        serviceOpinionService.addOpinion(serviceProviderId, opinionRequest, principal);

        return ResponseEntity.ok("Opinion has been added");
    }

    @PostMapping("/booking")
    public ResponseEntity<String> bookingServiceOption(@RequestBody @Valid BookingRequest bookingRequest,
                                                       Principal principal) {
        bookingService.createReservation(bookingRequest, principal);

        return ResponseEntity.ok("Successful booking");
    }

    @GetMapping("/booking")
    public ResponseEntity<List<BookingDTO>> displayClientBookingList(Principal principal) {
        List<BookingDTO> bookingDTOList = bookingService.findBookingByPrincipal(principal);

        return ResponseEntity.ok(bookingDTOList);
    }
}
