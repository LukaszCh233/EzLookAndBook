package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.serviceProvider.businessProfile.*;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BusinessProfileServiceTest extends DeleteBeforeEach {
    @Autowired
    private BusinessProfileRepository businessProfileRepository;
    @Autowired
    private BusinessProfileService businessProfileService;
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private TestHelper testHelper;

    @Test
    public void whenSubmitBusinessForVerificationShouldFindSubmitInBusinessListForVerification() {
        testHelper.newOwner("owner@example.com");

        ServiceCategory serviceCategory = testHelper.newServiceCategory("category");

        BusinessVerificationRequest request = new BusinessVerificationRequest();
        request.setBusinessAddress("test 1");
        request.setBusinessName("business");
        request.setCity("test");
        request.setPhoneNumber("123456789");
        request.setTaxId("123456");
        request.setServiceCategoryId(serviceCategory.getId());

        businessProfileService.submitBusinessForVerification(request, new TestPrincipal("owner@example.com"));

        List<BusinessDTO> businessProfileList = businessProfileService.findAllBusinessForVerification();

        assertEquals(businessProfileList.size(), 1);
        assertEquals(businessProfileList.get(0).businessName(), "business");
    }

    @Test
    public void whenBusinessProfileVerificationApproveBusinessProfileShouldBeFindInVerifiedBusinessList() {
        BusinessProfile businessProfile = testHelper.newBusinessProfile();

        businessProfileService.approveVerification(businessProfile.getId());

        List<BusinessDTO> businessProfileList = businessProfileService.findAllVerifiedBusiness();

        assertEquals(businessProfileList.size(), 1);
        assertEquals(businessProfileList.get(0).businessName(), "business");
    }

    @Test
    public void whenBusinessProfileVerificationRejectBusinessProfileShouldBeFindInRejectedBusinessList() {
        BusinessProfile businessProfile = testHelper.newBusinessProfile();

        businessProfileService.rejectVerification(businessProfile.getId());

        List<BusinessDTO> businessProfileList = businessProfileService.findAllRejectedBusiness();

        assertEquals(businessProfileList.size(), 1);
        assertEquals(businessProfileList.get(0).businessName(), "business");
    }
}
