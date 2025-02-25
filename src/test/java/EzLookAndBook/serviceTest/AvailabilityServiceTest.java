package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.serviceProvider.availability.AvailabilityDTO;
import EzLookAndBook.serviceProvider.availability.AvailabilityRepository;
import EzLookAndBook.serviceProvider.availability.AvailabilityRequest;
import EzLookAndBook.serviceProvider.availability.AvailabilityService;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
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
public class AvailabilityServiceTest extends DeleteBeforeEach {
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    ServiceOptionRepository serviceOptionRepository;
    @Autowired
    TestHelper testHelper;

    @Test
    public void whenAddAvailabilityToServiceOptionAvailabilityShouldBeFindInAvailabilityOptionList() {
        ServiceOption serviceOption = testHelper.newServiceOption();

        AvailabilityRequest request = new AvailabilityRequest();
        request.setAvailableDate(LocalDate.of(2025, 3, 10));
        request.setAvailableHours(List.of(LocalTime.of(9, 0), LocalTime.of(10, 30),
                LocalTime.of(15, 0)));

        availabilityService.addAvailability(serviceOption.getId(), request,
                new TestPrincipal("owner@example.com"));

        List<AvailabilityDTO> availabilityList = availabilityService.findAvailabilityDateOption(serviceOption.getId());

        assertEquals(availabilityList.size(), 1);
        assertEquals(availabilityList.get(0).availableDate(), LocalDate.of(2025, 3, 10));
        assertEquals(availabilityList.get(0).availableHours().size(), 3);
        assertEquals(availabilityList.get(0).availableHours().get(0), LocalTime.of(9, 0));
    }
}
