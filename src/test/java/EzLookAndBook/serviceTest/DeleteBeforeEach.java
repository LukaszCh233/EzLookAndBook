package EzLookAndBook.serviceTest;

import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.serviceProvider.booking.BookingRepository;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileRepository;
import EzLookAndBook.serviceProvider.report.ReportedOpinionRepository;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionRepository;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionRepository;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteBeforeEach {
    @Autowired
    protected BookingRepository bookingRepository;

    @Autowired
    protected ServiceOptionRepository serviceOptionRepository;

    @Autowired
    protected BusinessProfileRepository businessProfileRepository;

    @Autowired
    protected ServiceProviderRepository serviceProviderRepository;

    @Autowired
    protected ServiceCategoryRepository serviceCategoryRepository;
    @Autowired
    protected ServiceOpinionRepository serviceOpinionRepository;
    @Autowired
    protected ReportedOpinionRepository reportedOpinionRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected OwnerRepository ownerRepository;

    @BeforeEach
    public void setUp() {
        reportedOpinionRepository.deleteAll();
        serviceOpinionRepository.deleteAll();
        bookingRepository.deleteAll();
        serviceOptionRepository.deleteAll();
        businessProfileRepository.deleteAll();
        serviceProviderRepository.deleteAll();
        serviceCategoryRepository.deleteAll();
        clientRepository.deleteAll();
        ownerRepository.deleteAll();
    }
}
