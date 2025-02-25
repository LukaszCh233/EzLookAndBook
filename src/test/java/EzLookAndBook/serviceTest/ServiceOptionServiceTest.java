package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRequest;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceOptionServiceTest extends DeleteBeforeEach {

    @Autowired
    private ServiceOptionService serviceOptionService;
    @Autowired
    private ServiceOptionRepository serviceOptionRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private TestHelper testHelper;

    @Test
    public void whenAddServiceOptionToServiceThisOptionShouldBeFindInServiceOptionList() {
        ServiceProvider serviceProvider = testHelper.newServiceProvider();

        ServiceOptionRequest request = new ServiceOptionRequest();
        request.setName("option");
        request.setDescription("option number 1");
        request.setPrice("50");

        serviceOptionService.addServiceOption(request, new TestPrincipal("owner@example.com"));

        List<ServiceOptionDTO> serviceOptionList = serviceOptionService.findAllServiceOption(serviceProvider.getId());

        assertEquals(serviceOptionList.size(), 1);
        assertEquals(serviceOptionList.get(0).name(), "option");
    }
}