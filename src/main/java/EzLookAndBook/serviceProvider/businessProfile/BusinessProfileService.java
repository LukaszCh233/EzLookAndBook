package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderRepository;
import EzLookAndBook.account.owner.VerificationStatus;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BusinessProfileService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final BusinessProfileRepository businessProfileRepository;
    private final OwnerRepository ownerRepository;
    private final EntityMapper entityMapper;

    public BusinessProfileService(ServiceProviderRepository serviceProviderRepository,
                                  BusinessProfileRepository businessProfileRepository,
                                  OwnerRepository ownerRepository, EntityMapper entityMapper) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.ownerRepository = ownerRepository;
        this.entityMapper = entityMapper;
    }

    public List<BusinessDTO> findAllBusinessForVerification() {
        return findBusinessByStatus(VerificationStatus.PENDING);
    }

    public List<BusinessDTO> findAllVerifiedBusiness() {
        return findBusinessByStatus(VerificationStatus.APPROVED);
    }


    //maybe add info for owner when business verified(email mess)
    public void approveVerification(Long businessVerificationId) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessVerificationId)
                .orElseThrow(() -> new EntityNotFoundException("Verification not found"));

        if (businessProfile.getStatus().equals(VerificationStatus.APPROVED)) {
            throw new IllegalStateException("Verification is already approved");
        }
        businessProfile.setStatus(VerificationStatus.APPROVED);
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

    public BusinessProfileDTO findBusinessProfileById(Long businessProfileId) {
        BusinessProfile businessProfile = businessProfileRepository.findById(businessProfileId).orElseThrow(() ->
                new EntityNotFoundException("Not found business profile"));

        return entityMapper.mapBusinessProfileToBusinessProfileDTO(businessProfile);
    }

    private List<BusinessDTO> findBusinessByStatus(VerificationStatus status) {
        List<BusinessProfile> businessProfileList = businessProfileRepository.findByStatus(status);
        if (businessProfileList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }
        return entityMapper.mapBusinessVerificationListToBusinessListDTO(businessProfileList);
    }

    public void submitBusinessForVerification(BusinessVerificationRequest businessVerificationRequest, Principal principal) {
        String email = principal.getName();

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setBusinessName(businessVerificationRequest.getBusinessName());
        businessProfile.setBusinessAddress(businessVerificationRequest.getBusinessAddress());
        businessProfile.setCity(businessVerificationRequest.getCity());
        businessProfile.setPhoneNumber(businessVerificationRequest.getPhoneNumber());
        businessProfile.setOwner(owner);
        businessProfile.setServiceCategory(businessVerificationRequest.getServiceCategory());
        businessProfile.setTaxId(businessVerificationRequest.getTaxId());

        businessProfileRepository.save(businessProfile);
    }

    public BusinessProfileDTO findBusinessProfileByPrincipal(Principal principal) {
        String email = principal.getName();

        BusinessProfile businessProfile = businessProfileRepository.findByOwnerEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Business not found"));

        return entityMapper.mapBusinessProfileToBusinessProfileDTO(businessProfile);

    }
}
