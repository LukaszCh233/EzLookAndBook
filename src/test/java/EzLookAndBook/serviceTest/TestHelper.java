package EzLookAndBook.serviceTest;

import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.account.owner.VerificationStatus;
import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.availability.AvailabilityRepository;
import EzLookAndBook.serviceProvider.booking.Booking;
import EzLookAndBook.serviceProvider.booking.BookingRepository;
import EzLookAndBook.serviceProvider.booking.Status;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfile;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinion;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class TestHelper {
    @Autowired
    ServiceOptionRepository serviceOptionRepository;
    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BusinessProfileRepository businessProfileRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceOpinionRepository serviceOpinionRepository;

    public ServiceCategory newServiceCategory(String name) {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName(name);

        return serviceCategoryRepository.save(serviceCategory);
    }

    public ServiceProvider newServiceProvider() {
        Owner owner = newOwner("owner@example.com");

        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName("category");
        serviceCategoryRepository.save(serviceCategory);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName("service");
        serviceProvider.setAddress("address");
        serviceProvider.setNumberPhone("123456789");
        serviceProvider.setCity("city");
        serviceProvider.setServiceCategory(serviceCategory);
        serviceProvider.setOwner(owner);

        return serviceProviderRepository.save(serviceProvider);
    }

    public Client newClient(String city, String email) {
        Client client = new Client();
        client.setName("client");
        client.setEmail(email);
        client.setPassword("password");
        client.setCity(city);

        return clientRepository.save(client);
    }

    public Owner newOwner(String email) {
        Owner owner = new Owner();
        owner.setName("owner");
        owner.setLastName("ownerLastName");
        owner.setEmail(email);
        owner.setPassword("password");

        return ownerRepository.save(owner);
    }

    public BusinessProfile newBusinessProfile() {
        Owner owner = newOwner("owner@example.com");
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName("category");
        serviceCategoryRepository.save(serviceCategory);

        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setBusinessAddress("test 1");
        businessProfile.setBusinessName("business");
        businessProfile.setCity("test");
        businessProfile.setPhoneNumber("123456789");
        businessProfile.setTaxId("123456");
        businessProfile.setServiceCategory(serviceCategory);
        businessProfile.setOwner(owner);
        businessProfile.setVerificationStatus(VerificationStatus.PENDING);

        return businessProfileRepository.save(businessProfile);
    }

    public ServiceOption newServiceOption() {
        ServiceProvider serviceProvider = newServiceProvider();

        ServiceOption serviceOption = new ServiceOption();
        serviceOption.setServiceProvider(serviceProvider);
        serviceOption.setName("option");
        serviceOption.setDescription("description");
        serviceOption.setPrice("50");

        return serviceOptionRepository.save(serviceOption);
    }

    public Availability newAvailability() {
        ServiceOption serviceOption = newServiceOption();
        Availability availability = new Availability();
        availability.setServiceOption(serviceOption);
        availability.setAvailableDate(LocalDate.of(2026, 3, 15));
        availability.setAvailableHours(List.of(LocalTime.of(9, 0), LocalTime.of(14, 0)));

        return availabilityRepository.save(availability);
    }

    public Booking newBooking(Status status) {
        Client client = newClient("city", "client@example.com");

        Availability availability = newAvailability();

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setNumber("12345689");
        booking.setServiceProvider(availability.getServiceOption().getServiceProvider());
        booking.setAppointmentDate(LocalDate.of(2026, 3, 15));
        booking.setAppointmentHour(LocalTime.of(9, 0));
        booking.setStatus(status);
        booking.setServiceOption(availability.getServiceOption());

        return bookingRepository.save(booking);
    }

    public ServiceOpinion newServiceOpinion() {
        Client client = newClient("city", "client@example.com");
        ServiceProvider serviceProvider = newServiceProvider();

        ServiceOpinion serviceOpinion = new ServiceOpinion();
        serviceOpinion.setOpinion("opinion");
        serviceOpinion.setRating(1);
        serviceOpinion.setServiceProvider(serviceProvider);
        serviceOpinion.setUserName(client.getName());
        serviceOpinion.setLocalDateTime(LocalDateTime.now());

        return serviceOpinionRepository.save(serviceOpinion);
    }
}