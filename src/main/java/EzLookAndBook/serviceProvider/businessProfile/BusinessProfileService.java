package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.account.owner.VerificationStatus;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BusinessProfileService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final BusinessProfileRepository businessProfileRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final OwnerRepository ownerRepository;
    private final EntityMapper entityMapper;

    public BusinessProfileService(ServiceProviderRepository serviceProviderRepository,
                                  BusinessProfileRepository businessProfileRepository,
                                  ServiceCategoryRepository serviceCategoryRepository,
                                  OwnerRepository ownerRepository, EntityMapper entityMapper) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.ownerRepository = ownerRepository;
        this.entityMapper = entityMapper;
    }

    public List<BusinessDTO> findAllBusinessForVerification() {
        return findBusinessByStatus(VerificationStatus.PENDING);
    }

    public List<BusinessDTO> findAllVerifiedBusiness() {
        return findBusinessByStatus(VerificationStatus.APPROVED);
    }

    public List<BusinessDTO> findAllRejectedBusiness() {
        return findBusinessByStatus(VerificationStatus.REJECTED);
    }

    //maybe add info for owner when business verified(email mess)
    public void approveVerification(Long businessVerificationId) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessVerificationId)
                .orElseThrow(() -> new EntityNotFoundException("Verification not found"));

        if (businessProfile.getVerificationStatus().equals(VerificationStatus.APPROVED)) {
            throw new IllegalStateException("Verification is already approved");
        }
        businessProfile.setVerificationStatus(VerificationStatus.APPROVED);

        businessProfileRepository.save(businessProfile);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(businessProfile.getBusinessName());
        serviceProvider.setAddress(businessProfile.getBusinessAddress());
        serviceProvider.setCity(businessProfile.getCity());
        serviceProvider.setNumberPhone(businessProfile.getPhoneNumber());
        serviceProvider.setOwner(businessProfile.getOwner());
        serviceProvider.setServiceCategory(businessProfile.getServiceCategory());

        serviceProviderRepository.save(serviceProvider);
    }

    public void rejectVerification(Long businessVerificationId) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessVerificationId)
                .orElseThrow(() -> new EntityNotFoundException("Verification not found"));

        if (businessProfile.getVerificationStatus().equals(VerificationStatus.REJECTED)) {
            throw new IllegalStateException("Verification is already approved");
        }
        businessProfile.setVerificationStatus(VerificationStatus.REJECTED);

        businessProfileRepository.save(businessProfile);
    }

    public BusinessProfileDTO findBusinessProfileById(Long businessProfileId) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessProfileId).orElseThrow(() ->
                new EntityNotFoundException("Business profile not found"));

        return entityMapper.mapBusinessProfileToBusinessProfileDTO(businessProfile);
    }

    public void submitBusinessForVerification(BusinessVerificationRequest businessVerificationRequest,
                                              Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        ServiceCategory serviceCategory = serviceCategoryRepository.findById(businessVerificationRequest
                .getServiceCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setBusinessName(businessVerificationRequest.getBusinessName());
        businessProfile.setBusinessAddress(businessVerificationRequest.getBusinessAddress());
        businessProfile.setCity(businessVerificationRequest.getCity());
        businessProfile.setPhoneNumber(businessVerificationRequest.getPhoneNumber());
        businessProfile.setOwner(owner);
        businessProfile.setServiceCategory(serviceCategory);
        businessProfile.setTaxId(businessVerificationRequest.getTaxId());
        businessProfile.setVerificationStatus(VerificationStatus.PENDING);

        businessProfileRepository.save(businessProfile);
    }

    public List<BusinessProfileDTO> findBusinessProfileListByPrincipal(Principal principal) {
        String email = principal.getName();

        List<BusinessProfile> businessProfileList = businessProfileRepository.findAllByOwnerEmail(email);
        if (businessProfileList.isEmpty()) {
            throw new EntityNotFoundException("Business list is empty");

        }
        return entityMapper.mapBusinessProfileListToBusinessProfileListDTO(businessProfileList);
    }

    private List<BusinessDTO> findBusinessByStatus(VerificationStatus status) {
        List<BusinessProfile> businessProfileList = businessProfileRepository.findByVerificationStatus(status);
        if (businessProfileList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return entityMapper.mapBusinessVerificationListToBusinessListDTO(businessProfileList);
    }

    public List<BusinessOwnerDTO> findBusinessOwnerList() {
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        if (businessProfileList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return entityMapper.mapBusinessProfileListToBusinessOwnerListDTO(businessProfileList);
    }
}
