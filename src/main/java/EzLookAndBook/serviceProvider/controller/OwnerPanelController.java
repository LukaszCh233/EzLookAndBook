package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.availability.AvailabilityDTO;
import EzLookAndBook.serviceProvider.booking.Status;
import EzLookAndBook.serviceProvider.booking.BookingDTO;
import EzLookAndBook.serviceProvider.booking.BookingService;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileService;
import EzLookAndBook.serviceProvider.businessProfile.BusinessVerificationRequest;
import EzLookAndBook.serviceProvider.report.ReportOpinionRequest;
import EzLookAndBook.serviceProvider.report.ReportedOpinionService;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
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
    private final BusinessProfileService businessProfileService;
    private final BookingService bookingService;
    private final ReportedOpinionService reportedOpinionService;

    public OwnerPanelController(ServiceOptionService serviceOptionService, BusinessProfileService businessProfileService,
                                BookingService bookingService,
                                ReportedOpinionService reportedOpinionService) {
        this.serviceOptionService = serviceOptionService;
        this.businessProfileService = businessProfileService;
        this.bookingService = bookingService;
        this.reportedOpinionService = reportedOpinionService;
    }

    @PostMapping("/businessVerification")
    public ResponseEntity<String> sendVerificationRequest(@RequestBody BusinessVerificationRequest businessVerificationRequest, Principal principal) {
        businessProfileService.submitBusinessForVerification
                (businessVerificationRequest, principal);

        return ResponseEntity.ok("The request has been sent ");
    }

    @GetMapping("/businessProfile")
    public ResponseEntity<BusinessProfileDTO> displayBusinessProfile(Principal principal) {
        BusinessProfileDTO businessProfileDTO = businessProfileService.findBusinessProfileByPrincipal(principal);

        return ResponseEntity.ok(businessProfileDTO);
    }

    @PostMapping("/serviceOption")
    public ResponseEntity<String> addServiceOptionToService(@RequestBody ServiceOptionDTO serviceOptionDTO, Principal principal) {
        serviceOptionService.addServiceOption(serviceOptionDTO, principal);

        return ResponseEntity.ok("service option has been added");
    }

    @PostMapping("/availableDates/{serviceOptionId}")
    public ResponseEntity<?> addAvailableServiceDates(@PathVariable Long serviceOptionId,
                                                      @RequestBody AvailabilityDTO availabilityDTO, Principal principal) {
        serviceOptionService.addAvailability(serviceOptionId, availabilityDTO, principal);

        return ResponseEntity.ok("Available Dates has been added");
    }

    @PutMapping("/serviceOption/{serviceOptionId}")
    public ResponseEntity<?> updateServiceOption(@PathVariable Long serviceOptionId, @RequestBody ServiceOptionDTO serviceOptionDTO,
                                                 Principal principal) {
        serviceOptionService.updateServiceOption(serviceOptionId, serviceOptionDTO, principal);

        return ResponseEntity.ok("Service option has been updated");
    }

    @PutMapping("/availability/{serviceOptionId}")
    public ResponseEntity<?> updateAvailabilityHour(@PathVariable Long serviceOptionId,
                                                    @RequestBody AvailabilityDTO availabilityDTO, Principal principal) {
        serviceOptionService.updateAvailabilityHour(serviceOptionId, availabilityDTO, principal);

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
        serviceOptionService.deleteAvailabilityDate(serviceOptionId, availabilityId, principal);

        return ResponseEntity.ok("Date has been deleted");
    }

    @DeleteMapping("/availability/{serviceOptionId}/{availabilityId}/{hour}")
    public ResponseEntity<?> deleteAvailabilityHour(@PathVariable Long serviceOptionId, @PathVariable Long availabilityId,
                                                    @PathVariable LocalTime hour, Principal principal) {

        serviceOptionService.deleteAvailabilityHour(serviceOptionId, availabilityId, hour, principal);

        return ResponseEntity.ok("Hour has been deleted");
    }

    @PostMapping("reportedOpinion")
    public ResponseEntity<String> sendReportOpinionToAdmin(@RequestBody ReportOpinionRequest reportOpinionRequest,
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
