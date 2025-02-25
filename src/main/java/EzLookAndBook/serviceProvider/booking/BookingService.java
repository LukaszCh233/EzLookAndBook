package EzLookAndBook.serviceProvider.booking;

import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.availability.AvailabilityRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceOptionRepository serviceOptionRepository;
    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;
    private final EntityMapper entityMapper;

    public BookingService(BookingRepository bookingRepository, ClientRepository clientRepository,
                          ServiceProviderRepository serviceProviderRepository,
                          ServiceOptionRepository serviceOptionRepository, AvailabilityRepository availabilityRepository,
                          OwnerRepository ownerRepository, EntityMapper entityMapper) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.serviceOptionRepository = serviceOptionRepository;
        this.availabilityRepository = availabilityRepository;
        this.ownerRepository = ownerRepository;
        this.entityMapper = entityMapper;
    }

    public void createReservation(BookingRequest bookingRequest, Principal principal) {
        String email = principal.getName();

        Client client = clientRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findById(bookingRequest.getServiceProviderId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        ServiceOption serviceOption = serviceOptionRepository.findById(bookingRequest.getServiceOptionId())
                .orElseThrow(() -> new EntityNotFoundException("Option not found"));

        Availability availability = availabilityRepository.findByServiceOption_idAndAvailableDate(
                        serviceOption.getId(), bookingRequest.getAppointmentDate())
                .orElseThrow(() -> new EntityNotFoundException("Date not available"));

        if (!availability.getAvailableHours().contains(bookingRequest.getAppointmentHour())) {
            throw new IllegalArgumentException("Hour not available");
        }

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setServiceProvider(serviceProvider);
        booking.setServiceOption(serviceOption);
        booking.setNumber(bookingRequest.getNumber());
        booking.setAppointmentDate(bookingRequest.getAppointmentDate());
        booking.setAppointmentHour(bookingRequest.getAppointmentHour());
        booking.setStatus(Status.PENDING);

        bookingRepository.save(booking);
    }

    public BookingDTO findBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new EntityNotFoundException("Booking not found"));

        return entityMapper.mapBookingToBookingDTO(booking);
    }

    public List<BookingDTO> findPendingBookings(Principal principal) {
        return findBookingsByStatusAndPrincipal(Status.PENDING, principal);
    }

    public List<BookingDTO> findConfirmedBookings(Principal principal) {
        return findBookingsByStatusAndPrincipal(Status.CONFIRMED, principal);
    }

    public List<BookingDTO> findBookingByPrincipal(Principal principal) {
        String email = principal.getName();

        List<Booking> bookingList = bookingRepository.findByClientEmail(email);

        if (bookingList.isEmpty()) {
            throw new EntityNotFoundException("Bookings not found for client with email: " + email);
        }

        return entityMapper.mapBookingListToBookingListDTO(bookingList);
    }

    public void changeReservationStatus(Long bookingId, Status status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new EntityNotFoundException("Booking not found"));
        booking.setStatus(status);
        if (status.equals(Status.CONFIRMED)) {
            Availability availability = availabilityRepository.findByServiceOption_idAndAvailableDate(
                            booking.getServiceOption().getId(), booking.getAppointmentDate())
                    .orElseThrow(() -> new EntityNotFoundException("Date not available"));
            availability.getAvailableHours().remove(booking.getAppointmentHour());

            availabilityRepository.save(availability);
        }
        bookingRepository.save(booking);
    }

    public void rejectReservation(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new EntityNotFoundException("Booking not found"));
        if (booking.getStatus().equals(Status.CONFIRMED)) {
            booking.setStatus(Status.REJECTED);
            Availability availability = availabilityRepository.findByServiceOption_idAndAvailableDate(
                            booking.getServiceOption().getId(), booking.getAppointmentDate())
                    .orElseThrow(() -> new EntityNotFoundException("Date not available"));
            availability.getAvailableHours().add(booking.getAppointmentHour());

            availabilityRepository.save(availability);
        } else if (booking.getStatus().equals(Status.PENDING)) {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
    }


    private List<BookingDTO> findBookingsByStatusAndPrincipal(Status status, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Owner not found"));

        ServiceProvider serviceProvider = serviceProviderRepository.findByOwnerId(owner.getId()).orElseThrow(() ->
                new EntityNotFoundException("Service provider not found for this owner id"));

        List<Booking> bookingList = bookingRepository.findByStatusAndServiceProviderId(status, serviceProvider.getId());
        if (bookingList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }

        return entityMapper.mapBookingListToBookingListDTO(bookingList);
    }
}
