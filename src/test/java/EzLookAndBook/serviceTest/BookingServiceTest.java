package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.booking.*;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BookingServiceTest extends DeleteBeforeEach {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TestHelper testHelper;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceOptionRepository serviceOptionRepository;
    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Test
    public void whenReservationServiceOptionReservationShouldBeFindInPendingBookingList() {
        testHelper.newClient("city", "client@example.com");
        Availability availability = testHelper.newAvailability();

        BookingRequest request = new BookingRequest();
        request.setServiceProviderId(availability.getServiceOption().getServiceProvider().getId());
        request.setServiceOptionId(availability.getServiceOption().getId());
        request.setAppointmentDate(LocalDate.of(2026, 3, 15));
        request.setAppointmentHour(LocalTime.of(9, 0));
        request.setNumber("123456789");

        bookingService.createReservation(request, new TestPrincipal("client@example.com"));

        List<BookingDTO> bookingList = bookingService.findPendingBookings(new TestPrincipal("owner@example.com"));

        assertEquals(bookingList.size(), 1);
        assertEquals(bookingList.get(0).name(), "client");
        assertEquals(bookingList.get(0).date(), LocalDate.of(2026, 3, 15));
        assertEquals(bookingList.get(0).hour(), LocalTime.of(9, 0));
    }

    @Test
    public void whenReservationIsConfirmedAndChangedToRejected_thenAvailabilityHourIsReturnedToAvailableServiceOptionHours() {
        Booking booking = testHelper.newBooking(Status.CONFIRMED);

        bookingService.rejectReservation(booking.getId());


        List<BookingDTO> bookingList = bookingService.findBookingByPrincipal(new TestPrincipal("client@example.com"));

        assertEquals(bookingList.get(0).status(), Status.REJECTED);
    }

    @Test
    public void whenReservationIsPendingAndChangedToRejected_thenReservationIsRejected() {
        Booking booking = testHelper.newBooking(Status.PENDING);
        bookingService.rejectReservation(booking.getId());

        List<BookingDTO> bookingList = bookingService.findBookingByPrincipal(new TestPrincipal("client@example.com"));

        assertEquals(bookingList.get(0).status(), Status.REJECTED);
    }
}
