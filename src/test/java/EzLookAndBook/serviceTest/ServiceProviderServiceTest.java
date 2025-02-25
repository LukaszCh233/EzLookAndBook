package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderService;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceProviderServiceTest extends DeleteBeforeEach {
    @Autowired
    private ServiceProviderService serviceProviderService;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    private TestHelper testHelper;

    @Test
    public void shouldFindServiceProviderByCategoryIdByClientWhenCityServiceIsEqualsCityClient() {
        testHelper.newClient("city", "client@example.com");

        ServiceProvider serviceProvider = testHelper.newServiceProvider();

        List<ServiceProviderDTO> serviceProviderList = serviceProviderService
                .findServiceProviderByCategoryId(serviceProvider.getServiceCategory().getId(),
                        new TestPrincipal("client@example.com"));

        assertEquals(serviceProviderList.size(), 1);
        assertEquals(serviceProviderList.get(0).name(), "service");
    }

    @Test
    public void shouldFindServiceProviderByCategoryNameByNotClient() {
        ServiceProvider serviceProvider = testHelper.newServiceProvider();

        List<ServiceProviderDTO> serviceProviderList = serviceProviderService
                .findServiceProviderByCategoryName(serviceProvider.getServiceCategory().getName(),
                        new TestPrincipal(null));

        assertEquals(serviceProviderList.size(), 1);
        assertEquals(serviceProviderList.get(0).name(), "service");
    }

    @Test
    public void ifClientCityIsDifferentThanServiceProviderShouldNotFoundThisServiceProvider() {
        testHelper.newClient("differentCity", "client@example.com");

        ServiceProvider serviceProvider = testHelper.newServiceProvider();

        List<ServiceProviderDTO> serviceProviderList = serviceProviderService
                .findServiceProviderByCategoryId(serviceProvider.getServiceCategory().getId(),
                        new TestPrincipal("client@example.com"));

        assertEquals(serviceProviderList.size(), 0);

    }
}
