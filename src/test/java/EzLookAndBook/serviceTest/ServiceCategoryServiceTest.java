package EzLookAndBook.serviceTest;

import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryDTO;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRequest;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceCategoryServiceTest extends DeleteBeforeEach {
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @Autowired
    private BusinessProfileRepository businessProfileRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceOptionRepository serviceOptionRepository;
    @Autowired
    private TestHelper testHelper;

    @Test
    public void whenCreateServiceCategorySuccessfullyShouldFindInServiceCategoryList() {
        ServiceCategoryRequest request = new ServiceCategoryRequest("category");

        serviceCategoryService.createServiceCategory(request);

        List<ServiceCategoryDTO> serviceCategoryList = serviceCategoryService.findAllCategories();

        assertEquals(serviceCategoryList.size(), 1);
        assertEquals(serviceCategoryList.get(0).name(), "category");
    }

    @Test
    public void shouldDropExceptionWhenCreatedServiceCategoryNameExists() {
        ServiceCategoryRequest request = new ServiceCategoryRequest("category");

        testHelper.newServiceCategory("category");

        assertThrows(ExistsException.class, () -> serviceCategoryService.createServiceCategory(request));
    }
}