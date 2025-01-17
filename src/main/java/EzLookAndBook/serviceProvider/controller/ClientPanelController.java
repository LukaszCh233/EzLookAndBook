package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.booking.BookingDTO;
import EzLookAndBook.serviceProvider.booking.BookingRequest;
import EzLookAndBook.serviceProvider.booking.BookingService;
import EzLookAndBook.serviceProvider.serviceOpinion.OpinionRequest;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientPanelController {
    private final ServiceProviderService serviceProviderService;
    private final ServiceOptionService serviceOptionService;
    private final ServiceOpinionService serviceOpinionService;
    private final BookingService bookingService;

    public ClientPanelController(ServiceProviderService serviceProviderService, ServiceOptionService serviceOptionService,
                                 ServiceOpinionService serviceOpinionService, BookingService bookingService) {
        this.serviceProviderService = serviceProviderService;
        this.serviceOptionService = serviceOptionService;
        this.serviceOpinionService = serviceOpinionService;
        this.bookingService = bookingService;
    }

    @PostMapping("/userCity/{city}")
    public ResponseEntity<String> setUserCityForClient(@PathVariable String city, Principal principal) {
        serviceProviderService.setCityForClient(city, principal);
        return ResponseEntity.ok(city);
    }

    @GetMapping("/serviceProvider/{serviceProviderId}")
    public ResponseEntity<ServiceProviderDTO> displayServiceProviderById(@PathVariable Long serviceProviderId, Principal principal) {
        ServiceProviderDTO serviceProviderDTO = serviceProviderService.findServiceProviderById(serviceProviderId, principal);

        return ResponseEntity.ok(serviceProviderDTO);
    }

    @GetMapping("/serviceProvider/category/{categoryId}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByCategoryId(@PathVariable Long categoryId, Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.findServiceProviderByCategoryId(categoryId, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    @GetMapping("/serviceProvider/categoryName/{categoryName}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByCategoryName(@PathVariable String categoryName, Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.findServiceProviderByCategoryName(categoryName, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    //not work
    @GetMapping("/serviceProvider/option/{serviceOption}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByServiceOption(@PathVariable String serviceOption, Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.findServiceProviderByServiceOption(serviceOption, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    @GetMapping("/serviceOptions/{serviceProviderId}")
    public ResponseEntity<List<ServiceOptionDTO>> displayServiceOptions(@PathVariable Long serviceProviderId) {
        List<ServiceOptionDTO> serviceOptionDTOList = serviceOptionService.findAllServiceOption(serviceProviderId);

        return ResponseEntity.ok(serviceOptionDTOList);
    }

    @DeleteMapping("/serviceOpinion/{opinionId}")
    public ResponseEntity<?> deleteUserOpinion(@PathVariable Long opinionId, Principal principal) {
        serviceOpinionService.deleteOpinionByIdForClient(opinionId, principal);

        return ResponseEntity.ok("Opinion has been deleted");
    }

    @PostMapping("/serviceOpinion/{serviceProviderId}")
    public ResponseEntity<String> addServiceOpinion(@PathVariable Long serviceProviderId,
                                                    @RequestBody OpinionRequest opinionRequest,
                                                    Principal principal) {
        serviceOpinionService.addOpinion(serviceProviderId, opinionRequest, principal);

        return ResponseEntity.ok("Opinion has been added");
    }

    @PostMapping("/booking")
    public ResponseEntity<String> bookingServiceOption(@RequestBody BookingRequest bookingRequest, Principal principal) {
        bookingService.createReservation(bookingRequest, principal);

        return ResponseEntity.ok("Successful booking");
    }

    @GetMapping("/booking")
    public ResponseEntity<List<BookingDTO>> displayClientBookingList(Principal principal) {
        List<BookingDTO> bookingDTOList = bookingService.findBookingByPrincipal(principal);

        return ResponseEntity.ok(bookingDTOList);
    }
}
