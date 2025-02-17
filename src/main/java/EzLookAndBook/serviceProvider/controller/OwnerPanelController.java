package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.availability.AvailabilityRequest;
import EzLookAndBook.serviceProvider.availability.AvailabilityService;
import EzLookAndBook.serviceProvider.booking.BookingDTO;
import EzLookAndBook.serviceProvider.booking.BookingService;
import EzLookAndBook.serviceProvider.booking.Status;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileService;
import EzLookAndBook.serviceProvider.businessProfile.BusinessVerificationRequest;
import EzLookAndBook.serviceProvider.report.ReportOpinionRequest;
import EzLookAndBook.serviceProvider.report.ReportedOpinionService;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRequest;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerPanelController {
    private final ServiceOptionService serviceOptionService;
    private final AvailabilityService availabilityService;
    private final ServiceProviderService serviceProviderService;
    private final BusinessProfileService businessProfileService;
    private final BookingService bookingService;
    private final ReportedOpinionService reportedOpinionService;

    public OwnerPanelController(ServiceOptionService serviceOptionService, AvailabilityService availabilityService,
                                ServiceProviderService serviceProviderService,
                                BusinessProfileService businessProfileService, BookingService bookingService,
                                ReportedOpinionService reportedOpinionService) {
        this.serviceOptionService = serviceOptionService;
        this.availabilityService = availabilityService;
        this.serviceProviderService = serviceProviderService;
        this.businessProfileService = businessProfileService;
        this.bookingService = bookingService;
        this.reportedOpinionService = reportedOpinionService;
    }

    @PostMapping("/businessVerification")
    public ResponseEntity<String> sendVerificationRequest(@Valid @RequestBody
                                                          BusinessVerificationRequest businessVerificationRequest,
                                                          Principal principal) {
        businessProfileService.submitBusinessForVerification
                (businessVerificationRequest, principal);

        return ResponseEntity.ok("The request has been sent ");
    }

    @GetMapping("/businessProfile")
    public ResponseEntity<List<BusinessProfileDTO>> displayBusinessProfileList(Principal principal) {
        List<BusinessProfileDTO> businessProfileDTO = businessProfileService.findBusinessProfileListByPrincipal(principal);

        return ResponseEntity.ok(businessProfileDTO);
    }

    @GetMapping("/serviceProvider")
    public ResponseEntity<ServiceProviderDTO> displayServiceProviderByPrincipal(Principal principal) {
        ServiceProviderDTO serviceProviderDTO = serviceProviderService.findServiceProviderByPrincipal(principal);

        return ResponseEntity.ok(serviceProviderDTO);
    }

    @PostMapping("/serviceOption")
    public ResponseEntity<String> addServiceOptionToService(@RequestBody @Valid ServiceOptionRequest serviceOptionRequest,
                                                            Principal principal) {
        serviceOptionService.addServiceOption(serviceOptionRequest, principal);

        return ResponseEntity.ok("service option has been added");
    }

    @PostMapping("/availableDates/{serviceOptionId}")
    public ResponseEntity<?> addAvailableServiceDates(@PathVariable Long serviceOptionId,
                                                      @RequestBody @Valid AvailabilityRequest availabilityRequest,
                                                      Principal principal) {
        availabilityService.addAvailability(serviceOptionId, availabilityRequest, principal);

        return ResponseEntity.ok("Available Dates has been added");
    }

    @PutMapping("/serviceOption/{serviceOptionId}")
    public ResponseEntity<?> updateServiceOption(@PathVariable Long serviceOptionId,
                                                 @RequestBody @Valid ServiceOptionRequest serviceOptionRequest,
                                                 Principal principal) {
        serviceOptionService.updateServiceOption(serviceOptionId, serviceOptionRequest, principal);

        return ResponseEntity.ok("Service option has been updated");
    }

    @PutMapping("/availability/{serviceOptionId}")
    public ResponseEntity<?> updateAvailabilityHour(@PathVariable Long serviceOptionId,
                                                    @RequestBody @Valid AvailabilityRequest availabilityRequest,
                                                    Principal principal) {
        availabilityService.updateAvailabilityHour(serviceOptionId, availabilityRequest, principal);

        return ResponseEntity.ok("Hours has been updated");
    }

    @DeleteMapping("/serviceOption/{serviceOptionId}")
    public ResponseEntity<?> deleteServiceOption(@PathVariable Long serviceOptionId, Principal principal) {
        serviceOptionService.deleteServiceOption(serviceOptionId, principal);

        return ResponseEntity.ok("Service option has been deleted");
    }

    @DeleteMapping("/availability/{serviceOptionId}/{availabilityId}")
    public ResponseEntity<?> deleteAvailabilityDate(@PathVariable Long serviceOptionId, @PathVariable Long availabilityId
            , Principal principal) {
        availabilityService.deleteAvailabilityDate(serviceOptionId, availabilityId, principal);

        return ResponseEntity.ok("Date has been deleted");
    }

    @DeleteMapping("/availability/{serviceOptionId}/{availabilityId}/{hour}")
    public ResponseEntity<?> deleteAvailabilityHour(@PathVariable Long serviceOptionId, @PathVariable Long availabilityId,
                                                    @PathVariable LocalTime hour, Principal principal) {

        availabilityService.deleteAvailabilityHour(serviceOptionId, availabilityId, hour, principal);

        return ResponseEntity.ok("Hour has been deleted");
    }

    @PostMapping("reportedOpinion")
    public ResponseEntity<String> sendReportOpinionToAdmin(@Valid @RequestBody ReportOpinionRequest reportOpinionRequest,
                                                           Principal principal) {
        reportedOpinionService.reportOpinionToAdmin(reportOpinionRequest, principal);

        return ResponseEntity.ok("Report has been sent");
    }

    @GetMapping("/booking/pending")
    public ResponseEntity<List<BookingDTO>> displayPendingBookingList(Principal principal) {
        List<BookingDTO> bookingDTOList = bookingService.findPendingBookings(principal);

        return ResponseEntity.ok(bookingDTOList);
    }

    @GetMapping("/booking/confirmed")
    public ResponseEntity<List<BookingDTO>> displayConfirmedBookingList(Principal principal) {
        List<BookingDTO> bookingDTOList = bookingService.findConfirmedBookings(principal);

        return ResponseEntity.ok(bookingDTOList);
    }

    @PutMapping("/booking/{bookingId}/{status}")
    public ResponseEntity<String> reservationManagement(@PathVariable Long bookingId, @PathVariable Status status) {
        bookingService.changeReservationStatus(bookingId, status);

        return ResponseEntity.ok("Reservation " + status);
    }
}
